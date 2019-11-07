package com.timortech.imagecul.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.timortech.imagecul.base.BaseService;
import com.timortech.imagecul.base.TimorException;
import com.timortech.imagecul.config.ImageConfig;
import com.timortech.imagecul.domain.Point;
import com.timortech.imagecul.domain.ProjectDomain;
import com.timortech.imagecul.domain.TaskDomain;
import com.timortech.imagecul.domain.TaskFormDomain;
import com.timortech.imagecul.entity.Image;
import com.timortech.imagecul.entity.Project;
import com.timortech.imagecul.entity.Task;
import com.timortech.imagecul.entity.TaskStatus;
import com.timortech.imagecul.entity.User;
import com.timortech.imagecul.enums.ExceptionEnum;
import com.timortech.imagecul.enums.TaskStatusEnum;
import com.timortech.imagecul.repository.TaskRepository;
import com.timortech.imagecul.service.ImageService;
import com.timortech.imagecul.service.ProjectService;
import com.timortech.imagecul.service.TaskExecService;
import com.timortech.imagecul.service.TaskService;
import com.timortech.imagecul.utils.HttpClientUtils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.FileInputStream;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
//import jxl.Cell;
//import jxl.Sheet;
//import jxl.Workbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/4
 * @time: 20:59
 * @desc：
 */
@Service
@Slf4j
public class TaskServiceImpl extends BaseService implements TaskService {

	private final TaskRepository taskRepository;

	private final ImageService imageService;

	private final ImageConfig imageConfig;

	private final TaskExecService taskExecService;

	private final ProjectService projectService;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository, ImageService imageService, ImageConfig imageConfig, TaskExecService taskExecService, ProjectService projectService) {
		this.taskRepository = taskRepository;
		this.imageService = imageService;
		this.imageConfig = imageConfig;
		this.taskExecService = taskExecService;
		this.projectService = projectService;
	}


	@Override
	@Transactional
	public void create(List<TaskDomain> taskDomains) {
		User user = getUser();
		List<Task> tasks = new ArrayList<>();
		double totalPrice = 0;
		for(TaskDomain taskDomain : taskDomains){
			Task task = new Task();
			task.setImageaId(taskDomain.getImageaId());
			task.setImagebId(taskDomain.getImagebId());
			task.setPrice(calculatePrice(taskDomain.getImageaId(),taskDomain.getImagebId()));
			totalPrice += task.getPrice();
			//如果当前总价大于用户余额，则任务创建失败
			if(totalPrice > user.getBalance()+0.0001){
				throw new TimorException(ExceptionEnum.BALANCE_NOT_ENOUGH);
			}
			tasks.add(task);
		}
		List<Task> taskResults = taskRepository.saveAll(tasks);
	}

	@Override
//	@Transactional
	public double create(TaskFormDomain taskDomain) throws IOException {
		User user = getUser();
		Task task = new Task();
		task.setImageaId(taskDomain.getImageaId());
		task.setImagebId(taskDomain.getImagebId());
		task.setPrice(calculatePrice(taskDomain.getImageaId(),taskDomain.getImagebId()));
		double price = task.getPrice();
		/*//如果价格大于用户余额，则任务创建失败
		if(price > user.getBalance()+0.0001){
			throw new TimorException(ExceptionEnum.BALANCE_NOT_ENOUGH);
		}
		else{
			//先进行扣款操作
			user.setBalance(user.getBalance()-price);
			user = userService.update(user);
		}*/
		Image image = imageService.getImage(task.getImageaId());
		task.setUserId(user.getId());
		Task taskResult = taskRepository.save(task);
		String pointFile = getPointFile(taskDomain.getPoints(),taskResult,image.getPixelX(),image.getPixelY());
		File file = new File(pointFile);
//		if(taskDomain.getPoints().size()==0){
//			file = null;
//		}
		taskExecService.execTask(taskResult,file,user,projectService.getOpenProject());
		log.info("计算中");
		return price;
	}

	@Override
	public void start(Integer taskId) {
		Task task = taskRepository.getById(taskId);
		User user = getUser();
		user.setBalance(user.getBalance()-task.getPrice());
		user = userService.update(user);
		String pointFile = imageConfig.getLocation() + File.separator + user.getUsername() +
				File.separator + "point"+File.separator+task.getId()+".txt";
		File temp = new File(pointFile);
		task.setStatus(TaskStatusEnum.WAIT.toString());
		taskRepository.save(task);
		taskExecService.execTask(task,temp,user,projectService.getOpenProject());
		log.info("计算中");
	}

	@Override
	public List<TaskDomain> qury() {
		User user = getUser();
		List<String> statusList = new ArrayList<>();
		statusList.add(TaskStatusEnum.WAIT.toString());
		String result = HttpClientUtils.httpGet(imageConfig.getQueryUrl());
		log.info("【查询结果】{}",result);
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonObject object = parser.parse(result).getAsJsonObject();
		JsonArray jsonArray = object.getAsJsonArray("data");
		List<TaskStatus> taskStatuses = new ArrayList<>();
		for(JsonElement obj : jsonArray){
			TaskStatus status = gson.fromJson(obj,TaskStatus.class);
			taskStatuses.add(status);
		}
		//获取用户所有当前WAIT状态的任务
		List<Task> tasks = taskRepository.findAllByUserIdAndStatusIn(user.getId(),statusList);
		List<Integer> taskIds = tasks.stream().map(Task::getId).collect(Collectors.toList());
		//转换列表至map
		Map<Integer,Task> taskMap = tasks.stream().collect(
				Collectors.toMap(Task::getId,task -> task)
		);
		List<TaskDomain> taskDomains = new ArrayList<>();
		int position = 0;
		long start = System.currentTimeMillis();
		for(TaskStatus status : taskStatuses){
			int taskId = status.getImgId();
			//如果队列当前任务为该用户的任务
			if(taskIds.contains(taskId)){
				TaskDomain taskDomain = new TaskDomain();
				Task task = taskMap.get(taskId);
				BeanUtils.copyProperties(task,taskDomain);
				taskDomain.setImageaUrl(imageService.getImageDomain(task.getImageaId()).getThumbnailUrl());
				taskDomain.setImagebUrl(imageService.getImageDomain(task.getImagebId()).getThumbnailUrl());
				taskDomain.setStatus(TaskStatusEnum.build(status.getState()).getStatus());
				taskDomain.setPosition(position);
				taskDomains.add(taskDomain);
				position++;
			}
		}
		long end = System.currentTimeMillis();
		log.info("【得出结果耗时】{}",end-start);
		return taskDomains;
	}

	@Override
	public void finish(Integer taskId, boolean iserror, String taskPath) {
		Task task = taskRepository.getById(taskId);
		if(iserror){
			task.setStatus(TaskStatusEnum.FAIL.toString());
			task.setReason("计算过程出错");
		}else {
			task.setStatus(TaskStatusEnum.COMPLETED.toString());
			//设置结果的路径
//			task.setResultUrl(imageConfig.getRemoteUrl()+"/"+taskPath+"/");
			task.setResultUrl(taskPath);
		}
		task.setEndTime(new Date());
		taskRepository.save(task);
	}

	@Override
	public List<TaskDomain> getResult() {
		User user = getUser();
		List<String> status = new ArrayList<>();
		status.add(TaskStatusEnum.COMPLETED.toString());
		status.add(TaskStatusEnum.FAIL.toString());
		List<Task> tasks = taskRepository.findAllByUserIdAndStatusIn(user.getId(),status);
		List<TaskDomain> taskDomains = new ArrayList<>();
		for(Task task : tasks){
			TaskDomain domain = new TaskDomain();
			BeanUtils.copyProperties(task,domain);
			domain.setImageaUrl(imageService.getImageDomain(task.getImageaId()).getOriginalUrl());
			domain.setImagebUrl(imageService.getImageDomain(task.getImagebId()).getOriginalUrl());
			//domain.setResultUrl(imageConfig.getRemoteUrl()+"/"+task.getResultUrl()+"/");
			domain.setResultU(Matrix(task.getResultUrl(),"u"));
			domain.setResultV(Matrix(task.getResultUrl(),"v"));
			domain.setResultExx(Matrix(task.getResultUrl(),"Exx"));
			domain.setResultExy(Matrix(task.getResultUrl(),"Exy"));
			domain.setResultEyy(Matrix(task.getResultUrl(),"Eyy"));
			domain.setResultEf(Matrix(task.getResultUrl(),"E1"));
			domain.setResultEs(Matrix(task.getResultUrl(),"E2"));
			taskDomains.add(domain);
		}
		return taskDomains;
	}

	@Override
	public void run() {
		Project project = projectService.getOpenProject();

		ProjectDomain projectDomain = projectService.findById(project.getId());

		TaskFormDomain taskDomain = new TaskFormDomain();

		taskDomain.setImageaId(projectDomain.getReferenceImages().get(0));
		taskDomain.setImagebId(projectDomain.getDeformedImages().get(0));
		taskDomain.setPoints(new ArrayList<>());

		try {
			create(taskDomain);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private double calculatePrice(Integer imageAId,Integer imageBId){
		Image imageA = imageService.getImage(imageAId);
		Image imageB = imageService.getImage(imageBId);
		return (imageA.getSize()+imageB.getSize())*0.00001;
	}

	public String getPointFile(List<Point> points,Task task,int x,int y) throws IOException {
		User user = getUser();
	//	User user = new User();
   //   user.setUsername("test");
		String filePath = imageConfig.getLocation() + File.separator + user.getUsername() +
				File.separator + "point";
		File temp = new File(filePath);
		if(!temp.exists()){
			temp.mkdirs();
		}
		File pointFile = new File(filePath+File.separator+task.getId()+".txt");
		if(!pointFile.exists()){
			pointFile.createNewFile();
		}
		String pointContent = "";
		for(Point point : points){
			int px = (int) (point.getX()*x);
			int py = (int) (point.getY()*y);
			pointContent = pointContent + px + " " + py + "\n";
		}
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pointFile));
		bufferedWriter.write(pointContent);
		bufferedWriter.close();
		return filePath+File.separator+task.getId()+".txt";
	}
	
	//以mat文件形式读取计算结果的矩阵数据
	public static double[][] Matrix(String taskId, String variable) {
		try {
			MatFileReader read = new MatFileReader(
					"F:/DIC_workingstation/projection_website_and_server_construction/test_results_saving/projection_saving/"+taskId+"/Results.mat"
					);
			MLArray mlArray = read.getMLArray(variable);
			MLDouble d = (MLDouble)mlArray;
			double[][] matrix = d.getArray();
			return matrix;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
//	public static String ImageToBase64(String imgURL) {
//		ByteArrayOutputStream data = new ByteArrayOutputStream();
//		try {
//			// 创建URL
//			URL url = new URL(imgURL);
//			byte[] by = new byte[1024];
//			// 创建链接
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setConnectTimeout(5000);
//			InputStream is = conn.getInputStream();
//			// 将内容读取内存中
//			int len = -1;
//			while ((len = is.read(by)) != -1) {
//				data.write(by, 0, len);
//			}
//			// 关闭流
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// 对字节数组Base64编码
//		String output = "data:image/jpg;base64," + Base64.encodeBase64String(data.toByteArray());
//		return output;
//	}
	
//  以xls文件形式读取计算结果的矩阵数据	
//	public static double[][] Matrix(String taskId) {  
//        jxl.Workbook readwb = null;  
//        // List<String> list = new ArrayList<String>(); 
//    try {
//        // 构建Workbook对象, 只读Workbook对象 直接从本地文件创建Workbook  
//        readwb = Workbook.getWorkbook(new FileInputStream(new File("F:/DIC_workingstation/projection_website_and_server_construction/test_results_saving/projection_saving/"+taskId+"/a1a.xls")));  
//        // Sheet的下标是从0开始 获取第一张Sheet表  
//        Sheet readsheet = readwb.getSheet(0);  
//        // 获取Sheet表中所包含的总列数  
//        int rsColumns = readsheet.getColumns();  
//        // 获取Sheet表中所包含的总行数  
//        int rsRows = readsheet.getRows();  
//        // 获取指定单元格的对象引用  
//        double[][] arr = (new double[rsColumns][rsRows]);  
//        for (int i = 0; i < rsColumns; i++) {  
//            for (int j = 0; j < rsRows; j++) {  
//                Cell cell = readsheet.getCell(i, j);  
//                // System.out.print(cell.getContents() + " ");  
//                // list.add(cell.getContents());  
//                String a = cell.getContents();
//                double b = Double.parseDouble(a);
//                arr[i][j] = b;  
//            }  
//        }
//        return arr;
//    } catch (Exception e) {  
//        e.printStackTrace();  
//        return null;
//    } finally {  
//        readwb.close();  
//    }     
//    }  

}

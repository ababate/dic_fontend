package com.timortech.imagecul.service.impl;

import com.timortech.imagecul.domain.Point;
import com.timortech.imagecul.entity.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: loccen
 * @project: imagecul
 * @date: 2018/9/29
 * @time: 15:58
 * @desc：
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

	@Autowired
	private TaskServiceImpl taskService;

	@Test
	public void getPointFile() throws IOException {
		List<Point> points = new ArrayList<>();
		Point point = new Point();
		point.setX(11);
		point.setY(11);
		points.add(point);
		point.setX(12);
		point.setY(12);
		points.add(point);
		point.setX(13);
		point.setY(13);
		points.add(point);
		point.setX(14);
		point.setY(14);
		points.add(point);
		Task task = new Task();
		task.setId(3);
//		taskService.getPointFile(points,task);
	}
}
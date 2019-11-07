package com.imgCul.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/10/2
 * @time: 21:22
 * @desc：
 */

public class File2Zip {
	private static final int  BUFFER_SIZE = 2 * 1024;
	public static void toZip(String srcDir, String pathName, boolean KeepDirStructure)
		throws RuntimeException{
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(new FileOutputStream(new File(pathName)));
			File sourceFile = new File(srcDir);
			compress(sourceFile,zos,sourceFile.getName(),pathName,KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) +" ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void compress(File sourceFile, ZipOutputStream zos, String name,String pathName,
                                 boolean KeepDirStructure) throws Exception{
		byte[] buf = new byte[BUFFER_SIZE];
		if(sourceFile.isFile()){
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if(KeepDirStructure){
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + "/"));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
			}else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (file.getAbsolutePath().equals(pathName)) continue;
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + "/" + file.getName(),pathName,KeepDirStructure);
					} else {
						compress(file, zos, file.getName(),pathName,KeepDirStructure);
					}
				}
			}
		}
	}
}

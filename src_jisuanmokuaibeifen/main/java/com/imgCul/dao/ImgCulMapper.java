package com.imgCul.dao;

import com.imgCul.entity.Task;
import com.imgCul.utils.Config;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: sws558
 * @project: demo
 * @date: 2018/9/4
 * @time: 18:18
 * @desc：
 */
@Mapper
public interface ImgCulMapper {

	@Insert("INSERT INTO TASK (`imgId`,`imgName1`,`imgName2`,`pointName`,`dirName`,`params`,`state`, `createTime`)"+
			"VALUES(${imgId},'${imgName1}','${imgName2}','${pointName}','${dirName}','${params}',0,NOW())")
	void insert(Long imgId,String imgName1, String imgName2,String pointName,String dirName,String params);

	@Select("SELECT * FROM TASK WHERE state = 0 or state = 2 ORDER BY createTime ASC")
	List<Task> query();

	@Select("SELECT * FROM TASK WHERE state = ${state} ORDER BY createTime ASC")
	List<Task> querysql(@Param(value="state") String state);

	@Select("SELECT * FROM TASK WHERE state = 0 ORDER BY createTime ASC limit 1")
	List<Task> freeTask();

	@Update("UPDATE TASK set state = 3 WHERE id = ${id}")
	void error(@Param(value="id") Long id);

	@Update("UPDATE TASK set state = 2 WHERE id = ${id}")
	void doing(@Param(value="id") Long id);

	//@SelectProvider(type = sqlProvider.class, method = "sql")
	@Update("UPDATE TASK set state = 1, finishTime = NOW() WHERE id = ${id}")
	void finish(@Param(value="id") Long id);
}

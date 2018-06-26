package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Course;

public interface ICourseService {
	
	List<Course> findAll() throws Exception;
	
	//通过ID查询
	Course findById(long id) throws Exception;
	
	//通过关键字查询
	List<Course> query(String Keywords) throws Exception;
	
	//保存和更新
	void saveOrupdate(Course course) throws Exception;
	
	//通过ID删除
	void deleteById(long id) throws Exception;
	
	//批量删除
	void batchDelete(List<Long> ids) throws Exception;
	
}

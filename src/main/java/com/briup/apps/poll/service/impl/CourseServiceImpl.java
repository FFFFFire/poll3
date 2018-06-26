package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Course;
import com.briup.apps.poll.bean.CourseExample;
import com.briup.apps.poll.dao.CourseMapper;
import com.briup.apps.poll.service.ICourseService;

@Service
public class CourseServiceImpl implements ICourseService{
	
	@Autowired
	private CourseMapper courseMapper;

	@Override
	//查询所有的课程信息
	public List<Course> findAll() throws Exception {
		CourseExample example = new CourseExample();
		return courseMapper.selectByExample(example);
	}

	@Override
	//通过ID查询课程信息
	public Course findById(long id) throws Exception {
		// TODO Auto-generated method stub
		return courseMapper.selectByPrimaryKey(id);
	}

	@Override
	//通过关键字查询课程信息
	public List<Course> query(String Keywords) throws Exception {
		CourseExample example = new CourseExample();
		//模板查询 添加了一个文件，name 属性包含Keywords关键字    可以用andDescription();
		example.createCriteria().andNameLike(Keywords);
		return courseMapper.selectByExample(example);
	}

	@Override
	//ID不为空时更新课程信息  ID为空时保存课程信息
	public void saveOrupdate(Course course) throws Exception {
		if(course.getId()!=null) {
			//更新
			courseMapper.updateByPrimaryKey(course);
		}else {
			//插入
			courseMapper.insert(course);
		}
		
	}

	@Override
	//通过ID删除课程信息
	public void deleteById(long id) throws Exception {
		// TODO Auto-generated method stub
		courseMapper.deleteByPrimaryKey(id);
	}

	@Override
	//批量删除课程信息
	public void batchDelete(List<Long> ids) throws Exception {
		//遍历ids中的id
		for(long id:ids) {
			courseMapper.deleteByPrimaryKey(id);
		}

	}
	
}

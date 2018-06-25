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
	public List<Course> findAll() throws Exception {
		CourseExample example = new CourseExample();
		return courseMapper.selectByExample(example);
	}

	@Override
	public Course findById(long id) throws Exception {
		// TODO Auto-generated method stub
		return courseMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Course> query(String Keywords) throws Exception {
		CourseExample example = new CourseExample();
		//模板查询 添加了一个文件，name 属性包含Keywords关键字    可以用andDescription();
		example.createCriteria().andNameLike(Keywords);
		return courseMapper.selectByExample(example);
	}

	@Override
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
	public void deleteById(long id) throws Exception {
		// TODO Auto-generated method stub
		courseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void batchDelete(List<Long> ids) throws Exception {
		//遍历ids中的id
		for(long id:ids) {
			courseMapper.deleteByPrimaryKey(id);
		}

	}
	
}

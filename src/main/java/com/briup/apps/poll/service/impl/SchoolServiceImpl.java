package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.School;
import com.briup.apps.poll.bean.SchoolExample;
import com.briup.apps.poll.dao.SchoolMapper;
import com.briup.apps.poll.service.ISchoolService;

@Service
public class SchoolServiceImpl implements ISchoolService{
	
	@Autowired
	private SchoolMapper schoolMapper;
	
	@Override
	public List<School> findAll() throws Exception {
		SchoolExample example = new SchoolExample();
		return schoolMapper.selectByExample(example);
	}

	@Override
	public School findById(long id) throws Exception {
		// TODO Auto-generated method stub
		return schoolMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<School> query(String Keywords) throws Exception {
		SchoolExample example = new SchoolExample();
		example.createCriteria().andNameLike(Keywords);
		return schoolMapper.selectByExample(example);
	}

	@Override
	public void saveOrUpdate(School school) throws Exception {
		// TODO Auto-generated method stub
		if(school.getId()!=null) {
			schoolMapper.updateByPrimaryKey(school);
		}else {
			schoolMapper.insert(school);
		}
	}

	@Override
	public void deleteById(long id) throws Exception {
		// TODO Auto-generated method stub
		schoolMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void batchDelete(List<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		for(long id:ids) {
			schoolMapper.deleteByPrimaryKey(id);
		}
	}

}

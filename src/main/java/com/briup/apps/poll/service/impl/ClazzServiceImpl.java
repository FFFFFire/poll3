package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.ClazzExample;
import com.briup.apps.poll.bean.extend.ClazzVM;
import com.briup.apps.poll.dao.ClazzMapper;
import com.briup.apps.poll.dao.extend.ClazzVMMapper;
import com.briup.apps.poll.service.IClazzService;

@Service
public class ClazzServiceImpl implements IClazzService{
	
	@Autowired
	private ClazzMapper clazzMapper;
	@Autowired
	private ClazzVMMapper clazzVMMapper;

	@Override
	public List<Clazz> findAll() throws Exception {
		ClazzExample example = new ClazzExample();
		return clazzMapper.selectByExample(example);
	}

	@Override
	public List<ClazzVM> findAllClazzVM() throws Exception {
		
		return clazzVMMapper.selectAll();
	}
	
	//保存或更新Clazz信息  如果ID为空insert   否则update  
	@Override
	public void saveOrUpdate(Clazz clazz) throws Exception {
		if(clazz.getId()==null) {
			clazzMapper.insert(clazz);
		}else {
			clazzMapper.updateByPrimaryKey(clazz);
		}
		
	}
	
	//通过ID批量删除Clazz班级信息
	@Override
	public void batchDeleteClazz(List<Long> ids) throws Exception {
		for(long id : ids) {
			clazzMapper.deleteByPrimaryKey(id);
		}
		
	}
	
	//通过ID删除Clazz信息
	@Override
	public void deleteById(Long id) throws Exception {
		clazzMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public List<Clazz> findByKeywords(String Keywords) throws Exception {
		ClazzExample example = new ClazzExample();
		example.createCriteria().andNameLike(Keywords);
		return clazzMapper.selectByExample(example);
	}

}

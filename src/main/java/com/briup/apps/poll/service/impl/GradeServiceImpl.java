package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.extend.GradeVM;
import com.briup.apps.poll.dao.extend.GradeVMMapper;
import com.briup.apps.poll.service.IGradeService;

@Service
public class GradeServiceImpl implements IGradeService{
	@Autowired
	private GradeVMMapper gradeVMMapper;

	@Override
	public List<GradeVM> selectAll() throws Exception {
		
		return gradeVMMapper.selectAll();
	}
	

}

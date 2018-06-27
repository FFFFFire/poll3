package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.extend.ClazzVM;
import com.briup.apps.poll.service.IClazzService;

import io.swagger.annotations.Api;

@Api(description="班级相关接口")
@RestController
@RequestMapping("/clazz")
public class ClazzController {
	
	@Autowired
	private IClazzService clazzService;
	@GetMapping("findAllClazz")
	public List<Clazz> findAll() throws Exception{
		return clazzService.findAll();
		
	}
	@GetMapping("findAllClazzVM")
	public List<ClazzVM> selectAll() throws Exception{
		return clazzService.findAllClazzVM();
		
	}
}

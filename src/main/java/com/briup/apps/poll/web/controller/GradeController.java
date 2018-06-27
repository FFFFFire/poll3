package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.extend.GradeVM;
import com.briup.apps.poll.service.IGradeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="年级相关接口")
@RestController
@RequestMapping("/grade")
public class GradeController {
	@Autowired
	private IGradeService gradeService;
	
	@ApiOperation("查询所有的年级信息  以及学校的信息")
	@GetMapping("findAllGradeVM")
	public List<GradeVM> selectAll() throws Exception{
		return gradeService.selectAll();
	}
}

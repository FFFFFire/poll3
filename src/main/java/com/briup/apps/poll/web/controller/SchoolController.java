package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.School;
import com.briup.apps.poll.service.impl.SchoolServiceImpl;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="学校相关接口")
@RestController
@RequestMapping("/school")
public class SchoolController {
	
	@Autowired
	private SchoolServiceImpl schoolService;
	
	@ApiOperation("查询所有的学校信息")
	@GetMapping("findAllSchool")
	public MsgResponse findAll() {
		try {
			List<School> list = schoolService.findAll();
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("通过ID查询学校信息")
	@GetMapping("findById")
	public MsgResponse findById(@RequestParam long id) {
		try {
			School school = schoolService.findById(id);
			return MsgResponse.success("success", school);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("通过关键字查询学校信息")
	@GetMapping("findByKeywords")
	public MsgResponse query(String Keywords) {
		try {
			List<School> list = schoolService.query(Keywords);
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}

	@ApiOperation("ID不为空的时候更新学校信息  ID为空则保存学校信息")
	@GetMapping("saveOrUpdate")
	public MsgResponse saveOrUpdate(School school) {
		try {
			schoolService.saveOrUpdate(school);
			return MsgResponse.success("success", school);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("通过ID删除学校信息")
	@GetMapping("deleteById")
	public MsgResponse deleteById(@RequestParam long id) {
		try {
			schoolService.deleteById(id);
			return MsgResponse.success("success", id);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("批量删除学校信息")
	@GetMapping("batchDelete")
	public MsgResponse batchDelete(@RequestParam List<Long> ids) {
		try {
			schoolService.batchDelete(ids);
			return MsgResponse.success("success", ids);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
}

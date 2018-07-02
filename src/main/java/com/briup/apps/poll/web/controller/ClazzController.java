package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.extend.ClazzVM;
import com.briup.apps.poll.service.IClazzService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	
	@ApiOperation(value="查询所有的班级以及学校信息年级信息")
	@GetMapping("findAllClazzVM")
	public List<ClazzVM> selectAll() throws Exception{
		return clazzService.findAllClazzVM();
	}
	
	@ApiOperation(value="通过关键字查询班级信息",notes="单表")
	@GetMapping("findByKeywords")
	public MsgResponse findByKeywords(@RequestParam String Keywords){
		try {
			List<Clazz> list = clazzService.findByKeywords(Keywords);
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}

		
	}
	
	@ApiOperation(value="添加或更新班级信息")
	@PostMapping("saveOrUpdate")
	public  MsgResponse saveOrUpdate(Clazz clazz) throws Exception{
		try {
			clazzService.saveOrUpdate(clazz);
			return MsgResponse.success("success", clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="批量删除班级信息")
	@GetMapping("batchDeleteClazz")
	public  MsgResponse batchDeleteClazz(@RequestParam List<Long> ids) throws Exception{
		try {
			clazzService.batchDeleteClazz(ids);
			return MsgResponse.success("批量删除成功", ids);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="通过ID删除班级信息")
	@GetMapping("deleteById")
	public  MsgResponse deleteById(@RequestParam Long id) throws Exception{
		try {
			clazzService.deleteById(id);
			return MsgResponse.success("删除成功", id);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
}

package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Question;
import com.briup.apps.poll.bean.extend.QuestionVM;
import com.briup.apps.poll.service.IQuestionService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="问题相关接口")
@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	private IQuestionService questionService;
	
	
	@GetMapping("findAllQuestion")
	public MsgResponse findAll() throws Exception{
		List<Question> list = questionService.findAll();
		
		return MsgResponse.success("success", list);
		
	}
	
	@ApiOperation(value="查询所有的问题",notes="查询问题的时候会显示选项")
	@GetMapping("findAllQuestionVM")
	public MsgResponse findAllQuestionVM() throws Exception{
		try {
			List<QuestionVM> list = questionService.findAllQuestionVM();
			// 返回成功信息
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			return MsgResponse.error(e.getMessage()) ;
		}
	}
	
	@ApiOperation(value="查询所有的问题",notes="单表")
	@GetMapping("findByKeywords")
	public MsgResponse findByKeywords(String Keywords) throws Exception{
		try {
			List<Question> list = questionService.findByKeywords(Keywords);
			// 返回成功信息
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回失败信息
			return MsgResponse.error(e.getMessage()) ;
		}
	}
	
	
	@ApiOperation(value="保存或修改问题")
	@PostMapping("saveOrUpdateQuestion")
	public MsgResponse saveOrUpdate(QuestionVM questionVM) {
		try {
			questionService.saveOrUpdateQuestionVM(questionVM);
			return MsgResponse.success("success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="单个删除问题",notes="删除题目的同时会把题目下所有的选项删除")
	@GetMapping("deleteById")
	public MsgResponse deleteById(@RequestParam long id) throws Exception{
		try {
			questionService.deleteById(id);
			return MsgResponse.success("success", id);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="批量删除问题")
	@GetMapping("batchDelete")
	public MsgResponse batchDelete(@RequestParam List<Long> ids) {
		try {
			questionService.batchDelete(ids);
			return MsgResponse.success("success", ids);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
}
















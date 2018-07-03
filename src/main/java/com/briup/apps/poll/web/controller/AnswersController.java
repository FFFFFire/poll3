package com.briup.apps.poll.web.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.service.IAnswersService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="答卷相关接口")
@RestController
@MapperScan("/answers")
public class AnswersController {
	@Autowired
	private IAnswersService answersService;
	
	@ApiOperation(value="删除问卷主观题",notes="单选题和多选题不受影响")
	@GetMapping("deleteAnswerContent")
	public MsgResponse deleteAnswer(@RequestParam long id) {
		try {
			//通过ID找到答卷
			Answers answer = answersService.findById(id);
			answer.setContent("");
			answersService.saveOrUpdate(answer);
			return MsgResponse.success("success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="修改问卷主观题")
	@GetMapping("updateAnswerContent")
	public MsgResponse updateAnswerContent(@RequestParam long id, String content) {
		try {
			//通过ID找到答卷
			Answers answer = answersService.findById(id);
			// 设置答卷内容为content
			answer.setContent(content);
			answersService.saveOrUpdate(answer);
			return MsgResponse.success("修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
		
	}
}

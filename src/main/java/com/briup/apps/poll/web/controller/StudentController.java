package com.briup.apps.poll.web.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.service.IAnswersService;
import com.briup.apps.poll.service.ISurveyService;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="学生相关接口")
@RestController
@MapperScan("/student")
public class StudentController {
	@Autowired
	private ISurveyService surveyService;
	@Autowired
	private IAnswersService answersService;
	
	@ApiOperation("登录课调")
	@GetMapping("loginSurvey")
	public MsgResponse loginSurvey(long id) {
		try {
			//1.通过ID查询课调
			SurveyVM surveyVM = surveyService.findById(id);
			//2.如果课调存在，且状态为“开启”才能返回课调信息，否则返回错误信息
			if(surveyVM!=null && surveyVM.getStatus().equals(Survey.STATUS_BEGIN)) {
				return MsgResponse.success("success", null);
			}else {
				return MsgResponse.error("课调状态不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("提交答卷")
	@GetMapping("submitSurvey")
	public MsgResponse submitSurvey(Answers answers) {
		try {
			answersService.saveOrUpdate(answers);
			return MsgResponse.success("提交成功！", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
}

package com.briup.apps.poll.web.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.bean.Survey;
import com.briup.apps.poll.bean.extend.SurveyVM;
import com.briup.apps.poll.service.IAnswersService;
import com.briup.apps.poll.service.ISurveyService;
import com.briup.apps.poll.util.MsgResponse;
import com.briup.apps.poll.vm.SurveyAndAnswersVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="课调相关接口")
@RestController
@MapperScan("/survey")
public class SurveyController {
	
	@Autowired
	private ISurveyService surveyService;
	@Autowired
	private IAnswersService answersService;
	
	@ApiOperation(value="保存或更新课调", 
			notes="如果参数中包含id表示修改，否则表示添加。只需要接收clazzId,courseId,teacherId,questionnaireId")
	@PostMapping("saveOrUpdateSurvey")
	public MsgResponse saveOrUpdateSurvey(Survey survey) {
		try {
			surveyService.saveOrUpdate(survey);
			return MsgResponse.success("success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="级联查询所有课调", 
			notes="级联查询课程中的课程，班级，用户，问卷")
	@PostMapping("findAllSurveyVM")
	public MsgResponse findAllSurveyVM() {
		try {
			List<SurveyVM> list = surveyService.findAll();
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="通过ID删除课调", 
			notes="级联课调下的答卷信息")
	@GetMapping("deleteSurveyById")
	public MsgResponse deleteSurveyById(long id) {
		try {
			surveyService.deleteById(id);
			return MsgResponse.success("success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="delete",notes="外键")
	@GetMapping("batchDeleteSurvey")
	public MsgResponse batchDeleteSurvey(long[] ids) {
		try {
			surveyService.batchDelete(ids);
			return MsgResponse.success("success", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="find",notes="外键")
	@GetMapping("findSurveyById")
	public MsgResponse findSurveyById(long id) {
		try {
			SurveyVM list = surveyService.findById(id);
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="开启课调",notes="外键")
	@GetMapping("beginSurvey")
	public MsgResponse beginSurvey(@RequestParam long id){
		//1.查询课调

		try {
			Survey survey = surveyService.findSurveyById(id);
			//2.修改课调的状态。
			if(survey.getStatus().equals(Survey.STATUS_INIT)) {
				//课调状态改为开启
				survey.setStatus(Survey.STATUS_BEGIN);
				//code改为当前课调的ID
				survey.setCode(survey.getId().toString());
				//执行保存或更新
				surveyService.saveOrUpdate(survey);
				return MsgResponse.success("开启成功", null);
			}else {
				return MsgResponse.error("当前课调不是未开启状态");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="去审核课调",notes="返回课调的信息以及所有答卷的信息")
	@GetMapping("toCheckSurvey")
	public MsgResponse toCheckSurvey(long id) {
		try {
			//1.通过ID查询课调信息
			SurveyVM surveyVM = surveyService.findById(id);
			//2.如果课调状态为未审核才能审核
			if(surveyVM.getStatus().equals(Survey.STATUS_CHECK_UN)) {
				//查询出该课调写所有答卷
				List<Answers> list = answersService.findAnswersBySurveyId(id);
				//查询平均分
				//单个平均分的总和
				double total = 0;
				for(Answers answer:list) {
					//["5","3"]
					String[] arr = answer.getSelections().split("[|]");
					double singletotal = 0;
					for(String a:arr) {
						singletotal += Integer.parseInt(a);
					}
					//单个学生对于老师的平均分
					double singleAverage = singletotal/arr.length;
					total += singleAverage;
				}
				double average = total/list.size();
				surveyVM.setAverage(average);
				//将平均分保存到数据库中
				Survey survey = surveyService.findSurveyById(id);
				
				//如果数据库中的平均分没有设定，我们再进行设定，否则不做操作
				if(survey.getAverage() == null){
					survey.setAverage(average);
					surveyService.saveOrUpdate(survey);
				}
				
				//如何将surveyVM 和list 返回,封装到一个对象中
				SurveyAndAnswersVM savm = new SurveyAndAnswersVM();
				savm.setSurveyVM(surveyVM);
				savm.setAnswers(list);
				
				return MsgResponse.success("success", savm);
			}else {
				return MsgResponse.error("课调状态不合法");
			}
			//
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="关闭课调",notes="只有在课调状态为开启的时候才能关闭课调")
	@GetMapping("stopSurvey")
	public MsgResponse stopSurvey(long id) {
		try {
			//1.通过ID查询课调
			Survey survey = surveyService.findSurveyById(id);
			//2.修改课调的状态
			if(survey.getStatus().equals(Survey.STATUS_BEGIN)) {
				//2.1课调状态改为未审核
				survey.setStatus(Survey.STATUS_CHECK_UN);
				survey.setCode(survey.getId().toString());
				//执行保存或更新
				surveyService.saveOrUpdate(survey);
				return MsgResponse.success("关闭成功", null);
			}else {
				return MsgResponse.error("当前课调状态必须为未开启状态");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	
	@ApiOperation(value="审核课调",notes="只有当前课调状态为未审核时才能审核   status只能取1/0  如果是0表示不通过")
	@GetMapping("checkSurvey")
	public MsgResponse checkSurvey(long id,int status) {
		try {
			//1.通过ID查找课调
			Survey survey = surveyService.findSurveyById(id);
			//2.判断当前状态
			if(survey!=null&&survey.getStatus().equals(Survey.STATUS_CHECK_UN)) {
				if(status == 0) {
					//2.1status=0 审核不通过
					survey.setStatus(Survey.STATUS_CHECK_NOPASS);
				}else {
					//2.2 status = 1 审核通过
					survey.setStatus(Survey.STATUS_CHECK_PASS);
				}
			}else {
				return MsgResponse.error("状态不合法");
			}
			surveyService.saveOrUpdate(survey);
			return MsgResponse.success("审核完成", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation(value="预览课调",notes="只有当前课调状态为审核通过时才能预览   ")
	@GetMapping("previewSurvey")
	public MsgResponse previewSurvey(long id) {
		try {
			//1.课调的信息（SurveyVM）  
			SurveyVM surveyVM = surveyService.findById(id);
			if(surveyVM!=null && surveyVM.getStatus().equals(Survey.STATUS_CHECK_PASS)) {
				//2.课调的结果 主管题
				List<Answers> answers = answersService.findAnswersBySurveyId(id);
				//如 返回,封装到一个对象中
				SurveyAndAnswersVM savm = new SurveyAndAnswersVM();
				savm.setSurveyVM(surveyVM);
				savm.setAnswers(answers);
				//3.SurveyAndAnswersVM
				return MsgResponse.success("chengg ", savm);
			}else {
				return MsgResponse.error("课调状态不合法");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
}






















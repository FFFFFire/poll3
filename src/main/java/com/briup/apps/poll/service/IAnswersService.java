package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Answers;

public interface IAnswersService {
	
	void saveOrUpdate (Answers answer) throws Exception;
	
	//通过ID查询
	List<Answers> findAnswersBySurveyId(long id) throws Exception;
	
	Answers findById(long id) throws Exception;
}

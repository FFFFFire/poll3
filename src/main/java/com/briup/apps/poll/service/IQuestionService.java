package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Question;
import com.briup.apps.poll.bean.extend.QuestionVM;

public interface IQuestionService {
	
	List<Question> findAll() throws Exception;
	
	List<QuestionVM> findAllQuestionVM() throws Exception;
	
	List<Question> findByKeywords(String Keywords) throws Exception;
	
	void saveOrUpdateQuestionVM(QuestionVM questionVM) throws Exception;
	
	void deleteById(long id) throws Exception;
	
	//批量删除
	void batchDelete(List<Long> ids) throws Exception;
}

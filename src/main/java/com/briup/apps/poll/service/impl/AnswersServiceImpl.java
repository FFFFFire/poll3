package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Answers;
import com.briup.apps.poll.bean.AnswersExample;
import com.briup.apps.poll.dao.AnswersMapper;
import com.briup.apps.poll.service.IAnswersService;

@Service
public class AnswersServiceImpl implements IAnswersService{
	
	@Autowired
	private AnswersMapper answersMapper;
	
	@Override
	public void saveOrUpdate(Answers answers) {
		if(answers.getId()==null) {
			answersMapper.insert(answers);
		}else {
			answersMapper.updateByPrimaryKeySelective(answers);
		}
		
	}

	@Override
	public List<Answers> findAnswersBySurveyId(long id) throws Exception {
		AnswersExample example = new AnswersExample();
		example.createCriteria().andSurveyIdEqualTo(id);
		return answersMapper.selectByExample(example);
	}

}

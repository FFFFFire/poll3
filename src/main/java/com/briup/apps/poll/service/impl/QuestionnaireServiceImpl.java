package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.apps.poll.bean.Questionnaire;
import com.briup.apps.poll.bean.QuestionnaireExample;
import com.briup.apps.poll.bean.QuestionnaireQuestion;
import com.briup.apps.poll.bean.QuestionnaireQuestionExample;
import com.briup.apps.poll.bean.extend.QuestionnaireVM;
import com.briup.apps.poll.dao.QuestionnaireMapper;
import com.briup.apps.poll.dao.QuestionnaireQuestionMapper;
import com.briup.apps.poll.dao.extend.QuestionnaireVMMapper;
import com.briup.apps.poll.service.IQuestionnaireService;

@Service
public class QuestionnaireServiceImpl implements IQuestionnaireService{
	@Autowired
	private QuestionnaireMapper qnMapper;
	@Autowired
	private QuestionnaireVMMapper qnVMMapper;
	@Autowired
	private QuestionnaireQuestionMapper qqMapper;

	@Override
	public List<Questionnaire> findAll() throws Exception {
		
		QuestionnaireExample example = new QuestionnaireExample();
		return qnMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public QuestionnaireVM findById(long id) throws Exception {
		
		return qnVMMapper.selectById(id);
	}

	@Override
	public void saveOrUpdate(Questionnaire questionnaire, long[] questionIds) throws Exception {
		//1.保存还是更新
		if(questionnaire.getId()==null) {
			//1.1.1先保存问卷信息（id=null）
			qnMapper.insert(questionnaire);
			long questionnaireId = questionnaire.getId();
			//1.1.2维护问卷和问题的信息
			for(long questionId:questionIds) {
				QuestionnaireQuestion qq = new QuestionnaireQuestion();
				qq.setQuestionId(questionId);
				qq.setQuestionnaireId(questionnaireId);
				qqMapper.insert(qq);
			}
		}else {
			//1.2.1修改问卷信息
			qnMapper.updateByPrimaryKey(questionnaire);
			long questionnaireId = questionnaire.getId();
			//1.2.2删除问卷下所有的问卷关系
			//delete from poll_qq where questionnaire_id = #{id}
			QuestionnaireQuestionExample example = new QuestionnaireQuestionExample();
			example.createCriteria().andQuestionnaireIdEqualTo(questionnaireId);
			qqMapper.deleteByExample(example);
			//1.2.3保存新的问卷问题关系
			for(long questionId:questionIds) {
				QuestionnaireQuestion qq = new QuestionnaireQuestion();
				qq.setQuestionId(questionId);
				qq.setQuestionnaireId(questionnaireId);
				qqMapper.insert(qq);
			}
		}
		
	}

	@Override
	public void deleteById(long id) throws Exception {
		qnMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void batchDelete(List<Long> ids) throws Exception {
		for(long id : ids) {
			qnMapper.deleteByPrimaryKey(id);
		}
		
	}

}

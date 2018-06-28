package com.briup.apps.poll.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.briup.apps.poll.bean.Options;
import com.briup.apps.poll.bean.OptionsExample;
import com.briup.apps.poll.bean.Question;
import com.briup.apps.poll.bean.QuestionExample;
import com.briup.apps.poll.bean.extend.QuestionVM;
import com.briup.apps.poll.dao.OptionsMapper;
import com.briup.apps.poll.dao.QuestionMapper;
import com.briup.apps.poll.dao.extend.QuestionVMMapper;
import com.briup.apps.poll.service.IQuestionService;

@Service
public class QuestionServiceImpl implements IQuestionService{
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionVMMapper questionVMMapper;
	@Autowired
	private OptionsMapper optionsMapper;
	
	@Override
	public List<Question> findAll() throws Exception {
		QuestionExample example = new QuestionExample();
		return questionMapper.selectByExample(example);
	}
	
	@Override
	public List<QuestionVM> findAllQuestionVM() throws Exception {
		return questionVMMapper.selectAll();
	}
	
//	@Override
//	public List<QuestionVM> findByKeywords(String Keywords) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	/**
	 * 保存或修改  （包含选项）
	 */
	@Override
	public void saveOrUpdateQuestionVM(QuestionVM questionVM) throws Exception {
		// 0.分离questionVM  获取Question Options
		List<Options> options = questionVM.getOptions();
		
		Question question = new Question();
		question.setId(questionVM.getId());
		question.setName(questionVM.getName());
		question.setQuestiontype(questionVM.getQuestionType());
		
		
		// 1.判断保存还是修改
		// 2.判断是否是简答
		if(question.getId() == null) {
			//2.1保存
			if(question.getQuestiontype().equals("简答题")) {
				//2.1.1保存题目相关信息
				questionMapper.insert(question);
			}else {
				//2.1.2保存单选和多选的时候要先保存题目信息，再保存选项信息
				questionMapper.insert(question);
				//获取question的ID保存到option中
				long questionId = question.getId();
					for(Options option : options) {
						option.setQuestionId(questionId);
						optionsMapper.insert(option);
					}
			}
		}else {
			//2.2修改
			//2.2.1修改题目信息
			questionMapper.updateByPrimaryKey(question);
			//2.2.2修改选项问题（添加新选项，删除旧选项，修改原来的选项）
			//1.删除改题目原有选项
			OptionsExample example = new OptionsExample();
			example.createCriteria().andQuestionIdEqualTo(question.getId());
			optionsMapper.deleteByExample(example);
			//2.重新添加选项
			long questionId = question.getId();
				for(Options option : options) {
					option.setQuestionId(questionId);
					optionsMapper.insert(option);
				}	
		}
		
		/**
		 * 保存：（单表保存）
		 * 1.保存问题
		 * 2.保存选项
		 */
		
	}
	@Override
	public void deleteById(@RequestParam long id) throws Exception {
		questionMapper.deleteByPrimaryKey(id);
		
	}
	@Override
	public void batchDelete(@RequestParam List<Long> ids) throws Exception {
		for(long id:ids) {
			questionMapper.deleteByPrimaryKey(id);
		}
	}

	
}





















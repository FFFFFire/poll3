package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.Clazz;
import com.briup.apps.poll.bean.extend.ClazzVM;

public interface IClazzService {
	
	List<Clazz> findAll() throws Exception;
	
	List<ClazzVM> findAllClazzVM() throws Exception;
	
	List<Clazz> findByKeywords(String Keywords) throws Exception;
	
	void saveOrUpdate(Clazz clazz) throws Exception;
	
	void batchDeleteClazz(List<Long> ids) throws Exception;
	
	void deleteById(Long id) throws Exception;
}

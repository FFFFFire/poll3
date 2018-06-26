package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.User;

public interface IUserService {
	
	List<User> findAll() throws Exception;
	
	//通过ID查询用户
	User findById(long id) throws Exception;
	
	//通过关键字查询用户
	List<User> query(String Keywords) throws Exception;
	
	//通过ID删除用户
	void deleteById(long id) throws Exception;
	
	//批量删除用户
	void batchDelte(List<Long> ids) throws Exception;
	
	//保存或添加用户
	void saveOrUpdat(User user) throws Exception;
}

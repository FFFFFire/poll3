package com.briup.apps.poll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.apps.poll.bean.User;
import com.briup.apps.poll.service.impl.UserServiceImpl;
import com.briup.apps.poll.util.MsgResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@ApiOperation("查询所有的用户信息")
	@GetMapping("findAllUser")
	public MsgResponse findAll() {
		try {
			List<User> list = userService.findAll(); 
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("通过ID查询用户信息")
	@GetMapping("findById")
	public MsgResponse findById(long id) {
		try {
			User user = userService.findById(id);
			return MsgResponse.success("success", user);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("通过关键字查询用户信息")
	@GetMapping("findByKeywords")
	public MsgResponse query(String Keywords) {
		try {
			List<User> list = userService.query(Keywords);
			return MsgResponse.success("success", list);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}

	@ApiOperation("通过ID删除用户信息")
	@GetMapping("deleteById")
	public MsgResponse deleteById(@RequestParam long id) {
		try {
			userService.deleteById(id);
			return MsgResponse.success("success", id);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("批量删除用户信息")
	@GetMapping("deleteBatch")
	public MsgResponse batchDelete(@RequestParam List<Long> ids) {
		try {
			userService.batchDelte(ids);
			return MsgResponse.success("success", ids);
		} catch (Exception e) {
			e.printStackTrace();
			return MsgResponse.error(e.getMessage());
		}
	}
	
	@ApiOperation("更新或添加用户信息")
	@GetMapping("saveOrUpdate")
	public MsgResponse saveOrUpdate(User user) {
		try {
			userService.saveOrUpdat(user);
			return MsgResponse.success("success", user);
		} catch (Exception e) {
			return MsgResponse.error(e.getMessage());
		}
	} 
}


























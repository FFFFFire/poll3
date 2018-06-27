package com.briup.apps.poll.service;

import java.util.List;

import com.briup.apps.poll.bean.extend.GradeVM;

public interface IGradeService {
	List<GradeVM> selectAll() throws Exception;
}

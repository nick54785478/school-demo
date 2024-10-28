package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.service.TeacherService;
import com.example.demo.domain.share.TeacherCreated;
import com.example.demo.domain.teacher.command.CreateTeacherCommand;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
public class TeacherCommandService {

	@Autowired
	private TeacherService teacherService;

	/**
	 * 新增一筆老師資料
	 * 
	 * @param command
	 */
	public TeacherCreated create(CreateTeacherCommand command) {
		// 檢查是否衝堂
		teacherService.checkScheduleConflict(command.getCourses());
		
		return teacherService.create(command);
	}
}

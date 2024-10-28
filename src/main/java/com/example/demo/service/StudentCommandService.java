package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.service.StudentService;
import com.example.demo.domain.share.StudentCreated;
import com.example.demo.domain.student.command.CreateStudentCommand;
import com.example.demo.domain.student.command.UploadStudentCommand;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
public class StudentCommandService {

	@Autowired
	private StudentService studentService;

	/**
	 * 新增一筆學生資料
	 * 
	 * @param command
	 * @return StudentCreated
	 */
	public StudentCreated create(CreateStudentCommand command) {
		return studentService.create(command);
	}

	/**
	 * 上傳資料
	 * 
	 * @param command
	 */
	public void upload(UploadStudentCommand command) {
		studentService.upload(command);
	}

}

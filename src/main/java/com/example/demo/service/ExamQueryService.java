package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.ExamService;
import com.example.demo.domain.share.ExamQueried;
import com.example.demo.domain.share.StudentExamQueried;
import com.example.demo.domain.share.command.QueryExamCommand;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExamQueryService {

	private ExamService exampService;

	/**
	 * 查詢該班學生成績
	 * 
	 * @param subject  科目
	 * @param stuClass 班級(如:一年A班)
	 * @param examDate 考試日期
	 */
	public List<ExamQueried> queryExamByClass(String subject, String stuClass, String examDate) {
		return exampService.queryExamByClass(stuClass, subject, examDate);
	}
	
	/**
	 * 根據條件去查詢學生考試資訊
	 * 
	 * @param command
	 * */
	public List<StudentExamQueried> query(QueryExamCommand command) {
		return exampService.query(command);
	}
}

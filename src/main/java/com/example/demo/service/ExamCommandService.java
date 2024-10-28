package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.exam.command.CreateExamCommand;
import com.example.demo.domain.report.command.DownloadPersonalTranscriptCommand;
import com.example.demo.domain.report.command.DownloadTranscriptCommand;
import com.example.demo.domain.service.ExamService;
import com.example.demo.domain.share.ExamQueried;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
public class ExamCommandService {

	private ExamService examService;

	/**
	 * 建立考試資料
	 */
	public void create(List<CreateExamCommand> commands) {
		examService.create(commands);
	}

	/**
	 * 下載班級成績單
	 * 
	 * @param command
	 */
	public Map<String, Object> downloadTranscript(DownloadTranscriptCommand command) {
		// 取得成績資料
		List<ExamQueried> examList = examService.queryExamByClass(command.getStuClass(), command.getSubject(),
				command.getExamDate());
		return examService.downloadGroupTranscript(command, examList);
	}

	/**
	 * 下載個人成績單
	 * 
	 * @param command
	 */
	public Map<String, Object> downloadTranscript(DownloadPersonalTranscriptCommand command, String studentId) {
		return examService.downloadTranscript(command, studentId);
	}

}

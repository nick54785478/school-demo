package com.example.demo.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.report.command.UploadReportCommand;
import com.example.demo.domain.service.ReportUploadService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
public class ReportUploadCommandService {

	@Autowired
	private ReportUploadService reportUploadService;
	
	/**
	 * 上傳文件至 Mongo DB 儲存
	 * 
	 * @param fileName 檔名
	 * @param inputStream 資料流
	 * */
	public void uploadReport(UploadReportCommand command, InputStream inputStream) {
		reportUploadService.upload(command, inputStream);
	}
	
	
	
	
}

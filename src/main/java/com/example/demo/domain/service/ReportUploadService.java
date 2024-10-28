package com.example.demo.domain.service;

import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.report.aggregate.ReportSetting;
import com.example.demo.domain.report.command.UploadReportCommand;
import com.example.demo.infra.repository.ReportSettingRepository;
import com.example.demo.infra.store.GridFSService;

@Service
public class ReportUploadService {

	@Autowired
	private GridFSService gridFSService;
	@Autowired
	private ReportSettingRepository reportSettingRepository;
	
	/**
	 * 上傳文件至 Mongo DB 儲存
	 * 
	 * @param command 
	 * @param inputStream 資料流
	 * */
	public void upload(UploadReportCommand command, InputStream inputStream) {
		ObjectId objectId = gridFSService.uploadFile(command.getFileName(), inputStream);
		
		// 紀錄 ReportSetting
		ReportSetting reportSetting = new ReportSetting();
		reportSetting.create(command, objectId.toHexString());
		reportSettingRepository.save(reportSetting);
	}
}

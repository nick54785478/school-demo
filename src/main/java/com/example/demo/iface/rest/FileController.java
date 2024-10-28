package com.example.demo.iface.rest;

import java.io.IOException;

import org.jfree.util.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.report.command.UploadReportCommand;
import com.example.demo.exception.ValidationException;
import com.example.demo.iface.dto.ReportUploadedResource;
import com.example.demo.iface.dto.UploadReportResource;
import com.example.demo.service.ReportUploadCommandService;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/report")
public class FileController {

	private ReportUploadCommandService reportUploadCommandService;

	/**
	 * 上傳 Jasper 範本
	 * 
	 * @param requestData
	 * @param file
	 * @return ResponseEntity<ReportUploadedResource>
	 */
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ReportUploadedResource> upload(
			@RequestPart(name = "requestData", required = true) UploadReportResource resource,
			@RequestParam("file") MultipartFile file) {

		if (file == null) {
			Log.error("檔案有問題，上傳失敗");
			throw new ValidationException("VALIDATE_FAILED", "檔案有問題，上傳失敗");
		}
		try {
			UploadReportCommand command = BaseDataTransformer.transformData(resource, UploadReportCommand.class);
			reportUploadCommandService.uploadReport(command, file.getInputStream());
		} catch (IOException e) {
			Log.error("發生錯誤，上傳失敗", e);
		}
		return new ResponseEntity<>(new ReportUploadedResource(200, "上傳成功"), HttpStatus.OK);
	}
	
}

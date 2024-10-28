package com.example.demo.iface.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.exam.command.CreateExamCommand;
import com.example.demo.domain.report.command.DownloadPersonalTranscriptCommand;
import com.example.demo.domain.report.command.DownloadTranscriptCommand;
import com.example.demo.domain.share.command.QueryExamCommand;
import com.example.demo.iface.dto.BaseResponseResource;
import com.example.demo.iface.dto.CreateExamResource;
import com.example.demo.iface.dto.DownloadPersonalTrascriptResource;
import com.example.demo.iface.dto.DownloadTrascriptResource;
import com.example.demo.iface.dto.ExamQueriedResource;
import com.example.demo.iface.dto.QueryExamResource;
import com.example.demo.iface.dto.StudentExamQueriedResource;
import com.example.demo.service.ExamCommandService;
import com.example.demo.service.ExamQueryService;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamController {

	private ExamQueryService examQueryService;
	private ExamCommandService examCommandService;

	/**
	 * 新增一筆學生應試資料
	 * 
	 * @param resource
	 * @return ResponseEntity<StudentCreatedResource>
	 */
	@PostMapping("")
	public ResponseEntity<BaseResponseResource> create(@RequestBody List<CreateExamResource> resources) {
		List<CreateExamCommand> commands = BaseDataTransformer.transformData(resources, CreateExamCommand.class);
		examCommandService.create(commands);
		return new ResponseEntity<>(new BaseResponseResource("新增學生應試資料成功"), HttpStatus.CREATED);
	}

	/**
	 * 查詢班級考試成績
	 * 
	 * @param stuClass 班級
	 * @return ResponseEntity<List<TranscriptQueriedResource>>
	 */
	@GetMapping(value = "/queryExam")
	public ResponseEntity<List<ExamQueriedResource>> query(@RequestParam String stuClass, @RequestParam String examDate,
			@RequestParam String subject) {
		return new ResponseEntity<>(
				BaseDataTransformer.transformData(examQueryService.queryExamByClass(subject, stuClass, examDate),
						ExamQueriedResource.class),
				HttpStatus.OK);
	}

	/**
	 * 根據條件進行查詢學生考試資料
	 * 
	 * @param resource
	 * @return ResponseEntity<List<ExamQueriedResource>> 
	 */
	@PostMapping(value = "/query")
	public ResponseEntity<List<StudentExamQueriedResource>> query(@RequestBody QueryExamResource resource) {
		QueryExamCommand command = BaseDataTransformer.transformData(resource, QueryExamCommand.class);
		return new ResponseEntity<>(
				BaseDataTransformer.transformData(examQueryService.query(command), StudentExamQueriedResource.class),
				HttpStatus.OK);
	}

	/**
	 * 下載班級成績單
	 * 
	 * @param stuClass 班級
	 * @return ResponseEntity<Resource>
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping(value = "/downloadTranscript", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> download(@RequestBody DownloadTrascriptResource resource)
			throws UnsupportedEncodingException {
		DownloadTranscriptCommand command = BaseDataTransformer.transformData(resource,
				DownloadTranscriptCommand.class);

		// 建立文件
		Map<String, Object> result = examCommandService.downloadTranscript(command);

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
				+ URLEncoder.encode(result.getOrDefault("filename", "unknown").toString(), "UTF-8"));
		return new ResponseEntity<>((Resource) result.get("resource"), respHeaders, HttpStatus.OK);
	}

	/**
	 * 下載個人成績單
	 * 
	 * @param resource
	 * @param studentId
	 * @return ResponseEntity<Resource>
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping(value = "/downloadTranscript/{studentId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadPersonalTranscript(
			@PathVariable String studentId,
			@RequestBody DownloadPersonalTrascriptResource resource)
			throws UnsupportedEncodingException {
		
		DownloadPersonalTranscriptCommand command = BaseDataTransformer.transformData(resource,
				DownloadPersonalTranscriptCommand.class);

		// 建立文件
		Map<String, Object> result = examCommandService.downloadTranscript(command, studentId);

		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
				+ URLEncoder.encode(result.getOrDefault("filename", "unknown").toString(), "UTF-8"));
		return new ResponseEntity<>((Resource) result.get("resource"), respHeaders, HttpStatus.OK);
	}
}

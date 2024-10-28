package com.example.demo.iface.rest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.share.StudentQueried;
import com.example.demo.domain.student.command.CreateStudentCommand;
import com.example.demo.domain.student.command.UploadStudentCommand;
import com.example.demo.exception.ValidationException;
import com.example.demo.iface.dto.BaseResponseResource;
import com.example.demo.iface.dto.CreateStudentResource;
import com.example.demo.iface.dto.StudentCreatedResource;
import com.example.demo.iface.dto.StudentQueriedResource;
import com.example.demo.service.StudentCommandService;
import com.example.demo.service.StudentQueryService;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

	private StudentQueryService studentQueryService;
	private StudentCommandService studentCommandService;

	/**
	 * 新增一筆學生資料
	 * 
	 * @param resource
	 * @return ResponseEntity<StudentCreatedResource>
	 */
	@PostMapping("")
	public ResponseEntity<StudentCreatedResource> create(@RequestBody CreateStudentResource resource) {
		CreateStudentCommand command = BaseDataTransformer.transformData(resource, CreateStudentCommand.class);
		return new ResponseEntity<>(
				BaseDataTransformer.transformData(studentCommandService.create(command), StudentCreatedResource.class),
				HttpStatus.CREATED);
	}

	/**
	 * 查詢該班的所有學生
	 * 
	 * @param className 班級，一年A班
	 * @return List<StudentQueriedResource>
	 * */
	@GetMapping("/queryByClass")
	public ResponseEntity<List<StudentQueriedResource>> queryAll(@RequestParam(name = "className") String className) {
		List<StudentQueried> studentList = studentQueryService.queryAllStudents(className);
		return new ResponseEntity<>(BaseDataTransformer.transformData(studentList, StudentQueriedResource.class),
				HttpStatus.OK);
	}

	/**
	 * 上傳資料
	 * 
	 * @param mapping Mapping 的資料表類型
	 * @param file    檔案
	 * @return ResponseEntity<BaseResponseResource>
	 */
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponseResource> upload(@RequestParam(name = "mapping") String mapping,
			@RequestPart(name = "file", required = true) MultipartFile file) {

		if (Objects.isNull(file)) {
			throw new ValidationException("VALIDATE_FAILED", "上傳檔案為空，發生錯誤");
		}

		try {
			if (mapping.endsWith(",")) {
				mapping = mapping.substring(0, mapping.length() - 1); // 去除尾端逗號
			}

			UploadStudentCommand command = new UploadStudentCommand(mapping, file.getInputStream());
			studentCommandService.upload(command);
			return new ResponseEntity<>(new BaseResponseResource("上傳成功"), HttpStatus.OK);

		} catch (IOException e) {
			log.error("發生錯誤，上傳失敗", e);
			return new ResponseEntity<>(new BaseResponseResource("發生錯誤，上傳失敗"), HttpStatus.OK);
		}

	}

}

package com.example.demo.iface.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.teacher.command.CreateTeacherCommand;
import com.example.demo.domain.teacher.command.UpdateTeacherCommand;
import com.example.demo.iface.dto.CreateTeacherResource;
import com.example.demo.iface.dto.TeacherCreatedResource;
import com.example.demo.iface.dto.TeacherUpdatedResource;
import com.example.demo.iface.dto.UpdateTeacherResource;
import com.example.demo.service.TeacherCommandService;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teacher")
public class TeacherController {

	private TeacherCommandService teacherCommandService;

	/**
	 * 新增一筆老師資料
	 * 
	 * @param resource
	 * @return ResponseEntity<TeacherCreatedResource>
	 * */
	@PostMapping("")
	public ResponseEntity<TeacherCreatedResource> create(@RequestBody CreateTeacherResource resource) {
		CreateTeacherCommand command = BaseDataTransformer.transformData(resource, CreateTeacherCommand.class);
		return new ResponseEntity<>(
				BaseDataTransformer.transformData(teacherCommandService.create(command), TeacherCreatedResource.class),
				HttpStatus.CREATED);
	}
	
	/**
	 * 更新一筆老師資料
	 * 
	 * @param resource
	 * @return ResponseEntity<TeacherUpdatedResource>
	 * */
	@PutMapping("")
	public ResponseEntity<TeacherUpdatedResource> update(@RequestBody UpdateTeacherResource resource) {
		UpdateTeacherCommand command = BaseDataTransformer.transformData(resource, UpdateTeacherCommand.class);
		return new ResponseEntity<>(
				BaseDataTransformer.transformData(teacherCommandService.update(command), TeacherUpdatedResource.class),
				HttpStatus.OK);
	}
	
}

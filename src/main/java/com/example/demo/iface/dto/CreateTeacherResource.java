package com.example.demo.iface.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherResource {

	private String name; // 老師名稱

	private String subject; // 科目

	private String leadClass; // 帶領的班級

	private String isMentor; // 是否為導師

	private List<CreateTeacherCourseResource> courses = new ArrayList<>();

}

package com.example.demo.iface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherCourseResource {

	private String name;
	
	private String stuClass;
	
	private String lesson;
	
	private String weekDay;
	
}

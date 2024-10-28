package com.example.demo.domain.teacher.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherCourseCommand {

	private String name;
	
	private String stuClass;
	
	private String lesson;
	
	private String weekDay;
}

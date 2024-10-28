package com.example.demo.domain.student.command;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadStudentCommand {

	private String mapping;
	
	private InputStream inputStream;
}

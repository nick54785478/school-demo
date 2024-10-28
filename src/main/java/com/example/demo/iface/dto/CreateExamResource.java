package com.example.demo.iface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamResource {

	private String studentId; // 學生代號

	private String stuClass; // 班級，一年C班

	private String subject; // 科目
	
	private String score;

	private String type; // 考試類型

	private String testDate; // 測驗日期
	
	private String invigilatorId; // 監試人員代號
}

package com.example.demo.domain.exam.command;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamCommand {

	private String studentId; // 學生代號

	private String stuClass; // 班級，一年C班

	private String subject; // 科目

	private BigDecimal score; // 分數

	private String type; // 考試類型

	private Date examDate; // 測驗日期
	
	private String invigilatorId; // 監試老師 ID
}

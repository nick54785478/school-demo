package com.example.demo.iface.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExamQueriedResource {

	private String studentId; // 學生代號

	private Long number; // 座號

	private String name; // 學生名稱

	private String stuClass; // 班級

	private Date examDate; // 考試時間
	
	private BigDecimal score; // 分數
	
	private String subject;	// 科目
	
	private String invigilator; // 監試

}

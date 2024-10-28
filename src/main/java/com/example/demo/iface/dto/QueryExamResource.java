package com.example.demo.iface.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryExamResource {
	
	private String type; // 考試類型
	
	private String name; // 學生名

	private String stuClass; // 班級
	
	private String startDate; // 起日
	
	private String endDate; // 迄日
	
	private String subject; // 科目
}

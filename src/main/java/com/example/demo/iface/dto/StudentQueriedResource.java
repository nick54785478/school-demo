package com.example.demo.iface.dto;

import java.util.Date;

import com.example.demo.domain.student.aggregate.vo.Grade;
import com.example.demo.domain.student.aggregate.vo.StudentClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQueriedResource {

	private String uuid;	// 學生代號
	
	private Long number;	// 座號
	
	private String name;	// 學生名稱

	private Grade grade; // 年級
	
	private StudentClass studentClass;	// 學生班級
	
	private Date createdDate;	// 入學時間
	
	private String activeFlag = "Y"; // 是否有效
}

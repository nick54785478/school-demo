package com.example.demo.domain.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionQueried {

	private Long id;
	
	private String type; // 類型
	
	private String value; // 內容
	
	private String dependency;  // 依賴
	
	private String parent; // 父項
	
	private Integer priorityNo;
	
	private String description; // 敘述
	
	private String activeFlag;
}
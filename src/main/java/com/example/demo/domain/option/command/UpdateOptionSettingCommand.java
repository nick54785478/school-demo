package com.example.demo.domain.option.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOptionSettingCommand {

	private Long id; // 代號
	
	private String type; // 類型

	private String value; // 內容

	private String dependency; // 依賴

	private String parent; // 父項

	private String description; // 敘述
	
	private Integer priorityNo;
	
	private String activeFlag;
	
}

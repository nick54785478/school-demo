package com.example.demo.domain.mapping.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDataMappingCommand {

	private Long id;

	private String mapping; // Mapping 文件種類
	
	private String type; // 種類
	
	private String field; // 鍵
	
	private String value; // 值

	private String description; // 敘述
	
	private Integer priorityNo; // 順序
	
	private String activeFlag;
}

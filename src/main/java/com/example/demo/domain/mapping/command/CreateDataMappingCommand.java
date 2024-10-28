package com.example.demo.domain.mapping.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDataMappingCommand {

	private String type; // 種類
	
	private String mapping;// Mapping 文件種類
	
	private String field; // 鍵
	
	private String value; // 值

	private String description; // 敘述
	
	private Integer priorityNo; // 順序
}

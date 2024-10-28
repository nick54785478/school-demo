package com.example.demo.iface.dto;

import lombok.Data;

@Data
public class CreateDataMappingResource {

	private String mapping; // Mapping 文件種類
	
	private String type; // 種類
	
	private String field; // 鍵
	
	private String value; // 值

	private String description; // 敘述
	
	private Integer priorityNo; // 順序
}

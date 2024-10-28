package com.example.demo.iface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionSettingResource {

	private String type; // 類型

	private String value; // 內容

	private String dependency; // 依賴

	private String parent; // 父項
	
	private String description; // 敘述

}

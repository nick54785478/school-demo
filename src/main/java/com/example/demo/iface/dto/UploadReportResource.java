package com.example.demo.iface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadReportResource {

	private String type;	// 種類
	
	private String objectId;	// Object Id : 儲存在 Mongo DB 的 Id
	
	private String fileName;	// fileName

	private String description; // 敘述
}

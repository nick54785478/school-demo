package com.example.demo.iface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadTrascriptResource {

	private String stuClass; // 班級
	
	private String examDate; // 考試日期
	
	private String subject; // 科目
	
	private String reportType; // 文件種類
	
	private String type;
	
}

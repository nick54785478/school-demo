package com.example.demo.domain.report.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadPersonalTranscriptCommand {

	private String stuClass; // 班級

	private String examDate; // 考試日期

	private String type; // 種類
	
	private String reportType; // 文件種類

}

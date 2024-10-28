package com.example.demo.domain.report.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadTranscriptCommand {

	private String stuClass; // 班級

	private String examDate; // 考試日期

	private String subject; // 科目

	private String type; // 種類

}

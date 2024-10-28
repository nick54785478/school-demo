package com.example.demo.domain.report.aggregate;

import com.example.demo.domain.report.aggregate.vo.ReportType;
import com.example.demo.domain.report.command.UploadReportCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Report 範本設定
 */
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REPORT_SETTING")
public class ReportSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ReportType type; // 種類

	@Column(name = "OBJECT_ID")
	private String objectId; // Object Id : 儲存在 Mongo DB 的 Id

	@Column(name = "FILE_NAME")
	private String fileName; // fileName

	private String description; // 敘述

	/**
	 * 建立 範本設定
	 * 
	 * @param command
	 * @param objectId MongoDB 的 ID
	 */
	public void create(UploadReportCommand command, String objectId) {
		this.type = ReportType.fromLabel(command.getType());
		this.objectId = objectId;
		this.fileName = command.getFileName();
		this.description = command.getDescription();
	}
}

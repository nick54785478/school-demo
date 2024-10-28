package com.example.demo.domain.report.aggregate.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReportType {

	TRANSCRIPT("TRANSCRIPT", "班級成績單", "班級成績單範本"),
	PERSONAL_TRANSCRIPT("PERSONAL_TRANSCRIPT", "個人成績單", "個人成績單範本");

	@Getter
	private final String code;
	@Getter
	private final String label;
	@Getter
	private final String desc;
	
	// enum 轉 Map
	private static final Map<String, ReportType> labelToTypeMap = new HashMap<>();

	static {
		for (ReportType type : ReportType.values()) {
			labelToTypeMap.put(type.label, type);
		}
	}

	public static ReportType fromLabel(String label) {
		return labelToTypeMap.get(label);
	}

	public static Boolean checkTrainKind(String label) {
		ReportType kind = ReportType.fromLabel(label);
		if (kind == null) {
			return false;
		}
		return true;
	}

}

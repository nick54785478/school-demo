package com.example.demo.domain.exam.aggregate.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExamType {

	MIDTERM("MIDTERM", "期中考"), 
	FINAL("FINAL", "期末考"), 
	NORMAL("NORMAL","平常考");

	@Getter
	private final String code;
	@Getter
	private final String label;

	// enum 轉 Map
	private static final Map<String, ExamType> labelToTypeMap = new HashMap<>();

	static {
		for (ExamType type : ExamType.values()) {
			labelToTypeMap.put(type.label, type);
		}
	}

	public static ExamType fromLabel(String label) {
		return labelToTypeMap.get(label);
	}

	public static Boolean checkKind(String label) {
		ExamType kind = ExamType.fromLabel(label);
		if (kind == null) {
			return false;
		}
		return true;
	}
}

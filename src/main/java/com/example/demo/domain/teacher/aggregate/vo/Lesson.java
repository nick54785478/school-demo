package com.example.demo.domain.teacher.aggregate.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Lesson {

	FIRST("FIRST", "第一節", "08:10"), SECOND("SECOND", "第二節", "09:10"), THIRD("THIRD", "第三節", "10:10"), FOURTH("FOURTH", "第四節", "11:10"),
	FIFTH("FIFTH", "第五節", "13:10"), SIXTH("SIXTH", "第六節", "14:10"), SEVENTH("SEVENTH", "第七節", "15:10"), EIGHTH("EIGHTH", "第八節", "16:10");

	@Getter
	private final String code;
	@Getter
	private final String label;
	@Getter
	private final String time;

	// enum 轉 Map
	private static final Map<String, Lesson> labelToTypeMap = new HashMap<>();

	static {
		for (Lesson type : Lesson.values()) {
			labelToTypeMap.put(type.label, type);
		}
	}

	public static Lesson fromLabel(String label) {
		return labelToTypeMap.get(label);
	}

	public static Boolean checkKind(String label) {
		Lesson kind = Lesson.fromLabel(label);
		if (kind == null) {
			return false;
		}
		return true;
	}
}

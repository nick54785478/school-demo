package com.example.demo.domain.student.aggregate.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 年級
 */
@AllArgsConstructor
public enum Grade {

	FIRST("FIRST", "一年"), 
	SECOND("SECOND", "二年"), 
	THIRD("THIRD","三年"), 
	FOURTH("FOURTH","四年"), 
	FIFTH("FIFTH","五年"), 
	SIXTH("SIXTH","六年");

	@Getter
	private final String code;
	@Getter
	private final String label;

	// enum 轉 Map
	private static final Map<String, Grade> labelToTypeMap = new HashMap<>();

	static {
		for (Grade type : Grade.values()) {
			labelToTypeMap.put(type.label, type);
		}
	}

	public static Grade fromLabel(String label) {
		return labelToTypeMap.get(label);
	}

	public static Boolean checkTrainKind(String label) {
		Grade kind = Grade.fromLabel(label);
		if (kind == null) {
			return false;
		}
		return true;
	}

}

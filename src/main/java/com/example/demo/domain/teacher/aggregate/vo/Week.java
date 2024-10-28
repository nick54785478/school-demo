package com.example.demo.domain.teacher.aggregate.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Week {

	MONDAY("MON", "週一"), TUESDAY("TUE", "週二"), WEDNESDAY("WED", "週三"), THURSDAY("THUR", "週四"), FRIDAY("FRI", "週五"),
	SATURDAY("SAT", "週六"), SUNDAY("SUN", "週日");

	@Getter
	private final String code;
	@Getter
	private final String label;

	// enum 轉 Map
	private static final Map<String, Week> labelToTypeMap = new HashMap<>();

	static {
		for (Week type : Week.values()) {
			labelToTypeMap.put(type.label, type);
		}
	}

	public static Week fromLabel(String label) {
		return labelToTypeMap.get(label);
	}

	public static Boolean checkKind(String label) {
		Week kind = Week.fromLabel(label);
		if (kind == null) {
			return false;
		}
		return true;
	}
}

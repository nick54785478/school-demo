package com.example.demo.domain.student.aggregate.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 班別
 */
@AllArgsConstructor
public enum StudentClass {

	A("A", "A班"), B("B", "B班"), C("C", "C班"), D("D", "D班"), E("E", "E班"), F("F", "F班"), G("G", "G班");

	@Getter
	private final String code;
	@Getter
	private final String label;

	// enum 轉 Map
	private static final Map<String, StudentClass> labelToTypeMap = new HashMap<>();

	static {
		for (StudentClass type : StudentClass.values()) {
			labelToTypeMap.put(type.label, type);
		}
	}

	public static StudentClass fromLabel(String label) {
		return labelToTypeMap.get(label);
	}

	public static Boolean checkTrainKind(String label) {
		StudentClass kind = StudentClass.fromLabel(label);
		if (kind == null) {
			return false;
		}
		return true;
	}

}

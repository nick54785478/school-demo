package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.student.aggregate.Student;

@SpringBootTest
class ParameterMappingUtilTest {


	@Test
	void testSetFieldsFromMap() {
		Map<String, String> map = new HashMap<>();
		map.put("uuid", UUID.randomUUID().toString());
		map.put("number", "1.0");
		map.put("grade", "FIRST");
		map.put("studentClass", "A");
		map.put("createdDate", "2024-10-27 00:00:00");
		Student student = new Student();
		ParameterMappingUtil.setFieldsFromMap(student, map);
		System.out.println("Strudent: "+ student);
	}

}

package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.StudentService;
import com.example.demo.domain.share.StudentQueried;
import com.example.demo.util.RegexUtil;
import com.example.demo.util.RegexUtil.RegexPattern;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentQueryService {

	private StudentService studentService;

	/**
	 * 查詢該班所有學生
	 * 
	 * @param className 班級
	 * @return List<StudentQueried>
	 */
	public List<StudentQueried> queryAllStudents(String className) {
		Matcher matcher = RegexUtil.processByRegex(RegexPattern.GRADE_CLASS_PATTERN.getValue(), className);
		if (matcher.find()) {
			String grade = matcher.group(1);
			String classGroup = matcher.group(2);
			// className: 一年B班
			return studentService.queryByClass(grade, classGroup);
		}
		return new ArrayList<>();
	}

}

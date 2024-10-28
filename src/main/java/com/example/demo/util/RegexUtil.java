package com.example.demo.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexUtil {
	
	/**
	 * 使用正則表達式匹配
	 * 
	 * @param input 輸入字串
	 * @return Matcher
	 */
	public static Matcher processByRegex(String regex, String input) {
		// 使用正則表達式匹配"年級"與"班別"
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return Objects.isNull(matcher) ? null : matcher;
	}
	
	@Getter
	@AllArgsConstructor
	public enum RegexPattern {
		GRADE_CLASS_PATTERN("(一年|二年|三年|四年|五年|六年)([A-Z]班)"),
		STUDENT_CLASS_PATTERN("^(\\S+?)([A-Za-z]\\S+)$");
		private String value;
	}
}

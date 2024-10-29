package com.example.demo.domain.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.share.TeacherCreated;
import com.example.demo.domain.share.TeacherUpdated;
import com.example.demo.domain.teacher.aggregate.Teacher;
import com.example.demo.domain.teacher.command.CreateTeacherCommand;
import com.example.demo.domain.teacher.command.CreateTeacherCourseCommand;
import com.example.demo.domain.teacher.command.UpdateTeacherCommand;
import com.example.demo.exception.ValidationException;
import com.example.demo.infra.repository.TeacherRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeacherService {

	private TeacherRepository teacherRepository;

	/**
	 * 新增一筆老師資料
	 * 
	 * @param command
	 * @return TeacherCreated
	 */
	public TeacherCreated create(CreateTeacherCommand command) {
		Teacher teacher = new Teacher();
		teacher.create(command);
		Teacher saved = teacherRepository.save(teacher);
		return new TeacherCreated(saved.getUuid());
	}

	/**
	 * 更新一筆老師資料
	 * 
	 * @param command
	 * @return TeacherUpdated
	 */
	public TeacherUpdated update(UpdateTeacherCommand command) {
		teacherRepository.findById(command.getUuid()).ifPresent(teacher -> {
			teacher.update(command);
			teacherRepository.save(teacher);
		});

		return new TeacherUpdated(command.getUuid());
	}

	/**
	 * 檢查課程是否衝堂
	 * 
	 * @param courses 課程清單
	 */
	public void checkScheduleConflict(List<CreateTeacherCourseCommand> courses) {

		List<String> collect = courses.stream().map(course -> course.getWeekDay() + "-" + course.getLesson())
				.collect(Collectors.toList());

		List<String> duplicates = collect.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // 分組並計算出現次數
				.entrySet().stream().filter(entry -> entry.getValue() > 1) // 過濾出出現次數大於 1 的項目
				.map(Map.Entry::getKey) // 提取重複的項目
				.collect(Collectors.toList());

		if (!duplicates.isEmpty()) {
			throw new ValidationException("VALIDATE_FAILED", "課程衝堂，新增失敗");
		}

	}
}

package com.example.demo.domain.teacher.aggregate.entity;

import java.time.LocalTime;

import com.example.demo.domain.teacher.aggregate.vo.Lesson;
import com.example.demo.domain.teacher.aggregate.vo.Week;
import com.example.demo.domain.teacher.command.CreateTeacherCourseCommand;
import com.example.demo.domain.teacher.command.UpdateTeacherCourseCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COURSE")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TEACHER_ID")
	private String teacherId; // 老師代號

	private String name; // 課程名稱

	private String stuClass; // 授課班級

	@Enumerated(EnumType.STRING)
	private Lesson lesson; // 第幾節
	
	@Enumerated(EnumType.STRING)
	@Column(name = "WEEK_DAY")
	private Week weekDay; // 上課日

	private LocalTime time; // 上課時間

	/**
	 * 建立老師教課資料
	 * 
	 * @param command
	 * @param teacherId 老師代號
	 */
	public void create(CreateTeacherCourseCommand command, String teacherId) {
		this.teacherId = teacherId;
		this.name = command.getName();
		this.stuClass = command.getStuClass();
		this.lesson = Lesson.fromLabel(command.getLesson());
		this.time = LocalTime.parse(this.lesson.getTime());
		this.weekDay = Week.fromLabel(command.getWeekDay());
	}
	
	/**
	 * 更新老師教課資料
	 * 
	 * @param command
	 */
	public void update(UpdateTeacherCourseCommand command) {
		this.teacherId = command.getTeacherId();
		this.name = command.getName();
		this.stuClass = command.getStuClass();
		this.lesson = Lesson.fromLabel(command.getLesson());
		this.time = LocalTime.parse(this.lesson.getTime());
		this.weekDay = Week.fromLabel(command.getWeekDay());
	}

}

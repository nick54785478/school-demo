package com.example.demo.domain.exam.aggregate;

import java.math.BigDecimal;
import java.util.Date;

import com.example.demo.domain.exam.aggregate.vo.ExamType;
import com.example.demo.domain.exam.command.CreateExamCommand;

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

/**
 * 測驗
 */
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EXAM")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "STUDENT_ID")
	private String studentId; // 學生代號

	@Column(name = "STU_CLASS")
	private String stuClass; // 班級，一年C班

	private String subject; // 科目

	private BigDecimal score; // 分數

	@Enumerated(EnumType.STRING)
	private ExamType type; // 考試類型

	@Column(name = "EXAM_DATE")
	private Date examDate; // 測驗日期

	@Column(name = "INVIGILATOR_ID")
	private String invigilatorId; // 監考員
	
	/**
	 * 建立一筆 Exam 資料
	 * 
	 * @param command
	 */
	public void create(CreateExamCommand command) {
		this.studentId = command.getStudentId();
		this.score = command.getScore();
		this.stuClass = command.getStuClass();
		this.subject = command.getSubject();
		this.examDate = command.getExamDate();
		this.type = ExamType.fromLabel(command.getType());
	}
	
	/**
	 * 更新 StudentId
	 * 
	 * @param studentId
	 */
	public void updateStudentId(String studentId) {
		this.studentId = studentId;
	}
}

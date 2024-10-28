package com.example.demo.domain.student.aggregate;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.domain.student.aggregate.vo.Grade;
import com.example.demo.domain.student.aggregate.vo.StudentClass;
import com.example.demo.domain.student.command.CreateStudentCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 角色表
 */
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENT")
@EntityListeners(AuditingEntityListener.class)
public class Student {

	@Id
	private String uuid;	// 學生代號
	
	@Transient
	private String u;
	
	private Long number;	// 座號
	
	private String name;	// 學生名稱

	@Column(name = "GRADE")
	@Enumerated(EnumType.STRING)
	private Grade grade; // 年級
	
	@Column(name = "STUDENT_CLASS")
	@Enumerated(EnumType.STRING)
	private StudentClass studentClass;	// 學生班級
	
	@CreatedDate
	@Column(name = "CREATED_DATE")
	private Date createdDate;	// 入學時間
	
	@Column(name = "GRADUATED_DATE")
	private Date graduateDate;	// 畢業日期
	
	@Column(name = "ACTIVE_FLAG")
	private String activeFlag = "Y"; // 是否有效

	/**
	 * 在持久化之前執行的方法，用於設置 UUID。
	 */
	@PrePersist
	public void prePersist() {
		if (Objects.isNull(this.uuid)) {
			this.uuid = this.u;
		}
	}

	/**
	 * 建立 Student Entity
	 * 
	 * @param command
	 * */
	public void create(CreateStudentCommand command) {
		this.u = UUID.randomUUID().toString();
		this.number = command.getNumber();
		this.name = command.getName();
		this.grade = Grade.valueOf(command.getGrade());
		this.studentClass = StudentClass.valueOf(command.getStudentClass());
		this.activeFlag = "Y";
	}


}

package com.example.demo.domain.teacher.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.domain.teacher.aggregate.entity.Course;
import com.example.demo.domain.teacher.command.CreateTeacherCommand;
import com.example.demo.domain.teacher.command.CreateTeacherCourseCommand;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEACHER")
@EntityListeners(AuditingEntityListener.class)
public class Teacher {

	@Id
	private String uuid; // 老師代號
	
	@Transient
	private String u;

	private String name; // 老師名稱

	private String subject; // 主要負責科目

	@Column(name = "IS_MENTOR")
	private String isMentor; // 是否為導師

	@Column(name = "LEAD_CLASS")
	private String leadClass; // 帶領的班級

	// 使用懶加載，避免 N+1 query 效能問題
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHER_ID", updatable = false)
	private List<Course> courses = new ArrayList<>();

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
	 * 建立老師資料
	 * 
	 * @param command
	 * */
	public void create(CreateTeacherCommand command) {
		this.u = UUID.randomUUID().toString();
		this.name = command.getName();
		this.leadClass = command.getLeadClass();
		this.isMentor = command.getIsMentor();
		this.subject = command.getSubject();
		
		List<CreateTeacherCourseCommand> courseList = command.getCourses();
		courseList.stream().forEach(e -> {
			Course course = new Course();
			course.create(e, this.u);
			this.courses.add(course);
		});
		
	}

}

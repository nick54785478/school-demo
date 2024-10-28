package com.example.demo.domain.share.view;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "STUDENT_EXAM_V")
public class StudentExamV {
	
	@Id
	private Long id; // exam id

    @Column(name = "FULL_NAME") // 組合的班級和學生名稱
    private String fullName;
    
    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "NAME") // 學生名稱
    private String name;

    @Column(name = "STU_CLASS") // 班級
    private String stuClass;

    @Column(name = "SUBJECT") // 科目
    private String subject;

    @Column(name = "SCORE") // 分數
    private BigDecimal score;

    @Column(name = "TYPE") // 考試類型
    private String type;

    @Column(name = "EXAM_DATE") // 考試日期
    private Date examDate;

    @Column(name = "INVIGILATOR") // 監考老師名稱
    private String invigilator;
}

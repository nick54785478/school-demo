package com.example.demo.domain.student.command;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentCommand {

	private Long number; // 座號

	private String name; // 學生名稱

	private String grade; // 年級

	private String studentClass; // 學生班級

	private Date createdDate; // 入學時間

}

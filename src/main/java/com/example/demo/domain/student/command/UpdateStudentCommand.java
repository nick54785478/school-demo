package com.example.demo.domain.student.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentCommand {
	
	private String uuid;

	private Long number; // 座號

	private String name; // 學生名稱

	private String grade; // 年級

	private String studentClass; // 學生班級
	
	private String graduatedDate; // 畢業日期
	
	private String activeFlag; // 畢業

}

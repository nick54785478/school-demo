package com.example.demo.domain.share;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQueriedData {

	private Long number;
	
	private String name;
	
	private String stuClass;
	
	private String subject;
	
	private BigDecimal score;
	
	private Date testDate;
	
	private String type;
	
}

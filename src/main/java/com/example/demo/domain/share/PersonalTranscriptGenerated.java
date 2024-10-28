package com.example.demo.domain.share;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTranscriptGenerated {

	private String name;
	
	private Long number;

	private List<SubjectScore> scoreList;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SubjectScore {

		private String subject;

		private Float score;
	}

}

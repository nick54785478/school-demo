package com.example.demo.domain.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MappingQueroed {

	private Long id;

	private String mapping; // Mapping 哪類文件

	private String type; // 種類

	private String field; // Entity 屬性

	private String value; // 值

	private String description; // 敘述

	private Integer priorityNo; // 順序

	private String activeFlag = "Y"; // 是否有效
}

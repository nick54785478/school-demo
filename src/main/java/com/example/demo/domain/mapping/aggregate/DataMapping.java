package com.example.demo.domain.mapping.aggregate;

import com.example.demo.domain.mapping.command.CreateDataMappingCommand;
import com.example.demo.domain.mapping.command.UpdateDataMappingCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data Mapping
 */
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DATA_MAPPING")
public class DataMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mapping; // Mapping 哪類文件

	private String type; // 種類

	private String field; // Entity 屬性

	private String value; // 值

	private String description; // 敘述

	@Column(name = "PRIORITY_NO")
	private Integer priorityNo; // 順序

	@Column(name = "ACTIVE_FLAG")
	private String activeFlag = "Y"; // 是否有效

	/**
	 * 建立 DataMapping 資料
	 * 
	 * @param command
	 */
	public void create(CreateDataMappingCommand command) {
		this.mapping = command.getMapping();
		this.field = command.getField();
		this.type = command.getType();
		this.value = command.getValue();
		this.description = command.getDescription();
		this.priorityNo = command.getPriorityNo();
		this.activeFlag = "Y";
	}

	/**
	 * 更新 DataMapping 資料
	 * 
	 * @param command
	 */
	public void update(UpdateDataMappingCommand command) {
		this.id = command.getId();
		this.mapping = command.getMapping();
		this.field = command.getField();
		this.type = command.getType();
		this.value = command.getValue();
		this.description = command.getDescription();
		this.priorityNo = command.getPriorityNo();
		this.activeFlag = command.getActiveFlag();
	}
}

package com.example.demo.domain.option.aggregate;

import com.example.demo.domain.option.command.CreateOptionSettingCommand;
import com.example.demo.domain.option.command.UpdateOptionSettingCommand;

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
 * 選項設定
 */
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OPTION_SETTING")
public class OptionSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type; // 類型

	private String value; // 內容

	private String dependency; // 依賴

	private String parent; // 父項

	@Column(name = "PRIORITY_NO")
	private Integer priorityNo;

	private String description; // 敘述

	@Column(name = "ACTIVE_FLAG")
	private String activeFlag;

	/**
	 * 建立 Option Setting 資料
	 * 
	 * @param command
	 * @param priorityNo
	 */
	public void create(CreateOptionSettingCommand command, int priorityNo) {
		this.type = command.getType();
		this.value = command.getValue();
		this.dependency = command.getDependency();
		this.parent = command.getParent();
		this.description = command.getDescription();
		this.priorityNo = priorityNo;
		this.activeFlag = "Y";
	}

	/**
	 * 更新 Option Setting 資料
	 * 
	 * @param command
	 */
	public void update(UpdateOptionSettingCommand command) {
		this.id = command.getId();
		this.type = command.getType();
		this.value = command.getValue();
		this.dependency = command.getDependency();
		this.parent = command.getParent();
		this.description = command.getDescription();
		this.priorityNo = command.getPriorityNo();
		this.activeFlag = command.getActiveFlag();
	}
}

package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.option.command.CreateOptionSettingCommand;
import com.example.demo.domain.option.command.UpdateOptionSettingCommand;
import com.example.demo.domain.service.OptionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
public class OptionCommandService {

	private OptionService optionService;

	/**
	 * 建立 Option Setting 資料
	 * 
	 * @param command
	 */
	public void createOption(List<CreateOptionSettingCommand> commands) {
		optionService.createOptionSetting(commands);
	}
	
	/**
	 * 更新 Option Setting 資料
	 * 
	 * @param command
	 */
	public void updateOption(List<UpdateOptionSettingCommand> commands) {
		optionService.updateOptionSetting(commands);
	}
}

package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.mapping.command.CreateDataMappingCommand;
import com.example.demo.domain.mapping.command.UpdateDataMappingCommand;
import com.example.demo.domain.service.DataMappingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
public class SettingCommandService {

	private DataMappingService dataMappingService;

	/**
	 * 設置 DataMapping
	 * 
	 * @param commands
	 */
	public void createDataMapping(List<CreateDataMappingCommand> commands) {
		dataMappingService.create(commands);
	}
	
	/**
	 * 設置 DataMapping
	 * 
	 * @param commands
	 */
	public void updateDataMapping(List<UpdateDataMappingCommand> commands) {
		dataMappingService.update(commands);
	}
}

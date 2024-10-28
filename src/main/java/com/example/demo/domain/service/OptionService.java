package com.example.demo.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.option.aggregate.OptionSetting;
import com.example.demo.domain.option.command.CreateOptionSettingCommand;
import com.example.demo.domain.option.command.UpdateOptionSettingCommand;
import com.example.demo.domain.share.OptionQueried;
import com.example.demo.infra.repository.OptionRepository;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OptionService {

	private OptionRepository optionRepository;


	/**
	 * 建立 Option Setting
	 * 
	 * @param command
	 */
	public void createOptionSetting(List<CreateOptionSettingCommand> commands) {
		List<OptionSetting> optionList = commands.stream().map(command -> {
			int priorityNo = 1;
			OptionSetting option = new OptionSetting();
			option.create(command, priorityNo);
			priorityNo++;
			return option;
		}).collect(Collectors.toList());

		optionRepository.saveAll(optionList);
	}

	/**
	 * 更新 Option Setting 資料
	 * 
	 * @param command
	 */
	public void updateOptionSetting(List<UpdateOptionSettingCommand> commands) {
		List<Long> ids = commands.stream().map(UpdateOptionSettingCommand::getId).collect(Collectors.toList());
		List<OptionSetting> options = optionRepository.findAllById(ids);
		Map<Long, UpdateOptionSettingCommand> map = commands.stream()
				.collect(Collectors.toMap(UpdateOptionSettingCommand::getId, Function.identity()));

		List<OptionSetting> optionList = options.stream().map(option -> {
			if (!Objects.isNull(map.get(option.getId()))) {
				option.update(map.get(option.getId()));
				return option;
			}
			return option;
		}).collect(Collectors.toList());

		optionRepository.saveAll(optionList);
	}

	/**
	 * 查詢 Option 資料清單
	 * 
	 * @param type
	 */
	public List<OptionQueried> query(String type) {
		return BaseDataTransformer.transformData(optionRepository.findByTypeAndActiveFlag(type, "Y"),
				OptionQueried.class);
	}

}

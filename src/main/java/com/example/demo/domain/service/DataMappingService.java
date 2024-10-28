package com.example.demo.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.mapping.aggregate.DataMapping;
import com.example.demo.domain.mapping.command.CreateDataMappingCommand;
import com.example.demo.domain.mapping.command.UpdateDataMappingCommand;
import com.example.demo.domain.share.MappingQueroed;
import com.example.demo.infra.repository.DataMappingRepository;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DataMappingService {

	private DataMappingRepository dataMappingRepository;

	/**
	 * 建立 DataMapping
	 * 
	 * @param command
	 */
	public void create(List<CreateDataMappingCommand> commands) {
		
		List<DataMapping> dataMappingList = commands.stream().map(command -> {
			DataMapping dataMapping = new DataMapping();
			dataMapping.create(command);
			return dataMapping;
		}).collect(Collectors.toList());
		
		dataMappingRepository.saveAll(dataMappingList);

	}
	
	/**
	 * 建立 DataMapping
	 * 
	 * @param command
	 */
	public void update(List<UpdateDataMappingCommand> commands) {
		Map<Long, UpdateDataMappingCommand> map = commands.stream()
				.collect(Collectors.toMap(UpdateDataMappingCommand::getId, Function.identity()));
		List<Long> ids = commands.stream().map(UpdateDataMappingCommand::getId).collect(Collectors.toList());
		List<DataMapping> mappings = dataMappingRepository.findAllById(ids);

		List<DataMapping> mappingList = mappings.stream().map(mapping -> {
			if (!Objects.isNull(map.get(mapping.getId()))) {
				mapping.update(map.get(mapping.getId()));
				return mapping;
			}
			return mapping;
		}).collect(Collectors.toList());

		dataMappingRepository.saveAll(mappingList);
	}
	
	/**
	 * 查詢 DataMapping 清單
	 * 
	 * @param mapping
	 */
	public List<MappingQueroed> queryMappingList(String mapping) {
		return BaseDataTransformer.transformData(dataMappingRepository.findByMappingAndActiveFlag(mapping, "Y"),
				MappingQueroed.class);
	}
}

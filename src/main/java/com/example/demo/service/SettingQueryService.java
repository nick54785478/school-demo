package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.DataMappingService;
import com.example.demo.domain.share.MappingQueroed;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SettingQueryService {

	private DataMappingService dataMappingService;

	/**
	 * 查詢 Mapping List
	 * 
	 * @param mapping
	 * @return List<MappingQueroed>
	 */
	public List<MappingQueroed> queryMapping(String mapping) {
		List<MappingQueroed> options = dataMappingService.queryMappingList(mapping);
		return options;
	}

}

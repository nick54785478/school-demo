package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.service.OptionService;
import com.example.demo.domain.share.OptionQueried;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OptionQueryService {

	private OptionService optionService;

	/**
	 * 查詢 Option 資料清單
	 * 
	 * @param type
	 * @return OptionQueroedResource
	 */
	public List<OptionQueried> query(String type) {
		List<OptionQueried> mappingList = optionService.query(type);
		return mappingList;
	}
}

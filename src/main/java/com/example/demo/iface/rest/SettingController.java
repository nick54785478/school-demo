package com.example.demo.iface.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.mapping.command.CreateDataMappingCommand;
import com.example.demo.domain.mapping.command.UpdateDataMappingCommand;
import com.example.demo.iface.dto.CreateDataMappingResource;
import com.example.demo.iface.dto.MappingQueroedResource;
import com.example.demo.iface.dto.SettingCreatedResource;
import com.example.demo.iface.dto.SettingUpdatedResource;
import com.example.demo.iface.dto.UpdateDataMappingResource;
import com.example.demo.service.SettingCommandService;
import com.example.demo.service.SettingQueryService;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/setting")
public class SettingController {

	private SettingCommandService settingCommandService;
	private SettingQueryService settingQueryService;

	/**
	 * 新增 Data Mapping 資料
	 * 
	 * @param resources
	 * @return ResponseEntity<SettingCreatedResource>
	 */
	@PostMapping("/createDataMapping")
	public ResponseEntity<SettingCreatedResource> createDataMapping(
			@RequestBody List<CreateDataMappingResource> resources) {
		List<CreateDataMappingCommand> commands = BaseDataTransformer.transformData(resources,
				CreateDataMappingCommand.class);
		settingCommandService.createDataMapping(commands);
		return new ResponseEntity<>(new SettingCreatedResource(201, "新增成功"), HttpStatus.CREATED);
	}

	/**
	 * 更改 Data Mapping 資料
	 * 
	 * @param resources
	 * @return ResponseEntity<SettingCreatedResource>
	 */
	@PutMapping("/updateOption")
	public ResponseEntity<SettingUpdatedResource> updateDataMapping(
			@RequestBody List<UpdateDataMappingResource> resources) {
		List<UpdateDataMappingCommand> commands = BaseDataTransformer.transformData(resources,
				UpdateDataMappingCommand.class);
		settingCommandService.updateDataMapping(commands);
		return new ResponseEntity<>(new SettingUpdatedResource(200, "更新成功"), HttpStatus.OK);
	}

	/**
	 * 查詢 Data Mapping 資料
	 * 
	 * @param mapping
	 * @return ResponseEntity<List<MappingQueroedResource>>
	 */
	@GetMapping("/getMappingList")
	public ResponseEntity<List<MappingQueroedResource>> queryMapping(@RequestParam String mapping) {
		return new ResponseEntity<>(BaseDataTransformer.transformData(settingQueryService.queryMapping(mapping),
				MappingQueroedResource.class), HttpStatus.OK);
	}
}

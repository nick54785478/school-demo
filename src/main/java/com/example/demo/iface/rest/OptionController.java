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

import com.example.demo.domain.option.command.CreateOptionSettingCommand;
import com.example.demo.domain.option.command.UpdateOptionSettingCommand;
import com.example.demo.iface.dto.CreateOptionSettingResource;
import com.example.demo.iface.dto.OptionQueroedResource;
import com.example.demo.iface.dto.SettingCreatedResource;
import com.example.demo.iface.dto.SettingUpdatedResource;
import com.example.demo.iface.dto.UpdateOptionSettingResource;
import com.example.demo.service.OptionCommandService;
import com.example.demo.service.OptionQueryService;
import com.example.demo.util.BaseDataTransformer;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/option")
public class OptionController {

	private OptionQueryService optionQueryService;
	private OptionCommandService optionCommandService;

	/**
	 * 新增 Option Setting 資料
	 * 
	 * @param resource
	 * @return ResponseEntity<SettingCreatedResource>
	 */
	@PostMapping("/createOption")
	public ResponseEntity<SettingCreatedResource> createOption(
			@RequestBody List<CreateOptionSettingResource> resources) {
		List<CreateOptionSettingCommand> commands = BaseDataTransformer.transformData(resources,
				CreateOptionSettingCommand.class);
		optionCommandService.createOption(commands);
		return new ResponseEntity<>(new SettingCreatedResource(201, "新增成功"), HttpStatus.CREATED);
	}
	
	/**
	 * 更改 Option Setting 資料
	 * 
	 * @param resource
	 * @return ResponseEntity<SettingCreatedResource>
	 */
	@PutMapping("/updateOption")
	public ResponseEntity<SettingUpdatedResource> updateOption(
			@RequestBody List<UpdateOptionSettingResource> resources) {
		List<UpdateOptionSettingCommand> commands = BaseDataTransformer.transformData(resources,
				UpdateOptionSettingCommand.class);
		optionCommandService.updateOption(commands);
		return new ResponseEntity<>(new SettingUpdatedResource(200, "更新成功"), HttpStatus.OK);
	}

	/**
	 * 查詢 Option Setting 資料
	 * 
	 * @param type
	 * @return ResponseEntity<List<OptionQueroedResource>>
	 */
	@GetMapping("")
	public ResponseEntity<List<OptionQueroedResource>> query(@RequestParam String type) {
		return new ResponseEntity<>(
				BaseDataTransformer.transformData(optionQueryService.query(type), OptionQueroedResource.class),
				HttpStatus.OK);
	}
}

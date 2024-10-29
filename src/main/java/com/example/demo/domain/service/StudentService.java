package com.example.demo.domain.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mapping.aggregate.DataMapping;
import com.example.demo.domain.share.StudentCreated;
import com.example.demo.domain.share.StudentQueried;
import com.example.demo.domain.share.StudentUpdated;
import com.example.demo.domain.student.aggregate.Student;
import com.example.demo.domain.student.aggregate.vo.Grade;
import com.example.demo.domain.student.aggregate.vo.StudentClass;
import com.example.demo.domain.student.command.CreateStudentCommand;
import com.example.demo.domain.student.command.UpdateStudentCommand;
import com.example.demo.domain.student.command.UploadStudentCommand;
import com.example.demo.exception.ValidationException;
import com.example.demo.infra.repository.DataMappingRepository;
import com.example.demo.infra.repository.StudentRepository;
import com.example.demo.util.BaseDataTransformer;
import com.example.demo.util.ExcelUtil;
import com.example.demo.util.ParameterMappingUtil;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private DataMappingRepository dataMappingRepository;

	/**
	 * 新增學生資料
	 * 
	 * @param command
	 */
	public StudentCreated create(CreateStudentCommand command) {
		Student student = new Student();
		student.create(command);
		Student saved = studentRepository.save(student);
		return new StudentCreated(saved.getUuid());
	}
	
	/**
	 * 更新學生資料
	 * 
	 * @param command
	 */
	public StudentUpdated update(UpdateStudentCommand command) {
		studentRepository.findById(command.getUuid()).ifPresentOrElse(student -> {
			student.update(command);
			studentRepository.save(student);
		}, () -> {
			throw new ValidationException("VALIDATE_FAILED", "查無此資料，更新失敗");
		});
		return new StudentUpdated(command.getUuid());
	}
	

	/**
	 * 查詢該班的學生
	 * 
	 * @param className 班級名稱(如: 一年三班)
	 * @return List<StudentQueried>
	 */
	public List<StudentQueried> queryByClass(String grade, String studentClass) {
		List<Student> students = studentRepository.findByGradeAndStudentClassAndActiveFlag(Grade.fromLabel(grade),
				StudentClass.fromLabel(studentClass), "Y");
		return BaseDataTransformer.transformData(students, StudentQueried.class);
	}

	/**
	 * 上傳學生 Excel 資料
	 * 
	 * @param inputStream
	 */
	public void upload(UploadStudentCommand command) {
		try {
			// 取得 Map<SheetName, List<Map<Header, Value>>>
			List<Map<String, String>> multipleSheetData = ExcelUtil.readSheetData(command.getInputStream());
			List<Student> students = new ArrayList<>();

			HashMap<String, String> processMap = new HashMap<>();
			
			// 查出 Data Mapping
			List<DataMapping> dataMapping = dataMappingRepository
					.findByMappingAndActiveFlag(command.getMapping(), "Y");
			
			multipleSheetData.forEach(map -> {
				// 將 Data Mapping 轉為 Map<欄位名, 值>
				Map<String, String> mapping = dataMapping.stream()
						.collect(Collectors.toMap(DataMapping::getValue, DataMapping::getField));
				
				map.forEach((k, v) -> {
					if (!Objects.isNull(mapping.get(k))) {
						processMap.put(mapping.get(k), v);
					}
				});
				// 以 key 與 Class 屬性對應，設置進 Class 屬性以建立 Class
				Student student = ParameterMappingUtil.setFieldsFromMap(new Student(), processMap);
				students.add(student);
			});

			// 存入資料庫
			studentRepository.saveAll(students);

		} catch (IOException e) {
			Log.error("發生錯誤", e);
		}
	}

}

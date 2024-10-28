package com.example.demo.domain.service;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jfree.util.Log;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.example.demo.domain.exam.aggregate.Exam;
import com.example.demo.domain.exam.aggregate.vo.ExamType;
import com.example.demo.domain.exam.command.CreateExamCommand;
import com.example.demo.domain.mapping.aggregate.DataMapping;
import com.example.demo.domain.report.aggregate.ReportSetting;
import com.example.demo.domain.report.aggregate.vo.ReportType;
import com.example.demo.domain.report.command.DownloadPersonalTranscriptCommand;
import com.example.demo.domain.report.command.DownloadTranscriptCommand;
import com.example.demo.domain.share.ExamQueried;
import com.example.demo.domain.share.PersonalTranscriptGenerated;
import com.example.demo.domain.share.PersonalTranscriptGenerated.SubjectScore;
import com.example.demo.domain.share.StudentExamQueried;
import com.example.demo.domain.share.command.QueryExamCommand;
import com.example.demo.domain.share.view.StudentExamV;
import com.example.demo.domain.student.aggregate.Student;
import com.example.demo.domain.student.aggregate.vo.Grade;
import com.example.demo.domain.student.aggregate.vo.StudentClass;
import com.example.demo.infra.repository.DataMappingRepository;
import com.example.demo.infra.repository.ExamRepository;
import com.example.demo.infra.repository.ReportSettingRepository;
import com.example.demo.infra.repository.StudentRepository;
import com.example.demo.infra.repository.view.StudentExamVRepository;
import com.example.demo.infra.store.GridFSService;
import com.example.demo.util.BaseDataTransformer;
import com.example.demo.util.JasperUtil;
import com.example.demo.util.ParameterMappingUtil;
import com.example.demo.util.RegexUtil;
import com.example.demo.util.RegexUtil.RegexPattern;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ExamService {

	private GridFSService gridFsService;
	private ExamRepository examRepository;
	private StudentRepository studentRepository;
	private DataMappingRepository dataMappingRepository;
	private StudentExamVRepository studentExamVRepository;
	private ReportSettingRepository reportSettingRepository;

	/**
	 * 新增考試資料
	 * 
	 * @param commands
	 */
	public void create(List<CreateExamCommand> commands) {
		List<Exam> examList = commands.stream().map(command -> {
			Exam exam = new Exam();
			exam.create(command);
			return exam;
		}).collect(Collectors.toList());
		examRepository.saveAll(examList);
	}

	/**
	 * 查詢該班的所有學生考試成績
	 * 
	 * @param stuClass 班級(如:一年B班)
	 * @param subject  考試科目
	 * @param examDate 考試日期
	 */
	public List<ExamQueried> queryExamByClass(String stuClass, String subject, String examDate) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		AtomicInteger rank = new AtomicInteger(1);
		try {
			// 取得該班同學該次的考試資料
			List<Exam> examList = examRepository.findBySubjectAndStuClassAndExamDate(subject, stuClass,
					simpleDateFormat.parse(examDate));
			// Map<StudentId, Exam>
			Map<String, Exam> map = examList.stream()
					.collect(Collectors.toMap(Exam::getStudentId, Function.identity()));

			Matcher matcher = RegexUtil.processByRegex(RegexPattern.GRADE_CLASS_PATTERN.getValue(), stuClass);

			if (matcher.find()) {
				List<Student> students = studentRepository.findByGradeAndStudentClassAndActiveFlag(
						Grade.fromLabel(matcher.group(1)), StudentClass.fromLabel(matcher.group(2)), "Y");
				return students.stream().map(student -> {
					if (!Objects.isNull(map.get(student.getUuid()))) {
						Exam exam = map.get(student.getUuid());
						// 組裝資料
						ExamQueried data = new ExamQueried();
						data.setNumber(student.getNumber());
						data.setName(student.getName());
						data.setScore(exam.getScore());
						data.setUuid(student.getUuid());
						data.setSubject(exam.getSubject());
						data.setExamDate(exam.getExamDate());
						data.setStuClass(exam.getStuClass());
						return data;
					}
					return null;
				}).sorted(Comparator.comparing(ExamQueried::getScore).reversed()) // 根據 score 降序排序
						.map(e -> {
							// 設定排名
							e.setPriority(rank.getAndIncrement());
							return e;
						}).collect(Collectors.toList());
			}
		} catch (ParseException e) {
			Log.error("日期轉換發生錯誤。 ", e);
		}
		return new ArrayList<>();
	}

	/**
	 * 根據條件進行查詢學生考試
	 * 
	 * @param command
	 * @return List<ExamQueried>
	 */
	public List<StudentExamQueried> query(QueryExamCommand command) {
		List<StudentExamV> studentExamVList = studentExamVRepository.findDataBySpecification(command);
		return BaseDataTransformer.transformData(studentExamVList, StudentExamQueried.class);
	}

	/**
	 * 下載班級成績單
	 * 
	 * @param command
	 * @param examList
	 * @return Map<String, Object>
	 */
	public Map<String, Object> downloadGroupTranscript(DownloadTranscriptCommand command, List<ExamQueried> examList) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();

		// 取出 Report Setting
		List<ReportSetting> settings = reportSettingRepository.findByType(ReportType.fromLabel(command.getType()));

		// 班級成績單沒有多筆
		settings.stream().findFirst().ifPresent(setting -> {
			// 根據 ObjectId 向 MongoDB 取出文件流
			InputStream is = gridFsService.downloadFileAsInputStream(new ObjectId(setting.getObjectId()));

			paramMap.put("detailSize", examList.size()); // Detail 資料的筆數
			paramMap.put("examClass", command.getStuClass()); // 應試班級

			// 設置標題參數
			List<String> fields = ParameterMappingUtil.getFields(new ExamQueried());
			List<DataMapping> dataMappingList = dataMappingRepository
					.findByMappingAndFieldInAndActiveFlag(command.getType(), fields, "Y");
			dataMappingList.stream().forEach(mapping -> {
				paramMap.put(mapping.getField(), mapping.getValue());
			});

			ByteArrayResource resource = JasperUtil.generateReportToPDF(is, examList, paramMap);

			result.put("filename", command.getStuClass() + "成績單" + ".pdf");
			result.put("resource", resource);
		});
		return result;
	}

	/**
	 * 下載個人成績單
	 * 
	 * @param command
	 * @param studentId 學生ID
	 * @return Map<String, Object>
	 */
	public Map<String, Object> downloadTranscript(DownloadPersonalTranscriptCommand command, String studentId) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		List<StudentExamV> examList = new ArrayList<>();
		try {
			examList = studentExamVRepository.findByStudentIdAndStuClassAndExamDateAndType(studentId,
					command.getStuClass(), simpleDateFormat.parse(command.getExamDate()),
					ExamType.fromLabel(command.getType()).getCode());
		} catch (ParseException e) {
			log.error("日期轉換發生錯誤 ", e);
		}

		// 取出 Report Setting
		List<ReportSetting> settings = reportSettingRepository
				.findByType(ReportType.fromLabel(command.getReportType()));
		// 轉換為 Map<fileName, setting>
		Map<String, ReportSetting> settingMap = settings.stream()
				.collect(Collectors.toMap(ReportSetting::getFileName, Function.identity()));

		// 主報表處理
		if (settingMap.size() > 0) {
			ReportSetting mainSetting = settingMap.get("個人成績單(主)");
			// 主報表設定
			InputStream mainStream = gridFsService.downloadFileAsInputStream(new ObjectId(mainSetting.getObjectId()));

			ReportSetting subSetting = settingMap.get("個人成績單(副)");
			// 副報表設定
			InputStream subStream = gridFsService.downloadFileAsInputStream(new ObjectId(subSetting.getObjectId()));

			List<PersonalTranscriptGenerated> list = new ArrayList<>();

			List<SubjectScore> subjectScoreList = examList.stream().map(exam -> {
				return new SubjectScore(exam.getSubject(), exam.getScore().floatValue());
			}).collect(Collectors.toList());

			studentRepository.findById(studentId).ifPresent(student -> {
				PersonalTranscriptGenerated personalTranscriptGenerated = PersonalTranscriptGenerated.builder()
						.number(student.getNumber()).name(student.getName()).scoreList(subjectScoreList).build();
				list.add(personalTranscriptGenerated);
				paramMap.put("stuName", student.getName());
			});

			paramMap.put("stuClass", command.getStuClass());
			paramMap.put("subResource", subStream); // 設置 子報表的 InputStream
			paramMap.put("examType", command.getType());

			ByteArrayResource resource = JasperUtil.generateReportToPDF(mainStream, list, paramMap);
			result.put("filename", "個人成績單" + ".pdf");
			result.put("resource", resource);
		}
		return result;
	}

}

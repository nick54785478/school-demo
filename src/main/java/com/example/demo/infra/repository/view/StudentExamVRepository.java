package com.example.demo.infra.repository.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.exam.aggregate.vo.ExamType;
import com.example.demo.domain.share.command.QueryExamCommand;
import com.example.demo.domain.share.view.StudentExamV;
import com.example.demo.exception.ValidationException;

import jakarta.persistence.criteria.Predicate;

@Repository
public interface StudentExamVRepository extends JpaRepository<StudentExamV, Long> {
	
	List<StudentExamV> findByStudentIdAndSubjectInAndStuClassAndExamDateAndType(String studentId, List<String> subjects,
			String stuClass, Date examDate, String examType);
	
	List<StudentExamV> findByStudentIdAndStuClassAndExamDateAndType(String studentId, String stuClass, Date examDate,
			String examType);

	public static final String STARTDATESUFFIX = " 00:00:00";
	public static final String ENDDATESUFFIX = " 23:59:59";

	default List<StudentExamV> findDataBySpecification(QueryExamCommand command) {
		Specification<StudentExamV> specification = ((root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// 考試類型
			if (StringUtils.isNotBlank(command.getType())) {
				predicates.add(criteriaBuilder
						.and(criteriaBuilder.equal(root.get("type"), ExamType.fromLabel(command.getType()).getCode())));
			}
			// 學生名
			if (StringUtils.isNotBlank(command.getName())) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"), command.getName())));
			}
			// 班級
			if (StringUtils.isNotBlank(command.getStuClass())) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("stuClass"), command.getStuClass())));
			}
			// 科目
			if (StringUtils.isNotBlank(command.getSubject())) {
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("subject"), command.getSubject())));
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// StartDate 有值
			if (StringUtils.isNotBlank(command.getStartDate()) && StringUtils.isBlank(command.getEndDate())) {
				try {
					Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("examDate"),
							simpleDateFormat.parse(command.getStartDate() + STARTDATESUFFIX));
					predicates.add(predicate);
				} catch (ParseException e) {
					throw new ValidationException("VALIDATE_FAILED", "StartDate 有值，但日期轉換發生錯誤");
				}
				// End Date 有值
			} else if (StringUtils.isBlank(command.getStartDate()) && StringUtils.isNotBlank(command.getEndDate())) {
				try {
					Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("examDate"),
							simpleDateFormat.parse(command.getEndDate() + ENDDATESUFFIX));
					predicates.add(predicate);
				} catch (ParseException e) {
					throw new ValidationException("VALIDATE_FAILED", "End Date 有值，但日期轉換發生錯誤");
				}
				// 填表日期都有值
			} else if (StringUtils.isNotBlank(command.getStartDate()) && StringUtils.isNotBlank(command.getEndDate())) {
				try {
					Date startDate = simpleDateFormat.parse(command.getStartDate() + STARTDATESUFFIX);
					Date endDate = simpleDateFormat.parse(command.getEndDate() + ENDDATESUFFIX);
					Predicate predicate = criteriaBuilder.between(root.get("examDate"), startDate, endDate);
					predicates.add(predicate);
				} catch (ParseException e) {
					throw new ValidationException("VALIDATE_FAILED", "填表日期都有值，但日期轉換發生錯誤");
				}
			}

			Predicate[] predicateArray = new Predicate[predicates.size()];
			query.where(criteriaBuilder.and(predicates.toArray(predicateArray)));
			return query.getRestriction();
		});

		return findAll(specification);
	}

	<T> List<StudentExamV> findAll(Specification<T> specification);

}

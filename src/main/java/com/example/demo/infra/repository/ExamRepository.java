package com.example.demo.infra.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.exam.aggregate.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

	List<Exam> findBySubjectAndStuClassAndExamDate(String subject, String stuClass, Date examDate);


}

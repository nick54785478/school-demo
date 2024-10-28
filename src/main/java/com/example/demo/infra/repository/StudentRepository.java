package com.example.demo.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.student.aggregate.Student;
import com.example.demo.domain.student.aggregate.vo.Grade;
import com.example.demo.domain.student.aggregate.vo.StudentClass;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

	List<Student> findByGradeAndStudentClassAndActiveFlag(Grade grade, StudentClass studentClass, String activeFlag);


}

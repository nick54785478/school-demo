package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.teacher.aggregate.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {



}

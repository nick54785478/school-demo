package com.example.demo.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.report.aggregate.ReportSetting;
import com.example.demo.domain.report.aggregate.vo.ReportType;

@Repository
public interface ReportSettingRepository extends JpaRepository<ReportSetting, Long> {

	List<ReportSetting> findByType(ReportType type);

}

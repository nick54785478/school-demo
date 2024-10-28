package com.example.demo.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.option.aggregate.OptionSetting;
import java.util.List;


@Repository
public interface OptionRepository  extends JpaRepository<OptionSetting, Long> {

	List<OptionSetting> findByTypeAndActiveFlag(String type, String activeFlag);
}

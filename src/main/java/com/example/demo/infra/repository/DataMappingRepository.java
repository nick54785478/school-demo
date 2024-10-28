package com.example.demo.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.mapping.aggregate.DataMapping;

@Repository
public interface DataMappingRepository extends JpaRepository<DataMapping, Long> {

	List<DataMapping> findByMappingAndActiveFlag(String mapping, String activeFlag);

	List<DataMapping> findByMappingAndFieldInAndActiveFlag(String mapping, List<String> field, String activeFlag);
}

package com.example.demo.infra.store;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.example.demo.util.BaseDataTransformer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MongodbService {

	/*
	 * 使用 MongoTemplate（選擇性）
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 查詢相關資料 SELECT * FROM Document WHERE {key} = {value};
	 * 
	 * @param key        欄位名稱
	 * @param value      值
	 * @param targetType Class類型 (建議使用 註解 @Document 的 Class，否則會建立新表。)
	 */
	public <T> T findByKey(String key, String value, Class<T> targetType) {
		Query query = new Query(Criteria.where(key).is(value));
		return mongoTemplate.findOne(query, targetType);
	}

	/***
	 * 新增資料
	 * 
	 * @param <T>
	 * @param target Class類型(建議使用 註解 @Document 的 Class，否則會建立新表。)
	 */
	public <T> void create(T target, Class<T> clazz) {

		Class<? extends Object> targetClass = target.getClass();
		if (targetClass.isAssignableFrom(clazz)) {
			T data = BaseDataTransformer.transformData(target, clazz);
			mongoTemplate.insert(data);
		} else {
			log.error("轉換失敗，不新增此筆資料!");
		}
	}

	/**
	 * 更新資料 * SELECT * FROM Document WHERE {queriedTitle} = {queriedValue} * UPDATE
	 * Document SET {updatedTitle} = {newValue}
	 * 
	 * @param queriedField 查詢欄位
	 * @param queriedValue 查詢值
	 * @param updatedField 欲更新的欄位
	 * @param newValue     新值
	 * @param Document     類型
	 */
	public <T> void update(String queriedField, String queriedValue, String updatedField, String newValue,
			Class<T> targetType) {

		// 創建查詢條件，匹配指定 userId 的文檔
		Query query = new Query(Criteria.where(queriedField).is(queriedValue));
		// 創建更新操作，將 特定欄位 值 更新為 新值
		// Update update = new Update().set("city", newValue); 若要更新 city 的 value
		Update update = new Update().set(updatedField, newValue);
		mongoTemplate.updateFirst(query, update, targetType); // 更新第一筆
	}

	/**
	 * 刪除資料 DELETE FROM Document WHERE {queriedField} = {queriedValue}
	 * 
	 * @param queriedField 欲刪除欄位
	 * @param queriedValue 欲刪除欄位對應的值
	 * @param Document     類型
	 */
	public <T> void delete(String queriedField, String queriedValue, Class<T> targetType) {
		// 创建查询条件，匹配指定 條件 的 Document
		Query query = new Query(Criteria.where(queriedField).is(queriedValue));

		// 使用 mongoTemplate 進行刪除操作
		mongoTemplate.remove(query, targetType);
	}

	/**
	 * 使用 Aggregate Framework 進行聚合操作
	 * 
	 * @param field   特定欄位
	 * @param 參數
	 * @param 自定義顯示欄位
	 * @param Class類型
	 */
	public <T> List<T> getDataWithAggregate(String field, Object value, List<String> viewFieldsList,
			Class<T> targetType) {

		// 取得 Document Name;
		String collectionName = this.getCollectionName(targetType);

		String[] viewFields = viewFieldsList.toArray(new String[0]);
		Aggregation aggregation = Aggregation.newAggregation(

				// Criteria 相關操作，gt、lt ...
				// 請自訂邏輯，目前範例為取得大於特定年齡的資料
				Aggregation.match(Criteria.where(field).gt(value)), // 年齡大於特定歲數
				Aggregation.project(viewFields) // 用於投影字段，即欲顯示的欄位。
		);
		return (List<T>) mongoTemplate.aggregate(aggregation, collectionName, targetType).getMappedResults();
	}

	// 取得 Document 名稱
	private String getCollectionName(Class<?> entityClass) {
		MongoPersistentEntity<?> persistentEntity = mongoTemplate.getConverter().getMappingContext()
				.getPersistentEntity(entityClass);
		return Objects.isNull(persistentEntity) ? "" : persistentEntity.getCollection();
	}

}

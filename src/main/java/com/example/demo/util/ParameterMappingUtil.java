package com.example.demo.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Map<屬性, 值> 資料 轉換為 Entity
 */
@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParameterMappingUtil {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 將 Map 的資料設置到實體中
	 * 
	 * @param entity
	 * @param fieldMap Field Map
	 * @return entity
	 */
	public static <T> T setFieldsFromMap(T entity, Map<String, String> fieldMap) {
		// 取得實體的 Class 類型
		Class<?> entityClass = entity.getClass();

		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			String fieldName = entry.getKey();
			String fieldValue = entry.getValue();
			try {
				// 使用反射取得實體的對應屬性
				Field field = entityClass.getDeclaredField(fieldName);
				ReflectionUtils.makeAccessible(field);// 如果函數是私有的，需要設權限
//				field.setAccessible(true); // 設置可訪問性

				// 根據屬性類型進行轉換
				Object convertedValue = convertToFieldType(field, fieldValue);

				// 設置轉換後的值
				field.set(entity, convertedValue);

			} catch (NoSuchFieldException e) {
				log.error("未找到屬性", e);
			} catch (IllegalAccessException e) {
				log.error("設置值發生錯誤", e);
			}
		}

		return entity;
	}

	/**
	 * 根據屬性類型轉換 String 值
	 * 
	 * @param field 參數
	 * @param value 值
	 */
	private static Object convertToFieldType(Field field, String value) {
		Class<?> fieldType = field.getType();

		if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
			// 若有小數點.0，需去除
			return Long.parseLong(value.replace(".0", ""));
		} else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
			// 若有小數點.0，需去除
			return Integer.parseInt(value.replace(".0", ""));
		} else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
			return Double.parseDouble(value);
		} else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
			return Boolean.parseBoolean(value);
		} else if (fieldType.isEnum()) {
			// 轉換 String 為 Enum
			Class<Enum> enumType = (Class<Enum>) fieldType;
			return Enum.valueOf(enumType, value);
		} else if (fieldType.equals(Date.class)) {
			// 轉換 String 為 Date
			try {
				if (checkDate(value)) {
					return DATE_FORMAT.parse(value);
				} else {
					// Excel Date 轉換
					double excelDate = Double.parseDouble(value);
					
					return excelDate;

				}
			} catch (ParseException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Invalid date format for field: " + field.getName());
			}
		} else if (fieldType.equals(BigDecimal.class)) {
			// 轉換 String 為 BigDecimal
			return new BigDecimal(value);
		}

		// 如果是 String 類型或者找不到對應的類型，則直接返回原值
		return value;
	}

	/**
	 * 檢查日期是否為日期格式
	 * 
	 * @param input
	 */
	private static boolean checkDate(String input) {
		// 正則表達式：判斷是否為日期時間格式
		String datePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(datePattern);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 將 Double 轉為 Date
	 * 
	 * @param excelDate
	 * */
	private static Date convertExcelDateToDate(double excelDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long days = (long) excelDate - 2; // Excel 日期要減去 2 天
        calendar.add(Calendar.DATE, (int) days);

        double fractionalDay = excelDate - (long) excelDate;
        int millisecondsInDay = (int) (fractionalDay * 24 * 60 * 60 * 1000);
        calendar.add(Calendar.MILLISECOND, millisecondsInDay);

        return calendar.getTime();
    }

	public static <T> List<String> getFields(T target) {
		// 獲取 Class 的所有字段
		Field[] fields = target.getClass().getDeclaredFields();

		List<String> fieldNames = Arrays.stream(fields).map(Field::getName) // 获取每个字段的名称
				.collect(Collectors.toList());
		return fieldNames;
	}

}

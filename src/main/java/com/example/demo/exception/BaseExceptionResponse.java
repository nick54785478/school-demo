package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回傳訊息定義
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseExceptionResponse {

	private String code;
	
    private String message;

}

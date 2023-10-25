package com.kshfx.core.config;

import com.kshfx.core.utils.JsonWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionConfig {

	@EqualsAndHashCode(callSuper = false)
	@Data
	@NoArgsConstructor
	public static class CustomException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		private Integer code;

		private String message;

		private Object data;
		
		public CustomException(int code) {
			this.code = code;
		}
		
		public CustomException(int code, String msg) {
			this.message = msg;
			this.code = code;
		}

		public CustomException(int code, String msg, Object data) {
			this.message = msg;
			this.code = code;
			this.data = data;
		}
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public JsonWrapper errorHandler(Exception ex) {
		if(ex instanceof CustomException){
			CustomException exception = (CustomException) ex;
			return JsonWrapper.error(exception.getCode(), exception.getMessage());
		}else{
			return JsonWrapper.error(ex.getMessage());
		}
	}
}

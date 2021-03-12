package com.rhlinkcon.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.rhlinkcon.util.Utils;


@ControllerAdvice
public class ExceptionHaddler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(DataIntegretyException.class)
    public ResponseEntity<?> FkViolada(DataIntegretyException ex, HttpServletRequest request) {
        return Utils.badRequest(false, ex.getMessage());
    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {		
		ErroAPI erroAPI = criarListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erroAPI, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private ErroAPI criarListaErros(BindingResult bindingResult){
		List<Erro> errors = new ArrayList<>();	
		for(FieldError fieldError: bindingResult.getFieldErrors()) {
			String title = fieldError.getDefaultMessage();
			String details = fieldError.getField().toString();	
			errors.add(new Erro(title, details));
		}
		ErroAPI erroAPI = new ErroAPI(errors);		
		return erroAPI;
	}
	
	public class ErroAPI {
		private List<Erro> errors = new ArrayList<>();

		public List<Erro> getErrors() {
			return errors;
		}

		public void setErrors(List<Erro> errors) {
			this.errors = errors;
		}

		public ErroAPI(List<Erro> errors) {
			this.errors = errors;
		}
		
		public ErroAPI() {
		}
		

	}
	
	public class Erro {
		
		@JsonInclude(Include.NON_NULL)
		private String code;
		private String title;
		private String details;
		
		public Erro() {
		}
		
		public Erro(String code, String title,String details) {
			this.code = code;
			this.title = title;
			this.details = details;
		}
		
		public Erro(String title,String details) {
			this.title = title;
			this.details = details;
		}
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDetails() {
			return details;
		}
		public void setDetails(String details) {
			this.details = details;
		}	
	}

	
	
}

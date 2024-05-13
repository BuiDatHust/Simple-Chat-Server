package com.example.chatserver.exception;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice 
@Slf4j  
public class GlobalErrorHandler  
{  
    @Autowired
    private ResponseFactory responseFactory;

    @ResponseStatus  
    @ResponseBody  
    @ExceptionHandler(BaseException.class)  
    public ResponseEntity<GeneralResponse<Object>> handleException(BaseException ex){
        log.info("{}", ex);
        if(ex.getDataResponse()==null){
            return responseFactory.fail( ex.getDataResponse(), ex.getResponseStatusCode());
        }
        return responseFactory.fail(ex.getResponseStatusCode()); 
   }

   @ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
    ArrayList<String> errors = new ArrayList<>();

    ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

    Map<String, ArrayList<String>> result = new HashMap<>();
    result.put("errors", errors);

    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
  }
}
package com.wisenut.spring;

import com.wisenut.spring.controller.SearchControllerV2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = SearchControllerV2.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> nullPointerException(MethodArgumentNotValidException e) {


        Map<String, String> data = new HashMap<>();

        for (ObjectError oe : e.getBindingResult()
                               .getAllErrors()) {
            FieldError fieldError = (FieldError) oe;
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            if (data.containsKey(fieldName))
                data.put(fieldName, data.get(fieldName) + " " + errorMessage);
            else
                data.put(fieldName, errorMessage);
        }

        return ResponseEntity
                .badRequest()
                .body(data);
    }


}

package com.twd.SpringSecurityJWT.exception;
/* Created by Arjun Gautam */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MemberNotFoundAdvice {


    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> exceptionHandler(MemberNotFoundException exception){

        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",exception.getMessage());

        return errorMap;

    }

}

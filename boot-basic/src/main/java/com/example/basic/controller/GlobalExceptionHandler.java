package com.example.basic.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler()
    @ResponseBody
    String handleException(Exception e){
        logger.error(e);
        e.printStackTrace();
        return "Exception Deal! " + e.getMessage();
    }
}

package com.example.jpaspringdemo.controllers;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.exceptions.IncorrectParameterException;
import com.example.jpaspringdemo.exceptions.NoSuchElementFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(NoSuchElementFoundException.class)
    public ApiResponse handleNoSuchElementFoundException(NoSuchElementFoundException exception) {
        return new ApiResponse()
                .addError("errorMessage", exception.getMessage())
                .addError("stackTrace", exception.getStackTrace());
    }

    @ResponseBody
    @ExceptionHandler(IncorrectParameterException.class)
    public ApiResponse handleIncorrectParameterException(IncorrectParameterException exception) {
        return new ApiResponse()
                .addError("parameters", exception.getParameters())
                .addError("stackTrace", exception.getStackTrace());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception exception) {
        return new ApiResponse()
                .addError("message", exception.getMessage())
                .addError("stackTrace", exception.getStackTrace());
    }

}

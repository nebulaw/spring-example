package com.example.jpaspringdemo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.Set;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectParameterException extends RuntimeException {

    @Getter
    private Set<String> parameters;

    public IncorrectParameterException() {
        parameters = new HashSet<>();
    }

    public IncorrectParameterException(String parameter) {
        this();
        addParameter(parameter);
    }

    public IncorrectParameterException addParameter(String parameter) {
        parameters.add(parameter);
        return this;
    }

}

package com.example.jpaspringdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchElementFoundException extends RuntimeException {

    public NoSuchElementFoundException(String desc) {
        super(desc);
    }


/*
  Application
  id=1 name=Social Network
  id=2 name=Social Admin
  id=3 name=Messaging Service

  Keywords
  id=1 keyword="" fkApplicationId=2 description_en=... description_ge=... description_de=...
  id=2 fkApplicationId=3 description=...
  id=3 fkApplicationId=2 description=...

  */

    /*
     *
     * Collection - Application Errors
     * id, applicationName, applicationKeyword, errorKeyword, errorDescription{en:"...",ka:"...",ru="..."}
     *
     * */

}



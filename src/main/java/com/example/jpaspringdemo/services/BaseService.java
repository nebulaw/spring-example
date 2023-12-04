package com.example.jpaspringdemo.services;

import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.exceptions.IncorrectParameterException;


public abstract class BaseService {

  protected  <T> void checkData(DataDto<T> dataDto) {
    if (dataDto.getData() == null)
      throw new IncorrectParameterException("data");
  }


}

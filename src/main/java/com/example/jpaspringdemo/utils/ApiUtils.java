package com.example.jpaspringdemo.utils;

import com.example.jpaspringdemo.dtos.ApiResponse;

public class ApiUtils {

  public static ApiResponse getApiResponse(Object object) {
    String name = object.getClass().getSimpleName();
    return new ApiResponse(name, object);
  }

}

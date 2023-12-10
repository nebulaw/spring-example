package com.example.jpaspringdemo.dtos;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataDto<T> {
    private T data;
}

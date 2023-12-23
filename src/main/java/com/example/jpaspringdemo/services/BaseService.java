package com.example.jpaspringdemo.services;

import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.exceptions.IncorrectParameterException;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseService {

    protected <T> void checkData(DataDto<T> dataDto) {
        if (dataDto.getData() == null) {
            throw new IncorrectParameterException("data");
        }
    }

    protected Sort.Direction sortDirection(String direction) {
        return switch (direction) {
            case "desc", "DESC" -> Sort.Direction.DESC;
            default -> Sort.Direction.ASC;
        };
    }

    protected List<Sort.Order> extractOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort.length >= 1 && sort[0].contains(",")) {
            for (String item : sort) {
                String[] order = item.split(",");
                orders.add(new Sort.Order(sortDirection(order[1]), order[0]));
            }
        } else if (sort.length >= 1) {
            orders.add(new Sort.Order(sortDirection(sort[1]), sort[0]));
        }

        return orders;
    }

}

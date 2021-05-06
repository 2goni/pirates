package com.pirates.pirates.domain.store.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoreInfoDto {

    private String name;
    private String owner;
    private String description;
    private Integer level;
    private String address;
    private String phone;
    private List<BusinessTimesDto> businessTimes;

}
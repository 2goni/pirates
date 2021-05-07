package com.pirates.pirates.domain.store.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class StoreInfoDTO {

    private String name;
    private String owner;
    private String description;
    private Integer level;
    private String address;
    private String phone;
    private List<BusinessTimesDTO> businessTimes;

}
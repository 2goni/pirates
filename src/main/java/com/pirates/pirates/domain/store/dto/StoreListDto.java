package com.pirates.pirates.domain.store.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class StoreListDto {

    private String name;
    private String description;
    private Integer level;
    private String businessStatus;

    @Builder
    public StoreListDto(String name, String description, Integer level, String businessStatus){
        this.name = name;
        this.description = description;
        this.level = level;
        this.businessStatus = businessStatus;
    }

}

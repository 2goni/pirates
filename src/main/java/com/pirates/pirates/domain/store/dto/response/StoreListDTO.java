package com.pirates.pirates.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreListDTO {

    private String name;
    private String description;
    private Integer level;
    private String businessStatus;

    @Builder
    public StoreListDTO(String name, String description, Integer level, String businessStatus){
        this.name = name;
        this.description = description;
        this.level = level;
        this.businessStatus = businessStatus;
    }

}

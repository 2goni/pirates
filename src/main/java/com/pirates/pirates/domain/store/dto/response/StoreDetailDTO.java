package com.pirates.pirates.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDetailDTO {
    private Long id;
    private String name;
    private String description;
    private Integer level;
    private String address;
    private String phone;
    private List<BusinessDetailDTO> businessDays;

    @Builder
    public StoreDetailDTO(Long id, String name, String description, Integer level, String address, String phone, List<BusinessDetailDTO> businessDays){
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.address = address;
        this.phone = phone;
        this.businessDays = businessDays;

    }

}

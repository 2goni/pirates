package com.pirates.pirates.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessDetailDTO {
    private String day;
    private String open;
    private String close;
    private String status;

    @Builder
    public BusinessDetailDTO(String day, String open, String close, String status){
        this.day = day;
        this.open = open;
        this.close = close;
        this.status = status;
    }
}

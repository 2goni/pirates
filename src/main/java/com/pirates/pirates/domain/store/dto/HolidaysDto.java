package com.pirates.pirates.domain.store.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HolidaysDto {

    private Long id;
    private List<Date> holidays;

}

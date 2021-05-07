package com.pirates.pirates.domain.store.dto.request;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HolidaysDTO {

    private Long id;
    private List<Date> holidays;

}

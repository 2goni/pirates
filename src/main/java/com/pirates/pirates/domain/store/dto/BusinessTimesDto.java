package com.pirates.pirates.domain.store.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class BusinessTimesDto {
    private String day;
    private String open;
    private String close;

}

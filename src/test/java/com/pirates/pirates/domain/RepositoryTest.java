package com.pirates.pirates.domain;

import com.pirates.pirates.domain.store.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    StoreInfoRepository storeInfoRepository;
    @Autowired
    BusinessTimesRepository businessTimesRepository;
    @Autowired
    HolidaysRepository holidaysRepository;

    @AfterEach
    public void cleanUp(){
        storeInfoRepository.deleteAll();
        businessTimesRepository.deleteAll();
        holidaysRepository.deleteAll();
    }

    @Test
    public void testAddStore_저장(){
        StoreInfo storeInfo = StoreInfo.builder()
                .name("철수네 횟집")
                .owner("김철수")
                .description("인천 수산")
                .level(2)
                .address("")
                .phone("010-0000-0000")
                .build();
        storeInfoRepository.save(storeInfo);
        assertThat(storeInfoRepository.findById(1l).get().getName()).isEqualTo("철수네 횟집");
    }

    @Test
    public void testAddBusinessTimes_저장(){
        BusinessTimes businessTimes = BusinessTimes.builder()
                .day("Monday")
                .open("13:00")
                .close("23:00")
                .build();
        businessTimesRepository.save(businessTimes);
        assertThat(businessTimesRepository.findById(1l).get().getDay()).isEqualTo("Monday");
    }

    @Test
    public void testAddHolidays_저장() throws ParseException {
        StoreInfo storeInfo = StoreInfo.builder()
                .name("안녕")
                .build();
        String sampleDate = "2012-01-12T00:00:00.000";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = simpleDateFormat.parse(sampleDate);
        Holidays holidays = Holidays.builder()
                .holiday(date)
                .storeInfo(storeInfo)
                .build();
        holidaysRepository.save(holidays);
        assertThat(holidaysRepository.findById(1l).get().getHoliday()).isEqualTo(sampleDate);
    }
}

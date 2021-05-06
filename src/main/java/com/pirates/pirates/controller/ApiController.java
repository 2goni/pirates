package com.pirates.pirates.controller;

import com.pirates.pirates.domain.store.dto.HolidaysDto;
import com.pirates.pirates.domain.store.dto.StoreInfoDto;
import com.pirates.pirates.domain.store.dto.StoreListDto;
import com.pirates.pirates.service.ApiService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiController {

    private final ApiService apiService;

    @PostMapping("/addStore")
    public String addStore(@RequestBody StoreInfoDto storeInfoDto){
        if(apiService.checkBusinessTime(storeInfoDto)){
            return "영업시작시간과 종료시간은 같을수없음";
        }
        apiService.addStore(storeInfoDto);
        return "저장되었습니다";
    }

    @PostMapping("/addHolidays")
    public void addHolidays(@RequestBody HolidaysDto holidaysDto){
        apiService.addHolidays(holidaysDto);
    }

    @GetMapping("/listStore")
    public List<StoreListDto> listStore(){
        return apiService.getStoreList();
    }
}

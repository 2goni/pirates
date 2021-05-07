package com.pirates.pirates.controller;

import com.pirates.pirates.domain.store.dto.request.HolidaysDTO;
import com.pirates.pirates.domain.store.dto.request.StoreInfoDTO;
import com.pirates.pirates.domain.store.dto.response.StoreDetailDTO;
import com.pirates.pirates.domain.store.dto.response.StoreListDTO;
import com.pirates.pirates.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ApiController {

    private final ApiService apiService;

    @PostMapping("/addStore")
    public void addStore(@RequestBody StoreInfoDTO storeInfoDto){
        if(apiService.checkBusinessTime(storeInfoDto)){
        }
        apiService.addStore(storeInfoDto);
    }

    @PostMapping("/addHolidays")
    public void addHolidays(@RequestBody HolidaysDTO holidaysDto){
        apiService.addHolidays(holidaysDto);
    }

    @GetMapping("/listStore")
    public List<StoreListDTO> listStore(){
        return apiService.getStoreList();
    }

    @PostMapping("/storeDetail")
    public StoreDetailDTO storeDetail(@RequestBody Map<String,Long> map){
        return apiService.storeDetail(map.get("id"));
    }

    @PostMapping("/deleteStore")
    public void deleteStore(@RequestBody Map<String,Long> map){
        apiService.deleteStore(map.get("id"));
    }
}

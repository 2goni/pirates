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

    //점포 추가 API
    @PostMapping("/addStore")
    public void addStore(@RequestBody StoreInfoDTO storeInfoDto){
        //오픈시간과 마감시간이 같을때 저장안됨
        if(apiService.checkBusinessTime(storeInfoDto)){
        }else {
            apiService.addStore(storeInfoDto);
        }
    }

    //점포 휴무일 등혹 API
    @PostMapping("/addHolidays")
    public void addHolidays(@RequestBody HolidaysDTO holidaysDto){
        apiService.addHolidays(holidaysDto);
    }

    //점포 목록 조회 API
    @GetMapping("/listStore")
    public List<StoreListDTO> listStore(){
        return apiService.getStoreList();
    }

    //점포 상세 정보 조회 API
    @PostMapping("/storeDetail")
    public StoreDetailDTO storeDetail(@RequestBody Map<String,Long> map){
        return apiService.storeDetail(map.get("id"));
    }

    //점포 삭제 API
    @PostMapping("/deleteStore")
    public void deleteStore(@RequestBody Map<String,Long> map){
        apiService.deleteStore(map.get("id"));
    }
}

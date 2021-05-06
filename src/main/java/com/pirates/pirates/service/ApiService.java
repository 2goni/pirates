package com.pirates.pirates.service;

import com.pirates.pirates.domain.store.*;
import com.pirates.pirates.domain.store.dto.BusinessTimesDto;
import com.pirates.pirates.domain.store.dto.HolidaysDto;
import com.pirates.pirates.domain.store.dto.StoreInfoDto;
import com.pirates.pirates.domain.store.dto.StoreListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ApiService {

    private final StoreInfoRepository storeInfoRepository;
    private final BusinessTimesRepository businessTimesRepository;
    private final HolidaysRepository holidaysRepository;

    public void addStore(StoreInfoDto storeInfoDto){

        StoreInfo storeInfo = StoreInfo.builder()
                .name(storeInfoDto.getName())
                .owner(storeInfoDto.getOwner())
                .description(storeInfoDto.getDescription())
                .level(storeInfoDto.getLevel())
                .address(storeInfoDto.getAddress())
                .phone(storeInfoDto.getPhone())
                .build();

        storeInfoRepository.save(storeInfo);

        for(BusinessTimesDto businessTimesDto : storeInfoDto.getBusinessTimes()) {
            businessTimesRepository.save(BusinessTimes.builder()
                    .day(businessTimesDto.getDay())
                    .open(businessTimesDto.getOpen())
                    .close(businessTimesDto.getClose())
                    .storeInfo(storeInfo)
                    .build());
        }
    }

    public void addHolidays(HolidaysDto holidaysDto){
        Optional<StoreInfo> takeStoreInfo = storeInfoRepository.findById(holidaysDto.getId());
        if(takeStoreInfo.isPresent()){
            StoreInfo storeInfo = takeStoreInfo.get();
            for(Date date : holidaysDto.getHolidays())
                holidaysRepository.save(Holidays.builder()
                        .holiday(date)
                        .storeInfo(storeInfo)
                        .build());
        }
    }

    public List<StoreListDto> getStoreList(){
        List<StoreListDto> storeList = new ArrayList<>();
        List<StoreInfo> storeInfoList = storeInfoRepository.findAll(Sort.by(Sort.Direction.ASC,"level"));
        for(StoreInfo storeInfo: storeInfoList){
            StoreListDto storeListDto = StoreListDto.builder()
                    .name(storeInfo.getName())
                    .description(storeInfo.getDescription())
                    .level(storeInfo.getLevel())
                    .businessStatus(getBusinessStatus(storeInfo))
                    .build();
            storeList.add(storeListDto);
        }
        return storeList;
    }

    public boolean checkBusinessTime(StoreInfoDto storeInfoDto){
        for(BusinessTimesDto businessTimesDto : storeInfoDto.getBusinessTimes()) {
            if(businessTimesDto.getOpen().equals(businessTimesDto.getClose())){
                return true;
            }
        }
        return false;
    }

    public String getBusinessStatus(StoreInfo storeInfo){
        Date date = new Date();
        if(holidaysRepository.findByStoreInfoAndHoliday(storeInfo, date).isPresent()){
            return "HOLIDAY";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
        Calendar cal = Calendar.getInstance();
        String[] week = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        List<BusinessTimes> businessTimesList =  businessTimesRepository.findAllByStoreInfo(storeInfo);
        for(BusinessTimes businessTimes : businessTimesList){
            if(businessTimes.getOpen() == simpleDateFormat.format(date)
//////////
        }
        return "OPEN";

        return "CLOSE";
    }
}

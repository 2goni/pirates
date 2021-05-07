package com.pirates.pirates.service;

import com.pirates.pirates.domain.store.*;
import com.pirates.pirates.domain.store.dto.request.BusinessTimesDTO;
import com.pirates.pirates.domain.store.dto.request.HolidaysDTO;
import com.pirates.pirates.domain.store.dto.request.StoreInfoDTO;
import com.pirates.pirates.domain.store.dto.response.BusinessDetailDTO;
import com.pirates.pirates.domain.store.dto.response.StoreDetailDTO;
import com.pirates.pirates.domain.store.dto.response.StoreListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ApiService {

    private final StoreInfoRepository storeInfoRepository;
    private final BusinessTimesRepository businessTimesRepository;
    private final HolidaysRepository holidaysRepository;

    public void addStore(StoreInfoDTO storeInfoDto){

        StoreInfo storeInfo = StoreInfo.builder()
                .name(storeInfoDto.getName())
                .owner(storeInfoDto.getOwner())
                .description(storeInfoDto.getDescription())
                .level(storeInfoDto.getLevel())
                .address(storeInfoDto.getAddress())
                .phone(storeInfoDto.getPhone())
                .build();

        storeInfoRepository.save(storeInfo);

        for(BusinessTimesDTO businessTimesDto : storeInfoDto.getBusinessTimes()) {
            businessTimesRepository.save(BusinessTimes.builder()
                    .day(businessTimesDto.getDay())
                    .open(businessTimesDto.getOpen())
                    .close(businessTimesDto.getClose())
                    .storeInfo(storeInfo)
                    .build());
        }
    }

    public void addHolidays(HolidaysDTO holidaysDto){
        Optional<StoreInfo> takeStoreInfo = storeInfoRepository.findById(holidaysDto.getId());
        if(takeStoreInfo.isPresent()){
            StoreInfo storeInfo = takeStoreInfo.get();
            for(Date date : holidaysDto.getHolidays())
                if(holidaysRepository.findByStoreInfoAndHoliday(storeInfo,date).isPresent()){
                    break;
                }else {
                    holidaysRepository.save(Holidays.builder()
                            .holiday(date)
                            .storeInfo(storeInfo)
                            .build());
                }
        }
    }

    public List<StoreListDTO> getStoreList(){
        Date date = new Date();
        List<StoreListDTO> storeList = new ArrayList<>();
        List<StoreInfo> storeInfoList = storeInfoRepository.findAll(Sort.by(Sort.Direction.ASC,"level"));
        for(StoreInfo storeInfo: storeInfoList){
            StoreListDTO storeListDto = StoreListDTO.builder()
                    .name(storeInfo.getName())
                    .description(storeInfo.getDescription())
                    .level(storeInfo.getLevel())
                    .businessStatus(getBusinessStatus(storeInfo,date))
                    .build();
            storeList.add(storeListDto);
        }
        return storeList;
    }

    public StoreDetailDTO storeDetail(Long id){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        int calDate = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.setTime(date);
        StoreDetailDTO storeDetailDTO = null;
        Optional<StoreInfo> findStoreInfo = storeInfoRepository.findById(id);
        List<BusinessDetailDTO> businessDetailDTO = new ArrayList<>();
        if(findStoreInfo.isPresent()){
            StoreInfo storeInfo = findStoreInfo.get();
            for(int i=0; i<3; i++){
                Optional<BusinessTimes> getBusinessTimes = businessTimesRepository.findByStoreInfoAndAndDay(storeInfo, getDay(calDate));
                if(getBusinessTimes.isPresent()) {
                    date = calendar.getTime();
                    businessDetailDTO.add(BusinessDetailDTO.builder()
                            .day(getBusinessTimes.get().getDay())
                            .open(getBusinessTimes.get().getOpen())
                            .close(getBusinessTimes.get().getClose())
                            .status(getBusinessStatus(storeInfo, date))
                            .build());
                }
                calendar.add(Calendar.DATE,1);
                calDate++;
            }
            storeDetailDTO = StoreDetailDTO.builder()
                    .id(storeInfo.getStoreInfoId())
                    .name(storeInfo.getName())
                    .description(storeInfo.getDescription())
                    .level(storeInfo.getLevel())
                    .address(storeInfo.getAddress())
                    .phone(storeInfo.getPhone())
                    .businessDays(businessDetailDTO)
                    .build();
        }
        return storeDetailDTO;
    }

    public void deleteStore(Long id){
        storeInfoRepository.deleteById(id);
    }


    public boolean checkBusinessTime(StoreInfoDTO storeInfoDto){
        for(BusinessTimesDTO businessTimesDto : storeInfoDto.getBusinessTimes()) {
            if(businessTimesDto.getOpen().equals(businessTimesDto.getClose())){
                return true;
            }
        }
        return false;
    }

    public String getBusinessStatus(StoreInfo storeInfo, Date date){
        if(holidaysRepository.findByStoreInfoAndHoliday(storeInfo, date).isPresent()){
            return "HOLIDAY";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int calDate = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Optional<BusinessTimes> businessTimes = businessTimesRepository.findByStoreInfoAndAndDay(storeInfo,getDay(calDate));
        if(businessTimes.isPresent()){
            int open = getMinute(businessTimes.get().getOpen());
            int close = getMinute(businessTimes.get().getClose());
            int now = getMinute(simpleDateFormat.format(date));
            System.out.println(now);
            System.out.println(open);
            System.out.println(close);
            if(open <= now && close >= now)
                    return "OPEN";
                }
        return "CLOSE";
    }

    public int getMinute(String date){
        String[] time = date.split(":");
        return (Integer.parseInt(time[0])*60) + (Integer.parseInt(time[1]));
    }

    public String getDay(int date){
        switch (date){
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return getDay(date -7);
        }

    }
}

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

    //점포 추가 메소드
    public void addStore(StoreInfoDTO storeInfoDto) {
        StoreInfo storeInfo = StoreInfo.builder()
                .name(storeInfoDto.getName())
                .owner(storeInfoDto.getOwner())
                .description(storeInfoDto.getDescription())
                .level(storeInfoDto.getLevel())
                .address(storeInfoDto.getAddress())
                .phone(storeInfoDto.getPhone())
                .build();

        //storeInfo 테이블에 저장
        storeInfoRepository.save(storeInfo);

        for (BusinessTimesDTO businessTimesDto : storeInfoDto.getBusinessTimes()) {
            //businessTimes 테이블에 저장
            businessTimesRepository.save(BusinessTimes.builder()
                    .day(businessTimesDto.getDay())
                    .open(businessTimesDto.getOpen())
                    .close(businessTimesDto.getClose())
                    .storeInfo(storeInfo)
                    .build());
        }
    }

    //휴무일 등록 메소드
    public void addHolidays(HolidaysDTO holidaysDto) {
        Optional<StoreInfo> takeStoreInfo = storeInfoRepository.findById(holidaysDto.getId());
        if (takeStoreInfo.isPresent()) {
            StoreInfo storeInfo = takeStoreInfo.get();
            for (Date date : holidaysDto.getHolidays())
                //같은점포에서 같은날에 휴무 중복 등록시 등록 안됨
                if (holidaysRepository.findByStoreInfoAndHoliday(storeInfo, date).isPresent()) {
                    break;
                } else {
                    //holidays 테이블에 저장
                    holidaysRepository.save(Holidays.builder()
                            .holiday(date)
                            .storeInfo(storeInfo)
                            .build());
                }
        }
    }

    public List<StoreListDTO> getStoreList() {
        Date date = new Date();
        List<StoreListDTO> storeList = new ArrayList<>();
        List<StoreInfo> storeInfoList = storeInfoRepository.findAll(Sort.by(Sort.Direction.ASC, "level"));
        for (StoreInfo storeInfo : storeInfoList) {
            StoreListDTO storeListDto = StoreListDTO.builder()
                    .name(storeInfo.getName())
                    .description(storeInfo.getDescription())
                    .level(storeInfo.getLevel())
                    .businessStatus(getBusinessStatus(storeInfo, date))
                    .build();
            storeList.add(storeListDto);
        }
        return storeList;
    }

    public StoreDetailDTO storeDetail(Long id) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        int calDate = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.setTime(date);
        StoreDetailDTO storeDetailDTO = null;
        Optional<StoreInfo> findStoreInfo = storeInfoRepository.findById(id);
        List<BusinessDetailDTO> businessDetailDTO = new ArrayList<>();
        if (findStoreInfo.isPresent()) {
            StoreInfo storeInfo = findStoreInfo.get();
            for (int i = 0; i < 3; i++) {
                Optional<BusinessTimes> getBusinessTimes = businessTimesRepository.findByStoreInfoAndAndDay(storeInfo, getDay(calDate));
                if (getBusinessTimes.isPresent()) {
                    date = calendar.getTime();
                    businessDetailDTO.add(BusinessDetailDTO.builder()
                            .day(getBusinessTimes.get().getDay())
                            .open(getBusinessTimes.get().getOpen())
                            .close(getBusinessTimes.get().getClose())
                            .status(getBusinessStatus(storeInfo, date))
                            .build());
                }
                calendar.add(Calendar.DATE, 1);
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

    public void deleteStore(Long id) {
        storeInfoRepository.deleteById(id);
    }


    public boolean checkBusinessTime(StoreInfoDTO storeInfoDto) {
        for (BusinessTimesDTO businessTimesDto : storeInfoDto.getBusinessTimes()) {
            if (businessTimesDto.getOpen().equals(businessTimesDto.getClose())) {
                return true;
            }
        }
        return false;
    }

    //영업 상태 반환 메소드
    public String getBusinessStatus(StoreInfo storeInfo, Date date) {
        //오늘 휴무일시 HOLIDAY 반환
        if (holidaysRepository.findByStoreInfoAndHoliday(storeInfo, date).isPresent()) {
            return "HOLIDAY";
        }
        //오픈시간 보다 늦었고 마감시감보다 이르면 OPEN 반환
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int calDate = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Optional<BusinessTimes> businessTimes = businessTimesRepository.findByStoreInfoAndAndDay(storeInfo, getDay(calDate));
        if (businessTimes.isPresent()) {
            int open = getMinute(businessTimes.get().getOpen());
            int close = getMinute(businessTimes.get().getClose());
            int now = getMinute(simpleDateFormat.format(date));
            if (open <= now && close >= now) {
                return "OPEN";
            }
        }
        //그외 CLOSE 반환
        return "CLOSE";
    }

    //시간 분으로 바꿔주는 메소드
    public int getMinute(String date) {
        String[] time = date.split(":");
        return (Integer.parseInt(time[0]) * 60) + (Integer.parseInt(time[1]));
    }

    //요일 반환 메소드
    public String getDay(int date) {
        switch (date) {
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
                return getDay(date - 7);
        }

    }
}

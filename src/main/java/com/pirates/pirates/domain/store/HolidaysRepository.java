package com.pirates.pirates.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface HolidaysRepository extends JpaRepository<Holidays, Long> {
    Optional<Holidays> findByStoreInfoAndHoliday(StoreInfo storeInfoId, Date holiday);
}

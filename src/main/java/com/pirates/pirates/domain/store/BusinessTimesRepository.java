package com.pirates.pirates.domain.store;

import org.h2.store.DataReader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BusinessTimesRepository extends JpaRepository<BusinessTimes, Long> {
    List<BusinessTimes> findAllByStoreInfo(StoreInfo storeInfo);
}

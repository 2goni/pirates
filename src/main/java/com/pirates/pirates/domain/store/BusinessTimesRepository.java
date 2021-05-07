package com.pirates.pirates.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessTimesRepository extends JpaRepository<BusinessTimes, Long> {
    Optional<BusinessTimes> findByStoreInfoAndAndDay(StoreInfo storeInfo,String day);
}

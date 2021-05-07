package com.pirates.pirates.domain.store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//점포 테이블
@Getter
@NoArgsConstructor
@Entity
public class StoreInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeInfoId;

    private String name;

    private String owner;

    private String description;

    private Integer level;

    private String address;

    private String phone;

    @Builder
    public StoreInfo(String name, String owner, String description, Integer level, String address, String phone){
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.level = level;
        this.address = address;
        this.phone = phone;
    }
}

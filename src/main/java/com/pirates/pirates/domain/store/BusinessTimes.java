package com.pirates.pirates.domain.store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class BusinessTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BusinessTimesId;

    private String day;

    private String open;

    private String close;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "storeInfoId")
    private StoreInfo storeInfo;

    @Builder
    public BusinessTimes (String day, String open, String close, StoreInfo storeInfo){
        this.day = day;
        this.open = open;
        this.close = close;
        this.storeInfo= storeInfo;

    }

}

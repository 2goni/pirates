package com.pirates.pirates.domain.store;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Holidays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holidaysId;

    @Temporal(TemporalType.DATE)
    private Date holiday;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "storeInfoId")
    private StoreInfo storeInfo;

    @Builder
    public Holidays(Date holiday, StoreInfo storeInfo){
            this.holiday = holiday;
            this.storeInfo = storeInfo;
    }
}

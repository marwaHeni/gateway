package com.example.servicecompany.web.vm;

import java.math.BigDecimal;
import java.util.UUID;

public class StatList {

    String date;
    String stateProvince;
    Long numberOfSubscriptions;
    Long numberOfInactivesSubscriptions;
    BigDecimal amount;
    String longitude;
    String latitude;


    public StatList() {
    }

    public StatList(String stateProvince, Long numberOfSubscriptions, BigDecimal amount) {
        this.stateProvince = stateProvince;
        this.numberOfSubscriptions = numberOfSubscriptions;
        this.amount = amount;
    }



    public StatList(Long numberOfSubscriptions, BigDecimal amount) {
        this.numberOfSubscriptions = numberOfSubscriptions;
        this.amount = amount;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public Long getNumberOfSubscriptions() {
        return numberOfSubscriptions;
    }

    public void setNumberOfSubscriptions(Long numberOfSubscriptions) {
        this.numberOfSubscriptions = numberOfSubscriptions;
    }

    public Long getNumberOfInactivesSubscriptions() {
        return numberOfInactivesSubscriptions;
    }

    public void setNumberOfInactivesSubscriptions(Long numberOfInactivesSubscriptions) {
        this.numberOfInactivesSubscriptions = numberOfInactivesSubscriptions;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

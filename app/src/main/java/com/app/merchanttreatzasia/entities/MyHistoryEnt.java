package com.app.merchanttreatzasia.entities;

/**
 * Created by khan_muhammad on 9/14/2017.
 */

public class MyHistoryEnt {

    String dealImage;
    String flatePercentRate;
    String companyName;
    String totalRate;
    String remainingRate;
    String redemptionDate;

    public MyHistoryEnt(String dealImage, String flatePercentRate,  String companyName, String totalRate, String remainingRate, String redemptionDate){
        setDealImage(dealImage);
        setFlatePercentRate(flatePercentRate);
        setCompanyName(companyName);
        setTotalRate(totalRate);
        setRemainingRate(remainingRate);
        setRedemptionDate(redemptionDate);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDealImage() {
        return dealImage;
    }

    public void setDealImage(String dealImage) {
        this.dealImage = dealImage;
    }

    public String getFlatePercentRate() {
        return flatePercentRate;
    }

    public void setFlatePercentRate(String flatePercentRate) {
        this.flatePercentRate = flatePercentRate;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public String getRemainingRate() {
        return remainingRate;
    }

    public void setRemainingRate(String remainingRate) {
        this.remainingRate = remainingRate;
    }

    public String getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(String redemptionDate) {
        this.redemptionDate = redemptionDate;
    }
}

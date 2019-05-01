package com.example.marti.projecte_uf1.model;

public class PersoCloth {

    private int donorId;
    private int clothClassification;
    private int clothColor;
    private int clothSize;
    private int clothGender;
    private int currPunts;
    private int punts;
    private int pecesDonadesTotal;

    public PersoCloth(int donorId, int clothClassification, int clothColor, int clothSize, int clothGender, int currPunts, int punts, int pecesDonadesTotal) {
        this.donorId = donorId;
        this.clothClassification = clothClassification;
        this.clothColor = clothColor;
        this.clothSize = clothSize;
        this.clothGender = clothGender;
        this.currPunts = currPunts;
        this.punts = punts;
        this.pecesDonadesTotal = pecesDonadesTotal;
    }

    public int getDonorId() {
        return donorId;
    }

    public int getClothClassification() {
        return clothClassification;
    }

    public int getClothColor() {
        return clothColor;
    }

    public int getClothSize() {
        return clothSize;
    }

    public int getClothGender() {
        return clothGender;
    }

    public int getCurrPunts() {
        return currPunts;
    }

    public int getPunts() {
        return punts;
    }

    public int getPecesDonadesTotal() {
        return pecesDonadesTotal;
    }

}

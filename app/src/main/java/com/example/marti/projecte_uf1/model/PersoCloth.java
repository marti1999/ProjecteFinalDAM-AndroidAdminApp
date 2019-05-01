package com.example.marti.projecte_uf1.model;

public class PersoCloth {

    private int donorId;
    private int clothClassification;
    private int clothColor;
    private int clothSize;
    private int clothGender;
    private int points;

    public PersoCloth(int donorId, int clothClassification, int clothColor, int clothSize, int clothGender, int points) {
        this.donorId = donorId;
        this.clothClassification = clothClassification;
        this.clothColor = clothColor;
        this.clothSize = clothSize;
        this.clothGender = clothGender;
        this.points = points;
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


    public int getPoints() {
        return points;
    }



    @Override
    public String toString() {
        return "{" +
                "donorId:" + donorId +
                ", clothClassification:" + clothClassification +
                ", clothColor:" + clothColor +
                ", clothSize:" + clothSize +
                ", clothGender:" + clothGender +
                ", points:" + points +
                '}';
    }
}

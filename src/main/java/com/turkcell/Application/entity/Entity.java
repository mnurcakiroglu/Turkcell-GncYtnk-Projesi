package com.turkcell.Application.entity;

public class Entity {
    private String Marka;

    public String getMarka() {
        return Marka;
    }

    public void setMarka(String marka) {
        Marka = marka;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getSinif() {
        return Sinif;
    }

    public void setSinif(String sinif) {
        Sinif = sinif;
    }

    private String Model;
    private String Sinif;
}

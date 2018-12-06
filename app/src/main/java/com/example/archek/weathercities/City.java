package com.example.archek.weathercities;

public class City {
    String cityName;
    String citySize;
    double winter;
    double spring;
    double summer;
    double autumn;
    double cityClimate = (winter + spring + summer + autumn) / 4;

    public City(String cityName, String citySize, double cityClimateWinter, double cityClimateSpring, double cityClimateSummer, double cityClimateAutumn) {
        this.cityName = cityName;
        this.citySize = citySize;
        this.winter = cityClimateWinter;
        this.spring = cityClimateSpring;
        this.summer = cityClimateSummer;
        this.autumn = cityClimateAutumn;
    }
    public String getCityName() {
        return cityName;
    }

    public String getCitySize() {
        return citySize;
    }

    public double getCityClimate() {
        return cityClimate;
    }
    public double getWinter() {
        return winter;
    }

    public double getSpring() {
        return spring;
    }

    public double getSummer() {
        return summer;
    }

    public double getAutumn() {
        return autumn;
    }

}

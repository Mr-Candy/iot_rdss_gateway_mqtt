package com.rd.iot_rdss_gateway.redis;

import java.io.Serializable;

/**
 * @program: iot_rdss_gateway
 * @description: redis实体类
 * @author: laoXue
 * @create: 2019-10-24 18:55
 **/
public class ShiShiData implements Serializable {
    private static final long serialVersionUID = 1L;
    private double lng;
    private double lat;
    private double speed;
    private double direction;
    private double temperature;
    private double pressure;
    private double level;
    private double electric;
    private String uploadTime;

    public ShiShiData(double lng, double lat, double speed, double direction, double temperature, double pressure, double level, double electric, String uploadTime) {
        this.lng = lng;
        this.lat = lat;
        this.speed = speed;
        this.direction = direction;
        this.temperature = temperature;
        this.pressure = pressure;
        this.level = level;
        this.electric = electric;
        this.uploadTime = uploadTime;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getElectric() {
        return electric;
    }

    public void setElectric(double electric) {
        this.electric = electric;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}

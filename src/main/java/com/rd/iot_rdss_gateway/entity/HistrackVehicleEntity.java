package com.rd.iot_rdss_gateway.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @program: iot_rdss_gateway
 * @description: 罐体历史表
 * @author: laoXue
 * @create: 2019-10-11 15:27
 **/
@Table(name = "histrack_vehicle_g")
public class HistrackVehicleEntity implements Serializable {
    private static final long serialVersionUID = -771410234905970287L;
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "device_id")
    private long deviceId;
    @Column(name = "lng")
    private double lng;
    @Column(name = "lat")
    private double lat;
    @Column(name = "speed")
    private double speed;
    @Column(name = "direction")
    private double direction;
    @Column(name = "temperature")
    private double temperature;
    @Column(name = "pressure")
    private double pressure;
    @Column(name = "level")
    private double level;
    @Column(name = "electric")
    private double electric;
    @Column(name = "upload_time")
    private Timestamp uploadTime = new Timestamp(System.currentTimeMillis());

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
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

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }
}

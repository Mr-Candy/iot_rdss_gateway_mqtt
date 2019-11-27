package com.rd.iot_rdss_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：Mr-Candy
 * @date ：Created in 2019-10-28 13:43
 * @description：netty参数配置
 * @modified By：
 * @version: 1.0
 */
@Configuration
public class NettyConfig {
    @Value("${server.serialPort}")
    private String serialPort;

    @Value("${server.baudRate}")
    private int baudRate;

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }
}

package com.rd.iot_rdss_gateway.process.processTask.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/10/22 09:18
 * @Version V1.0
 */
public class MessageModel implements Serializable {
    private byte shiBieMark;//识别标识
    private byte zhenMark;//帧类型标识
    private byte zhenNum;//帧数据包个数
    private boolean buChuanMark = false;//补传标识，false-实时，true-补传
    private byte[] body;//帧数据内容

    public MessageModel(byte shiBieMark, byte zhenMark, byte zhenNum, boolean buChuanMark, byte[] body) {
        this.shiBieMark = shiBieMark;
        this.zhenMark = zhenMark;
        this.zhenNum = zhenNum;
        this.buChuanMark = buChuanMark;
        this.body = body;
    }

    public byte getShiBieMark() {
        return shiBieMark;
    }

    public void setShiBieMark(byte shiBieMark) {
        this.shiBieMark = shiBieMark;
    }

    public byte getZhenMark() {
        return zhenMark;
    }

    public void setZhenMark(byte zhenMark) {
        this.zhenMark = zhenMark;
    }

    public byte getZhenNum() {
        return zhenNum;
    }

    public void setZhenNum(byte zhenNum) {
        this.zhenNum = zhenNum;
    }

    public boolean isBuChuanMark() {
        return buChuanMark;
    }

    public void setBuChuanMark(boolean buChuanMark) {
        this.buChuanMark = buChuanMark;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "shiBieMark=" + shiBieMark +
                ", zhenMark=" + zhenMark +
                ", zhenNum=" + zhenNum +
                ", buChuanMark=" + buChuanMark +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}

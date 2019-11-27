package com.rd.iot_rdss_gateway.process.processTask.process;

import com.rd.iot_rdss_gateway.dao.HistrackVehicleDao;
import com.rd.iot_rdss_gateway.entity.HistrackVehicleEntity;
import com.rd.iot_rdss_gateway.process.processTask.interfaces.ReceiveInterface;
import com.rd.iot_rdss_gateway.process.processTask.model.MessageModel;
import com.rd.iot_rdss_gateway.redis.ShiShiData;
import com.rd.iot_rdss_gateway.util.ASCIIUtil;
import com.rd.iot_rdss_gateway.util.DateUtil;
import com.rd.iot_rdss_gateway.util.SpringUtil;
import com.rd.iot_rdss_gateway.util.StringByteConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @Description:
 * @Author 老薛
 * @Date 2019/7/3 09:24
 * @Version V1.0
 */
public class RdssLocationTask implements ReceiveInterface {
    private final static Logger logger = LoggerFactory.getLogger(RdssLocationTask.class);

    private MessageModel msg;

    public RdssLocationTask(MessageModel msg) {
        this.msg = msg;
    }

    @Override
    public void handle() {
        int num = msg.getZhenNum();
        if (msg.getBody().length == 0 || msg.getBody().length / 23 != num) {
            logger.error("帧数据内容不全，结束处理，帧数据内容：" + StringByteConvertor.bytesToHexString(msg.getBody()));
            return;
        }
        try {
            DataInputStream data = new DataInputStream(new ByteArrayInputStream(msg.getBody()));
            for (int i = 0; i < num; i++) {
                byte[] bs = new byte[3];
                data.read(bs);
                int terminalId = Integer.parseInt(StringByteConvertor.bytesToHexString(bs), 16);//设备编号
                bs = new byte[4];
                data.read(bs);
                int var = ASCIIUtil.parseHex4(StringByteConvertor.bytesToHexString(bs));
                double lon = ASCIIUtil.parseWeizhi(var);//经度
                data.read(bs);
                var = ASCIIUtil.parseHex4(StringByteConvertor.bytesToHexString(bs));
                double lat = ASCIIUtil.parseWeizhi(var);//纬度

                int speed = data.readUnsignedByte();//速度
                double direction = (double) data.readUnsignedShort() / 10.0;//方向
                bs = new byte[2];
                data.read(bs);
                double temp = (double) ASCIIUtil.parseHex2(StringByteConvertor.bytesToHexString(bs)) / 10;//罐体温度
           //     double temp = (double) data.readShort() / 10.0;
                double pressure = (double) data.readUnsignedShort() / 1000.0;//压力
                double level = (double) data.readUnsignedShort() / 100.0;//液位
                double electric = data.readUnsignedByte()/10.0;//电量
                bs = new byte[6];
                data.read(bs);
                String time = DateUtil.parseTime(StringByteConvertor.bytesToHexString(bs));            //时间

                logger.info("数据包个数:" + num + " 是不是补传:" + msg.isBuChuanMark() + " 设备编号:" + terminalId + " 经度:" + lon + " 纬度:" + lat + " 速度:" + speed
                        + " 方向:" + direction + " 罐体温度:" + temp + " 压力:" + pressure + " 液位:" + level + " 电池电量:" + electric + " 时间(UTC):" + time);

                if(Math.abs(lon)<0.0001 && Math.abs(lat)<0.0001) {
                    logger.info("未定位数据，舍弃");
                }else{
                    HistrackVehicleEntity entity = new HistrackVehicleEntity();
                    entity.setDeviceId(terminalId);
                    entity.setLng(lon);
                    entity.setLat(lat);
                    entity.setSpeed(speed);
                    entity.setDirection(direction);
                    entity.setTemperature(temp);
                    entity.setPressure(pressure);
                    entity.setLevel(level);
                    entity.setElectric(electric);
                    //    Timestamp uploadTime = DateUtil.utcToLocal(time);
                    Timestamp uploadTime = DateUtil.StringToTimestamp(time);
                    entity.setUploadTime(uploadTime);
                    SpringUtil.getBean(HistrackVehicleDao.class).save(entity);
                    ShiShiData redisData = new ShiShiData(lon, lat, speed, direction, temp, pressure, level, electric, time/*DateUtil.TimestampToString(uploadTime, DateUtil.DEFUALT_SECOND)*/);
                    SpringUtil.getBean("redisLocationTemplate", RedisTemplate.class).opsForValue().set(String.valueOf(terminalId), redisData/*, 60, TimeUnit.MINUTES*/);
                    logger.info("数据已存储HistrackVehicle_g表中");
                }
            }
        } catch (IOException e) {
            logger.error("该帧数据内容解析异常，帧数据内容：" + StringByteConvertor.bytesToHexString(msg.getBody()));
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}

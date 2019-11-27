package com.rd.iot_rdss_gateway.dao;

import com.rd.iot_rdss_gateway.entity.HistrackVehicleEntity;
import com.rd.iot_rdss_gateway.util.IbaseDao;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @Description:
 * @Author 老薛
 * @Date 2018/12/7 10:05
 * @Version V1.0
 */
@Mapper
public interface HistrackVehicleDao extends IbaseDao<HistrackVehicleEntity> {
    @Insert("insert into histrack_vehicle_g(uuid,device_id,lng,lat,speed,direction,temperature,pressure,level,electric,upload_time) values(#{uuid}, #{deviceId},#{lng},#{lat},#{speed},#{direction},#{temperature},#{pressure},#{level},#{electric},#{uploadTime})")
    @SelectKey(keyProperty = "uuid", resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '') as id from dual")
    int save(HistrackVehicleEntity entity);
}

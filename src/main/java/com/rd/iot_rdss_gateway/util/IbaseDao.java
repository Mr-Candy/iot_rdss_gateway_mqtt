package com.rd.iot_rdss_gateway.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Description:通用Mapper接口
 * @Author 老薛
 * @Date 2018/12/6 19:11
 * @Version V1.0
 */
public interface IbaseDao<T> extends Mapper<T>, MySqlMapper<T> {
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}

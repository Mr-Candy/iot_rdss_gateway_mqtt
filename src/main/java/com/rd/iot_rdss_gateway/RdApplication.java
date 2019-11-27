package com.rd;

import com.rd.iot_rdss_gateway.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@Import(SpringUtil.class)
@MapperScan("com.rd.iot_rdss_gateway.dao")
public class RdApplication {

    public static void main(String[] args) {

        SpringApplication.run(RdApplication.class, args);
    }

}

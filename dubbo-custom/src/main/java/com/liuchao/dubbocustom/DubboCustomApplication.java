package com.liuchao.dubbocustom;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.liuchao"},exclude = DataSourceAutoConfiguration.class)
@EnableDubboConfiguration
public class DubboCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCustomApplication.class, args);
    }

}

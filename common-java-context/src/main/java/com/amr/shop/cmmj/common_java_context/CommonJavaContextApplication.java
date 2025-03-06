package com.amr.shop.cmmj.common_java_context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class CommonJavaContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonJavaContextApplication.class, args);
    }
}

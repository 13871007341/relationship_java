package com.shuwen.kinship_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class KinshipCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(KinshipCalculatorApplication.class, args);
    }

}

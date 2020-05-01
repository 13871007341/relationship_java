package com.shuwen.kinship_calculator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
class KinshipCalculatorApplicationTests {


    @Test
    public void test01() {
        String str = ",0,xs,h";
        String pattern = "^,x([sb])$";
        Matcher matcher = Pattern.compile(pattern).matcher(str);
        if (matcher.find()) {
            log.info("=========匹配==========");
        }

    }

}

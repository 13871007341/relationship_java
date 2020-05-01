package com.shenzhen.kinship_calculator.utils.validator;

import com.shenzhen.kinship_calculator.utils.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isInValid(String str, String message) {
        int length=str.length();
        if (length!=8 && length!=11) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}

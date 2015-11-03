package com.pdk.manage.util;

import java.math.BigDecimal;

/**
 * Created by 程祥 on 15/10/12.
 * Function： 金额工具类
 */
public class MoneyUtil {

    public static BigDecimal safeAdd(BigDecimal one,BigDecimal two){
        BigDecimal result = new BigDecimal(0.00);
        if(null==one && null == two){
            return result;
        }
        if(null == one){
            return two;
        }
        if(null == two){
            return one;
        }

        return one.add(two);

    }


}

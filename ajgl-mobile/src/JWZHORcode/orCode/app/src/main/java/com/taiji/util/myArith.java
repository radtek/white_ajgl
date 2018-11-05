package com.taiji.util;

import java.math.BigDecimal;

/**
 * Created by z0 on 2017/5/4.
 */

/**
 * double的计算不精确，会有类似0.0000000000000002的误差，正确的方法是使用BigDecimal或者用整型
 * 整型地方法适合于货币精度已知的情况，比如12.11+1.10转成1211+110计算，最后再/100即可
 * 以下是摘抄的BigDecimal方法:
 */

public class myArith extends  Arith
{ /**
 * 提供精确加法计算的add方法
 * @param value1 被加数
 * @param value2 加数
 * @return 两个参数的和
 */
public static double add(double value1,double value2){

    long templ=getIndex(value1,value2);
    double result=(value1*templ+value2*templ)/templ;

    return result;
}

    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1,double value2){
        long templ=getIndex(value1,value2);
        double result=(value1*templ-value2*templ)/templ;

        return result;
    }

    //取小数位,然后10次幂,比如10.112,返回1000
    private static long getIndex(double value1,double value2)
    {
        int length1 = 0;
        String s1 = String.valueOf(value1);
        int tempint=s1.indexOf(".");
        if(tempint<0){

        }else
        {
            String tempstr=s1.substring(tempint, s1.length());
            length1 = tempstr.length();
        }
        int length2 = 0;
        String s2 = String.valueOf(value2);
        tempint=s2.indexOf(".");
        if(tempint<0){

        }else
        {
            String tempstr=s2.substring(tempint, s2.length());
            length2 = tempstr.length();
        }


        long templ=length1>length2?(long)Math.pow(10,length1):(long)Math.pow(10,length2);
        return templ;
    }

}

package com.taiji.pubsec.ajqlc.util;

import java.text.SimpleDateFormat;
import org.springframework.cglib.core.Converter;

/**
 * CaseBeanCopierConverter
 *
 */
public class WritBeanCopierConverter implements Converter {

    private String dateFmt;

    public WritBeanCopierConverter(String dateFmt) {
        if (dateFmt == null || "".equals(dateFmt)) {
            this.dateFmt = "yyyy-MM-dd HH:mm:ss";
        } else {
            this.dateFmt = dateFmt;
        }
    }

    // arg0 源对象属性，arg1 目标对象属性类，arg2 目标对象setter方法名
    @Override
    @SuppressWarnings("rawtypes")
    public Object convert(Object arg0, Class arg1, Object arg2) {
        SimpleDateFormat df = new SimpleDateFormat(dateFmt);

        if (arg0 == null) {
            return "";
        } else {
            if (arg0 instanceof String) {
                if("".equals(arg0)){
                	return "";
                }else{
                	return arg0;
                }
            }else if (arg0 instanceof Integer || arg0 instanceof Double) {
            	return String.valueOf(arg0);
            }else if (arg0 instanceof java.util.Date 
    				|| arg0 instanceof java.sql.Date 
    				|| arg0 instanceof java.sql.Timestamp) {
            	return df.format(arg0);
            }else{
            	return "";
            }
        }
    }

    public String getDateFmt() {
        return dateFmt;
    }

    public void setDateFmt(String dateFmt) {
        if (dateFmt == null) {
            this.dateFmt = "yyyy-MM-dd HH:mm:ss";
        } else {
            this.dateFmt = dateFmt;
        }

    }

}

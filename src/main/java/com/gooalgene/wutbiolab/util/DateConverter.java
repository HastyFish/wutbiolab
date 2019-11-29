package com.gooalgene.wutbiolab.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转化器
 */
@Component
public class DateConverter implements Converter<String, Date> {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String shortDateFormat = "yyyy-MM-dd";
    private static final String timeStampFormat = "^\\d+$";
    private static final String hDateFormat = "yyyy年MM月dd日 HH:mm:ss";
    private static final String hshortDateFormat = "yyyy年MM月dd日";

    @Override
    public Date convert(String value){
        if (StringUtils.isEmpty(value)){
            return  null;
        }

        value = value.trim();

        try {
            if (value.contains("-")){
                SimpleDateFormat format;
                if (value.contains(":")){
                    format = new SimpleDateFormat(dateFormat);
                } else {
                    format = new SimpleDateFormat(shortDateFormat);
                }
                return format.parse(value);
            } else if (value.matches(timeStampFormat)){
                Long lDate = new Long(value);
                return new Date(lDate);
            } else if (value.contains("年")){
                SimpleDateFormat format;
                if (value.contains(":")){
                    format = new SimpleDateFormat(hDateFormat);
                } else {
                    format = new SimpleDateFormat(hshortDateFormat);
                }
                return format.parse(value);
            }
        } catch (Exception e){
            throw new RuntimeException(String.format("解析 %s 日期失败", value));
        }
        throw new RuntimeException(String.format("parser %s to Date fail", value));
    }
}

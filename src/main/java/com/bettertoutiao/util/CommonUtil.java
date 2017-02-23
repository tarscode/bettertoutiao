package com.bettertoutiao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuyang on 17/2/8.
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static int ANONYMOUS_USERID = 9;
    public static int SYSTEM_USERID = 8;
    public static int TYPE_USER= 2;


    public static String dateFormat(Date date) throws ParseException {
        String type = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(date);
    }

}

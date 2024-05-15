package com.example.chatserver.helper.datetime;

import java.util.Date;

public class DateTimeHelper {
    public static Long getCurrentTimeUtcMs(){
        Date now = new Date();
        return now.getTime();
    }

    public static Date getCurrentDate(){
        return new Date();
    }

    public static Date addTimeToCurrentTime(int extraTime){
        return new Date(new Date().getTime() + extraTime);
    }

    public static Date convertUtcToDate(Long time) {
        return new Date(time);
    }
}

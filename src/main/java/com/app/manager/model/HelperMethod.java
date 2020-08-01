package com.app.manager.model;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static java.lang.Math.abs;

public class HelperMethod {
    public static String getDateString(Long timeStamp){
        try {
            return (new Date(timeStamp)).toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static long roundUpIntDiv(long num, long divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
    }

    public static void pingTo(String url){
        try {
            var result  = getStatus(url);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static String getStatus(String url) {
        try {
            var siteURL = new URL(url);
            var connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.connect();

            var code = connection.getResponseCode();
            return "Status Code: " + code;
        } catch (Exception e) {
            return  "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
    }

    public static String upperCaseFirstChar(String input){
        if(input == null || input.isEmpty()) return "";
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}

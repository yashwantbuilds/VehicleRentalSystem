package com.example.utils;

public class Utils {

    private static boolean debuggingEnabled = false;

    public static boolean nonNull(Object obj){
        return obj!=null?true:false;
    }

    public static boolean notEmpty(String str){
        return str!=null && str.trim().length()>0 ? true : false;
    }

    public static void logInfo(String info, String... input){
        if(debuggingEnabled) {
            System.out.println(info + " " + String.join(", ", input));
        }
    }

    public static void logError(String error, String input, String errorMessage){
        if(debuggingEnabled) {
            System.out.println(error + "for " + input + " due to " + errorMessage);
        }
    }

    public static  void logInfo(Object info){
        if(debuggingEnabled) {
            System.out.println(info);
        }
    }
}

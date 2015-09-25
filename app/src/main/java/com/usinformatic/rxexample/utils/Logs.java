package com.usinformatic.rxexample.utils;

import android.util.Log;

/**
 * Created by d1m11n on 9/25/15.
 */
public final class Logs {



    public static void err(String tag, String message){
        String methName=Thread.currentThread().getStackTrace()[3].getMethodName();
        Log.e(tag, "" + methName + "; " + message);

    }

    public static void err( String message){
        String methName3=Thread.currentThread().getStackTrace()[3].getMethodName();
        String className3=Thread.currentThread().getStackTrace()[3].getClassName();
        Log.e((className3!=null?className3:"---"),  "method  =  " + methName3 + ";   "+ message);
    }
}

package com.usinformatic.rxexample.common;

import android.text.TextUtils;

/**
 * Created by D1m11n on 06.08.2015.
 */
public class StringUtils {

   public static final String getWithFirstCapitalLetter(String text){
            if(TextUtils.isEmpty(text)) return "";
            return text.substring(0, 1).toUpperCase() +(text.length()==1?"":text.substring(1));
    }
}

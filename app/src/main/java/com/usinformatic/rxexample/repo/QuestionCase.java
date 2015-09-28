package com.usinformatic.rxexample.repo;

import java.util.Random;

/**
 * Created by d1m11n on 9/28/15.
 */
public class QuestionCase {


    public static String getNewQuestion(){
        return "Question is "+ new Random().nextInt(100);
    }

}

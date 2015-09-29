package com.usinformatic.rxexample.repositories;

import android.content.Context;

import com.usinformatic.rxexample.models.Round;

import java.util.List;

/**
 * Created by d1m11n on 9/29/15.
 */
public class RoundsRepository {


    private Context mContext;

    private RoundsRepository(Context context){
        mContext=context;

    }

    public static RoundsRepository newInstance(Context context){
        return new RoundsRepository(context);
    }

    public List<Round> getRoundsForQuiz(){
        return generateFakeRounds();
    }

    private List<Round> generateFakeRounds(){

        return null;
    }



}

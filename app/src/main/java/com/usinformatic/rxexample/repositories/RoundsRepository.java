package com.usinformatic.rxexample.repositories;

import android.content.Context;

import com.usinformatic.rxexample.models.Round;

import java.util.ArrayList;
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
        List<Round> rounds= new ArrayList<>();
        rounds.add(makeFirstRound());
        rounds.add(makeSecondRound());
        rounds.add(makeThirdRound());
        return rounds;
    }

    private Round makeFirstRound(){
        Round r = new Round();
        r.id="1";
        r.question="Are you ready?";
        r.options= new String[]{"Yes", "No", "Maybe"};
        r.rightOption=0;
        r.timeOut=10000;
         return r;
    }

    private Round makeSecondRound(){
        Round r = new Round();
        r.id="1";
        r.question="Do yu really want to continue?";
        r.options= new String[]{"Yes", "No", "Maybe"};
        r.rightOption=2;
        r.timeOut=8000;
        return r;
    }

    private Round makeThirdRound(){
        Round r = new Round();
        r.id="1";
        r.question="Ok finish";
        r.options= new String[]{"Ok", "Wait", "Stop"};
        r.rightOption=0;
        r.timeOut=10000;
        return r;
    }



}

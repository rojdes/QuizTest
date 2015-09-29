package com.usinformatic.rxexample.models;

/**
 * Created by d1m11n on 9/29/15.
 */
public class Round {

    public String id;

    public String question;

    public String [] options;

    public int rightOption;

    /**
     *  in millis
     */
    public long timeOut;


    public Round(){}

    public Round (String id, String question, String [] options, int rightOption,long timeOut){
        this.id=id;
        this.question=question;
        this.options=options;
        this.rightOption=rightOption;
        this.timeOut=timeOut;

    }




}

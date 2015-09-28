package com.usinformatic.rxexample.models.enums;

/**
 * Created by d1m11n on 9/28/15.
 */
public enum CommentCollection {



    HELLO("hello"), THINK("i'm thinking"), WAIT("wait for me"), OK("ok");


    private final String comment;

    private CommentCollection(String comment){
        this.comment=comment;
    }

}

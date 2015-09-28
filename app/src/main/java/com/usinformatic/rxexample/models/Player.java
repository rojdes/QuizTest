package com.usinformatic.rxexample.models;

import com.usinformatic.rxexample.models.enums.PlayerType;

/**
 * Created by d1m11n on 9/28/15.
 */
public class Player {


    public String id;

    public String name;

    public String answer;

    public Comment comment;

    public PlayerType role;

    public long timeMs;


    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", answer='" + answer + '\'' +
                ", comment=" + comment +
                ", role=" + role +
                ", timeMs=" + timeMs +
                '}';
    }
}

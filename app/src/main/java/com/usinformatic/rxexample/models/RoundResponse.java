package com.usinformatic.rxexample.models;

/**
 * Created by d1m11n on 9/29/15.
 */
public class RoundResponse {

    //public String userId;

    public Player player;

    public String roundId;

    public int selectedOption;

    public long timeTook;

    @Override
    public String toString() {
        return "RoundResponse{" +
                "player=" + player +
                ", roundId='" + roundId + '\'' +
                ", selectedOption=" + selectedOption +
                ", timeTook=" + timeTook +
                '}';
    }
}

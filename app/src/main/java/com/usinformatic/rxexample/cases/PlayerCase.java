package com.usinformatic.rxexample.cases;

import com.usinformatic.rxexample.AppConsts;
import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.enums.PlayerType;

import java.util.Random;

/**
 * Created by d1m11n on 9/28/15.
 */
public class PlayerCase {


    private static final Random rnd= new Random();




    public static Player getNewPlayer(){
        Player p= new Player();
        int n=rnd.nextInt(200);
        p.id="Opponent " + n;
        p.name="Opponent " + n;
        p.answer = "Answer from " +n;
        p.timeMs = rnd.nextInt((int) (AppConsts.GAME_ROUND_DURATIONms/10))*10;
        p.role= PlayerType.values()[rnd.nextInt(2)];
        return p;
    }

    public static Player getNewPlayer(String id){
        Player p= new Player();
        p.id=id;
        int n=rnd.nextInt(200);
        p.name="Opponent " + n;
        p.answer = "Answer from " +n;
        p.timeMs = 2000L + rnd.nextInt((int) (AppConsts.GAME_ROUND_DURATIONms/10))*8;
        p.role= PlayerType.values()[rnd.nextInt(2)];
        return p;
    }

    public static Player getMe(){
        //may be make static or get from preferences
        Player p= new Player();
        p.name="Me";
        p.answer="I win";
        p.role=PlayerType.ME;
        return p;
    }

}

package com.usinformatic.rxexample.cases;

import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.enums.PlayerType;

import java.util.Random;

/**
 * Created by d1m11n on 9/28/15.
 */
public class PlayerCase {


    private static final Random rnd= new Random();
    private static Player me;




    public static Player getNewPlayer(){
        Player p= new Player();
        int n=rnd.nextInt(200);
        p.id="Opponent " + n;
        p.name="Opponent " + n;
        p.role= PlayerType.values()[rnd.nextInt(2)];
        return p;
    }

    public static Player getNewPlayer(String id){
        Player p= new Player();
        p.id=id;
        int n=rnd.nextInt(200);
        p.name="Opponent " + n;
        p.role= PlayerType.values()[rnd.nextInt(2)];
        return p;
    }

    public static Player getMe(){
        //may be make static or get from preferences
        if(me!=null)
            return me;
        me= new Player();
        me.name="Me";
        me.role=PlayerType.ME;
        return me;
    }

}

package com.usinformatic.rxexample.cases;

import android.util.Log;

import com.usinformatic.rxexample.models.Player;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by d1m11n on 9/28/15.
 */
public class PlayerObservableCase {




    private static final String TAG="PLAYER_OBSERVABlE_CASE";

    public static Observable newInstance(final Player player, Scheduler scheduler ){
        if(player==null) return null;
        if(scheduler==null)
            scheduler= Schedulers.computation();
        //Log.e(TAG, player.toString());
        Observable<Long> opponent =  Observable.timer(new Random().nextInt(4000)+6000, TimeUnit.MILLISECONDS, scheduler);
        opponent=opponent.map(new Func1() {
            @Override
            public Object call(Object o) {
  //              Log.e(TAG, "======call====");
                return player;
            }
        });
//        opponent.observeOn(scheduler);
        opponent.subscribe(new Action1() {
            @Override
            public void call(Object aLong) {
                //Logs.err("opponent call ");
            }
        });
        return opponent;
    }




    public static Observable<?> upgradeCurrent(Observable<?> observable, final Player player){
        if(player==null||observable==null) return null;
        observable=observable.map(new Func1() {
            @Override
            public Object call(Object o) {
                Log.e(TAG, "======call====");
                return player;
            }
        });
        observable.subscribe(new Action1() {
            @Override
            public void call(Object aLong) {
                //Logs.err("opponent call ");
            }
        });
        return observable;
    }

}

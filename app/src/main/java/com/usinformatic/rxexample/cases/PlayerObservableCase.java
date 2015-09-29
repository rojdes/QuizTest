package com.usinformatic.rxexample.cases;

import android.util.Log;

import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.Round;
import com.usinformatic.rxexample.models.RoundResponse;
import com.usinformatic.rxexample.utils.Logs;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
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

    public static Observable newInstance(final Player player, final Round round){
        if(player==null) return null;
        //Log.e(TAG, player.toString());
        final int timeTook=new Random().nextInt((int) (round.timeOut-1000))+1000;
        Log.e(TAG, "timeTook = " + timeTook);
        Observable<Long> opponent =  Observable.timer(timeTook, TimeUnit.MILLISECONDS,  Schedulers.computation());
        opponent=opponent.map(new Func1() {
            @Override
            public Object call(Object o) {
                //              Log.e(TAG, "======call====");
                RoundResponse r=  new RoundResponse();
                r.player=player;
                r.selectedOption=new Random().nextInt(round.options.length);
                r.timeTook=timeTook;
                r.roundId=round.id;
                return r;
            }
        });
        opponent.observeOn(AndroidSchedulers.mainThread());
//        opponent.observeOn(scheduler);
        opponent.subscribe(new Action1() {
            @Override
            public void call(Object aLong) {
                Logs.err("opponent call " +aLong.toString());
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

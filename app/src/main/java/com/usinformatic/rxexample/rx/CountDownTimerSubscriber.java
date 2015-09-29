package com.usinformatic.rxexample.rx;

import android.os.Handler;
import android.util.Log;

import com.usinformatic.rxexample.rx.exceptions.TimeEndedException;
import com.usinformatic.rxexample.utils.Logs;

import rx.Subscriber;

/**
 * Created by d1m11n on 9/29/15.
 */
public abstract class CountDownTimerSubscriber extends Subscriber {

    private static final String TAG =CountDownTimerSubscriber.class.getSimpleName() ;
    private long maxTimeSec;

    private Handler mHandler;

    public CountDownTimerSubscriber(long maxTimeSec){
        this.maxTimeSec=maxTimeSec;
        mHandler= new Handler();
    }


    @Override
    public void onCompleted() {
        Logs.err("timer");
        onAction(true, null);
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof TimeEndedException){
            Logs.err("timer  timeEndedException");
            onAction(true, null);
            return;
        }
        Logs.err("timer" + e.toString());
        error(e);

    }

    @Override
    public void onNext(final Object o) {
        if(o==null) return;
        if(o instanceof Long){
            updateTimeOrStop((Long) o);
            return;
        }
        Log.e(TAG, "is " + o + "  ");
        sendAction(false, o);
    }

    private void updateTimeOrStop(Long current){
        final long time=(maxTimeSec - current - 1);
        Logs.err("time now = " + time);
        if(time<0){
            stopTimer();
            sendAction(true, null);
        }
        else{
           sendTime(time);

        }
    }

    private void sendTime(final long time){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                time(time);
            }
        });
    }

    private  void sendError(final Throwable e){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                error(e);
            }
        });
    }

    private void sendAction(final boolean completed, final Object o){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onAction(completed,o);
            }
        });
    }

    public abstract void time(long time);

    public abstract void onAction(boolean completed, Object o);


    public abstract void error(Throwable e);

    public void stopTimer(){
       maxTimeSec=-1;
       unsubscribe();
       //onAction(true, null);
    }



}

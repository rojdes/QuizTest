package com.usinformatic.rxexample.rx;

import com.usinformatic.rxexample.rx.exceptions.TimeEndedException;
import com.usinformatic.rxexample.utils.Logs;

import rx.Subscriber;

/**
 * Created by d1m11n on 9/29/15.
 */
public abstract class CountDownTimerSubscriber extends Subscriber {

    private long maxTimeSec;

    public CountDownTimerSubscriber(long maxTimeSec){
        this.maxTimeSec=maxTimeSec;
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
    public void onNext(Object o) {
        if(o==null) return;
        if(o instanceof Long){
            sendNewTimeOrStop((Long) o);
            return;
        }
        onAction(false, o);
    }

    private void sendNewTimeOrStop(Long current){
        long time=(maxTimeSec - current - 1);
        Logs.err("time now = " + time);
        if(time<=0){
            //TimeEndedException(); //because of on error and onompletedhas autounsubsribe annd http://bryangilbert.com/blog/2013/11/03/rx-the-importance-of-honoring-unsubscribe/
            throw new TimeEndedException();
        }
        else{
            onAction(false, time);
        }
    }

    public abstract void onAction(boolean completed, Object o);


    public abstract void error(Throwable e);

    public void stopTimer(){
       maxTimeSec=-1;
       unsubscribe();
       //onAction(true, null);
    }



}

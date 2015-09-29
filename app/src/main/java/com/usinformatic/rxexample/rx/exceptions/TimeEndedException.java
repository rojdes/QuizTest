package com.usinformatic.rxexample.rx.exceptions;

/**
 * Created by d1m11n on 9/29/15.
 */
public class TimeEndedException extends RuntimeException {

    public TimeEndedException() {
        super();
    }

    public TimeEndedException(String detailMessage) {
        super(detailMessage);
    }
}

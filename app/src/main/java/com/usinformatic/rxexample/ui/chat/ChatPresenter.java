package com.usinformatic.rxexample.ui.chat;

import com.usinformatic.rxexample.utils.Logs;

import rx.Subscriber;

/**
 * Created by d1m11n on 9/29/15.
 */
class ChatPresenter {

    private IQuizChatView mChatView;
    private final Subscriber mTimeSubscriber=new Subscriber() {
        @Override
        public void onCompleted() {
            Logs.err("oncompleted timer");
            //updateViewsOnEndGame();
        }

        @Override
        public void onError(Throwable e) {
            Logs.err("onerror timer" + e.toString());
            //updateViewsOnEndGame();
        }

        @Override
        public void onNext(Object o) {
            //updateGameProcessWith(this,o);
        }
    };;




    public ChatPresenter(IQuizChatView view){
       mChatView=view;

    }


    public void onOptionClick(int ordinal){


    }

    public void startQuiz(){

    }

    public void interruptQuiz(){
        if(!mTimeSubscriber.isUnsubscribed()){
            mTimeSubscriber.unsubscribe();
        }
    }



}

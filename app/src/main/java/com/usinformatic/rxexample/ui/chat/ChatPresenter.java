package com.usinformatic.rxexample.ui.chat;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.usinformatic.rxexample.R;
import com.usinformatic.rxexample.cases.PlayerCase;
import com.usinformatic.rxexample.common.ListsUtils;
import com.usinformatic.rxexample.common.StringUtils;
import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.Round;
import com.usinformatic.rxexample.repositories.RoundsRepository;
import com.usinformatic.rxexample.utils.Logs;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by d1m11n on 9/29/15.
 */
class ChatPresenter {

    private static final String TAG = ChatPresenter.class.getSimpleName();
    private IQuizChatView mChatView;
    private Context mContext;
    private List<Round> mRoundsLst;
    private Player mOpponent;
    private Observable mPlayerObservable;

    private AtomicBoolean mInerruptedQuiz= new AtomicBoolean(false);
    private AtomicInteger mCurrentQuizNumber=new AtomicInteger(0);



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
            updateGameProcessWith(this,o);
        }
    };

    private void updateGameProcessWith(Subscriber subscriber, Object o) {

    }


    public ChatPresenter(IQuizChatView view, Context context){
       mChatView=view;
       mContext=context;
    }


    public void setOptionViews(View ... options){
        if(ListsUtils.isEmpty(options)) return;
        for(View v:options)
            RxView.clickEvents(v).doOnNext(new Action1<ViewClickEvent>() {
                @Override
                public void call(ViewClickEvent viewClickEvent) {

                }
            });
    }

    public void userClick(int optionOrder){
        mPlayerObservable.just(optionOrder);
    }

    public void startQuiz(){
        mChatView.showProgress(StringUtils.getWithFirstCapitalLetter(mContext.getResources().getString(R.string.loading)));
        mRoundsLst= RoundsRepository.newInstance(mContext).getRoundsForQuiz();
        mOpponent= PlayerCase.getNewPlayer();
        mChatView.hideProgress();
        if(ListsUtils.isEmpty(mRoundsLst)){
            mChatView.showMessageDialog(mContext.getResources().getString(R.string.Error), mContext.getResources().getString(R.string.Empty_rounds));
            return;
        }
        mChatView.showPlayersInfo(PlayerCase.getMe(), mOpponent);
        mPlayerObservable=Observable.create(new Observable.OnSubscribe() {
            @Override
            public void call(Object o) {
                Log.e(TAG,"call");
            }
        });
        startItemQuiz(mRoundsLst.get(0));
    }

    private void startItemQuiz(Round round) {
        if(mInerruptedQuiz.get()){
            return;
        }
        mChatView.resetOptionViews();
        mChatView.setOptionsContent(round.options);
        mChatView.setQuestion(round.question);
        Observable<Long> timer = Observable.interval(0, 1000, TimeUnit.MILLISECONDS, Schedulers.computation());
        timer.subscribe(mTimeSubscriber);

    }

    public void interruptQuiz(){
        if(!mTimeSubscriber.isUnsubscribed()){
            mTimeSubscriber.unsubscribe();
        }
        mInerruptedQuiz.set(true);
        mChatView.updateTime("-");
        mChatView.resetOptionViews();
        mChatView.setQuestion("");
    }



}

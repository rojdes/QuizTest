package com.usinformatic.rxexample.ui.chat;

import android.content.Context;

import com.usinformatic.rxexample.R;
import com.usinformatic.rxexample.cases.PlayerCase;
import com.usinformatic.rxexample.common.ListsUtils;
import com.usinformatic.rxexample.common.StringUtils;
import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.Round;
import com.usinformatic.rxexample.repositories.RoundsRepository;
import com.usinformatic.rxexample.rx.CountDownTimerSubscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
    private AtomicBoolean mInerruptedQuiz= new AtomicBoolean(false);
    private AtomicInteger mCurrentQuizNumber=new AtomicInteger(0);

    private CountDownTimerSubscriber mTimeSubscriber;/*=new Subscriber() {
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
    };*/

    public ChatPresenter(IQuizChatView view, Context context){
       mChatView=view;
       mContext=context;
    }

    public void userClick(int optionOrder){
        if(mTimeSubscriber==null||mTimeSubscriber.isUnsubscribed())
            return;
        mTimeSubscriber.onNext(optionOrder);
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
        startItemQuiz(0);
    }

    private void startItemQuiz(int order) {
        if(mInerruptedQuiz.get()){
            return;
        }
        if (order>=mRoundsLst.size()){
            mChatView.showResult();
            return;
        }
        Round round=mRoundsLst.get(order);
        mChatView.resetOptionViews();
        mChatView.setOptionsContent(round.options);
        mChatView.setQuestion(round.question);
        Observable<Long> timer = Observable.interval(0, 1000, TimeUnit.MILLISECONDS, Schedulers.computation());
        mTimeSubscriber=initNewtimeSubscriber(round.timeOut);
        timer.observeOn(AndroidSchedulers.mainThread());
//        timer.subscribeOn(AndroidSchedulers.mainThread());]
        timer.subscribe(mTimeSubscriber);

    }

    private CountDownTimerSubscriber initNewtimeSubscriber(long timeOut) {
        return new CountDownTimerSubscriber(timeOut/1000) {
            @Override
            public void time(long time) {
//                mChatView.updateTime("Remaining " + time);
//                mTimeSubscriber=null;
            }

            @Override
            public void onAction(boolean completed, Object o) {
//                if(completed){
//                    mChatView.updateTime("Time is over");
//                    startItemQuiz(mCurrentQuizNumber.incrementAndGet());
//                }else{
//                    updateGameProcessWith(this,o);
//                }
            }

            @Override
            public void error(Throwable e) {
                mChatView.showMessageDialog("Error", e.toString());
            }
        };
    }

    private void updateGameProcessWith(Subscriber subscriber, Object o) {

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

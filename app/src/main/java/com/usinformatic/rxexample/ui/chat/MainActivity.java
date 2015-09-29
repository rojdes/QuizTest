package com.usinformatic.rxexample.ui.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.usinformatic.rxexample.AppConsts;
import com.usinformatic.rxexample.R;
import com.usinformatic.rxexample.cases.PlayerCase;
import com.usinformatic.rxexample.cases.PlayerObservableCase;
import com.usinformatic.rxexample.cases.QuestionCase;
import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.enums.PlayerType;
import com.usinformatic.rxexample.rx.exceptions.TimeEndedException;
import com.usinformatic.rxexample.utils.Logs;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private static final long MAX_TIME = AppConsts.GAME_ROUND_DURATIONms/1000;

    private TextView mtvTimer,mtvQuestion;
    private  View mvRoot;
    private Button mbtnUserAnswer, mbtnRestart;
    //private Observable mMyPlayerObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        initPlayers();
        startGame();
    }

    private void initPlayers() {
//
//        mMyPlayerObservable = Observable.interval(0, MAX_TIME, TimeUnit.SECONDS);
//        mMyPlayerObservable.subscribe(playerSub);
         //PlayerObservableCase.upgradeCurrent(RxView.clickEvents(mbtnUserAnswer), PlayerCase.getMe());
    }

    private void findViews() {
        mbtnRestart=(Button)findViewById(R.id.restart);
        mbtnUserAnswer=(Button)findViewById(R.id.user_answer);
        mvRoot=getWindow().getDecorView().findViewById(android.R.id.content);
        mtvTimer=(TextView)findViewById(R.id.timer);
        mtvQuestion=(TextView)findViewById(R.id.question);
    }

    private void initViews(){
        mbtnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        mbtnUserAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player p= PlayerCase.getMe();
                timerSubscriber.onNext(p);
            }
        });
    }

    final Subscriber timerSubscriber = new Subscriber() {
        @Override
        public void onCompleted() {
            Logs.err("oncompleted timer");
            updateViewsOnEndGame();
        }

        @Override
        public void onError(Throwable e) {
            Logs.err("onerror timer" + e.toString());
            updateViewsOnEndGame();
        }

        @Override
        public void onNext(Object o) {
            updateGameProcessWith(this,o);
        }
    };

    public void startGame() {
        Scheduler sc=Schedulers.computation();
        prepareViewsForNewGame();

        Observable<Long> timer = Observable.interval(0, 1000, TimeUnit.MILLISECONDS, sc);
        //if i use something like for() ...{ mergeWith } it doesn't want to work


       timer.
              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_0"), null)).
              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_1"), null)).
              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_2"), null)).
              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_3"), null)).
         /*mergeWith(mMyPlayerObservable).*/observeOn(AndroidSchedulers.mainThread()).subscribe(timerSubscriber);
      }

    private void updateViewsOnEndGame() {
         mbtnRestart.setEnabled(true);
         mbtnUserAnswer.setEnabled(false);
    }

    private void prepareViewsForNewGame(){
        mbtnRestart.setEnabled(false);
        mbtnUserAnswer.setEnabled(true);
        mtvTimer.setText("10");
        mtvQuestion.setText(QuestionCase.getNewQuestion());
        ((TextView)findViewById(R.id.player_0)).setText("");
        ((TextView)findViewById(R.id.player_1)).setText("");
        ((TextView)findViewById(R.id.player_2)).setText("");
        ((TextView)findViewById(R.id.player_3)).setText("");
    }

    private void updateGameProcessWith(Subscriber sub, Object o)  {
    if ( o instanceof Long) {
        updateTime(sub, (Long) o);
        return;
    }
    //Logs.err(String.valueOf(o.getClass().getSimpleName()));
    if( o instanceof Player){
        showResultOfGame((Player) o);
        mtvTimer.setText("--");
        throw new TimeEndedException();
    }
   }

    private void showResultOfGame(Player p) {
        if(p.role== PlayerType.ME){
            mtvQuestion.setText("You are winner");
        }
        else{
            ((TextView)mvRoot.findViewWithTag(p.id)).setText("Winner is " + p.name);
        }
    }

    private void updateTime(Subscriber sub, Long o) {
        long time=(MAX_TIME - o - 1);
        if(time<=0){
            mtvTimer.setText("Time is over");
            //sub.onNext(null); //because of on error and onompletedhas autounsubsribe annd http://bryangilbert.com/blog/2013/11/03/rx-the-importance-of-honoring-unsubscribe/
            throw new TimeEndedException();
        }
        else{
            mtvTimer.setText("Remaining " + time);
        }
    }
}

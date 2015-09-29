package com.usinformatic.rxexample.ui.chat;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.usinformatic.rxexample.R;
import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.enums.OptionState;

public class MainActivity extends AppCompatActivity implements IQuizChatView {




    private TextView mtvTimer,mtvQuestion;
//    private  View mvRoot;
    private Button mbtnRestart;
    private Button mbtnFirstOption,mbtnSecondOption,mbtnThirdOption;
    private ChatPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        mPresenter=new ChatPresenter(this,this);
    }

    private void initPlayers() {
//
//        mMyPlayerObservable = Observable.interval(0, MAX_TIME, TimeUnit.SECONDS);
//        mMyPlayerObservable.subscribe(playerSub);
         //PlayerObservableCase.upgradeCurrent(RxView.clickEvents(mbtnUserAnswer), PlayerCase.getMe());
    }

    private void findViews() {
        mbtnRestart=(Button)findViewById(R.id.restart);
        mbtnFirstOption=(Button)findViewById(R.id.option_1);
        mbtnSecondOption=(Button)findViewById(R.id.option_2);
        mbtnThirdOption=(Button)findViewById(R.id.option_3);
//        mvRoot=getWindow().getDecorView().findViewById(android.R.id.content);
        mtvTimer=(TextView)findViewById(R.id.timer);
        mtvQuestion=(TextView)findViewById(R.id.question);
    }

    private void initViews(){
//        mbtnRestart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startGame();
//                }
//        });
//        mbtnUserAnswer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Player p= PlayerCase.getMe();
////                timerSubscriber.onNext(p);
//            }
//        });
    }

    private void onOptionClick(View v){
        int id=-1;
        switch (v.getId()){
            case R.id.option_1:
               id=0; break;
            case R.id.option_2:
                id=1; break;
            case R.id.option_3:
                id=2; break;
        }
        mPresenter.userClick(id);
    }

    @Override
    public void setOptionsContent(String[] options) {
        mbtnFirstOption.setText(options[0]);
        mbtnSecondOption.setText(options[1]);
        mbtnThirdOption.setText(options[2]);
    }

    @Override
    public void resetOptionViews() {
        mbtnFirstOption.setText("");
        mbtnSecondOption.setText("");
        mbtnThirdOption.setText("");
    }

    @Override
    public void setSelectOptions(boolean locked) {
        mbtnFirstOption.setEnabled(locked);
        mbtnSecondOption.setEnabled(locked);
        mbtnThirdOption.setEnabled(locked);
    }

    @Override
    public void setQuestion(String question) {
        mtvQuestion.setText(question);

    }

    @Override
    public void updateOptionState(int number, OptionState state) {
        int color=state==OptionState.RIGHT? ContextCompat.getColor(this,android.R.color.holo_green_dark): ContextCompat.getColor(this, android.R.color.holo_red_dark);
        switch (number){
            case 0: mbtnFirstOption.setBackgroundColor(color); break;
            case 1: mbtnSecondOption.setBackgroundColor(color); break;
            case 2: mbtnThirdOption.setBackgroundColor(color); break;
        }
    }

    @Override
    public void updateTime(String value) {
        mtvTimer.setText(value);
    }

    @Override
    public void showResult() {

    }

    @Override
    public void showMessageDialog(String title, String message) {

    }

    @Override
    public void showPlayersInfo(Player me, Player... opponents) {

    }

    @Override
    public void showProgress(String message) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.startQuiz();
    }

    //    final CountDownTimerSubscriber timerSubscriber = new CountDownTimerSubscriber(MAX_TIME) {
//        @Override
//        public void onAction(boolean completed, Object o) {
//            if(completed){
//                updateViewsOnEndGame();
//                mtvTimer.setText("Time is over");
//            }else{
//                updateGameProcessWith(this,o);
//            }
//
//        }
//
//        @Override
//        public void error(Throwable e) {
//
//        }
//    };


//
//            new Subscriber() {
//        @Override
//        public void onCompleted() {
//            Logs.err("oncompleted timer");
//            updateViewsOnEndGame();
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            Logs.err("onerror timer" + e.toString());
//            updateViewsOnEndGame();
//        }
//
//        @Override
//        public void onNext(Object o) {
//            updateGameProcessWith(this,o);
//        }
//    };

//    public void startGame() {
//        Scheduler sc=Schedulers.computation();
//        prepareViewsForNewGame();
//
//        Observable<Long> timer = Observable.interval(0, 1000, TimeUnit.MILLISECONDS, sc);
//        //if i use something like for() ...{ mergeWith } it doesn't want to work
//
//
//       timer.
////              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_0"), null)).
////              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_1"), null)).
////              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_2"), null)).
////              mergeWith(PlayerObservableCase.newInstance(PlayerCase.getNewPlayer("player_3"), null)).
////         /*mergeWith(mMyPlayerObservable).*/observeOn(AndroidSchedulers.mainThread()).subscribe(timerSubscriber);
//      }

//    private void updateViewsOnEndGame() {
//         mbtnRestart.setEnabled(true);
////         mbtnUserAnswer.setEnabled(false);
//    }
//
//    private void prepareViewsForNewGame(){
//        mbtnRestart.setEnabled(false);
//        mbtnUserAnswer.setEnabled(true);
//        mtvTimer.setText("10");
//        mtvQuestion.setText(QuestionCase.getNewQuestion());
//        ((TextView)findViewById(R.id.player_0)).setText("");
//        ((TextView)findViewById(R.id.player_1)).setText("");
//        ((TextView)findViewById(R.id.player_2)).setText("");
//        ((TextView)findViewById(R.id.player_3)).setText("");
//    }
//
//    private void updateGameProcessWith(Subscriber sub, Object o)  {
//    if ( o instanceof Long) {
//        updateTime(sub, (Long) o);
//        return;
//    }
//    //Logs.err(String.valueOf(o.getClass().getSimpleName()));
//    if( o instanceof Player){
//        showResultOfGame((Player) o);
//        mtvTimer.setText("--");
////        throw new TimeEndedException();
////        timerSubscriber.stopTimer();
//    }
//   }
//
//    private void showResultOfGame(Player p) {
//        if(p.role== PlayerType.ME){
//            mtvQuestion.setText("You are winner");
//        }
//        else{
//            ((TextView)mvRoot.findViewWithTag(p.id)).setText("Winner is " + p.name);
//        }
//    }
//
//    private void updateTime(Subscriber sub, Long o) {
//        long time=(MAX_TIME - o - 1);
////        if(time<=0){
//            //mtvTimer.setText("Time is over");
//            //sub.onNext(null); //because of on error and onompletedhas autounsubsribe annd http://bryangilbert.com/blog/2013/11/03/rx-the-importance-of-honoring-unsubscribe/
// //           throw new TimeEndedException();
// //       }
// //       else{
//            mtvTimer.setText("Remaining " + o/*time*/);
// //       }
//    }
}

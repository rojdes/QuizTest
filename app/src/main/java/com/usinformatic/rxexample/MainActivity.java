package com.usinformatic.rxexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.usinformatic.rxexample.utils.Logs;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_TIME=10;

    private TextView mTimer, mOpponentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTimer=(TextView)findViewById(R.id.timer);
        mOpponentTime=(TextView)findViewById(R.id.opponent);
        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimer();
            }
        });
        onTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(){
        RxView.clickEvents(findViewById(R.id.button)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ViewClickEvent>() {
            @Override
            public void onCompleted() {
                Logs.err("oncompleted");
            }

            @Override
            public void onError(Throwable e) {
                Logs.err("onerror " + e.toString());
            }

            @Override
            public void onNext(ViewClickEvent viewClickEvent) {
                Logs.err("onnext " + viewClickEvent.view().toString());
                this.onCompleted();

            }
        });
    }

    public void onTimer() {
        long opponentTime=new Random().nextInt(110)*100L;
        mOpponentTime.setText("Opponent will finish on " + opponentTime + " millis " );
        final Subscriber<Long> sub = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Logs.err("oncompleted");
                unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                Logs.err("onerror " + e.toString());
            }

            @Override
            public void onNext(Long o) {
                Logs.err("onnext " + o.toString());
                long time=(MAX_TIME - o - 1);
                if(time<=0) {
                    mTimer.setText("Time is over");
                }
                else
                   mTimer.setText("Remaining " + time);
            }
        };
        Scheduler thread = Schedulers.computation();
        Observable<Long> timer = Observable.interval(0, 1000, TimeUnit.MILLISECONDS, thread);
        Observable opponent =  Observable.timer(opponentTime, TimeUnit.MILLISECONDS, thread); //somebody completes
        Observable clicker= RxView.clickEvents(findViewById(R.id.button));
        clicker.subscribe(new Subscriber<ViewClickEvent>() {
            @Override
            public void onCompleted() {
                Logs.err("oncompleted");
                mTimer.setText("You are winner");
                unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                Logs.err("onerror" + e.toString());
            }

            @Override
            public void onNext(ViewClickEvent viewClickEvent) {
                Logs.err("onnext + ");
                this.onCompleted();
            }
        });
        mTimer.setTag(true);
        timer.takeUntil(opponent).takeUntil(clicker).observeOn(AndroidSchedulers.mainThread()).subscribe(sub);
    }
}

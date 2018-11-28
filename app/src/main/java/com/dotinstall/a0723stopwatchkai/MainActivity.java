package com.dotinstall.a0723stopwatchkai;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Button rapButton;

    private long startTime;
    private TextView timerLabel;

    private Handler handler = new Handler();
    private Runnable updateTimer;

    private long elapsedTime = 0l;

    private int mRapCount = 0;

    private List<String> rapList = new ArrayList<>();//RAP用のリストのデータ格納
    private ListView rapView;//lap_listからのデータを受け渡される

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerLabel = (TextView) findViewById(R.id.timerLabel);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        rapButton = (Button) findViewById(R.id.rapButton);

        setButtonState(true, false, false, true);
    }

    public void setButtonState(boolean start, boolean stop, boolean reset, boolean rap) {
        startButton.setEnabled(start);
        stopButton.setEnabled(stop);
        resetButton.setEnabled(reset);
        rapButton.setEnabled(rap);
    }

    public void startTimer(View view) {
        startTime = SystemClock.elapsedRealtime();

        //一定時間ごとに現在の経過時間表示
        //Handler -> Runnable(処理) -> UI
        updateTimer = new Runnable() {
            @Override
            public void run() {
                long t = SystemClock.elapsedRealtime() - startTime + elapsedTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS", Locale.US);
                timerLabel.setText(sdf.format(t));

                handler.removeCallbacks(updateTimer);
                handler.postDelayed(updateTimer, 10);
            }
        };
        handler.postDelayed(updateTimer, 10);

        //ボタンの操作
        setButtonState(false, true, false, true);
    }

    public void stopTimer(View view) {
        elapsedTime += SystemClock.elapsedRealtime() - startTime;

        handler.removeCallbacks(updateTimer);
        setButtonState(true, false, true, false);
    }

    public void resetTimer(View view) {
        elapsedTime = 0;
        mRapCount = 0;
        timerLabel.setText(R.string.timer_label);
        rapList.clear();//リストのクリア
        rapView.setAdapter(null);//ビューを空にする
        setButtonState(true, false, false, false);
    }

    public void rapTimer(View view) {
        ArrayAdapter<String> adapter;
        if (mRapCount < 5) {
            mRapCount++;
            setButtonState(false, true, false, true);
            long lt = SystemClock.elapsedRealtime() - startTime;
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS", Locale.US);

            rapView = (ListView) findViewById(R.id.myListView);//ID対応付け
            rapList.add("RAP" + mRapCount + ":" + convertDateToStr(sdf, lt));//listに追加

            adapter = new ArrayAdapter<>(this, R.layout.list_item,
                    rapList);
            rapView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "これ以上記録できません", Toast.LENGTH_SHORT).show();
            setButtonState(false, true, false, false);
        }
    }

    public String convertDateToStr(SimpleDateFormat sdf, long lt) {
        return String.valueOf(sdf.format(lt));
    }
}

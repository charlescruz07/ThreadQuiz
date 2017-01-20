package com.cruz.threadquiz;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    Switch aSwitch;
    TextView scoreTxt,timeTxt,colorTxt;
    FrameLayout frameLayout;
    Button trueBtn, falseBtn;
    Handler m_handler;
    Runnable m_handlerTask = null;
    Handler m_handler2;
    Runnable m_handlerTask2 = null;
    Handler m_handler3;
    Runnable m_handlerTask3 = null;
    Handler final_handler;
    Runnable final_handlerTask = null;
    int timeleft = 10;
    String [] colors = {"#FFFFFF", //black
                        "#FF0000", //red
                        "#FFFF00", //yellow
                        "#008000", // green
                        "#000080", // blue
                        "#FF00FF", //pink
                        "#800080", //purple
    };

    String [] colorNames = {
            "White",
            "Red",
            "Yellow",
            "Green",
            "Blue",
            "Pink",
            "Purple"
    };
    int hexColorSelected = 0,nameColorSelected = 0;
    int score = 0;
    ExecutorService threadPoolExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = (Switch) findViewById(R.id.switchy);
        scoreTxt = (TextView) findViewById(R.id.scoreTxt);
        timeTxt = (TextView) findViewById(R.id.timeTxt);
        colorTxt = (TextView) findViewById(R.id.colorTxt);
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        trueBtn = (Button) findViewById(R.id.trueBtn);
        falseBtn = (Button) findViewById(R.id.falseBtn);
        falseBtn.setEnabled(false);
        trueBtn.setEnabled(false);
        threadPoolExecutor = Executors.newSingleThreadExecutor();
        trueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Future longRunningTaskFuture = threadPoolExecutor.submit(final_handlerTask);
//                longRunningTaskFuture.cancel(true);
//                final_handlerTask.run();
                falseBtn.setEnabled(false);
                trueBtn.setEnabled(false);
                timeleft = 0;
                if(hexColorSelected == nameColorSelected){
                    score++;
                    scoreTxt.setText("Score: " + score);
                }
                else{
                    score--;
                    scoreTxt.setText("Score: " + score);
                }
            }
        });

        falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Future longRunningTaskFuture = threadPoolExecutor.submit(final_handlerTask);
//                longRunningTaskFuture.cancel(true);
//                final_handlerTask.run();
                falseBtn.setEnabled(false);
                trueBtn.setEnabled(false);
                timeleft = 0;
                if(hexColorSelected == nameColorSelected){
                    score--;
                    scoreTxt.setText("Score: " + score);
                }
                else{
                    score++;
                    scoreTxt.setText("Score: " + score);
                }
            }
        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    falseBtn.setEnabled(true);
                    trueBtn.setEnabled(true);
                    final_handler = new Handler();
                    final_handlerTask = new Runnable() {
                        @Override
                        public void run() {
                            m_handler2 = new Handler();
                            m_handlerTask2 = new Runnable() {
                                @Override
                                public void run() {
                                    final Random random = new Random();
                                    int imageColorPicker = random.nextInt(colors.length - 1);
                                    frameLayout.setBackgroundColor(Color.parseColor(colors[imageColorPicker]));
                                    hexColorSelected = imageColorPicker;
                                }
                            };
                            m_handlerTask2.run();

                            m_handler3 = new Handler();
                            m_handlerTask3 = new Runnable() {
                                @Override
                                public void run() {
                                    final Random random = new Random();
                                    int imageColorPicker = random.nextInt(colorNames.length - 1);
                                    colorTxt.setText(colorNames[imageColorPicker]);
                                    nameColorSelected = imageColorPicker;
                                }
                            };
                            m_handlerTask3.run();

                            m_handler = new Handler();
                            m_handlerTask = new Runnable() {
                                @Override
                                public void run() {
                                    if(timeleft>=0) {
                                        timeTxt.setText(Integer.toString(timeleft));
                                        timeleft--;
                                    }
                                    else {
                                        timeleft = 10;
                                        m_handler2.removeCallbacks(m_handlerTask2);
                                        m_handler3.removeCallbacks(m_handlerTask3);
                                        m_handler.removeCallbacks(m_handlerTask);
                                        m_handlerTask3.run();
                                        m_handlerTask2.run();
                                        falseBtn.setEnabled(true);
                                        trueBtn.setEnabled(true);
                                    }
                                    m_handler.postDelayed(m_handlerTask, 1000);
                                }
                            };
                            m_handlerTask.run();
                        }
                    };
                    final_handlerTask.run();
                }
                else {
                    falseBtn.setEnabled(false);
                    trueBtn.setEnabled(false);
                }
            }
        });
    }

    //private class StartTimer extends AsyncTask<>{}
}

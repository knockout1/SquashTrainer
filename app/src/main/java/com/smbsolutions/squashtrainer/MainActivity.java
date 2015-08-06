package com.smbsolutions.squashtrainer;

import com.smbsolutions.squashtrainer.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.FrameLayout;


import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {


    private Button startButton;
    private EditText editText;
    private TextToSpeech ttobj;
    private Random r;
    private FrameLayout background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    ttobj.setLanguage(Locale.UK);
                }
            }
        });

        r = new Random();
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);


        final CounterClass timer = new CounterClass(60000,1000);
        startButton = (Button)findViewById(R.id.start_button);
        background = (FrameLayout)findViewById(R.id.backgroundLayout);



        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getText().equals("Cancel")) {
                    setRandomPin();
                }
            }
        });
        editText = (EditText)findViewById(R.id.editText);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getText().equals("Start")) {
                    startButton.setText("Cancel");
                    timer.start();
                    setRandomPin();
                } else {
                    startButton.setText("Start");
                    timer.cancel();
                }
            }
        });
    }

    private void setRandomPin()
    {

        int randomNum = r.nextInt(6) + 1;
        ttobj.speak(Integer.toString(randomNum), TextToSpeech.QUEUE_FLUSH, null, null);
        switch (randomNum) {

            case 1:
                clearAllPins();
                findViewById(R.id.pin1).setVisibility(View.VISIBLE);
                break;
            case 2:
                clearAllPins();
                findViewById(R.id.pin2).setVisibility(View.VISIBLE);

                break;
            case 3:
                clearAllPins();
                findViewById(R.id.pin3).setVisibility(View.VISIBLE);
                break;
            case 4:
                clearAllPins();
                findViewById(R.id.pin4).setVisibility(View.VISIBLE);
                break;
            case 5:
                clearAllPins();
                findViewById(R.id.pin5).setVisibility(View.VISIBLE);
                break;
            case 6:
                clearAllPins();
                findViewById(R.id.pin6).setVisibility(View.VISIBLE);
        }


    }
    private void clearAllPins()
    {
        findViewById(R.id.pin1).setVisibility(View.INVISIBLE);
        findViewById(R.id.pin2).setVisibility(View.INVISIBLE);
        findViewById(R.id.pin3).setVisibility(View.INVISIBLE);
        findViewById(R.id.pin4).setVisibility(View.INVISIBLE);
        findViewById(R.id.pin5).setVisibility(View.INVISIBLE);
        findViewById(R.id.pin6).setVisibility(View.INVISIBLE);
    }



    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            editText.setText("Completed.");
            ttobj.shutdown();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            editText.setText(hms);
        }
    }




}

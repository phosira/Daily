package com.example.daily;



import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.dinuscxj.progressbar.CircleProgressBar;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static android.content.Context.*;


public class Timer extends Fragment {

    public View view;
    long START_TIME_IN_MILLIS = 10000;
    long Rest_time_in_millis = 5000;
    TextView mTextViewCountDown;
    TextView title;
    TextView pomo;
    Button mButtonStartPause;
    Button mButtonReset;
    Button mButtonquit;
    CountDownTimer mCountDownTimer;
    CountDownTimer mcountdownresttimer;
    CircleProgressBar circleProgressBar;
    boolean mTimerRunning;
    long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    long restTimeleft = Rest_time_in_millis;
    int i = 0;
    int j = 0;
    int cot;
    View decoview;



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_timer, container, false);

        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        mButtonStartPause = view.findViewById(R.id.button_start_pause);
        mButtonReset = view.findViewById(R.id.button_reset);
        mButtonquit = view.findViewById(R.id.quit);
        title = view.findViewById(R.id.pomotitle);
        pomo = view.findViewById(R.id.pomolab);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("키값", MODE_PRIVATE);

        String concentime = sharedPreferences.getString("집중시간", "null");
        String resttime = sharedPreferences.getString("휴식시간", "null");


        if (concentime.equals("25분")) {

            START_TIME_IN_MILLIS = 15000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;

        }
        if (concentime.equals("40분")) {

            START_TIME_IN_MILLIS = 20000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;

        }
        if (concentime.equals("1시간")) {

            START_TIME_IN_MILLIS = 25000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;

        }

        if (resttime.equals("5분")) {

            Rest_time_in_millis = 6000;
            restTimeleft = Rest_time_in_millis;

        }
        if (resttime.equals("10분")) {

            Rest_time_in_millis = 7000;
            restTimeleft = Rest_time_in_millis;

        }
        if (resttime.equals("15분")) {

            Rest_time_in_millis = 8000;
            restTimeleft = Rest_time_in_millis;

        }
        SharedPreferences sp=getActivity().getSharedPreferences("뽀모횟수",MODE_PRIVATE);
        cot=sp.getInt("매일",0);
        pomo.setText(cot + " 뽀모 완료!");
        Toast.makeText(getContext(),cot+"",Toast.LENGTH_SHORT).show();

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decoview= getActivity().getWindow().getDecorView();
                decoview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                if (mTimerRunning) {
                    if (i % 2 == 0) {
                        pauseTimer();
                    } else if (i % 2 == 1) {
                        pauserestTimer();
                    }
                } else {
                    if (i % 2 == 0) {
                        startTimer();
                    } else if (i % 2 == 1) {
                        startrestTimer();
                    }
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decoview= getActivity().getWindow().getDecorView();
                decoview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                if (i % 2 == 0) {
                    resetTimer();
                    title.setText("뽀모도로 집중");
                } else if (i % 2 == 1) {
                    resetrestTimer();
                    title.setText("휴식시간");
                }
            }
        });
        mButtonquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decoview= getActivity().getWindow().getDecorView();
                decoview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                if (mTimerRunning) {
                    if (i % 2 == 0) {
                        quitTimer();
                        title.setText("뽀모도로 집중");
                    } else if (i % 2 == 1) {
                        quitresetTimer();
                        mButtonReset.setText("휴식하기");
                        title.setText("뽀모도로 집중");
                    }
                    mTimerRunning = false;
                } else {
                    quitTimer();
                    mButtonReset.setText("휴식하기");
                    title.setText("뽀모도로 집중");
                }
            }
        });
        if(i%2==0){updateCountDownText();}
        else if(i%2==1){updaterestCountDownText();}

        return view;
    }

    public void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                i++;
                j++;
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("뽀모횟수",MODE_PRIVATE);
                cot=sharedPreferences.getInt("매일",0);
                pomo.setText(cot + " 뽀모 완료!");
                cot=+j;
                Toast.makeText(getContext(),""+cot,Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("매일",cot);
                editor.commit();
                cot=sharedPreferences.getInt("매일",0);
                pomo.setText(cot + " 뽀모 완료!");
                show();

                mTimerRunning = false;
                mButtonStartPause.setText("시작");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                mButtonquit.setVisibility(View.INVISIBLE);

            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("일시정지");
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.VISIBLE);
    }

    public void startrestTimer() {
        mcountdownresttimer = new CountDownTimer(restTimeleft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                restTimeleft = millisUntilFinished;
                updaterestCountDownText();
            }

            @Override
            public void onFinish() {
                i++;
                show();
                mTimerRunning = false;
                mButtonStartPause.setText("시작");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                mButtonquit.setVisibility(View.INVISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("일시정지");
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.VISIBLE);
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("시작");
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.VISIBLE);
    }

    public void pauserestTimer() {
        mcountdownresttimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("시작");
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.VISIBLE);
    }


    public void resetTimer() {
        mButtonReset.setText("휴식하기");
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    public void resetrestTimer() {
        mButtonReset.setText("집중하기");
        restTimeleft = Rest_time_in_millis;
        updaterestCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);

    }

    public void quitTimer() {
        mCountDownTimer.cancel();
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonStartPause.setText("시작");
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    public void quitresetTimer() {
        mcountdownresttimer.cancel();
        mButtonStartPause.setText("시작");
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonquit.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }


    public void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);

    }

    public void updaterestCountDownText() {
        int restminutes = (int) (restTimeleft / 1000) / 60;
        int restseconds = (int) (restTimeleft / 1000) % 60;
        String resttimeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", restminutes, restseconds);
        mTextViewCountDown.setText(resttimeLeftFormatted);

    }

    public void show() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "default");

        if (i % 2 == 0) {
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Daily");
            builder.setContentText("휴식 종료!");
        } else if (i % 2 == 1) {
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Daily");
            builder.setContentText("뽀모도로 집중 종료!");

        }

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("parti", "intent");
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(largeIcon);
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        builder.setAutoCancel(true);


        NotificationManager manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }


        manager.notify(1, builder.build());
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("뽀모횟수",MODE_PRIVATE);
        cot=sharedPreferences.getInt("매일",0);
        cot=+j;
        pomo.setText(cot + " 뽀모 완료!");
        Toast.makeText(getContext(),cot+"",Toast.LENGTH_SHORT).show();

    }

}
package com.example.daily;





import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {


    FragmentManager fm;
    FragmentTransaction ft;
    Daily daily;
    Week week;
    Timer timer;
    Setting setting;
    BottomNavigationView bn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bn = findViewById(R.id.bottomnavigationbar);


        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                daily = new Daily();
                week = new Week();
                timer = new Timer();
                setting = new Setting();

                switch (item.getItemId()) {
                    case R.id.todo:
                        setFrag(0);
                        break;
                    case R.id.month:
                        setFrag(1);
                        break;
                    case R.id.timer:
                        setFrag(2);
                        break;
                    case R.id.setting:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        setFrag(0);
    }

    public void setFrag(int i) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (i) {
            case 0:
                daily = new Daily();
                ft.replace(R.id.frame, daily);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 1:
                week = new Week();
                ft.replace(R.id.frame, week);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 2:
                timer = new Timer();
                ft.replace(R.id.frame, timer);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 3:
                setting = new Setting();
                ft.replace(R.id.frame, setting);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }

    }

    public void hidebottom(){
           bn.setVisibility(View.GONE);
    }
    public void showbottom(){
        bn.setVisibility(View.VISIBLE);
    }
}


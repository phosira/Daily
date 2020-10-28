package com.example.daily;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DailyRecyclerItem{
    String time ;
    String title;




    public DailyRecyclerItem(String title, String time){


        this.title=title;
        this.time=time;
    }



    public void setTitle(String title) {
        this.title = title ;
    }

    public String getTitle() {
        return this.title ;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
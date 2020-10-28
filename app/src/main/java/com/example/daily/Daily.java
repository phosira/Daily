package com.example.daily;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dinuscxj.progressbar.CircleProgressBar;
import android.view.View;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

public class Daily extends Fragment {
    private View view;
    int i;
    int hour;
    int minute;
    int second;
    int value=0;//시연용
    int add=1;//시연용
    Handler handler;
    Calendar day;
    PieChart pieChart;
    CircleProgressBar circleProgressBar;
    DailyRecyclerAdapter adapter;
    RecyclerView rv;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<DailyRecyclerItem> items;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.activity_daily, container, false);

        circleProgressBar = view.findViewById(R.id.circle_view);
        //circleProgressBar.setMax(86400);
        circleProgressBar.setMax(10);//시연용
        handler = new Handler();
        ProgressClock();

        pieChart = view.findViewById(R.id.pieChart);
        setPieChart();
        buildRecyclerView();

        return view;

    }
void ProgressClock(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    value = value + add;
                    if (value >= 11 || value <= 0) {
                        if(value==11){
                           value=1;
                        }
                    }//시연용
                   /*day = Calendar.getInstance();
                        hour = day.get(Calendar.HOUR_OF_DAY);
                        minute = day.get(Calendar.MINUTE);
                        second = day.get(Calendar.SECOND);
                        i = (hour*60*60)+(minute*60)+second;*/
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            circleProgressBar.setProgress(value);

                            //circleProgressBar.setProgress(i);
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        }).start();

    }
    public void setPieChart(){
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setUsePercentValues(false);
        pieChart.setRotationEnabled(false);
        pieChart.setDescription(null);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        ArrayList data = new ArrayList();
        data.add(new Entry(10f,0));
        data.add(new Entry(20f,0));
        data.add(new Entry(5f,0));
        data.add(new Entry(10f,0));
        ArrayList todo = new ArrayList();
        todo.add("취침");
        todo.add("공부");
        todo.add("휴식");
        todo.add("운동");

        PieDataSet dataset = new PieDataSet(data,"");
        dataset.setDrawValues(false);
        dataset.setColor(1);
        PieData pieData = new PieData(todo,dataset);
        pieData.setValueTextSize(20f);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    private void buildRecyclerView() {


            rv = view.findViewById(R.id.dailylist);
            rv.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            adapter = new DailyRecyclerAdapter(items);
            rv.setLayoutManager(mLayoutManager);
            rv.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(rv);
    }
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            items.remove(position-1);
            adapter.notifyItemRemoved(position);
            if(items.isEmpty()){
                adapter.notifyItemRemoved(position);
            }
        }
    };
}

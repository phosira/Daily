package com.example.daily;




import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.*;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.*;

import static android.content.Context.MODE_PRIVATE;


public class Week extends Fragment {

    private View view;
    TextView selectday;
    MaterialCalendarView calendarView;
    RecyclerView rv;
    FrameLayout fm;
    ArrayList<RecyclerItem> items;
    RecyclerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HashMap<CalendarDay,List<RecyclerItem>> map = new HashMap<>();




    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.activity_week, container, false);
        FloatingActionButton btn = view.findViewById(R.id.fab);
        calendarView = view.findViewById(R.id.calendarView);
        selectday = view.findViewById(R.id.selectday);
        Button backtoday = view.findViewById(R.id.backtoday);
        Button backselected = view.findViewById(R.id.selectedback);
        fm = view.findViewById(R.id.none);



        if(items==null){
            items = new ArrayList<>();
            fm.setVisibility(View.VISIBLE);
        }
        loadData();
        buildRecyclerView();




        backtoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setCurrentDate(new Date(System.currentTimeMillis()));
            }
        });
        backselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setCurrentDate(calendarView.getSelectedDate());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rundialog();

            }

        });


        calendarView.setCurrentDate(new Date(System.currentTimeMillis()));
        calendarView.setSelectedDate(new Date(System.currentTimeMillis()));



        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2018, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull @NotNull MaterialCalendarView widget, @NonNull @NotNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();
                String select = Year + "년 " + Month + "월 " + Day+"일";
                String pickdate = select;
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("날짜",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("요일",pickdate);
                editor.commit();
                selectday.setText(pickdate);


            }



        });

        return view;
    }



    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {}


        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }
    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }
    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();

        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(2.0f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }
    public class EventDecorator implements DayViewDecorator {

        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }



        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }
    public void rundialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.customdialog,null,false);
        builder.setView(view);
        Button exit = view.findViewById(R.id.mod_exitbt);
        Button save = view.findViewById(R.id.mod_bt);
        EditText title = view.findViewById(R.id.mod_title);
        EditText input = view.findViewById(R.id.mod_inputtext);


        final AlertDialog dialog = builder.create();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title1 = title.getText().toString();
                String input1 = input.getText().toString();
                RecyclerItem recyclerItem = new RecyclerItem(title1, input1);
                items.add(recyclerItem);
                saveData();
                fm.setVisibility(View.GONE);

                dialog.dismiss();


            }
        });


        dialog.show();
    }
    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("todo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.clear();
        editor.putString("todolist", json);
        editor.apply();

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("todo", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("todolist", null);
        Type type = new TypeToken<ArrayList<RecyclerItem>>() {}.getType();
        items = gson.fromJson(json, type);

        if (items.isEmpty()) {
            items = new ArrayList<>();
            fm.setVisibility(View.VISIBLE);
        }
    }
    private void buildRecyclerView() {
        if(items.isEmpty()){
            items = new ArrayList<>();
            fm.setVisibility(View.VISIBLE);
        }else {
            fm.setVisibility(View.GONE);
            rv = view.findViewById(R.id.todolist);
            rv.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getContext());
            adapter = new RecyclerAdapter(items);
            rv.setLayoutManager(mLayoutManager);
            rv.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(rv);
        }
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
                        fm.setVisibility(View.VISIBLE);
            }
        }
    };


}

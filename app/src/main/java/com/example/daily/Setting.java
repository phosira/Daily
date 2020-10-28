package com.example.daily;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import org.jetbrains.annotations.NotNull;



import static android.content.Context.MODE_PRIVATE;

public class Setting extends Fragment {
    private View view;
    String ID;
    ImageView profile;
    Button logout;
    TextView login;
    String name;
    String image;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getContext(),"destroy",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getContext(),"stop",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("아이디",MODE_PRIVATE);
        ID = sharedPreferences.getString("UserEmail", null);
        name = sharedPreferences.getString("UserName",null);
        image = sharedPreferences.getString("이미지", null);

        if (ID != null) {
            login.setText(name + "님 환영합니다");
            logout.setVisibility(View.VISIBLE);
        }
        if (image != null) {
            Bitmap bitmap = StringToBitmap(image);
            profile.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(),"pause",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(),"resume",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(),"attach",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(),"oncreate",Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_settings, container, false);



        profile = view.findViewById(R.id.profile);
        TextView time = view.findViewById(R.id.pomotimeset);
        login = view.findViewById(R.id.login_signin);
        CardView mail = view.findViewById(R.id.mailcaredview);
        CardView recomand = view.findViewById(R.id.recomandcardview);
        logout = view.findViewById(R.id.logout);




            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences= getActivity().getSharedPreferences("아이디",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    logout.setVisibility(View.INVISIBLE);

                    Setting setting = new Setting();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, setting);
                    fragmentTransaction.commit();
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ID==null) {
                    Intent intent = new Intent(getActivity(), LogIN.class);
                    startActivity(intent);
                    }else{
                        Intent intent = new Intent(getActivity(), Loginpage.class);
                        startActivity(intent);
                    }
                }
            });


        recomand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msg = new Intent(Intent.ACTION_SEND);

                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=Daily");
                msg.putExtra(Intent.EXTRA_TITLE, "Daily를 사용해보세요");
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "앱을 선택해 주세요"));
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/text");
                String[] address = {"email@address.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "문의해주세요");
                email.putExtra(Intent.EXTRA_TEXT, "어떤 문의사항이 있나요?");
                startActivity(email);


            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runpomodialog();
            }
        });




        return view;
    }
    public void runpomodialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pomodialog,null,false);
        builder.setView(view);
        final Button exit = view.findViewById(R.id.pomo_exitbt);
        final Button save = view.findViewById(R.id.pomo_savebt);
        final Spinner concen= view.findViewById(R.id.pomo_cocen);
        final Spinner rest = view.findViewById(R.id.pomo_rest);




        final AlertDialog dialog = builder.create();

        concen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String concent = concen.getSelectedItem().toString();
                String restt =  rest.getSelectedItem().toString();

                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("키값",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.putString("집중시간",concent);
                editor.putString("휴식시간",restt);
                editor.commit();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public Bitmap StringToBitmap(String encodeString){
        try{
            byte[] encedeByte= Base64.decode(encodeString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encedeByte,0,encedeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }

    }
}
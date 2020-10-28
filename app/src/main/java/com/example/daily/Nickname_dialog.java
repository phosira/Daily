
package com.example.daily;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Nickname_dialog extends Dialog {

    private EditText et_text;
    private Context mContext;


    public Nickname_dialog(@NonNull @NotNull Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_dialog);


        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        et_text = findViewById(R.id.put_text);
        Button saveButton = findViewById(R.id.btnSave);
        Button cancelButton = findViewById(R.id.btnCancel);

        SharedPreferences sharedPreferences= getContext().getSharedPreferences("아이디",MODE_PRIVATE);
       String name = sharedPreferences.getString("UserName",null);

        et_text.setText(name);


        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String change = et_text.getText().toString();
                SharedPreferences sharedPreferences= getContext().getSharedPreferences("아이디",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("UserName");
                editor.putString("UserName",change);
                editor.commit();
                TextView text = findViewById(R.id.nickname);
                Toast.makeText(mContext,"변경 성공!", Toast.LENGTH_SHORT).show();
                dismiss();

            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

    }


}
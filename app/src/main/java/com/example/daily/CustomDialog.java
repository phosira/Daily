package com.example.daily;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class CustomDialog extends Dialog {
    private Dialoglistener listener;
    private Button mod_bt;
    private Button mod_exit;
    private EditText mod_title, mod_inputtext;
    private String title,inputtext;




    public CustomDialog(Context context, final int position, RecyclerItem listViewItem) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.customdialog);

        title= listViewItem.getTitle();
        inputtext = listViewItem.getInputtext();


        mod_title = findViewById(R.id.mod_title);
        mod_title.setText(title);

        mod_inputtext=findViewById(R.id.mod_inputtext);
        mod_inputtext.setText(inputtext);

        mod_bt=findViewById(R.id.mod_bt);
        mod_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    String title=mod_title.getText().toString();
                    String inputtext = mod_inputtext.getText().toString();


                    listener.onFinsh(position,listViewItem);
                    dismiss();
                }
            }
        });
        mod_exit = findViewById(R.id.mod_exitbt);
        mod_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setDialogListener(Dialoglistener listener){
        this.listener=listener;
    }
}

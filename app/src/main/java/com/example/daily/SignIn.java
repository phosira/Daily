package com.example.daily;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class SignIn extends AppCompatActivity {

    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ImageView back = findViewById(R.id.backlogin);
        Button signin = findViewById(R.id.signinbutton);
        Button check = findViewById(R.id.checkbutton);
        EditText id = findViewById(R.id.editid);
        EditText nic = findViewById(R.id.Name);
        EditText pw = findViewById(R.id.editpw);
        EditText pwcheck = findViewById(R.id.editpwcheck);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    finish();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEmail = id.getText().toString();
                if(validate){
                    return;
                }
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()){
                    Toast.makeText(getApplicationContext(), "올바른 형식이 아닙니다.ex)abc@abc.co.kr", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        if(success){
                            Toast.makeText(getApplicationContext(),"사용가능",Toast.LENGTH_SHORT).show();
                            id.setEnabled(false);
                            validate = true;
                            check.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        }else{
                            Toast.makeText(getApplicationContext(),"이미 존재하는 이메일입니다",Toast.LENGTH_SHORT).show();
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(UserEmail,responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(SignIn.this);
                requestQueue.add(validateRequest);

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String UserEmail = id.getText().toString();
            final String UserName = nic.getText().toString();
            final String UserPwd = pw.getText().toString();
            final String passCk = pwcheck.getText().toString();

            if(UserEmail.equals("") || UserPwd.equals("") || UserName.equals("")){
                Toast.makeText(getApplicationContext(),"모든 정보를 입력해 주세요",Toast.LENGTH_LONG).show();
                return;
            }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(UserPwd.equals(passCk)) {
                                if (success) {

                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", UserName), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignIn.this, LogIN.class);
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {

                                Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                SigninRequest signinRequest = new SigninRequest( UserEmail, UserPwd, UserName, responseListener);
                RequestQueue queue = Volley.newRequestQueue( SignIn.this );
                queue.add( signinRequest );
            }
        });
    }
}
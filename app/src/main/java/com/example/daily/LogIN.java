package com.example.daily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class LogIN extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_i_n);

        ImageView back = findViewById(R.id.backsetting);
        TextView sigin = findViewById(R.id.signin);
        Button login = findViewById(R.id.loginbutton);
        EditText id = findViewById(R.id.editid);
        EditText pw = findViewById(R.id.editpw);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        SignIn.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = id.getText().toString();
                String PW = pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );
                            String UserEmail = jsonObject.getString( "UserEmail" );
                            String UserPwd = jsonObject.getString( "UserPwd" );
                            String UserName = jsonObject.getString( "UserName" );

                            if(success) {

                                SharedPreferences sharedPreferences= getSharedPreferences("아이디",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString( "UserEmail", UserEmail );
                                editor.putString( "UserPwd", UserPwd );
                                editor.putString( "UserName", UserName );
                                editor.commit();

                                finish();

                            } else if(!UserEmail.equals(ID)){
                                Toast.makeText( getApplicationContext(), "아이디가 올바르지 않습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }else if(!UserPwd.equals(PW)){
                                Toast.makeText( getApplicationContext(), "비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( ID, PW, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LogIN.this );
                queue.add( loginRequest );

            }
        });

    }

}
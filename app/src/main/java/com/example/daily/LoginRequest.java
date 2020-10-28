package com.example.daily;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://phosira.dothome.co.kr/Login.php";
    private Map<String, String> map;

    public LoginRequest(String UserEmail, String UserPwd, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserEmail", UserEmail);
        map.put("UserPwd", UserPwd);
    }
    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}

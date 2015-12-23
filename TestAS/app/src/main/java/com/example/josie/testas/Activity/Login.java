package com.example.josie.testas.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.josie.testas.MainActivity;
import com.example.josie.testas.Model.Student;
import com.example.josie.testas.R;
import com.example.josie.testas.UI.ClearEdittext;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Josie on 2015/12/17.
 */

public class Login extends Activity {
    private ClearEdittext userclearEdittext;
    private ClearEdittext passwordEdittext;
    private Button surebutton;
    private static String username;
    private static String password;
    private RequestQueue requestQueue;
    private Student student = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        initview();
        test();
    }

    private void test() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,"http://222.24.63.100:9160/TimeZone/account/login?username=04121110&password=glacierlx1994", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response: ", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", error.getMessage());
            }
        });
        requestQueue.add(request);
    }

    private void initview() {
        userclearEdittext = (ClearEdittext)findViewById(R.id.login_et_userid);
        passwordEdittext = (ClearEdittext)findViewById(R.id.login_et_password);
        surebutton = (Button)findViewById(R.id.login_bt_sure);

        surebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = userclearEdittext.getText().toString().trim();
                password = passwordEdittext.getText().toString().trim();
                try {
                    if (check(username, password)) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("StudentInfo", (Serializable) student);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                //TODO
//                调用登录接口判断是否可以正常登录
//                添加加载中动态效果

            }
        });
    }

    private boolean check(String username, String password) throws MalformedURLException {
        String path = "https://222.24.63.100:9160/TimeZone/account/login?";
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = null;
        final String urlString = "http://192.168.56.1:8080/TimeZone/account/login?username=" + username +"&password=" +password;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,urlString,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("login",response.toString());
                try {
                    JSONObject studentObject = response.getJSONObject("info");
                    student = new Student();
                    student.setAccessToken(studentObject.getString("accessToken"));
                    student.setId(studentObject.getInt("id"));
                    student.setStuClass(studentObject.getString("stuClass"));
                    student.setStuName(studentObject.getString("stuName"));
                    student.setStuId(studentObject.getString("stuId"));
                    student.setStuMajor(studentObject.getString("stuMajor"));
                    student.setStuIntroduction(studentObject.getString("stuIntroduction"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.e("loginerror",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
        if (student != null)
        {
            Log.e("student", student.toString());
            return true;
        }
        return false;
    }


}

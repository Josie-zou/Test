package com.example.josie.testas.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.josie.testas.MainActivity;
import com.example.josie.testas.R;
import com.example.josie.testas.UI.ClearEdittext;

/**
 * Created by Josie on 2015/12/17.
 */
public class Login extends Activity {
    private ClearEdittext userclearEdittext;
    private ClearEdittext passwordEdittext;
    private Button surebutton;
    private static String username;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        initview();
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
                //TODO
//                调用登录接口判断是否可以正常登录
//                添加加载中动态效果
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }
}

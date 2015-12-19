package com.example.josie.testas.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.josie.testas.Activity.Login;
import com.example.josie.testas.R;


public class Animation extends Activity {

    private ImageView iv_welcome;//动画的imageview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置为无标题，要在setoncontent之前设置
        setContentView(R.layout.first);
        iv_welcome = (ImageView) findViewById(R.id.img_welcome);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);//第一个参数为动画刚开始时的透明度，第二个参数为动画结束时的透明度
        alphaAnimation.setDuration(3000);//设置动画持续时间为3秒
        iv_welcome.startAnimation(alphaAnimation);//启动动画

        alphaAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {

                iv_welcome.setBackgroundResource(R.drawable.animation_photo);//设置动画的背景图片
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                //在动画结束的时候进行页面跳转

                Intent intent = new Intent(Animation.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.josie.testas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.race604.flyrefresh.FlyRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class MainActivity extends ActionBarActivity implements FlyRefreshLayout.OnPullRefreshListener, Toolbar.OnMenuItemClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private FlyRefreshLayout flyRefreshLayout;
//    private ImageButton mebttn;
//    private ImageButton signbttn;
    private RecyclerView recyclerView;
    private List<TimeModel> list;
    private GalleryAdapter timeLineAdapter;
    private LinearLayout linearLayout;
    private LinearLayoutManager linearLayoutManager;
    private Handler handler = new Handler();
    private Toolbar toolbar;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastItems;
    private int visibleThreshold = 5;
    private boolean onLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initData();
        initView();
//        initToolbar();
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.item_default);
        toolbar.setNavigationIcon(R.drawable.me);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitle("toolbar");
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("home", "home");
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
            }
        });
//        addBlur();
        Log.e("start", "start");
        recyclerView.setAdapter(timeLineAdapter);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                visibleItemCount = linearLayoutManager.getChildCount();
//                totalItemCount = linearLayoutManager.getItemCount();
//                pastItems = linearLayoutManager.findFirstVisibleItemPosition();
//                if (!onLoading){
//                    if ((pastItems + visibleItemCount) >= totalItemCount){
//                        Toast.makeText(MainActivity.this, "loading...", Toast.LENGTH_SHORT ).show();
//                        onLoading = true;
//                        loadmoredata();
//                        //TODO
//                    }
//                }
//                if (onLoading && ((totalItemCount - visibleItemCount) == (pastItems + visibleItemCount))){
//                    onLoading = true;
//                }
//            }
//        });


    }

    private void loadmoredata() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list = getnewdata(list);
                timeLineAdapter.notifyDataSetChanged();
                linearLayoutManager.scrollToPosition(0);
            }
        }, 1000);
    }

    private List<TimeModel> getnewdata(List<TimeModel> list) {
        if (list == null){
            list = new ArrayList<TimeModel>();
        }
        list.add(new TimeModel("zouzhili","hahahahha","dfjsiodjfsdoig"));
        list.add(new TimeModel("zouzhili","hahahahha","dfjsiodjfsdoig"));
        list.add(new TimeModel("zouzhili","hahahahha","dfjsiodjfsdoig"));

        return list;
    }

    private void initToolbar() {
        toolbar.setLogo(R.drawable.item_default);
        toolbar.setNavigationIcon(R.drawable.me);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitle("toolbar");
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setSubtitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("home", "home");
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBlur() {
       linearLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
           @Override
           public boolean onPreDraw() {
//               linearLayout.getViewTreeObserver().removeOnPreDrawListener(this);
//               linearLayout.buildDrawingCache();;
               Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
               blur(bitmap, linearLayout);
               return  true;
           }
       });
    }



    private void initData() {
        list = new ArrayList<TimeModel>();
        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:25:00"));
        list.add(new TimeModel( "任立翔", "gsghsg,jsdifdfgdf", "2015.12.12 20:26:00"));
        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:27:00"));
        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:28:00"));
        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:29:00"));
        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:30:00"));
        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:31:00"));
//        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:32:00"));
//        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:33:00"));
//        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:34:00"));
//        list.add(new TimeModel( "任立翔", "woainiwoainiwoainiwoainiwoaini", "2015.12.12 20:35:00"));

        Log.e("list",list.toString());

    }

    private void initView() {
        linearLayout = (LinearLayout)findViewById(R.id.main_layout);
        flyRefreshLayout = (FlyRefreshLayout)findViewById(R.id.fly_layout);
        recyclerView = (RecyclerView)findViewById(R.id.m_recycleview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        timeLineAdapter = new GalleryAdapter(this,list);
        toolbar = (Toolbar)findViewById(R.id.main_toolbar);
//        listView.setAdapter(timeLineAdapter);

//        mebttn = (ImageButton)findViewById(R.id.me_imgbttn);
//        signbttn = (ImageButton)findViewById(R.id.sign_in_imgbttn);

        timeLineAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });

        flyRefreshLayout.setOnPullRefreshListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    private void  blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;
        float radius = 20;

//        Bitmap bitmap1 = bitmap.copy(bitmap.getConfig(), true);
//        final RenderScript renderScript = RenderScript.create(context);
//        final Allocation input = Allocation.createCubemapFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
//        final Allocation output = Allocation.createTyped(renderScript, input.getType());
//        final ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
//        scriptIntrinsicBlur.setRadius(radius);
//        scriptIntrinsicBlur.setInput(input);
//        scriptIntrinsicBlur.forEach(output);
//        output.copyTo(bitmap);
//        return bitmap;
//
        Log.e("hegiyh", Integer.toString(view.getMeasuredHeight()));
        Log.e("width", Integer.toString(view.getMeasuredWidth()));
        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredHeight() / scaleFactor),
                (int) (view.getMeasuredWidth() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
                / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setBackground(new BitmapDrawable(getResources(), overlay));
        System.out.println(System.currentTimeMillis() - startMs + "ms");
    }

    @Override
    public void onRefresh(FlyRefreshLayout view) {
        View child = recyclerView.getChildAt(0);
        if (child != null){
            //TODO
            //需要添加刷新操作，更新recycleview
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flyRefreshLayout.onRefreshFinish();
            }
        }, 2000);
    }

    private void bounceAnimateView(View viewById) {
        initData();
    }


//    动画结束的的时候更新数据
    @Override
    public void onRefreshAnimationEnd(FlyRefreshLayout view) {
        Log.e("hello","loading?");
        Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT).show();
        loadmoredata();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }
}
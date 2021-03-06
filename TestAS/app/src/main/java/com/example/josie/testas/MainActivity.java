package com.example.josie.testas;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.josie.testas.Activity.AboutMe;
import com.example.josie.testas.Activity.newads;
import com.example.josie.testas.Adapter.GalleryAdapter;
import com.example.josie.testas.Model.Notice;
import com.example.josie.testas.Model.Student;
import com.example.josie.testas.Model.TimeModel;
import com.example.josie.testas.UI.DialogFragment;
import com.example.josie.testas.UI.FastBlur;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.race604.flyrefresh.FlyRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity implements FlyRefreshLayout.OnPullRefreshListener, Toolbar.OnMenuItemClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private FlyRefreshLayout flyRefreshLayout;
    private FloatingActionButton actionButton;
    private RecyclerView recyclerView;
    private List<Notice> noticeList;
    private GalleryAdapter timeLineAdapter;
    private LinearLayout linearLayout;
    private LinearLayoutManager linearLayoutManager;
    private Handler handler = new Handler();
    private Toolbar toolbar;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastItems;
    private MenuItem menuItem;
    private boolean ifsign_in = true;
    private Student student;
    private String token;
    private RequestQueue requestQueue;
//    private int visibleThreshold = 5;
//    private boolean onLoading = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        student = (Student) (Student) intent.getSerializableExtra("StudentInfo");
        Log.e("student", student.toString());
        token = student.getAccessToken();
        requestQueue = Volley.newRequestQueue(this);

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
                Intent intent = new Intent(MainActivity.this, AboutMe.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                finish();
            }
        });
//        addBlur();
        Log.e("start", "start");
        recyclerView.setAdapter(timeLineAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastItems = linearLayoutManager.findFirstVisibleItemPosition();
//                if (!onLoading) {
                //设置提前加载
                    if ((pastItems + visibleItemCount) >= totalItemCount-2) {
//                        Toast.makeText(MainActivity.this, "loading...", Toast.LENGTH_SHORT).show();
//                        onLoading = true;
                        if (noticeList == null){
                            Toast.makeText(getApplicationContext(), "loading...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadmoredata();
                        }
                        //TODO
                    }
//                }
//                if (onLoading && ((totalItemCount - visibleItemCount - 2) == (pastItems + visibleItemCount))) {
//                    onLoading = false;
//                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void loadmoredata() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                noticeList = getnewdata(noticeList);
//                timeLineAdapter.notifyItemInserted(totalItemCount);
                timeLineAdapter.notifyDataSetChanged();
                linearLayoutManager.scrollToPosition(totalItemCount);
            }
        }, 1000);
    }

    private List<Notice> getnewdata(List<Notice> list) {
        if (list == null) {
            list = new ArrayList<Notice>();
        }
        JsonObjectRequest getMoreRequest = null;
        final String token = student.getAccessToken();
        String stringUrl = "http://192.168.56.1:8080/TimeZone/notice/0?accessToken=" + token;;
        getMoreRequest = new JsonObjectRequest(Request.Method.GET, stringUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("dongtai", response.toString());
                try {
                    JSONArray noticeJson = response.getJSONArray("notices");
                    for (int i = 0; i < noticeJson.length(); i ++){
                        JSONObject jsonObject = noticeJson.getJSONObject(i);
                        Notice notice = new Notice();
                        notice.setAccessToken(token);
                        notice.setContent(jsonObject.getString("content"));
                        notice.setDate(convertToDate(jsonObject.getString("date")));
                        notice.setId(jsonObject.getInt("id"));
                        notice.setStuName(jsonObject.getString("stuID"));
                        noticeList.add(notice);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("dongtai", "dongtaierror");
            }
        });
        requestQueue.add(getMoreRequest);
        Log.e("notices", noticeList.toString());
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
                return true;
            }
        });
    }


    private void initData() {
        noticeList = new ArrayList<Notice>();
        JsonObjectRequest firstnoticeRequest = null;
        Log.e("token", token);
        String stringUrl = "http://192.168.56.1:8080/TimeZone/notice?accessToken=" + token;
        Log.e("stringURL",stringUrl);
        firstnoticeRequest = new JsonObjectRequest(Request.Method.GET, stringUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("dongtai", response.toString());
                try {
                    JSONArray noticeJson = response.getJSONArray("notices");
                    for (int i = 0; i < noticeJson.length(); i ++){
                        JSONObject jsonObject = noticeJson.getJSONObject(i);
                        Notice notice = new Notice();
                        notice.setAccessToken(token);
                        notice.setContent(jsonObject.getString("content"));
                        String datetime = jsonObject.getString("date");
                        String date = convertToDate(datetime);
                        notice.setDate(date);
                        notice.setStuName(jsonObject.getString("stuID"));
                        notice.setId(jsonObject.getInt("id"));
                        noticeList.add(notice);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("dongtai", "dongtaierror");
            }
        });
        requestQueue.add(firstnoticeRequest);

        Log.e("notices", noticeList.toString());
    }

    private String convertToDate(String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Long(datetime));

        return time ;
    }

    private void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        actionButton = (FloatingActionButton)findViewById(R.id.action_add);
        flyRefreshLayout = (FlyRefreshLayout) findViewById(R.id.fly_layout);
        recyclerView = (RecyclerView) findViewById(R.id.m_recycleview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        timeLineAdapter = new GalleryAdapter(this, noticeList);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
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
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //添加动态
                Intent intent = new Intent(MainActivity.this, newads.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.sign_in);

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void blur(Bitmap bkg, View view) {
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
        if (child != null) {
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
        Log.e("hello", "loading?");
        //TODO
        //更新数据源
//        Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT).show();
//        loadmoredata();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_in:
                if (ifsign_in){
                    menuItem.setIcon(R.drawable.qiantui);
                    ifsign_in = false;
                    Toast.makeText(getApplicationContext(),"sign_in", Toast.LENGTH_SHORT).show();
                }
                else {
                    DialogFragment dialogFragment = DialogFragment.newInstance(4, 5.0f,true,true);
                    dialogFragment.show(getFragmentManager(), "sign up");
//                    Toast.makeText(getApplicationContext(),"sign_up", Toast.LENGTH_SHORT).show();
//                    Runnable runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogFragment dialogFragment = new DialogFragment();
//                            dialogFragment.show(getFragmentManager(), "Sign Up");
//                        }
//                    };
//
                }
                break;
            case R.id.action_setting:
                Toast.makeText(getApplicationContext(),"setting", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.josie.testas/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.josie.testas/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

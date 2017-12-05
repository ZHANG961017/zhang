package com.bwie.threadstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bwie.threadstudy.bean.Event;
import com.bwie.threadstudy.bean.FileInfo;
import com.bwie.threadstudy.service.MyService;
import com.bwie.threadstudy.util.DownLoadUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DownLoadActivity extends AppCompatActivity {

    private TextView title;
    private Button start,stop;
    private ProgressBar progressBar;
    private String url;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        context = this;
        //得到传过来的地址
        url = getIntent().getStringExtra("url");
        //获取控件
        title = (TextView) findViewById(R.id.titles);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        progressBar = (ProgressBar) findViewById(R.id.pb);

        progressBar.setMax(100);
        //EventBus订阅
        EventBus.getDefault().register(this);
        //开始下载
        startDownLoad(url);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadUtils.ispause = false;
                startDownLoad(url);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadUtils.ispause = true;
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Event ev){
        progressBar.setProgress(ev.getCount());
    }

    public void startDownLoad(String url){
        Log.e("url",url);
        Intent intent = new Intent(DownLoadActivity.this, MyService.class);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(0);
        fileInfo.setUrl(url);
        intent.putExtra("bean",fileInfo);
        DownLoadUtils.ispause = false;
        startService(intent);
    }
}

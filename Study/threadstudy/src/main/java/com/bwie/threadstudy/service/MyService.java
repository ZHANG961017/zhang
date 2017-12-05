package com.bwie.threadstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.bwie.threadstudy.bean.FileInfo;
import com.bwie.threadstudy.util.DownLoadUtils;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyService extends Service{
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                FileInfo bean = (FileInfo) msg.obj;
                DownLoadUtils loadUtils = new DownLoadUtils(MyService.this, bean);
                loadUtils.downLoad();
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //得到传过来的值
        final FileInfo bean = (FileInfo) intent.getSerializableExtra("bean");
        //网络请求，获取需下载文件长度
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(bean.getUrl());
                    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setConnectTimeout(3000);
                    httpUrlConnection.setRequestMethod("GET");
                    int length = -1;
                    if (httpUrlConnection.getResponseCode() == 200){
                        //获取文件长度
                        length = httpUrlConnection.getContentLength();
                    }
                    if (length <= 0){
                        return;
                    }
                    //创建文件夹
                    //创建相同大小的本地文件
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/mdownload");
                    //文件夹不存在则创建
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    //创建文件
                    File file = new File(dir, "load.mp4");
                    //实例RandomAccessFile
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                    randomAccessFile.setLength(length);
                    //长度给bean对象
                    bean.setLength(length);
                    //handler发送消息
                    Message message = new Message();
                    message.what = 0;
                    message.obj = bean;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}

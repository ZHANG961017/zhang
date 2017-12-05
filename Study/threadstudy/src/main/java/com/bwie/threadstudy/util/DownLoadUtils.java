package com.bwie.threadstudy.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.bwie.threadstudy.DownLoadActivity;
import com.bwie.threadstudy.app.MyApp;
import com.bwie.threadstudy.bean.Event;
import com.bwie.threadstudy.bean.FileInfo;
import com.bwie.threadstudy.dao.com.bwei.versionupdata.bean.FileInfoDao;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownLoadUtils {
    public static boolean ispause = false;
    private Context context;
    private FileInfo bean;
    private int finshed;
    private final FileInfoDao dao;
    private List<FileInfo> list;

    public DownLoadUtils(Context context, FileInfo bean) {
        this.context = context;
        this.bean = bean;
        dao = MyApp.getInstances().getDaoSession().getFileInfoDao();
    }

    public void downLoad(){
        //根据url查询数据库，看是否有未完成下载的任务
        list = (List<FileInfo>) dao.queryBuilder().where(FileInfoDao.Properties.Url.eq(bean.getUrl())).build().list();
        if (this.list.size() == 0){
            //第一次下载 创建子线程进行下载
            new MyThread(bean).start();
        }else {
            //中间开始下载
            FileInfo info = this.list.get(0);
            new MyThread(info).start();
        }
    }

    class MyThread extends Thread{
        private FileInfo fileInfo;

        public MyThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
            super.run();
            if (list.size() == 0){
                //向数据库添加线程信息
                dao.insert(fileInfo);
            }
            //进行网络请求
            try {
                URL url = new URL(fileInfo.getUrl());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                //设置下载的位置
                int start = fileInfo.getStart()+fileInfo.getNow();
                //从指定位置开始请求
                connection.setRequestProperty("Range","bytes=" + start + "-" + fileInfo.getLength());
                //设置文件写入位置
                String[] split = fileInfo.getUrl().split("|/");
                File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/mdownload",split[split.length-1]+".apk");
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(start);
                //更新下载的进度
                finshed = fileInfo.getNow();
                if (connection.getResponseCode() == 206){//断点下载响应码206
                    //获得文件流
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[512];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer))!= -1){
                        //写入文件
                        randomAccessFile.write(buffer,0,len);
                        //把进度发送给Activity
                        finshed += len;
                        //看时间间隔，时间间隔大于500ms再发
                        if(System.currentTimeMillis() - time >500){
                            time = System.currentTimeMillis();
                            Log.e("time",finshed*100/bean.getLength()+"");
                            EventBus.getDefault().post(new Event(finshed*100/bean.getLength()));
                        }
                        //判断是否是暂停状态
                        if(ispause==true){
                            FileInfo unique = dao.queryBuilder().where(FileInfoDao.Properties.Url.eq(bean.getUrl())).build().unique();
                            Log.e("tag","保存位置："+finshed);
                            unique.setNow(finshed);
                            dao.update(unique);
                            return; //结束循环
                        }
                    }
                    EventBus.getDefault().post(new Event(100));
                    //删除线程信息
                    FileInfo unique = dao.queryBuilder().where(FileInfoDao.Properties.Url.eq(fileInfo.getUrl())).build().unique();
                    if(unique!=null){
                        Log.e("delete","yes");
                        dao.delete(unique);
                    }
                    //apk下载完成后 调用系统的安装方法
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    DownLoadActivity.context.startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

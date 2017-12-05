package com.bwie.threadstudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bwie.threadstudy.api.API;
import com.bwie.threadstudy.bean.VersionBean;
import com.bwie.threadstudy.inter.ApiServer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private int versionCode;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            //得到当前versionName
            versionCode = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API.baseUrl)
                .build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        Flowable<VersionBean> flowable = apiServer.getVersionData("1");
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Subscriber<VersionBean>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(VersionBean versionBean) {
                        Log.e("VersionName",versionBean.getData().getVersionName());
                        if (Integer.parseInt(versionBean.getData().getVersionCode())>versionCode){
                            showNormalDialog();
                            url = versionBean.getData().getApkUrl();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showNormalDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("版本升级");
        normalDialog.setMessage("有新的版本需要更新");
        normalDialog.setPositiveButton("立即更新",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, DownLoadActivity.class);
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("稍后再说",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }
}

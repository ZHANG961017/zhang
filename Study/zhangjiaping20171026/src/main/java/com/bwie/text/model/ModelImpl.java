package com.bwie.text.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bwie.text.SecondActivity;
import com.bwie.text.adapter.MyAdapter;
import com.bwie.text.bean.DataBean;
import com.bwie.text.okHttp.GsonObjectCallback;
import com.bwie.text.okHttp.OkHttp3Utils;
import com.bwie.text.view.ViewListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 13435 on 2017/10/26.
 */

public class ModelImpl implements ModelIf {

    String Url = "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList&type=1&size=10&offset=0";
    private MyAdapter adapter;

    @Override
    public void modelIf(final Context ctx, final RecyclerView rlv, final ViewListener listener) {

        rlv.setLayoutManager(new LinearLayoutManager(ctx));
        OkHttp3Utils.doGet(Url, new GsonObjectCallback<DataBean>() {
            @Override
            public void onUi(DataBean dataBean) {
                listener.onSuccess();
                Toast.makeText(ctx, dataBean.getSong_list().toString(), Toast.LENGTH_SHORT).show();
                List<DataBean.SongListBean> song_list = dataBean.getSong_list();
                adapter = new MyAdapter(ctx,song_list);
                rlv.setAdapter(adapter);
                adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ctx, SecondActivity.class);
                        ctx.startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailed(Call call, IOException e) {
                listener.unSuccessful();
            }
        });
    }
}

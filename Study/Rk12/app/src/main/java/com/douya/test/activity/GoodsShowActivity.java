package com.douya.test.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.douya.okhttplibrary.utils.GsonObjectCallback;
import com.douya.okhttplibrary.utils.OkHttp3Utils;
import com.douya.recycellibrary.view.GridDivider;
import com.douya.recycellibrary.view.MyDecoration;
import com.douya.test.R;
import com.douya.test.adapter.MyGoodsAdapter;
import com.douya.test.api.API;
import com.douya.test.bean.GoodsBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class GoodsShowActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_select;
    private Button bt_select;
    private RecyclerView goodsList_recyclerView;
    private int page = 1;
    private ImageButton iv_bt;
    private int list_grid =0;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_show);
        initView();
        //设置LinearLayoutManager布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        //设置GridLayoutManager布局管理器
        gridLayoutManager = new GridLayoutManager(this, 2);
        //设置StaggeredGridLayoutManager布局管理器
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        goodsList_recyclerView.setLayoutManager(linearLayoutManager);
        list_grid=1;
        MyDecoration decoration=new MyDecoration(this,LinearLayoutManager.VERTICAL);
    }

    private void initView() {
        et_select = (EditText) findViewById(R.id.et_select);
        bt_select = (Button) findViewById(R.id.bt_select);
        goodsList_recyclerView = (RecyclerView) findViewById(R.id.goodsList_recyclerView);

        bt_select.setOnClickListener(this);
        iv_bt = (ImageButton) findViewById(R.id.iv_bt);
        iv_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select:
                String goodsName = et_select.getText().toString();
                Map<String, String> goodsParams = new HashMap<>();
                goodsParams.put("keywords", goodsName);
                goodsParams.put("page", page + "");
                OkHttp3Utils.doPost(API.SELECT_GOODS_PATH, goodsParams, new GsonObjectCallback<GoodsBean>() {
                    @Override
                    public void onUi(GoodsBean goodsBean) {
                        Log.i("内容", goodsBean.toString());
                        List<GoodsBean.DataBean> list = goodsBean.getData();
                        MyGoodsAdapter adapter=new MyGoodsAdapter(GoodsShowActivity.this,list);
                        goodsList_recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
                break;
            case R.id.iv_bt:
                if(list_grid==0){
                    goodsList_recyclerView.setLayoutManager(linearLayoutManager);
                    list_grid=1;
                    MyDecoration decoration=new MyDecoration(this,LinearLayoutManager.VERTICAL);
                    goodsList_recyclerView.addItemDecoration(decoration);
                }else if(list_grid==1){
                    goodsList_recyclerView.setLayoutManager(gridLayoutManager);
                    list_grid=2;
                    GridDivider divider=new GridDivider(this,2, Color.RED);
                    goodsList_recyclerView.addItemDecoration(divider);
                }else if(list_grid==2){
                    goodsList_recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    list_grid=0;
                }
                break;
        }
    }

}

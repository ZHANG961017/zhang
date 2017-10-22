package bwie.com.myshop.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import bwie.com.myshop.R;
import bwie.com.myshop.adapter.GoodsAdapter;
import bwie.com.myshop.bean.GoodsBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

public class Goods_ParticularsActivity extends BaseActivity implements View.OnClickListener{

    private GoodsAdapter goodsAdapter;
    private RecyclerView goods;
    private ImageButton getBack;
    private ImageButton ccamera;
    private EditText search;
    private Button find;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods__particulars);
        //初始化组件
        getBack = (ImageButton) findViewById(R.id.getBack);
        ccamera = (ImageButton) findViewById(R.id.ccamera);
        search = (EditText) findViewById(R.id.Search);
        find = (Button) findViewById(R.id.find);
        goods = (RecyclerView) findViewById(R.id.goods);
        //设置点击事件
        getBack.setOnClickListener(this);
        ccamera.setOnClickListener(this);
        find.setOnClickListener(this);
        //设置布局管理器
        goods.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.getBack:
                Goods_ParticularsActivity.this.finish();
                break;
            case R.id.ccamera:
                break;
            case R.id.find:
                OkHttp3Utils.doGet(OptionUtil.GOODS_FIND + 100, new GsonObjectCallback<GoodsBean>() {

                    @Override
                    public void onUi(GoodsBean goodsBean) {
                        if(goodsBean.getCode() == 200){
                            final List<GoodsBean.DatasBean.GoodsListBean> goods_list = goodsBean.getDatas().getGoods_list();
                            goodsAdapter = new GoodsAdapter(Goods_ParticularsActivity.this,goods_list);
                            goods.setAdapter(goodsAdapter);
                            goodsAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(Goods_ParticularsActivity.this,GoodsActivity.class);
                                    intent.putExtra("goods_id",goods_list.get(position).getGoods_id());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailed(Call call, IOException e) {

                    }
                });
                break;
            default:
                break;
        }
    }

}

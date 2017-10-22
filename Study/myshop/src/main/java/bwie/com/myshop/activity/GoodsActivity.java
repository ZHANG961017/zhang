package bwie.com.myshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.adapter.ExpandableAdapter;
import bwie.com.myshop.bean.BlurbBean;
import bwie.com.myshop.bean.PlusBean;
import bwie.com.myshop.fragment.ShopFragment;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

public class GoodsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_blurb;
    private TextView tv_goods;
    private TextView share;
    private TextView price;
    private TextView Content;
    private TextView If_store_cn;
    private TextView Area_name;
    private RelativeLayout but_rl;
    private ImageButton Back;
    private Button add_shopCar;
    private Button nowBuy;
    private WebView webView;
    private ImageView iv_pop;
    private String goods_price;
    private String goods_name;
    private String goods_jingle;
    private BlurbBean.DatasBean.GoodsHairInfoBean goods_hair_info;
    private String area_name;
    private String content;
    private String if_store_cn;
    private String goods_image;
    private String[] temp;
    private String spec;
    private TextView money;
    private String goods_storage;
    private TextView inventory;
    private TextView iv_name;
    private ImageButton plus;
    private TextView count;
    private ImageButton subtract;
    private Button put_in_shopCap;
    private Button NowBuy;
    private int sum = 1;
    private String goods_id;
    private TextView buy_name;
    private String[] split;
    private TextView setMoney;
    private double aDouble;
    private SharedPreferences instance;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        //初始化组件
        iv_blurb = (ImageView) findViewById(R.id.iv_blurb);
        tv_goods = (TextView) findViewById(R.id.tv_goods);
        share = (TextView) findViewById(R.id.share);
        price = (TextView) findViewById(R.id.tvPrice);
        Content = (TextView) findViewById(R.id.content);
        If_store_cn = (TextView) findViewById(R.id.if_store_cn);
        Area_name = (TextView) findViewById(R.id.area_name);
        but_rl = (RelativeLayout) findViewById(R.id.but_rl);
        Back = (ImageButton) findViewById(R.id.Back);
        Back.setOnClickListener(this);
        add_shopCar = (Button) findViewById(R.id.add_shopCar);
        nowBuy = (Button) findViewById(R.id.nowBuy);
        webView = (WebView) findViewById(R.id.webView);

        //传入商品ID
        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        Toast.makeText(this, goods_id, Toast.LENGTH_SHORT).show();

        OkHttp3Utils.doGet(OptionUtil.BLURB+"&goods_id="+ goods_id, new GsonObjectCallback<BlurbBean>() {

            @Override
            public void onUi(BlurbBean blurbBean) {
                if(blurbBean.getCode() == 200){
                   //获取商品简介
                    goods_name = blurbBean.getDatas().getGoods_info().getGoods_name();
                    goods_jingle = blurbBean.getDatas().getGoods_info().getGoods_jingle();
                    tv_goods.setText(goods_name + goods_jingle);
                    //获取商品单价
                    goods_price = blurbBean.getDatas().getGoods_info().getGoods_price();
                    price.setText(goods_price);
                    //获取商品库存
                    goods_storage = blurbBean.getDatas().getGoods_info().getGoods_storage();
                    //获取商品发货细则
                    goods_hair_info = blurbBean.getDatas().getGoods_hair_info();
                    area_name = goods_hair_info.getArea_name();
                    content = goods_hair_info.getContent();
                    if_store_cn = goods_hair_info.getIf_store_cn();
                    Content.setText(content);
                    If_store_cn.setText(if_store_cn);
                    Area_name.setText(area_name);
                    //获取图片地址
                    goods_image = blurbBean.getDatas().getGoods_image();
                    spec = blurbBean.getDatas().getSpec_image().get(0);
                    //分割字符串获取图片地址
                    temp = goods_image.split(",");
                    //加载图片
                    Picasso.with(GoodsActivity.this)
                            .load(temp[0])
                            .placeholder(R.mipmap.ic_launcher)
                            .into(iv_blurb);
                    //设置适配屏幕并加载页面
                    WebSettings settings = webView.getSettings();
                    settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                    settings.setLoadWithOverviewMode(true);
                    settings.setSupportZoom(true);
                    settings.setUseWideViewPort(true);
                    settings.setJavaScriptEnabled(true);
                    webView.loadUrl(OptionUtil.INTRODUCE+"&goods_id="+ goods_id);
                    but_rl.setOnClickListener(GoodsActivity.this);
                    Back.setOnClickListener(GoodsActivity.this);
                    add_shopCar.setOnClickListener(GoodsActivity.this);
                    nowBuy.setOnClickListener(GoodsActivity.this);
                }
            }
            @Override
            public void onFailed(Call call, IOException e) {
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.Back:
                GoodsActivity.this.finish();
                break;
            case R.id.add_shopCar:
                instance = BaseActivity.getSharedPreferencesInstance(GoodsActivity.this);
                flag = instance.getBoolean("flag", false);
                if(flag == false){
                    Intent intent = new Intent(GoodsActivity.this,Login_register_Activity.class);
                    startActivity(intent);
                }else{
                    View addView = View.inflate(GoodsActivity.this, R.layout.add_pop, null);
                    //初始化popupwindow中的控件
                    iv_pop = addView.findViewById(R.id.iv_pop);
                    money = addView.findViewById(R.id.money);
                    iv_name = addView.findViewById(R.id.iv_name);
                    inventory = addView.findViewById(R.id.inventory);
                    plus = addView.findViewById(R.id.plus);
                    count = addView.findViewById(R.id.count);
                    subtract = addView.findViewById(R.id.subtract);
                    put_in_shopCap = addView.findViewById(R.id.put_in_shopCap);

                    //初始化PopUpWindow
                    final PopupWindow addPop = new PopupWindow(addView, WindowManager.LayoutParams.MATCH_PARENT,450, true);
                    View addWindow = View.inflate(GoodsActivity.this,R.layout.activity_goods,null);
                    addPop.setFocusable(true);
                    addPop.setOutsideTouchable(true);
                    addPop.setTouchable(true);
                    addPop.setBackgroundDrawable(new ColorDrawable());
                    addPop.showAtLocation(addWindow,Gravity.BOTTOM,0,0);
                    Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
                    Picasso.with(GoodsActivity.this)
                            .load(temp[0])
                            .placeholder(R.mipmap.ic_launcher)
                            .into(this.iv_pop);
                    money.setText(goods_price);
                    inventory.setText("库存:"+goods_storage+"件");
                    split = goods_name.split(" ");
                    iv_name.setText(split[0]);
                    //设置默认数量为1
                    count.setText(""+sum);
                    //设置默认减号不可点击
                    subtract.setEnabled(false);
                    plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sum++;
                            count.setText(""+sum);
                            subtract.setEnabled(true);
                            if(sum>99){
                                plus.setEnabled(false);
                                Toast.makeText(GoodsActivity.this, "没有更多库存了~~~", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(GoodsActivity.this,sum+"", Toast.LENGTH_SHORT).show();
                        }
                    });
                    subtract.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sum--;
                            count.setText(""+sum);
                            if(sum < 2){
                                subtract.setEnabled(false);
                            }
                            Toast.makeText(GoodsActivity.this,sum+"", Toast.LENGTH_SHORT).show();
                        }
                    });
                    put_in_shopCap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String key = instance.getString("key", "");
                            Map<String,String> map = new HashMap<String, String>();
                            map.put("key",key);
                            map.put("goods_id",goods_id);
                            map.put("quantity",sum+"");

                            OkHttp3Utils.doPost(OptionUtil.PLUS, map, new GsonObjectCallback<PlusBean>() {
                                @Override
                                public void onUi(PlusBean plusBean) {

                                    if(plusBean.getCode() == 200 & plusBean.getDatas() == 1){
                                        addPop.dismiss();
                                        Toast.makeText(GoodsActivity.this, "已加入购物车", Toast.LENGTH_SHORT).show();
                                    } else{
                                        Toast.makeText(GoodsActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailed(Call call, IOException e) {
                                }
                            });
                        }
                    });
                }

                break;
            case R.id.nowBuy:
                instance = BaseActivity.getSharedPreferencesInstance(GoodsActivity.this);
                flag = instance.getBoolean("flag", false);
                if(flag == false){
                    Intent intent = new Intent(GoodsActivity.this,Login_register_Activity.class);
                    startActivity(intent);
                }else {
                    View buyView = View.inflate(GoodsActivity.this, R.layout.buy_pop, null);
                    //初始化popupwindow中的控件
                    iv_pop = buyView.findViewById(R.id.iv_pop);
                    money = buyView.findViewById(R.id.money);
                    buy_name = buyView.findViewById(R.id.buy_name);
                    inventory = buyView.findViewById(R.id.inventory);
                    plus = buyView.findViewById(R.id.plus);
                    count = buyView.findViewById(R.id.count);
                    subtract = buyView.findViewById(R.id.subtract);
                    NowBuy = buyView.findViewById(R.id.NowBuy);
                    setMoney = buyView.findViewById(R.id.setMoney);
                    aDouble = Double.parseDouble(goods_price);
                    setMoney.setText("" + sum * aDouble);

                    //初始化PopUpWindow
                    final PopupWindow buyPop = new PopupWindow(buyView, WindowManager.LayoutParams.MATCH_PARENT, 450, true);
                    View buyWindow = View.inflate(GoodsActivity.this, R.layout.activity_goods, null);
                    buyPop.setFocusable(true);
                    buyPop.setOutsideTouchable(true);
                    buyPop.setTouchable(true);
                    buyPop.setBackgroundDrawable(new ColorDrawable());
                    buyPop.showAtLocation(buyWindow, Gravity.BOTTOM, 0, 0);
                    Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
                    Picasso.with(GoodsActivity.this)
                            .load(temp[0])
                            .placeholder(R.mipmap.ic_launcher)
                            .into(this.iv_pop);
                    money.setText(goods_price);
                    inventory.setText("库存:" + goods_storage + "件");
                    buy_name.setText(goods_name);
                    //设置默认数量为1
                    count.setText("" + sum);
                    //设置默认减号不可点击
                    subtract.setEnabled(false);
                    plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sum++;
                            count.setText("" + sum);
                            subtract.setEnabled(true);
                            setMoney.setText("" + sum * aDouble);
                            if (sum > 99) {
                                plus.setEnabled(false);
                                Toast.makeText(GoodsActivity.this, "没有更多库存了~~~", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(GoodsActivity.this, sum + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    subtract.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sum--;
                            count.setText("" + sum);
                            if (sum < 2) {
                                subtract.setEnabled(false);
                            }
                            setMoney.setText("" + sum * aDouble);
                            Toast.makeText(GoodsActivity.this, sum + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    NowBuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buyPop.dismiss();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
}

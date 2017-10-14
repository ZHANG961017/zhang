package bwie.com.myshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

import bwie.com.myshop.R;
import bwie.com.myshop.activity.BaseActivity;
import bwie.com.myshop.bean.RegRequestBean;
import bwie.com.myshop.fragment.HomeFragment;
import bwie.com.myshop.fragment.MsgFragment;
import bwie.com.myshop.fragment.MyFragment;
import bwie.com.myshop.fragment.ShopFragment;
import bwie.com.myshop.fragment.WtFragment;
import bwie.com.myshop.mvp.model.LoginImpl;
import bwie.com.myshop.mvp.model.LoginModel;
import bwie.com.myshop.mvp.view.LoginLitener;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg_touch;
    private HomeFragment homeFragment;
    private WtFragment wtFragment;
    private MsgFragment msgFragment;
    private ShopFragment shopFragment;
    private MyFragment myFragment;
    LoginLitener litener;
    private String username;
    private String password;
    private String client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化组件
        rg_touch = (RadioGroup) findViewById(R.id.rg_touch);
        //实例化fragment
        homeFragment = new HomeFragment();
        wtFragment = new WtFragment();
        msgFragment = new MsgFragment();
        shopFragment = new ShopFragment();
        myFragment = new MyFragment();
        //初始化fragment管理器
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout, homeFragment);
        transaction.add(R.id.framelayout, wtFragment);
        transaction.add(R.id.framelayout, msgFragment);
        transaction.add(R.id.framelayout, shopFragment);
        transaction.add(R.id.framelayout, myFragment);
        transaction.commit();
        getSupportFragmentManager().beginTransaction().show(homeFragment)
                .hide(wtFragment)
                .hide(msgFragment)
                .hide(shopFragment)
                .hide(myFragment)
                .commit();

        //设置RadioGroup点击监听
        rg_touch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch(i){

            case R.id.bt_home :

                getSupportFragmentManager().beginTransaction().show(homeFragment)
                        .hide(wtFragment)
                        .hide(msgFragment)
                        .hide(shopFragment)
                        .hide(myFragment)
                        .commit();
                break;
            case R.id.bt_wt :
                getSupportFragmentManager().beginTransaction().show(wtFragment)
                        .hide(homeFragment)
                        .hide(msgFragment)
                        .hide(shopFragment)
                        .hide(myFragment)
                        .commit();
                break;
            case R.id.bt_mag :
                getSupportFragmentManager().beginTransaction().show(msgFragment)
                        .hide(wtFragment)
                        .hide(homeFragment)
                        .hide(shopFragment)
                        .hide(myFragment)
                        .commit();
                break;
            case R.id.bt_shop :
                getSupportFragmentManager().beginTransaction().show(shopFragment)
                        .hide(wtFragment)
                        .hide(msgFragment)
                        .hide(homeFragment)
                        .hide(myFragment)
                        .commit();
                break;
            case R.id.bt_my :
                getSupportFragmentManager().beginTransaction().show(myFragment)
                        .hide(wtFragment)
                        .hide(msgFragment)
                        .hide(shopFragment)
                        .hide(homeFragment)
                        .commit();

                SharedPreferences instance = OptionUtil.getSharedPreferencesInstance(this);
                boolean verify = instance.getBoolean("verify", false);
                if(verify == false){
                    Intent intent = new Intent(this, Login_register_Activity.class);
                    startActivity(intent);
                }else{

                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

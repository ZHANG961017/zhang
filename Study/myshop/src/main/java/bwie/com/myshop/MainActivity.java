package bwie.com.myshop;

import android.app.ActionBar;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import bwie.com.myshop.fragment.HomeFragment;
import bwie.com.myshop.fragment.MsgFragment;
import bwie.com.myshop.fragment.MyFragment;
import bwie.com.myshop.fragment.ShopFragment;
import bwie.com.myshop.fragment.WtFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg_touch;
    private HomeFragment homeFragment;
    private WtFragment wtFragment;
    private MsgFragment msgFragment;
    private ShopFragment shopFragment;
    private MyFragment myFragment;

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
                break;
            default:
                break;
        }
    }
}

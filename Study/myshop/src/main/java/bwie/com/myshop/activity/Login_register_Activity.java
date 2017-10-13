package bwie.com.myshop.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import bwie.com.myshop.R;

public class Login_register_Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);


    }

    //跳转到登录界面
    public void login(View view){
        Intent intent = new Intent(Login_register_Activity.this,LoginActivity.class);
        startActivity(intent);
    }
    //跳转到注册界面
    public void register(View view){
        Intent intent = new Intent(Login_register_Activity.this,RegisterActivity.class);
        startActivity(intent);
    }
    //跳转到支付宝登录界面
    public void alipay(View view){
        Intent intent = new Intent(Login_register_Activity.this,AlipayActivity.class);
        startActivity(intent);
    }
}

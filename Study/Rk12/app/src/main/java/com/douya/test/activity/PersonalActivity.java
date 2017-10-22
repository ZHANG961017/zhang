package com.douya.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.douya.test.R;
import com.douya.test.api.API;

public class PersonalActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_head;
    private TextView tv_username;
    private TextView tv_niChen;
    private Button bt_loginCancel;
    private Button bt_goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
    }

    private void initView() {
        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_niChen = (TextView) findViewById(R.id.tv_niChen);
        bt_loginCancel = (Button) findViewById(R.id.bt_loginCancel);
        bt_goodsList = (Button) findViewById(R.id.bt_goodsList);

        bt_loginCancel.setOnClickListener(this);
        bt_goodsList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_loginCancel:
                String login_cancel = bt_loginCancel.getText().toString();
                if(login_cancel.equals("登录")){
                    startActivity(new Intent(this,LoginActivity.class));
                }else{
                    API.mEditor.putBoolean("isLogin",false);
                    API.mEditor.putString("userName","");
                    API.mEditor.putString("userNiChen","");
                    API.mEditor.commit();
                    tv_username.setText("未登录");
                    tv_niChen.setText("未登录");
                    bt_goodsList.setVisibility(View.GONE);
                    bt_loginCancel.setText("登录");
                }
                break;
            case R.id.bt_goodsList:
                startActivity(new Intent(this,GoodsShowActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(API.sp.getBoolean("isLogin",false)){
            tv_username.setText(API.sp.getString("userName",""));
            tv_niChen.setText(API.sp.getString("userNiChen",""));
            bt_goodsList.setVisibility(View.VISIBLE);
            bt_loginCancel.setText("退出登录");
        }else{
            tv_username.setText("未登录");
            tv_niChen.setText("未登录");
            bt_goodsList.setVisibility(View.GONE);
            bt_loginCancel.setText("登录");
        }
    }
}

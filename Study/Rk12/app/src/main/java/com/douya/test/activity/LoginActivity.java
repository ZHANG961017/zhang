package com.douya.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douya.test.R;
import com.douya.test.presenter.LoginPresenterImpl;
import com.douya.test.view.LoginViewInterface;

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginViewInterface {

    private EditText et_loginName;
    private EditText et_loginPwd;
    private Button bt_login;
    private Button bt_reg;
    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loginPresenter = new LoginPresenterImpl(this);
    }

    private void initView() {
        et_loginName = (EditText) findViewById(R.id.et_loginName);
        et_loginPwd = (EditText) findViewById(R.id.et_loginPwd);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_reg = (Button) findViewById(R.id.bt_reg);

        bt_login.setOnClickListener(this);
        bt_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                loginPresenter.loginGetData(this,et_loginName.getText()  .toString(),et_loginPwd.getText().toString());
                break;
            case R.id.bt_reg:
                startActivity(new Intent(this,RegActivity.class));
                break;
        }
    }


    @Override
    public void onLoginNameEmpty() {
        et_loginName.setText("登录用户名不能为空");
    }

    @Override
    public void onLoginPwdEmpty() {
        et_loginPwd.setText("登录密码不能为空");
    }

    @Override
    public void onLoginSucceed() {
        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
        //跳转页面到个人信息页面
       finish();
    }

    @Override
    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
        et_loginName.setText("");
        et_loginPwd.setText("");
    }
}

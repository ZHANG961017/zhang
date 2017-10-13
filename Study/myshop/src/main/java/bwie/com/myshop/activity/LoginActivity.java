package bwie.com.myshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bwie.com.myshop.R;
import bwie.com.myshop.mvp.presenter.LoginPresenter;
import bwie.com.myshop.mvp.presenter.LoginPresenterImpl;
import bwie.com.myshop.mvp.view.LoginView;

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginView {

    private Button login;
    private EditText et_name;
    private EditText et_pwd;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化组件
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        presenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        presenter.validatepass(this,
                et_name.getText().toString(),
                et_pwd.getText().toString());
    }

    @Override
    public void setNameError() {
        login.setEnabled(Boolean.FALSE);
    }

    @Override
    public void setPwdError() {
        //et_pwd.setError("password cannot be empty");
        login.setEnabled(Boolean.TRUE);
    }

    @Override
    public void toEmpty() {
        et_name.setText("");
        et_pwd.setText("");
    }

    @Override
    public void toLogin() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        LoginActivity.this.finish();
    }

}

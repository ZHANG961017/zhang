package bwie.com.myshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bwie.com.myshop.R;
import bwie.com.myshop.mvp.presenter.RegisterPresenter;
import bwie.com.myshop.mvp.presenter.RegisterPresenterImpl;
import bwie.com.myshop.mvp.view.RegisterView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,RegisterView {

    private EditText rg_name;
    private EditText rg_pwd;
    private EditText rgPwdCfm;
    private EditText rg_email;
    private Button bt_register;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化组件
        rg_name = (EditText) findViewById(R.id.rg_name);
        rg_pwd = (EditText) findViewById(R.id.rg_pwd);
        rgPwdCfm = (EditText) findViewById(R.id.rgPwdCfm);
        rg_email = (EditText) findViewById(R.id.rg_Email);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);

        presenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        presenter.validatepass(this,
                rg_name.getText().toString(),
                rg_pwd.getText().toString(),
                rgPwdCfm.getText().toString(),
                rg_email.getText().toString());
    }

    @Override
    public void setNameError() {
        rg_name.setError("name cannot be empty");
    }

    @Override
    public void setPwdError() {
        rg_pwd.setError("password cannot be empty");
    }

    @Override
    public void setPwdCfmError() {
        rgPwdCfm.setError("name cannot be empty");
    }

    @Override
    public void setEmailError() {
        rg_email.setError("email cannot be empty");
    }

    @Override
    public void toMypage() {

        Intent intent = new Intent(RegisterActivity.this, Login_register_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        RegisterActivity.this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

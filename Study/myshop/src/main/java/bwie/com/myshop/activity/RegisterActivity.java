package bwie.com.myshop.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bwie.com.myshop.R;
import bwie.com.myshop.presenter.RegisterPresenter;
import bwie.com.myshop.presenter.RegisterPresenterImpl;
import bwie.com.myshop.view.RegisterView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,RegisterView{

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
        Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
    }
}

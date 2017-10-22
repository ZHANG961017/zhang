package com.douya.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.douya.test.R;
import com.douya.test.presenter.RegPresenterImpl;
import com.douya.test.view.RegViewInterface;

public class RegActivity extends AppCompatActivity implements View.OnClickListener,RegViewInterface {


    private EditText et_regName;
    private EditText et_regPwd;
    private Button bt_regCommit;
    private RegPresenterImpl regPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
        regPresenter = new RegPresenterImpl(this);
    }


    private void initView() {
        et_regName = (EditText) findViewById(R.id.et_regName);
        et_regPwd = (EditText) findViewById(R.id.et_regPwd);
        bt_regCommit = (Button) findViewById(R.id.bt_regCommit);

        bt_regCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regCommit:
                if(regPresenter!=null){
                    regPresenter.regGetData(this,et_regName.getText().toString(), et_regPwd.getText().toString());
                }
                break;
        }
    }
    @Override
    public void onRegNameEmpty() {
        et_regName.setError("注册手机号不能为空");
    }

    @Override
    public void onRegPwdEmpty() {
        et_regPwd.setError("注册密码不能为空");
    }

    @Override
    public void onRegSucceed() {
        Toast.makeText(RegActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRegFailed() {
        Toast.makeText(RegActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
        et_regName.setText("");
        et_regPwd.setText("");
    }
}

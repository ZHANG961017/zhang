package bwie.com.newsinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class UserLoginActivity extends AppCompatActivity {

    private EditText edit_userName;
    private EditText edit_userPwd;
    private CheckBox remember_pwd;
    private CheckBox auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        //初始化组件
        edit_userName = (EditText) findViewById(R.id.edit_userName);
        edit_userPwd = (EditText) findViewById(R.id.edit_userPwd);
        remember_pwd = (CheckBox) findViewById(R.id.remember_pwd);
        auto_login = (CheckBox) findViewById(R.id.auto_login);
    }

    //用户注册
    public void UserRegister(View view){
        Intent intent = new Intent(UserLoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    //找回密码
    public void FindPwd(View view){
        Intent intent = new Intent(UserLoginActivity.this,FindPwdActivity.class);
        startActivity(intent);
    }
    //用户登录
    public void UserLogin(View view){



    }
}

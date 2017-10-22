package com.douya.test.presenter;

import android.content.Context;

import com.douya.test.model.LoginModelImpl;
import com.douya.test.port.LoginFinishListener;
import com.douya.test.view.LoginViewInterface;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public class LoginPresenterImpl implements LoginPresenterInterface,LoginFinishListener {
    private LoginViewInterface loginViewInterface;
    private final LoginModelImpl loginModel;

    public LoginPresenterImpl(LoginViewInterface loginViewInterface) {
        this.loginViewInterface = loginViewInterface;
        loginModel = new LoginModelImpl(this);
    }

    @Override
    public void loginGetData(Context context, String loginName, String loginPwd) {
        if(loginModel!=null){
            loginModel.loginModel(context, loginName, loginPwd);
        }
    }

    @Override
    public void loginNameEmpty() {
        if (loginViewInterface!=null){
            loginViewInterface.onLoginNameEmpty();
        }
    }

    @Override
    public void loginPwdEmpty() {
        if (loginViewInterface!=null){
            loginViewInterface.onLoginPwdEmpty();
        }
    }

    @Override
    public void loginSucceed() {
        if (loginViewInterface!=null){
            loginViewInterface.onLoginSucceed();
        }
    }

    @Override
    public void loginFailed() {
        if (loginViewInterface!=null){
            loginViewInterface.onLoginFailed();
        }
    }
}

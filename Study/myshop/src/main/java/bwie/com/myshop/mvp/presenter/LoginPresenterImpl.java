package bwie.com.myshop.mvp.presenter;

import android.content.Context;

import bwie.com.myshop.mvp.model.LoginImpl;
import bwie.com.myshop.mvp.model.LoginModel;
import bwie.com.myshop.mvp.view.LoginLitener;
import bwie.com.myshop.mvp.view.LoginView;

/**
 * Created by 13435 on 2017/10/12.
 */

public class LoginPresenterImpl implements LoginPresenter,LoginLitener {

    LoginView loginView;
    LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;

        loginModel = new LoginImpl();
    }

    @Override
    public void validatepass(Context ctx, String name, String pwd) {
        loginModel.login(ctx,name,pwd,this);
    }
    @Override
    public void OnNameError() {
        if(loginView!=null){
            loginView.setNameError();
        }
    }

    @Override
    public void OnPwdError() {
        if(loginView!=null){
            loginView.setPwdError();
        }
    }

    @Override
    public void UnSuccessful() {
        if(loginView!=null){
            loginView.toEmpty();
        }
    }

    @Override
    public void OnSuccess() {
        if(loginView!=null){
            loginView.toLogin();
        }
    }
}

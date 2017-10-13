package bwie.com.myshop.mvp.presenter;

import android.content.Context;

import bwie.com.myshop.mvp.model.RegisterImpl;
import bwie.com.myshop.mvp.model.RegisterModel;
import bwie.com.myshop.mvp.view.RegisterListener;
import bwie.com.myshop.mvp.view.RegisterView;


/**
 * Created by 13435 on 2017/10/11.
 */

public class RegisterPresenterImpl implements RegisterPresenter,RegisterListener {

    RegisterView registerView;
    RegisterModel registerModel;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;

        registerModel = new RegisterImpl();
    }

    @Override
    public void validatepass(Context ctx, String name, String pwd, String pwdCfm, String email) {
        registerModel.register(ctx,name,pwd,pwdCfm,email,this);
    }

    @Override
    public void OnNameError() {
        if(registerView != null){
            registerView.setNameError();
        }
    }

    @Override
    public void OnPwdError() {
        if(registerView != null){
            registerView.setPwdError();
        }
    }

    @Override
    public void OnPwdCfmError() {
        if(registerView != null){
            registerView.setPwdCfmError();
        }
    }

    @Override
    public void OnEmailError() {
        if(registerView != null){
            registerView.setEmailError();
        }
    }

    @Override
    public void OnSuccess() {
        if(registerView != null){

            registerView.toMypage();
        }
    }
}

package com.douya.test.presenter;

import android.content.Context;

import com.douya.test.model.RegModelImpl;
import com.douya.test.port.RegFinishListener;
import com.douya.test.view.RegViewInterface;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public class RegPresenterImpl implements RegPresenterInterface,RegFinishListener {
    private RegViewInterface regViewInterface;
    private final RegModelImpl regModel;

    public RegPresenterImpl(RegViewInterface regViewInterface) {
        this.regViewInterface = regViewInterface;
        regModel = new RegModelImpl(this);
    }

    @Override
    public void regGetData(Context context, String regName, String regPwd) {
        if(regModel!=null){
            regModel.regModel(context, regName, regPwd);
        }

    }

    @Override
    public void regNameEmpty() {
        if(regViewInterface!=null){
            regViewInterface.onRegNameEmpty();
        }
    }

    @Override
    public void regPwdEmpty() {
        if(regViewInterface!=null){
            regViewInterface.onRegPwdEmpty();
        }
    }

    @Override
    public void regSucceed() {
        if(regViewInterface!=null){
            regViewInterface.onRegSucceed();
        }
    }

    @Override
    public void regFailed() {
        if(regViewInterface!=null){
            regViewInterface.onRegFailed();
        }
    }
}

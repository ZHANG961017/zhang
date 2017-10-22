package com.douya.test.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.douya.okhttplibrary.utils.GsonObjectCallback;
import com.douya.okhttplibrary.utils.OkHttp3Utils;
import com.douya.test.api.API;
import com.douya.test.bean.RegBean;
import com.douya.test.port.LoginFinishListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public class LoginModelImpl implements LoginModelInterface {
    private LoginFinishListener listener;

    public LoginModelImpl(LoginFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void loginModel(Context context, String loginName, String loginPwd) {
        if(TextUtils.isEmpty(loginName)){
            listener.loginNameEmpty();
            return;
        }
        if(TextUtils.isEmpty(loginPwd)){
            listener.loginPwdEmpty();
            return;
        }
        loginRequest(context, loginName, loginPwd);
    }

    @Override
    public void loginRequest(final Context context, String loginName, String loginPwd) {
        //定义一个Map集合，用来存注册的参数
        Map<String,String> regParams=new HashMap<>();
        //将参数添加到集合
        regParams.put("mobile",loginName);
        regParams.put("password",loginPwd);
        //进行请求
        OkHttp3Utils.doPost(API.LOGIN_PATH, regParams, new GsonObjectCallback<RegBean>() {
            @Override
            public void onUi(RegBean regBean) {
                if(regBean.getCode()==0){
                    RegBean.DataBean data = regBean.getData();
                    API.mEditor.putBoolean("isLogin",true);
                    API.mEditor.putString("userName",data.getMobile());
                    API.mEditor.putString("userNiChen",data.getMobile());
                    API.mEditor.commit();
                    listener.loginSucceed();
                }
                if(regBean.getCode()==1){
                    Toast.makeText(context,regBean.getMsg(),Toast.LENGTH_SHORT).show();
                    listener.loginFailed();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
}

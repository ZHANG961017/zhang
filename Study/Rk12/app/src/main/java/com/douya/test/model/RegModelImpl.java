package com.douya.test.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.douya.okhttplibrary.utils.GsonObjectCallback;
import com.douya.okhttplibrary.utils.OkHttp3Utils;
import com.douya.test.api.API;
import com.douya.test.bean.RegBean;
import com.douya.test.port.RegFinishListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public class RegModelImpl implements RegModelInterface {
    private RegFinishListener listener;

    public RegModelImpl(RegFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void regModel(Context context, String regName, String regPwd) {
        if(TextUtils.isEmpty(regName)){
            listener.regNameEmpty();
            return;
        }
        if(TextUtils.isEmpty(regPwd)){
            listener.regPwdEmpty();
            return;
        }
        regRequest(context,regName, regPwd);
    }

    @Override
    public void regRequest(final Context context, final String regName, String regPwd) {
        //定义一个Map集合，用来存注册的参数
        Map<String,String> regParams=new HashMap<>();
        //将参数添加到集合
        regParams.put("mobile",regName);
        regParams.put("password",regPwd);
        //进行请求
        OkHttp3Utils.doPost(API.REG_PATH, regParams, new GsonObjectCallback<RegBean>() {
            @Override
            public void onUi(RegBean regBean) {
                if(regBean.getCode()==0){
                    listener.regSucceed();
                }
                if(regBean.getCode()==1){
                    Toast.makeText(context,regBean.getMsg(),Toast.LENGTH_SHORT).show();
                    listener.regFailed();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
}

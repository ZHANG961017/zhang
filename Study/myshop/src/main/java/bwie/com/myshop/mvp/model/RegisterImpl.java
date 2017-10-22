package bwie.com.myshop.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bwie.com.myshop.activity.BaseActivity;
import bwie.com.myshop.bean.RegRequestBean;
import bwie.com.myshop.mvp.view.RegisterListener;
import bwie.com.myshop.myapp.MyApp;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;


/**
 * Created by 13435 on 2017/10/11.
 */

public class RegisterImpl implements RegisterModel {

    @Override
    public void register(final Context ctx, String name, String pwd, String pwdCfm, String email, final RegisterListener listener) {

        if(TextUtils.isEmpty(name)){
            listener.OnNameError();
            Toast.makeText(ctx, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            listener.OnPwdError();
            Toast.makeText(ctx, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pwdCfm)){
            listener.OnPwdCfmError();
            Toast.makeText(ctx, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pwd != pwdCfm & !pwd.equals(pwdCfm)){
            listener.OnPwdCfmError();
            Toast.makeText(ctx, "请确认后再次输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(ctx, "请输入邮箱", Toast.LENGTH_SHORT).show();
            listener.OnEmailError();
            return;
        }
        if(BaseActivity.isSpecific_Symbol(ctx,name) == false){
            return;
        }
        if(BaseActivity.isSpecific_Symbol(ctx,pwd) == false){
            return;
        }
        if(name.length() < 5){
            Toast.makeText(ctx, "用户名不能少于五位", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pwd.length() < 6){
            Toast.makeText(ctx, "密码不能少于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> parameter = new HashMap<String,String>();
        parameter.put("username",name);
        parameter.put("password",pwd);
        parameter.put("password_confirm",pwdCfm);
        parameter.put("email",email);
        parameter.put("client",OptionUtil.CLIENT);
        OkHttp3Utils.doPost(OptionUtil.REGISTER, parameter, new GsonObjectCallback<RegRequestBean>() {

            @Override
            public void onUi(RegRequestBean regRequestBean) {
                if(regRequestBean.getCode()==200){

                    listener.OnSuccess();
                    Toast.makeText(ctx, "注册成功", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(ctx, regRequestBean.getDatas().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {

            }
        });


    }
}

package bwie.com.myshop.mvp.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import bwie.com.myshop.bean.RegRequestBean;
import bwie.com.myshop.mvp.view.LoginLitener;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;

/**
 * Created by 13435 on 2017/10/12.
 */

public class LoginImpl implements LoginModel {

    private int code;
    public static Map<String, String> parameter;

    @Override
    public void login(final Context ctx, final String name, final String pwd, final LoginLitener litener) {
        if(TextUtils.isEmpty(name)){
            litener.OnNameError();
            Toast.makeText(ctx, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
       if(TextUtils.isEmpty(pwd)){
            litener.OnNameError();
            Toast.makeText(ctx, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        parameter = new HashMap<String,String>();
        parameter.put("username",name);
        parameter.put("password",pwd);
        parameter.put("client",OptionUtil.CLIENT);
        OkHttp3Utils.doPost(OptionUtil.LOGIN, parameter, new GsonObjectCallback<RegRequestBean>() {

            @Override
            public void onUi(RegRequestBean regRequestBean) {
                code = regRequestBean.getCode();
                if(code == 200){
                    litener.OnSuccess();
                    Toast.makeText(ctx, "登录成功", Toast.LENGTH_SHORT).show();
                }else if(code == 400){
                    litener.UnSuccessful();
                    Toast.makeText(ctx, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Call call, IOException e) {
            }
        });
    }
}

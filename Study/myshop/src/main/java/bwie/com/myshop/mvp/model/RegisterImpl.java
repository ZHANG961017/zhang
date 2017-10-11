package bwie.com.myshop.mvp.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import bwie.com.myshop.view.RegisterListener;

/**
 * Created by 13435 on 2017/10/11.
 */

public class RegisterImpl implements RegisterModel {
    @Override
    public void register(Context ctx, String name, String pwd, String pwdCfm, String email, RegisterListener listener) {

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
        listener.OnSuccess();

    }
}

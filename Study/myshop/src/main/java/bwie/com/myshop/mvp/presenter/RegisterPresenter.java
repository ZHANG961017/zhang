package bwie.com.myshop.mvp.presenter;

import android.content.Context;

/**
 * Created by 13435 on 2017/10/11.
 */

public interface RegisterPresenter {
    void validatepass(Context ctx, String name, String pwd, String pwdCfm, String email);
}

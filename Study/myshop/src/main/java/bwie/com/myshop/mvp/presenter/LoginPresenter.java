package bwie.com.myshop.mvp.presenter;

import android.content.Context;

/**
 * Created by 13435 on 2017/10/12.
 */

public interface LoginPresenter {
    void validatepass(Context ctx, String name, String pwd);
}

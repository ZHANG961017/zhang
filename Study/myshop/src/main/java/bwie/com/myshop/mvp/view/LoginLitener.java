package bwie.com.myshop.mvp.view;

/**
 * Created by 13435 on 2017/10/12.
 */

public interface LoginLitener {
    void OnNameError();
    void OnPwdError();
    void UnSuccessful();
    void OnSuccess();
}

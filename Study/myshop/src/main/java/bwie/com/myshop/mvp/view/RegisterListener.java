package bwie.com.myshop.mvp.view;

/**
 * Created by 13435 on 2017/10/11.
 */

public interface RegisterListener {

    void OnNameError();
    void OnPwdError();
    void OnPwdCfmError();
    void OnEmailError();
    void OnSuccess();
}

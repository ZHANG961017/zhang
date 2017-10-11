package bwie.com.myshop.view;

/**
 * Created by 13435 on 2017/10/10.
 */

public interface LoginView {
    //用户名错误
    void setNameError();
    //用户名为空
    void NameEmpty();
    //密码错误
    void setPwdError();
    //密码为空
    void PwdEmpty();
}

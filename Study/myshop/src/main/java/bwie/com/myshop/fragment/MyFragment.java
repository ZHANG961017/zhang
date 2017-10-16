package bwie.com.myshop.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bwie.com.myshop.R;
import bwie.com.myshop.activity.LoginActivity;
import bwie.com.myshop.activity.Login_register_Activity;
import bwie.com.myshop.utils.OptionUtil;

/**
 * Created by 13435 on 2017/9/28.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private TextView username;
    private TextView setting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        //初始化组件
        setting = view.findViewById(R.id.setting);
        //设置监听事件
        setting.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {

    }
}

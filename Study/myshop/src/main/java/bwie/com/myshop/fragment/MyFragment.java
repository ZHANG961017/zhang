package bwie.com.myshop.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;

import bwie.com.myshop.R;
import bwie.com.myshop.activity.AddressManagerActivity;
import bwie.com.myshop.activity.BaseActivity;
import bwie.com.myshop.activity.SettingActivity;

/**
 * Created by 13435 on 2017/9/28.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private TextView username;
    private TextView setting;
    private Button address;
    private Intent intent;
    private int REQUEST_CODE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        //初始化组件
        setting = view.findViewById(R.id.setting);
        username = (TextView) view.findViewById(R.id.username);
        address = view.findViewById(R.id.address);

        SharedPreferences instance = BaseActivity.getSharedPreferencesInstance(getActivity());
        boolean flag = instance.getBoolean("flag", false);
        Toast.makeText(getActivity(), "flag="+flag, Toast.LENGTH_SHORT).show();
        if(flag == true){
            address.setOnClickListener(this);
            String name = instance.getString("name", "");
            username.setText(name);
            //设置监听事件
            setting.setOnClickListener(this);
        }else{
            username.setText("游客");
        }
        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.address:
                intent = new Intent(getActivity(),AddressManagerActivity.class);
                startActivity(intent);
        }
    }
}

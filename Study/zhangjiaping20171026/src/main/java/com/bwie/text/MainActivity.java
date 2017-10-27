package com.bwie.text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bwie.text.adapter.MyAdapter;
import com.bwie.text.presenter.PresenterIf;
import com.bwie.text.presenter.PresenterImpl;
import com.bwie.text.view.ViewIf;

public class MainActivity extends BaseActivity implements ViewIf{

    private RecyclerView rlv;
    private PresenterIf presenterIf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化组件
        rlv = (RecyclerView) findViewById(R.id.rlv);

        presenterIf = new PresenterImpl(this);
        presenterIf.validatepass(MainActivity.this,rlv);

    }

    @Override
    public void isSeccess() {
        Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isUnSeccess() {
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
    }
}

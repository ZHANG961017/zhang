package bwie.com.exam_customview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //全局变量
    private MyCustomView my_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        my_view = (MyCustomView) findViewById(R.id.my_view);
    }
    public void onClick(View view){
        my_view.setColor(Color.BLUE);
    }
    public void add(View view){
        my_view.speed();
    }
    public void slow(View view){
        my_view.slowDown();
    }
    public void pauseOrStart(View view){
        my_view.pauseOrStart();
    }
}

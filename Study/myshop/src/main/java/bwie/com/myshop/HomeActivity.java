package bwie.com.myshop;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends BaseActivity {

    private int i=3;
    private Handler handler = new Handler(){};
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(i>0){
                i--;
                handler.postDelayed(runnable,1000);
            }else{
                Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intent);
                handler.removeCallbacks(runnable);
                HomeActivity.this.finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //每隔一秒向handler发送消息
        handler.postDelayed(runnable,1000);
    }
}

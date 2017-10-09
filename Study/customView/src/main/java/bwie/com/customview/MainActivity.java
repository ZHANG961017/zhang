package bwie.com.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int mTotalProgress = 100;
    private int mCurrentProgress = 0;
    //进度条
    private MyCustomView mTasksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTasksView = (MyCustomView) findViewById(R.id.tasks_view);
    }

    public void start(View view){
        mTasksView.setProgress(mCurrentProgress);
        new Thread(new ProgressRunable()).start();
    }
    public void reset(View view){
        mTasksView.setProgress(0);
        mCurrentProgress = 0;
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while(mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mTasksView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

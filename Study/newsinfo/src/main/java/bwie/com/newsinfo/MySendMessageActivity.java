package bwie.com.newsinfo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.smssdk.SMSSDK;

public class MySendMessageActivity extends AppCompatActivity {


    private int i=60;
    private EditText phone;
    private EditText identfying_code;
    private String strPhone;
    private String strIdentfying_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_send_message);
        //初始化组件
        phone = (EditText) findViewById(R.id.phone);
        identfying_code = (EditText) findViewById(R.id.identfying_code);

        MobSDK.init(MySendMessageActivity.this,"20a76d812c20c","3490fd48bc230121544bdad154bb55ff");

    }

    public void obtain_identfying_code(View view){

        //获取输入的手机号
        strPhone = phone.getText().toString();
        //判断手机号不为空
        if(strPhone != null&!strPhone.equals("")){
            //获取验证码
            SMSSDK.getVerificationCode("86",strPhone);
            Toast.makeText(MySendMessageActivity.this,"已发送验证码",Toast.LENGTH_SHORT).show();
        }else{
            //为空提示输入手机号
            Toast.makeText(MySendMessageActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
        }
    }

    public void submit(View view){
        //new SMSSDK.VerifyCodeReadListener()
        //获取输入的验证码
        strIdentfying_code = identfying_code.getText().toString();
        if(strIdentfying_code !=null & !strIdentfying_code.equals("")){

            Intent intent = new Intent(MySendMessageActivity.this,MainActivity.class);
            startActivity(intent);
            MySendMessageActivity.this.finish();
        }else{
            Toast.makeText(MySendMessageActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
        }
    }
}

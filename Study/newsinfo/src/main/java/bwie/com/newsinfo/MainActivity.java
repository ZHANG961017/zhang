package bwie.com.newsinfo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.example.city_picker.CityListActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidkun.com.versionupdatelibrary.entity.VersionUpdateConfig;
import bwie.com.newsinfo.fragment.Fragment1;
import bwie.com.newsinfo.fragment.FragmentInLand;
import bwie.com.newsinfo.fragment.FragmentSociety;
import bwie.com.newsinfo.fragment.Fragment_CaiJing;
import bwie.com.newsinfo.fragment.Fragment_Guoji;
import bwie.com.newsinfo.fragment.Fragment_JunShi;
import bwie.com.newsinfo.fragment.Fragment_KeJi;
import bwie.com.newsinfo.fragment.Fragment_ShiShang;
import bwie.com.newsinfo.fragment.Fragment_TiYu;
import bwie.com.newsinfo.fragment.Fragment_YuLe;
import bwie.com.newsinfo.util.ClearCache;
import bwie.com.newsinfo.util.NetWorkType;
import bwie.com.newsinfo.util.UtilFragment;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<ChannelBean> list;
    private List<String> titleList = new ArrayList<String>();
    private List<String> list_item = new ArrayList<String>();
    private List<Fragment> listFragment = new ArrayList<Fragment>();
    private DrawerLayout drawer;
    private LinearLayout left_layout;
    private LinearLayout right_layout;
    private TabLayout tab;
    private ViewPager viewPager;
    private ActionBarDrawerToggle toggle;
    private ImageView userIcon;
    private TextView userId;
    private TextView userName;
    private TextView userGender;
    private ListView listview;
    private String jsonStr;
    private Fragment1 fragment1;
    private FragmentSociety fragmentSociety;
    private FragmentInLand fragmentInLand;
    private Fragment_Guoji fragmentGuoji;
    private Fragment_YuLe fragmentYuLe;
    private Fragment_TiYu fragmentTiYu;
    private Fragment_JunShi fragmentJunShi;
    private Fragment_KeJi fragmentKeJi;
    private Fragment_CaiJing fragmentCaiJing;
    private Fragment_ShiShang fragmentShiShang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取Action管理
        ActionBar actionbar = getSupportActionBar();
        //标题栏显示箭头
        actionbar.setDisplayHomeAsUpEnabled(true);

        //初始化组件
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        left_layout = (LinearLayout) findViewById(R.id.left_Layout);
        userIcon = (ImageView) findViewById(R.id.userIcon);
        userId = (TextView) findViewById(R.id.userId);
        userName = (TextView) findViewById(R.id.userName);
        userGender = (TextView) findViewById(R.id.userGender);
        listview = (ListView) findViewById(R.id.listview);

        //实例化Fragment
        fragment1 = new Fragment1();
        fragmentSociety = new FragmentSociety();
        fragmentInLand = new FragmentInLand();
        fragmentGuoji = new Fragment_Guoji();
        fragmentYuLe = new Fragment_YuLe();
        fragmentTiYu = new Fragment_TiYu();
        fragmentJunShi = new Fragment_JunShi();
        fragmentKeJi = new Fragment_KeJi();
        fragmentCaiJing = new Fragment_CaiJing();
        fragmentShiShang = new Fragment_ShiShang();

        //调用方法
        initTitleAndItem();
        initView();
        userInfo();
        detect(this);
        JudgeNetWorkType();

        //侧拉菜单选项适配
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.item_listview,R.id.select_list,list_item);
        listview.setAdapter(arrayAdapter);
        //初始化Item点击监听
        listview.setOnItemClickListener(this);
    }
    //判断网络类型
    private void JudgeNetWorkType() {
        int  net= NetWorkType.getNetWorkStatus(MainActivity.this);

        if(net==1) {
            Toast.makeText(MainActivity.this, "使用无线网络中", Toast.LENGTH_SHORT).show();
        }else
        if(net==2) {
            Toast.makeText(MainActivity.this, "使用2G网络中", Toast.LENGTH_SHORT).show();
        }else
        if(net==3) {
            Toast.makeText(MainActivity.this, "使用3G网络中", Toast.LENGTH_SHORT).show();
        }else
        if(net==4) {
            Toast.makeText(MainActivity.this, "使用4G网络中", Toast.LENGTH_SHORT).show();
        }else
        if(net==0) {
            Toast.makeText(MainActivity.this, "未知网络", Toast.LENGTH_SHORT).show();
        }
    }
    //添加TabLayout标签及侧拉菜单Item
    private void initTitleAndItem() {

        //添加标题
        titleList.add("头条");
        titleList.add("社会");
        titleList.add("国内");
        titleList.add("国际");
        titleList.add("娱乐");
        titleList.add("体育");
        titleList.add("军事");
        titleList.add("科技");
        titleList.add("财经");
        titleList.add("时尚");
        //titleList.add("视频");

        //添加侧拉菜单选项
        list_item.add("短信验证");
        list_item.add("清除缓存");
        list_item.add("检查更新");
        list_item.add("模式切换");
        list_item.add("更多设置");

    }
    //频道管理
    public void add_Channel(View view){
        if (list==null){//判断集合中是否已有数据，没有则创建
            list=new ArrayList<>();
            //第一个是显示的条目，第二个参数是否显示
            list.add(new ChannelBean("热点",true));
            list.add(new ChannelBean("军事",true));
            list.add(new ChannelBean("八卦",true));
            list.add(new ChannelBean("游戏",true));
            list.add(new ChannelBean("宠物",true));
            list.add(new ChannelBean("汽车",false));
            list.add(new ChannelBean("热卖",false));
            list.add(new ChannelBean("外卖",false));
            list.add(new ChannelBean("太阳花",false));

            ChannelActivity.startChannelActivity(this,list);

        }else if (jsonStr!=null){//当判断保存的字符串不为空的时候，直接加载已经有了的字符串
            ChannelActivity.startChannelActivity(this,jsonStr);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ChannelActivity.REQUEST_CODE&&resultCode==ChannelActivity.RESULT_CODE) {
            jsonStr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
        } }
    //用户信息
    private void userInfo() {
        //接收并设置值
        Intent intent = getIntent();
        String ID = intent.getStringExtra("userId");
        String Name = intent.getStringExtra("userName");
        String icon = intent.getStringExtra("userIcon");
        String Gender = intent.getStringExtra("userGender");
        userGender.setText(Gender);
        userName.setText(Name);
        userId.setText(ID);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.user)//加载失败显示的图片
                .showImageForEmptyUri(R.drawable.user)//url为空时显示的图片
                .displayer(new CircleBitmapDisplayer())//显示圆形图片
                .build();
        //设置图片
        ImageLoader.getInstance().displayImage(icon,userIcon,options);
        //打印url地址
        //System.out.println(icon);
    }
    //重写图片点击事件
    public void Image_Tauch(View view){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    //重写ActionBar监听事件,更改状态
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_City:
                CityListActivity.startCityActivityForResult(this);
                break;
        }
        return toggle.onOptionsItemSelected(item)|super.onOptionsItemSelected(item);
    }
    //主页面信息
    private void initView() {

        //初始化抽屉
        toggle = new ActionBarDrawerToggle(this,drawer, R.string.openDrawer,R.string.closeDrawer);
        //设置同步
        toggle.syncState();
        //设置监听
        drawer.addDrawerListener(toggle);
        tab.setTabMode(tab.MODE_SCROLLABLE);
        for(int i=0;i<titleList.size();i++){
         tab.addTab(tab.newTab().setText(titleList.get(i)));
        }

        //将Fragment添加进集合
        listFragment.add(fragment1);
        listFragment.add(fragmentSociety);
        listFragment.add(fragmentInLand);
        listFragment.add(fragmentGuoji);
        listFragment.add(fragmentYuLe);
        listFragment.add(fragmentTiYu);
        listFragment.add(fragmentJunShi);
        listFragment.add(fragmentKeJi);
        listFragment.add(fragmentCaiJing);
        listFragment.add(fragmentShiShang);

        //实例化工具类
        UtilFragment utilFragment = new UtilFragment(getSupportFragmentManager(),titleList,listFragment);
        viewPager.setAdapter(utilFragment);
        tab.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(10);
    }
    //城市列表
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.citymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //ListView  Item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position){
            //短信验证
            case 0:{
                Intent intent = new Intent(MainActivity.this,MySendMessageActivity.class);
                startActivity(intent);
            }
            break;
            //清除缓存
            case 1:{
                File filesDir = MainActivity.this.getFilesDir();
                ClearCache.cleanApplicationData(MainActivity.this,filesDir.getAbsolutePath());
                Toast.makeText(MainActivity.this,"已清理",Toast.LENGTH_SHORT).show();
            }
            break;
            //版本更新
            case 2:{
                new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题
                    .setMessage("版本更新！")//设置显示的内容
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                            // @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        VersionUpdateConfig.getInstance()//获取配置实例
                            .setContext(MainActivity.this)//设置上下文
                            .setDownLoadURL("https://apk.apk.hz155.com/down/yuedushenqi.apk")//设置文件下载链接
                            .setNotificationTitle("版本升级Demo")//设置通知标题
                            .startDownLoad();//开始下载
                            //finish();
                            }
                        }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub
                        Log.i("alertdialog"," 请保存数据！");
                    }
                }).show();//在按键响应事件中显示此对话框
            }
            break;
            //日夜间模式切换
            case 3:{
                //获取SharedPreferences对象
                SharedPreferences sp = getSharedPreferences("dayNight",MODE_PRIVATE);
                boolean click = sp.getBoolean("click", false);
                if(click == true){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Toast.makeText(this,"白天",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("click",false);
                    edit.commit();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Toast.makeText(this,"夜间",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("click",true);
                    edit.commit();
                }
                recreate();
            }
            break;
            //更多设置
            case 4:{
                //跳转到设置界面
                Intent intent = new Intent(MainActivity.this,Set_MoreActivity.class);
                startActivity(intent);
            }
            break;
            default:break;
        }
    }
    //判断网络
    public boolean detect(Activity act) {

        ConnectivityManager manager = (ConnectivityManager) act
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            //无网络 显示弹窗
            showDialog();
            Toast.makeText(MainActivity.this,"无网络连接",Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(MainActivity.this,"网络连接正常",Toast.LENGTH_SHORT).show();
        return true;
    }
    //提示用户的一个联网对话框（Toast）
    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("无网络需要连接网络");
        builder.setNegativeButton("取消并加载本地缓存",null);

        builder.setPositiveButton("跳转到网络设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //设置跳转网络界面
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.create().show();
    }
}

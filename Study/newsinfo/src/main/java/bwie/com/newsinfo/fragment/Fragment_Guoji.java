package bwie.com.newsinfo.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bwie.com.newsinfo.Adapter.MyDataAdapter;
import bwie.com.newsinfo.R;
import bwie.com.newsinfo.WebContentActivity;
import bwie.com.newsinfo.util.HttpData;
import bwie.com.newsinfo.util.Stream;

/**
 * Created by 13435 on 2017/9/14.
 */

public class Fragment_Guoji extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private XListView xLv;
    private boolean flag;
    private MyDataAdapter dataAdapter;
    private List<HttpData.ResultBean.DataBean> dataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null, false);

        //初始化组件
        xLv = (XListView) view.findViewById(R.id.xLv);
        xLv.setPullLoadEnable(true);
        xLv.setXListViewListener(this);
        xLv.setOnItemClickListener(this);
        getData();
        return view;
    }

    public void getData() {

        try {
            getJson("http://v.juhe.cn/toutiao/index?type=guoji&key=54e3d5f4ee64f51bef570ce8505d37b5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getJson(String url){

        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s == null){
                    return;
                }else{
                    Gson gson = new Gson();
                    HttpData json = gson.fromJson(s, HttpData.class);
                    dataList = json.getResult().getData();
                    if(dataAdapter == null){
                        dataAdapter = new MyDataAdapter(getActivity(),dataList);
                        xLv.setAdapter(dataAdapter);
                    }else{
                        dataAdapter.loadMore(dataList,flag);
                    }
                }
            }

            @Override
            protected String doInBackground(String... strings) {

                try {
                    URL url = new URL(strings[0]);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);

                    int code = connection.getResponseCode();
                    if(code == 200){
                        InputStream is = connection.getInputStream();
                        return Stream.getHttpData(is);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url);
    }

    @Override
    public void onRefresh() {
        flag = false;
        getData();
        xLv.stopRefresh(true);
    }

    @Override
    public void onLoadMore() {
        flag = true;
        getData();
        xLv.stopLoadMore();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HttpData.ResultBean.DataBean bean = dataList.get(position - 1);
        String url = bean.getUrl();
        Intent intent = new Intent(getActivity(), WebContentActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}

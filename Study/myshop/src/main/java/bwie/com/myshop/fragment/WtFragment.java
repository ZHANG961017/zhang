package bwie.com.myshop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;import java.io.IOException;
import java.util.List;

import bwie.com.myshop.R;
import bwie.com.myshop.adapter.SecondAdapter;
import bwie.com.myshop.adapter.StairAdapter;
import bwie.com.myshop.adapter.ThreeAdapter;
import bwie.com.myshop.bean.SecondAndThreeBean;
import bwie.com.myshop.bean.StairBean;
import bwie.com.myshop.utils.OptionUtil;
import bwie.com.myshop.utils.okhttp.GsonObjectCallback;
import bwie.com.myshop.utils.okhttp.OkHttp3Utils;
import okhttp3.Call;


/**
 * Created by 13435 on 2017/9/28.
 */

public class WtFragment extends Fragment {

    private RecyclerView rv_stair;
    private RecyclerView rv_second;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wt_fragment, container, false);
        //初始化组件
        rv_stair = view.findViewById(R.id.rv_stair);
        rv_second = view.findViewById(R.id.rv_second);

        rv_stair.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_second.setLayoutManager(new LinearLayoutManager(getActivity()));

        upLoad();
        return view;
    }
    /*
     * 请求数据
     * */
    private void upLoad() {
        //一级分类，请求数据
        OkHttp3Utils.doGet(OptionUtil.STAIR, new GsonObjectCallback<StairBean>() {
            //成功返回
            @Override
            public void onUi(StairBean stairBean) {
                //获取一级列表数据
                final List<StairBean.DatasBean.ClassListBean> stairList = stairBean.getDatas().getClass_list();
                //设置一级列表adapter
                final StairAdapter stairAdapter = new StairAdapter(getActivity(),stairList);
                rv_stair.setAdapter(stairAdapter);
                //设置一级列表监听
                stairAdapter.setOnItemClickListener(new StairAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //将ID转换为int类型
                        int i = Integer.parseInt(stairList.get(position).getGc_id());
                        //二级分类，请求数据
                        OkHttp3Utils.doGet(OptionUtil.SECOND + i, new GsonObjectCallback<SecondAndThreeBean>() {
                            //成功返回
                            @Override
                            public void onUi(SecondAndThreeBean secondAndThreeBean) {
                                //获取二级列表数据
                                final List<SecondAndThreeBean.DatasBean.ClassListBean> secondList = secondAndThreeBean.getDatas().getClass_list();
                                //设置二级列表adapter
                                SecondAdapter secondAdapter = new SecondAdapter(getActivity(),secondList);
                                rv_second.setAdapter(secondAdapter);
                            }
                            @Override
                            public void onFailed(Call call, IOException e) {

                            }
                        });
                    }
                });
            }
            @Override
            public void onFailed(Call call, IOException e) {

            }
        });
    }
}

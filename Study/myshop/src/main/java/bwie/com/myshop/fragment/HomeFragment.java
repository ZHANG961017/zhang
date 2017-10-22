package bwie.com.myshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;

import bwie.com.myshop.R;
import bwie.com.myshop.activity.Goods_ParticularsActivity;

/**
 * Created by 13435 on 2017/9/28.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView scan;
    private TextView search;
    private int REQUEST_CODE;
    private Intent intent;
    private ImageButton acamera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化组件
        scan = getActivity().findViewById(R.id.scan);
        search = getActivity().findViewById(R.id.Search);
        acamera = getActivity().findViewById(R.id.acamera);
        acamera.setOnClickListener(this);
        search.setOnClickListener(this);
        scan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.scan:
                intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                Toast.makeText(getActivity(), "...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Search:
                intent = new Intent(getActivity(), Goods_ParticularsActivity.class);
                startActivity(intent);
                break;
            case R.id.acamera:
                Toast.makeText(getActivity(), "...", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

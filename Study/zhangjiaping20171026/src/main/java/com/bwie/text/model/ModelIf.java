package com.bwie.text.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bwie.text.view.ViewListener;

/**
 * Created by 13435 on 2017/10/26.
 */

public interface ModelIf {
    void modelIf(Context ctx, RecyclerView rlv, ViewListener listener);
}

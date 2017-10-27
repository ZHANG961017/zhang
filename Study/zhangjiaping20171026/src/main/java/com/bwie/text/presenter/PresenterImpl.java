package com.bwie.text.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bwie.text.model.ModelIf;
import com.bwie.text.model.ModelImpl;
import com.bwie.text.view.ViewIf;
import com.bwie.text.view.ViewListener;

/**
 * Created by 13435 on 2017/10/26.
 */

public class PresenterImpl implements PresenterIf,ViewListener {

    ViewIf viewIf;
    ModelIf modelIf;

    public PresenterImpl(ViewIf viewIf) {
        this.viewIf = viewIf;

        modelIf = new ModelImpl();
    }

    @Override
    public void validatepass(Context ctx, RecyclerView rlv) {
        modelIf.modelIf(ctx,rlv,this);
    }
    @Override
    public void onSuccess() {

        if(viewIf != null){
            viewIf.isSeccess();
        }
    }

    @Override
    public void unSuccessful() {
        if(viewIf != null){
            viewIf.isUnSeccess();
        }
    }
}

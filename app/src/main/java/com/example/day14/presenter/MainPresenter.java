package com.example.day14.presenter;

import com.example.day14.base.BasePresenter;
import com.example.day14.bean.JavaBean;
import com.example.day14.model.MainModel;
import com.example.day14.net.MainCallBack;
import com.example.day14.view.MainView;

public class MainPresenter extends BasePresenter<MainView> implements MainCallBack {

    private MainModel mainModel;

    @Override
    protected void initModel() {
        mainModel = new MainModel();
        addModel(mainModel);
    }

    @Override
    public void onSuccess(JavaBean bean) {
        mView.setData(bean);
    }

    @Override
    public void onFaild(String s) {
        mView.showToast(s);
    }

    public void getDatap() {
        mainModel.getDatam(this);
        new MainModel.LoggingInterceptor();
    }
}

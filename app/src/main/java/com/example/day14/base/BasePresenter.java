package com.example.day14.base;

import java.util.ArrayList;

public abstract class BasePresenter<V extends BaseView> {
    public V mView;
    public ArrayList<BaseModel> mModels=new ArrayList<>();

    public BasePresenter(){
        initModel();
    }


    public void addModel(BaseModel baseModel){
        mModels.add(baseModel);
    }

    protected abstract void initModel();

    public void bindView(V view){
        this.mView=view;
    }

    public void destroy(){
        mView=null;
        for (int i = 0; i < mModels.size(); i++) {
            mModels.get(i).dispose();
        }
    }
}

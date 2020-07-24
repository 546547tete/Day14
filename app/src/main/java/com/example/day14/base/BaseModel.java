package com.example.day14.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel {
    private CompositeDisposable mDispoable=null;

    public void addDispose(Disposable disposable){
        if (mDispoable==null){
            synchronized (BaseModel.class){
                if (mDispoable==null){
                    mDispoable=new CompositeDisposable();
                }
            }
        }
        mDispoable.add(disposable);
    }
    public void dispose(){
        if (mDispoable!=null){
            mDispoable.dispose();
        }
    }
    public void removeDispose(Disposable disposable){
        if (mDispoable!=null){
            mDispoable.remove(disposable);
        }
    }
}

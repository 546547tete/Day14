package com.example.day14.net;

import com.example.day14.bean.JavaBean;

public interface MainCallBack {
    void onSuccess(JavaBean bean);
    void onFaild(String s);
}

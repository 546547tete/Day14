package com.example.day14.model;

import android.util.Log;

import com.example.day14.base.BaseModel;
import com.example.day14.bean.JavaBean;
import com.example.day14.net.ApiService;
import com.example.day14.net.MainCallBack;
import com.example.day14.presenter.MainPresenter;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModel extends BaseModel {
    public void getDatam(MainCallBack callBack) {

        Retrofit build = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = build.create(ApiService.class);
        Observable<JavaBean> json = apiService.getJson();
        json.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JavaBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDispose(d);
                    }

                    @Override
                    public void onNext(JavaBean bean) {
                        callBack.onSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaild(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //日志拦截器
    public static class LoggingInterceptor implements Interceptor {
        private static final String TAG = "LoggingInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException {
            //拦截链对象
            Request request = chain.request();
            //Object...args ,...是可变参数,可以理解成数组
            //String.format()第一个参数是格式,后面的参数是替代参数,需要将里面的%s的位置使用
            //后面的参数给替代掉
            //"发送请求地址:"+request.url()+"%n请求头:"+request.header();
            Log.d(TAG, String.format("发送请求地址:%s%n请求头:%s",request.url(),
                    request.headers()));
            long startTime = System.currentTimeMillis();
            //递归+循环的方式把所有的拦截器串联起来,并获取响应结果
            Response response = chain.proceed(request);
            long endTime = System.currentTimeMillis();

            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);

            Log.d(TAG, String.format("耗时:%s%n收到来自:%s的结果:%n%s",
                    (endTime-startTime)+"ms",response.request().url(),responseBody.string()));

            return response;
        }
    }
}

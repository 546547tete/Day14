package com.example.day14;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day14.base.BaseActivity;
import com.example.day14.bean.JavaBean;
import com.example.day14.presenter.MainPresenter;
import com.example.day14.view.MainView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @BindView(R.id.toobr)
    Toolbar toobr;
    @BindView(R.id.tv_jine)
    TextView tvJine;
    @BindView(R.id.tv_shengyu)
    TextView shengyu;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.bt_sign)
    Button btSign;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected void initView() {
        TextView childAt = (TextView) toobr.getChildAt(0);
        childAt.getLayoutParams().width= LinearLayout.LayoutParams.MATCH_PARENT;
        childAt.setGravity(Gravity.CENTER);
    }

    @Override
    protected void initData() {

    }



    @Override
    protected void initListener() {

    }

    @Override
    public void setData(JavaBean bean) {

    }

    @Override
    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.bt_sign)
    public void onViewClicked() {
        String s = et.getText().toString();
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("aaa",s);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==2){
            //隐藏控件
            tvJine.setVisibility(View.GONE);
            et.setVisibility(View.GONE);
            btSign.setVisibility(View.GONE);
            //获取回传回来的数据
            String bbb = data.getStringExtra("bbb");
            //把回传回来的数据给TEXTVIEW
            shengyu.setText("兑换成功!"+"余额为："+bbb);
//            yincang();
        }
    }
}

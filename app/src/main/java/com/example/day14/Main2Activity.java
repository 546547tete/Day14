package com.example.day14;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day14.adapter.RcyAdapter;
import com.example.day14.base.BaseActivity;
import com.example.day14.bean.JavaBean;
import com.example.day14.presenter.MainPresenter;
import com.example.day14.view.MainView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends BaseActivity<MainPresenter> implements MainView {

    @BindView(R.id.toob)
    Toolbar toob;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_rellphone)
    EditText etRellphone;
    @BindView(R.id.tv_yu)
    TextView tvYu;
    @BindView(R.id.tv_shouxu)
    TextView shouxu;
    @BindView(R.id.rcy_main2)
    RecyclerView rcyMain2;
    @BindView(R.id.bt_quxiao)
    Button btQuxiao;
    @BindView(R.id.bt_queren)
    Button btQueren;
    private ArrayList<JavaBean.DataBean.ListBean> listBeans;
    private RcyAdapter rcyAdapter;
    private String aaa;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        ButterKnife.bind(this);
//    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
//        TextView childAt = (TextView) toob.getChildAt(0);
//        childAt.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
//        childAt.setGravity(Gravity.CENTER);

        Intent intent = getIntent();
        aaa = intent.getStringExtra("aaa");
        tvYu.setText("账户余额：" + aaa);

        rcyMain2.setLayoutManager(new LinearLayoutManager(this));
        rcyMain2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        listBeans = new ArrayList<>();
        rcyAdapter = new RcyAdapter(listBeans, this);
        rcyMain2.setAdapter(rcyAdapter);

    }

    @Override
    protected void initData() {
        mPresenter.getDatap();
    }

    @Override
    public void setData(JavaBean bean) {
        if (bean != null && bean.getData().getList() != null) {
            listBeans.addAll(bean.getData().getList());
            rcyAdapter.notifyDataSetChanged();
            shouxu.setText("兑换手续费："+bean.getData().getService());
            rcyAdapter.setSxf(bean.getData().getService());//把手续费传递给适配器
        }
    }

    @Override
    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.bt_quxiao, R.id.bt_queren})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_quxiao:
                finish();
                break;
            case R.id.bt_queren:
                String et1 = etPhone.getText().toString();
                String et2 = etRellphone.getText().toString();
                int i = Integer.parseInt(aaa);//余额
                //通过适配器中记录的最后点击的数据的索引，获得最后点击的数据
                JavaBean.DataBean.ListBean bean = listBeans.get(rcyAdapter.index);
                int count = bean.getStockCount();//库存
                int price = bean.getPrice();//价钱
                int p;
                p=price+2;//价钱+手续费
                if (!TextUtils.isEmpty(et1) && !TextUtils.isEmpty(et2) && et1.equals(et2) && i >= p && count > 0) {
                    Toast.makeText(this, "兑换成功", Toast.LENGTH_SHORT).show();
                    int w=i-p;
                    String t=w+"";
                    Intent intent = new Intent();
                    intent.putExtra("bbb",t);
                    setResult(2,intent);
                    finish();
                } else {
                    Toast.makeText(this, "兑换失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

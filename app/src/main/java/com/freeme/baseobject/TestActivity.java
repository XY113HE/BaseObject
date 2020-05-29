package com.freeme.baseobject;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freeme.baseobject.base.BaseActvity;
import com.freeme.baseobject.base.BaseBean;
import com.freeme.baseobject.base.BaseItemClick;
import com.freeme.baseobject.databinding.ActivityTestBinding;
import com.freeme.baseobject.util.AppHook;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActvity implements TestInterf.MainView{
    private ActivityTestBinding mBinding;
    private TestAdapter adapter;
    private List<BaseBean> beans;
    private TestVM testVM;

    @Override
    protected boolean[] initSetting() {
        return new boolean[]{false, true, true};
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mBinding = getViewBinding();
        testVM = new TestVM(this, mBinding);
    }

    @Override
    protected void initData() {
        beans = new ArrayList<>();
        beans.add(new BaseBean("张三"));
        beans.add(new BaseBean("李四"));
        beans.add(new BaseBean("王二"));
        beans.add(new BaseBean("赵五"));
    }

    @Override
    protected void initOther() {
        adapter = new TestAdapter(this);
        adapter.setData(beans);

        mBinding.rv.setAdapter(adapter);
        mBinding.rv.setLayoutManager(new LinearLayoutManager(AppHook.get().currentActivity(), RecyclerView.VERTICAL, false));

        adapter.setItemClick(new BaseItemClick() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(TestActivity.this, beans.get(position).getBtnName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public BaseActvity getActivity() {
        return this;
    }
}

package com.freeme.baseobject;

import android.content.Context;

import com.freeme.baseobject.base.BaseAdapter;
import com.freeme.baseobject.base.BaseBean;
import com.freeme.baseobject.databinding.ItemTestBinding;

public class TestAdapter extends BaseAdapter<ItemTestBinding, BaseBean> {

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_test;
    }

    @Override
    protected void showData(BaseBean baseBean, ItemTestBinding binding) {
        binding.btn.setText(baseBean.getBtnName());

    }

}

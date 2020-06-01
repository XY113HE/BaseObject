package com.freeme.baselib.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.freeme.baselib.util.Constants;
import com.freeme.baselib.util.Tools;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * 针对DataBinding方式的封装
 * 返回键，底部按钮，屏幕常亮等设置
 * 配合RxJava的绑定好生命周期的
 * SharedPreferences
 */
public abstract class BaseActvity extends RxAppCompatActivity implements LifecycleOwner {
    public SharedPreferences mySharedPreferences;
    public SharedPreferences.Editor editor;
    //是否屏蔽返回键
    protected boolean backBtnDisable = false;
    //是否隐藏底部按钮
    protected boolean hideBottomBtn = false;
    //是否屏幕常亮
    protected boolean screenOn = true;
    //创建Lifecycle对象
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    //view布局id
    private int layoutId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取参数设置
        boolean[] setting = initSetting();
        backBtnDisable = setting[0];
        hideBottomBtn = setting[1];
        screenOn = setting[2];
        if(hideBottomBtn) Tools.hideBottomUIMenu(getWindow());

        //创建sp
        mySharedPreferences = getSharedPreferences(Constants.APP_SP, Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();

        //设置屏幕常亮
        if(screenOn)getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //标记状态
        mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        layoutId = getContentViewId();
        //初始化View
        initView();
        initData();
        initOther();
    }


    //设置参数，第一个为是否屏蔽返回键，第二个为是否隐藏底部虚拟键，第三个为是否屏幕常亮
    protected abstract boolean[] initSetting();
    protected abstract int getContentViewId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initOther();

    protected <T extends ViewDataBinding> T getViewBinding(){
        return DataBindingUtil.setContentView(this, layoutId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //标记状态
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //标记状态
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //标记状态
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        if(mLifecycleRegistry == null) {
            mLifecycleRegistry = new LifecycleRegistry(this);
            //标记状态
            mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        }
        return mLifecycleRegistry;
    }

    //屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && backBtnDisable) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

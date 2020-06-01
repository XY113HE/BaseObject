package com.freeme.baselib.base;

import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.freeme.baselib.util.RxBus;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public abstract class BaseVM<T extends ViewDataBinding, G extends BaseInterf.MainView> implements BaseInterf.ViewModel{
    protected G mainView;
    protected T binding;

    public BaseVM(G mainView, T binding){
        this.mainView = mainView;
        this.binding = binding;
        initClicks();
        initRxBus();
        initNet();
    }

    @Override
    public abstract void initClicks();
    @Override
    public abstract void initRxBus();
    @Override
    public abstract void initNet();

    protected <T>void setRxbus(Class<T> cla, Action1<T> action1){
        RxBus.getRxBus().toObservable(cla)
                .compose(mainView.getActivity().<T>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        action1,
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
    }

    protected void setClick(View view, Action1<Void> action1){
        RxView.clicks(view)
                .compose(mainView.getActivity().<Void>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                //限定时间内只发送第一次的数据
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(
                        action1,
                        new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}

package com.freeme.baseobject;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.freeme.baseobject.base.BaseBean;
import com.freeme.baseobject.base.BaseVM;
import com.freeme.baseobject.databinding.ActivityTestBinding;
import com.freeme.baseobject.net.NetWorkManager;
import com.freeme.baseobject.util.RxBus;
import com.freeme.baseobject.util.Tools;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestVM extends BaseVM<ActivityTestBinding, TestInterf.MainView> {


    public TestVM(TestInterf.MainView mainView, ActivityTestBinding binding) {
        super(mainView, binding);
    }

    @Override
    public void initClicks() {
        setClick(binding.hahaha, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Toast.makeText(mainView.getActivity(), "笑什么呢", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initRxBus() {
        setRxbus(BaseBean.class, new Action1<BaseBean>() {
            @Override
            public void call(BaseBean baseBean) {
                Toast.makeText(mainView.getActivity(), baseBean.getBtnName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 5; i++) {
                    SystemClock.sleep(5000);
                    RxBus.getRxBus().post(new BaseBean("12332"+i));
                }
            }
        }).start();

        NetWorkManager
                .getInstance(null)
//                .getInstance(NetWorkManager.getInterceptor())
                .getNetApi()
                .login("admin", "123456")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("lmy", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {

                    }
                });

    }
}

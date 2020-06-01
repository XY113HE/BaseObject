package com.freeme.baselib.base;

public interface BaseInterf {
    interface MainView{
        BaseActvity getActivity();
    }

    interface ViewModel{
        void initClicks();
        void initRxBus();
        void initNet();
    }
}

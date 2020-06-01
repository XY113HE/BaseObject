package com.freeme.baselib.base;

public class BaseBean {
    private String btnName;
    public BaseBean(String btnName){
        this.btnName = btnName;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }
}

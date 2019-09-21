package com.mars.core.bean.param;

/**
 * Created by lixl on 2017/7/20.
 */
public class SelectItem {

    private Object key;

    private String text;

    public SelectItem(Object key, String text) {
        this.key = key;
        this.text = text;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

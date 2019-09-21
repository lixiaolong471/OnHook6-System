package com.mars.core.bean.param;
/**
 * Created by lixl on 2017/2/17.
 */
public class DataResult extends Result {

    protected Object data;

    public DataResult(){
        super();
    }

    public DataResult(int code,String message,Object data){
        super(code,message);
        this.data = data;
    }

    public DataResult(Object data){
        this.data = data;
    }

    public Object getData(){
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

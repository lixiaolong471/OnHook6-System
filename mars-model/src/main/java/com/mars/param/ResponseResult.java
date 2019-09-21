package com.mars.param;

import java.io.Serializable;

import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.Result;

public class ResponseResult implements Serializable{

	private static final long serialVersionUID = -4592001204958985364L;
	
	private int code;
	private String message;
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Result getResult(){
		return new DataResult(this.getCode(), this.getMessage(),this.getData());
	}
	
	
}

package com.mars.core.bean.notice;

public class Sms {
	private String phone;
	private String content;
	private String code;
	private Boolean selfMotion;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getSelfMotion() {
		return selfMotion;
	}
	public void setSelfMotion(Boolean selfMotion) {
		this.selfMotion = selfMotion;
	}
	
	
}

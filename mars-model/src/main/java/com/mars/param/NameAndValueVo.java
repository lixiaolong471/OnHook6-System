package com.mars.param;

import java.io.Serializable;

public class NameAndValueVo implements Serializable{

	private static final long serialVersionUID = 1598072755595582281L;

	private String name;
	
	private String value;
	
	
	public NameAndValueVo() {
		// TODO Auto-generated constructor stub
	}
	
	public NameAndValueVo(String name,String value) {
		this.name=name;
		this.value=value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}

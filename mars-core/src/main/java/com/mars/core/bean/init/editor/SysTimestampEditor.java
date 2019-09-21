package com.mars.core.bean.init.editor;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SysTimestampEditor extends PropertyEditorSupport {
	
	private SimpleDateFormat dateFormat;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("setAsText"+text);
		if (text==null || text.isEmpty()) {
			setValue(null);
			return;
		}
		if(dateFormat == null){
			if (text.length() == 7) {
				dateFormat = new SimpleDateFormat("yyyy-MM");
			} else if (text.length() == 10) {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			} else if (text.length() == 16) {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else if (text.length() == 19) {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				throw new IllegalArgumentException((new StringBuilder("日期格式错误： ").append(text)).toString());
			}
		}
		try {
			setValue(new Timestamp(this.dateFormat.parse(text).getTime()));
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
		}
		
	}

	public String getAsText() {
		Timestamp value = (Timestamp) getValue();
		return ((value != null) ? this.dateFormat.format(value) : "");
	}
}

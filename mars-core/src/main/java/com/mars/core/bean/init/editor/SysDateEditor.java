package com.mars.core.bean.init.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SysDateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("setAsText"+text);
		SimpleDateFormat sdf = null;
		if (text.length() == 7) {
			sdf = new SimpleDateFormat("yyyy-MM");
		} else if (text.length() == 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		} else if (text.length() == 16) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (text.length() == 19) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (text == null || text.isEmpty()) {
			setValue(null);
			return;
		} else {
			throw new IllegalArgumentException((new StringBuilder("日期格式错误： ").append(text)).toString());
		}

		try {
			Date date = sdf.parse(text);
			setValue(date);
		} catch (ParseException ex) {
			throw new IllegalArgumentException((new StringBuilder("Could not parse date: ")).append(ex.getMessage()).toString(), ex);
		}
	}

	@Override
	public String getAsText() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Object obj = getValue();
			if (obj == null) {
				return "";
			}
			Date value = (Date) obj;
			String text = value == null ? "" : sdf.format(value);
			return text;
		} catch (Exception ex) {
			throw new IllegalArgumentException((new StringBuilder("Could not parse date: ")).append(ex.getMessage()).toString(), ex);
		}
	}
}

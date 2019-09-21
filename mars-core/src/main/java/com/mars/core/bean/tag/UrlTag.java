package com.mars.core.bean.tag;

import com.mars.core.SysConfig;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 给js和css加上 上下文和一个时间戳
 * @author lixl
 *
 */
public class UrlTag implements TemplateDirectiveModel{

	@Override
	public void execute(Environment arg0, Map arg1, TemplateModel[] arg2,
			TemplateDirectiveBody arg3) throws TemplateException, IOException {
		Random random = new Random();
		String value = arg1.get("value")+"";
		String split =(value.indexOf("?")>=0)?"&":"?";
		arg0.getOut().write(SysConfig.getRequest().getContextPath()+"/"+value+split+"_="+new Date().getTime()+random.nextInt(10));
		
	}
	

}

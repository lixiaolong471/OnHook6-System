package com.mars.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.mars.core.SysConfig;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	
	public static String getTemplate(String templateContent,Map<String, Object> map){
		Configuration cfg = new Configuration();  
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("myTemplate",templateContent);  
          
        cfg.setTemplateLoader(stringLoader);  
          
        try {  
            Template template = cfg.getTemplate("myTemplate",SysConfig.getSystemCharacterCode());
              
            StringWriter writer = new StringWriter();    
            try {
                template.process(map, writer);  
                return writer.toString();
            } catch (TemplateException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }
              
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;
	}

}

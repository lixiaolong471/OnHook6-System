package com.mars.core.bean.init;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * 所有实现了WebApplicationInitializer接口的类都会在容器启动时自动被加载运行，用@Order注解设定加载顺序
 * 这是servlet3.0+后加入的特性，web.xml中可以不需要配置内容，都硬编码到WebApplicationInitializer的实现类中
 */
@Order(3)
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	 /*
	  * DispatcherServlet的映射路径
	  */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do","*.g","*.htm","*.thtml","*.3g"};
    }
 
    /*
	  * 应用上下文，除web部分
	  */
	@Override
    protected Class[] getRootConfigClasses() {
        return new Class[] {DaoConfig.class,AppConfig.class};
    }
 
    /*
	  * web上下文
	  */
	@Override
    protected Class[] getServletConfigClasses() {
        return new Class[] {MvcConfig.class};
    }
	
    @Override
    public void onStartup(ServletContext servletContext)throws ServletException {
    	//编码处理与过滤
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        super.onStartup(servletContext);
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.addMapping("*.htm","*.g","*.do","*.thtml","*.3g");
    }

}

package com.mars.core.bean.init;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.mars.core.bean.annotation.interceptor.Interceptor;
import com.mars.core.bean.annotation.interceptor.SysInterceptor;
import com.mars.core.bean.tag.UrlTag;
import com.mars.core.util.SpringUtils;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.mars"})
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Order(4)
public class MvcConfig extends WebMvcConfigurationSupport {
	
	private static final Logger logger = Logger
			.getLogger(MvcConfig.class);


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat(){
                	@Override
                	public Date parse(String text) {
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
                			return null;
                		} else {
                			throw new IllegalArgumentException((new StringBuilder("日期格式错误： ").append(text)).toString());
                		}

                		try {
                			Date date = sdf.parse(text);
                			return date;
                		} catch (ParseException ex) {
                			throw new IllegalArgumentException((new StringBuilder("Could not parse date: ")).append(ex.getMessage()).toString(), ex);
                		}
                    }
                });
//                .modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

    /**
     * 描述 : <HandlerMapping需要显示声明，否则不能注册资源访问处理器>. <br>
     *<p>
     <这个比较奇怪,理论上应该是不需要的>
     </p>
     * @return
     */
    @Bean
    public HandlerMapping resourceHandlerMapping() {
        logger.info("HandlerMapping");
        return super.resourceHandlerMapping();
    }

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        logger.info("RequestMappingHandlerMapping");
        return super.requestMappingHandlerMapping();
    }

    @Override
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        adapter.setWebBindingInitializer(webBindingInitializer());
        return adapter;
    }


    /**
     * 描述 : <添加拦截器>. <br>
     *<p>
     <使用方法说明>
     </p>
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        logger.info("addInterceptors start");
        registry.addInterceptor(sysInterceptor());
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Interceptor.class));
        String basePackage = "com.mars";
        Set<BeanDefinition> components = provider.findCandidateComponents(basePackage);
        for (BeanDefinition component : components) {
            String clz = component.getBeanClassName();
            try {
                registry.addInterceptor((HandlerInterceptor) SpringUtils.getBean(Class.forName(clz)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("addInterceptors end");
    }

    @Bean
    public WebBindingInitializer webBindingInitializer(){
        return new SysWebBindingInitializer();
    }


    @Bean
    public UrlTag urlTag(){
        return new UrlTag();
    }


    public Properties loadConfig(){
        Properties properties = new Properties();
        InputStream ins = null;
        try{
            ins =MvcConfig.class.getResourceAsStream("/freemarker.properties");
            properties.load(ins);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("加载freemarker配置文件出错"+e.getMessage());
        }finally{
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 描述 : 登录验证拦截 <br>
     * @return
     */
    @Bean
    public SysInterceptor sysInterceptor(){
        return new SysInterceptor();
    }


	/**                                                          
	* 描述 : <文件上传处理器>. <br> 
	* @return
	*/  
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver(){
		return new CommonsMultipartResolver();
	}


}
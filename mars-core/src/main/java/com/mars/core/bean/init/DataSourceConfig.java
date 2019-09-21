
package com.mars.core.bean.init;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/** 
 * Description: 数据源相关配置
 * @author Administrator  
 * @version V1.0                             
 */
@Configuration
//加载资源文件
public class DataSourceConfig {

    private static final Logger logger = Logger.getLogger(DataSourceConfig.class);
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try{
            dataSource.setDriverClass(SpringContent.getDbDriverClassName());
            dataSource.setJdbcUrl(SpringContent.getDburl());
            dataSource.setUser(SpringContent.getDbUsername());
            dataSource.setPassword(SpringContent.getDbPassword());
            dataSource.setTestConnectionOnCheckin(false);
            dataSource.setTestConnectionOnCheckout(true);

        }catch(Exception e){
            e.printStackTrace();
        }
		return dataSource;
	}
}



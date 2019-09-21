
package com.mars.core.bean.init;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * 数据库处理相关配置
 * 
 * @author lixl
 * @version V1.0
 */
@Configuration
//启用注解事务管理，使用CGLib代理
@EnableTransactionManagement(proxyTargetClass = true)
@Import({DataSourceConfig.class})
@EnableJpaRepositories(basePackages="com.mars")
@PropertySource({"classpath:spring.properties"})
@EnableAspectJAutoProxy(proxyTargetClass=true)
@Order(1)
public class DaoConfig {

	private static final Logger logger = Logger.getLogger(DaoConfig.class);


	@Resource(name="dataSource")
	public DataSource dataSource;


    @Bean (name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean emf() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(new String[]{"com.mars"});
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        return emf;
    }


    @Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setDatabase(Database.MYSQL);
        jpaAdapter.setShowSql(SpringContent.getDbShowsql());
        jpaAdapter.setGenerateDdl(false);
        jpaAdapter.setDatabasePlatform(SpringContent.getDbDialect());
        return jpaAdapter;
    }

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager() {
		return new JpaTransactionManager();
	}



}

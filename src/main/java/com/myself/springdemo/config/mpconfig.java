package com.myself.springdemo.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.myself.springdemo.handler.MybatisplusFillHandler;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.config.JtaTransactionManagerFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

@Configuration
public class mpconfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="MybatisSqlSessionFactoryBean")
    SqlSessionFactory mybatisSqlSessionFactory(DataSource ds, MybatisPlusInterceptor interceptor, MetaObjectHandler metaObjectHandler) throws Exception{
        MybatisSqlSessionFactoryBean ms=new MybatisSqlSessionFactoryBean();
        ms.setPlugins(interceptor);
        ms.setDataSource(ds);
        ms.setConfiguration(this.myConfiguration());
        GlobalConfig globalConfig=new GlobalConfig();
        globalConfig.setMetaObjectHandler(metaObjectHandler);
        globalConfig.setBanner(false);
        ms.setGlobalConfig(globalConfig);
        return ms.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory mybatisSession) throws Exception {
        return new SqlSessionTemplate(mybatisSession);
    }

    @Bean(name = "sqlsessionStandard")
    public SqlSession sqlSessionStandard(@Qualifier("MybatisSqlSessionFactoryBean") SqlSessionFactory MybatisSqlSessionFactoryBean) {
        return MybatisSqlSessionFactoryBean.openSession();
    }

    @Bean(name = "sqlsessionBatch")
    public SqlSession sqlSessionBatch(@Qualifier("MybatisSqlSessionFactoryBean") SqlSessionFactory sqlSessionFactory) {
        return sqlSessionFactory.openSession(ExecutorType.BATCH);
    }

    /**
     * 配置本地单数据源的数据库事务管理
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
    /**
     * 分布式数据库的数据库事务管理
     */
    @Bean
    public JtaTransactionManager transactionManager() {
        return new JtaTransactionManagerFactoryBean().getObject();
    }

    @ConfigurationProperties("mybatis-plus.configuration")
    @Bean
    public MybatisConfiguration myConfiguration(){
        return new MybatisConfiguration();
    }



    @Bean("plusInterceptor")
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor p=  new PaginationInnerInterceptor();
        p.setDbType(DbType.MYSQL);
        p.setMaxLimit(5L);
        interceptor.addInnerInterceptor(p);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MybatisplusFillHandler();
    }

//    @Bean
//    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
//        return plusProperties -> plusProperties.getGlobalConfig().setIdentifierGenerator(new CustomIdGenerator());
//    }
//    @Bean
//    public IdentifierGenerator idGenerator() {
//        return new CustomIdGenerator();
//    }
}

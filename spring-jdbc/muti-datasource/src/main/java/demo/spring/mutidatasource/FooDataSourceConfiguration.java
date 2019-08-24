//package demo.spring.springmutidatasource;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//@Slf4j
////@Configuration
//public class FooDataSourceConfiguration {
//
//    @Bean
//    @ConfigurationProperties("foo.datasource")
//    public DataSourceProperties fooDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource fooDataSource() {
//        DataSourceProperties dataSourceProperties = fooDataSourceProperties();
//        log.info("foo DataSource: {}", dataSourceProperties.getUrl());
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    @Bean
//    @Resource
//    public PlatformTransactionManager fooTxManager(DataSource fooDataSource) {
//        return new DataSourceTransactionManager(fooDataSource);
//    }
//}
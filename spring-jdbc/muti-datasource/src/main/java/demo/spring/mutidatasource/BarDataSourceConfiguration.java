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
//public class BarDataSourceConfiguration {
//
//    @Bean
//    @ConfigurationProperties("bar.datasource")
//    public DataSourceProperties barDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource barDataSource() {
//        DataSourceProperties dataSourceProperties = barDataSourceProperties();
//        log.info("bar DataSource: {}", dataSourceProperties.getUrl());
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    @Bean
//    @Resource
//    public PlatformTransactionManager barTxManager(DataSource barDataSource) {
//        return new DataSourceTransactionManager(barDataSource);
//    }
//}

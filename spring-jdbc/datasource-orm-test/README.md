# schema.sql,data.sql

测试spring-boot启动时，是否执行schema.sql和data.sql?

加入spring-boot-starter-data-jpa依赖后，没有执行data.sql初始化

这时，可以自定义DataSourceInitializer，在spring-boot启动时，执行指定的sql脚本。


# cache-with-redis

通过spring cache 缓存抽象使用redis 和 单纯使用spring cache缓存抽象 不同点就在：

1.需要加入下面两个starter起步依赖

    <!-- 这两个依赖是必须的 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

2.application.properties配置：

  	spring.cache.type=redis
  	spring.cache.cache-names=coffee
  	spring.cache.redis.time-to-live=5000
  	spring.cache.redis.cache-null-values=false

  	spring.redis.host=localhost

3.本地需要启动redis
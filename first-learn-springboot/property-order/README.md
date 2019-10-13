# spring配置的加载顺序

测试配置加载顺序，以及它们之间是如何覆盖的。

- 配置加载顺序：file/config > classpath/config > classpath根路径下的properties
- 可以通过 spring.profiles.active=dev 激活指定环境的配置文件

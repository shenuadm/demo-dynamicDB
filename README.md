# SpringBoot整合Druid+DynamicDatasource



## 创建 SpringBoot 项目

项目架构如下

![image-20250103093941197](https://github.com/user-attachments/assets/10dc3435-d0ad-4520-b9d3-7a89fb945eaa)

## 整合 Druid

### 添加依赖

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.13</version>
        <relativePath/>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>DemoDynamicDatasource</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>动态数据源 Demo</name>
    <description>SpringBoot: 动态数据源 Demo</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>

        <!-- Spring Boot 核心, 包含了 Tomcat, Jetty 和 Undertow -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>

        <!-- Lombok, 需要在IDE中安装Lombok插件 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Junit 单元测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- MyBatis-Plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.3.1</version>
        </dependency>

        <!-- Druid 数据库连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.23</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

### 修改配置文件 application.xml

```
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid: # Druid 连接池
      initial-size: 5 # 初始化连接池大小
      min-idle: 5 # 最小连接池数量
      max-active: 20 # 最大连接池数量
      max-wait: 60000 # 连接时最大等待时间（单位：毫秒）
      test-while-idle: true
      time-between-eviction-runs-millis: 60000 # 配置多久进行一次检测，检测需要关闭的连接（单位：毫秒）
      min-evictable-idle-time-millis: 300000 # 配置一个连接在连接池中最小生存的时间（单位：毫秒）
      max-evictable-idle-time-millis: 900000 # 配置一个连接在连接池中最大生存的时间（单位：毫秒）
      validation-query: SELECT 1 FROM DUAL # 配置测试连接是否可用的查询 sql
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: false
      web-stat-filter:
        enabled: true
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/* # 配置监控后台访问路径
        login-username: admin # 配置监控后台登录的用户名、密码
        login-password: 123456
      filter:
        stat:
          enabled: true
          log-slow-sql: true # 开启慢 sql 记录
          slow-sql-millis: 2000 # 若执行耗时大于 2s，则视为慢 sql
          merge-sql: true
        wall: # 防火墙
          config:
            multi-statement-allow: true
```

### 添加测试类

  具体代码可查看代码仓库

### 访问测试

http://localhost:8080/druid/login.html

使用配置文件里面的用户名和密码登录即可

![image-20250103150929218](https://github.com/user-attachments/assets/ca99e86b-f678-4a78-9f31-2a4a36bce611)

页面如下：

![image-20250103151006769](https://github.com/user-attachments/assets/322760bd-3166-46f6-ae97-52921822f1d2)

## 整合 DynamicDatasource

### 添加依赖

```
<!-- 动态数据源切换 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
    <version>3.6.1</version>
</dependency>
```

最终pom如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.13</version>
        <relativePath/>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>DemoDynamicDatasource</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>动态数据源 Demo</name>
    <description>SpringBoot: 动态数据源 Demo</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>

        <!-- Spring Boot 核心, 包含了 Tomcat, Jetty 和 Undertow -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 数据库驱动 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>

        <!-- Lombok, 需要在IDE中安装Lombok插件 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Junit 单元测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- MyBatis-Plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.3.1</version>
        </dependency>

        <!-- Druid 数据库连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.8</version>
        </dependency>

        <!-- 动态数据源切换 -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>3.6.1</version>
        </dependency>
        
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

### 修改配置文件，配置 dynamic

配置文件如下：

```
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid: # Druid 连接池
      initial-size: 5 # 初始化连接池大小
      min-idle: 5 # 最小连接池数量
      max-active: 20 # 最大连接池数量
      max-wait: 60000 # 连接时最大等待时间（单位：毫秒）
      test-while-idle: true
      time-between-eviction-runs-millis: 60000 # 配置多久进行一次检测，检测需要关闭的连接（单位：毫秒）
      min-evictable-idle-time-millis: 300000 # 配置一个连接在连接池中最小生存的时间（单位：毫秒）
      max-evictable-idle-time-millis: 900000 # 配置一个连接在连接池中最大生存的时间（单位：毫秒）
      validation-query: SELECT 1 FROM DUAL # 配置测试连接是否可用的查询 sql
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: false
      web-stat-filter:
        enabled: true
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/* # 配置监控后台访问路径
        login-username: admin # 配置监控后台登录的用户名、密码
        login-password: 123456
      filter:
        stat:
          enabled: true
          log-slow-sql: true # 开启慢 sql 记录
          slow-sql-millis: 2000 # 若执行耗时大于 2s，则视为慢 sql
          merge-sql: true
        wall: # 防火墙
          config:
            multi-statement-allow: true
    dynamic:
      primary: master # 设置默认数据源
      datasource:
        # master 数据源配置
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/demo-dynamic-datasource-master?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
        # slave1 数据源配置
        slave1:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/demo-dynamic-datasource-slave1?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
```

### 启动测试

启动的时候，注意查看日志，如下：

![log1](https://github.com/user-attachments/assets/9aeb4904-f941-4771-a40a-7a593f439c8b)

运行后可以测试得出使用动态数据源了

### 仓库 URL

GitHub：https://github.com/shenuadm/demo-dynamicDB

Gitee:

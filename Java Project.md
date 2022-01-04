# Java Project

#### 开发环境检查

- jdk 1.8

​    配置：JAVA_HOME、path

- maven 3.6

​    配置：MAVEN_HOME、本地仓库路径、中央仓库地址、jdk版本

- ideaIU-2020.2.3：

 注意：在idea的欢迎页面选择Configure > settings

​    配置：Java Compiler、File Encodings、Maven、Auto Import、Code Completion > Match case（取消）

​    插件：lombok、MyBatisX



#### MyBatis-Plus:

[http://mp.baomidou.com](http://mp.baomidou.com/guide/)

##### 支持数据库

mysql 、mariadb 、oracle 、db2 、h2 、hsql 、sqlite 、postgresql 、sqlserver 、presto 、Gauss 、Firebird

Phoenix 、clickhouse 、Sybase ASE 、 OceanBase 、达梦数据库 、虚谷数据库 、人大金仓数据库 、南大通用数据库 



##### BFF: backend for frontend

可以使用一个BFF层提前将页面渲染好，发送给浏览器，那么BFF层可以提前将多个服务的数据聚合起来



# Logback日志 

## 1. 日志级别

日志记录器（Logger）的行为是分等级的。如下表所示：

分为：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF

默认情况下，spring boot从控制台打印出来的日志级别只有INFO及以上级别，可以配置日志级别

## 2. 创建日志文件

spring boot内部使用Logback作为日志实现的框架。

先删除前面在application.yml中的日志级别配置

resources 中创建 logback-spring.xml （默认日志文件的名字）

```java
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
</configuration>
```

## 二、基本配置说明

## 1、configuration

日志配置的根节点

 

```
<configuration></configuration>
```

## 2、contextName

<contextName>是<configuration>的子节点。

每个logger都关联到logger上下文，默认上下文名称为“default”。但可以使用<contextName>设置成其他名字，用于区分不同的应用程序。

 

```
<contextName>atguiguSrb</contextName>
```

## 3、property

<property>是<configuration>的子节点，用来定义变量。

<property> 有两个属性，name和value：name的值是变量的名称，value是变量的值。

通过<property>定义的值会被插入到logger上下文中。定义变量后，可以使“${}”来使用变量。

```
<!-- 日志的输出目录 -->
<property name="log.path" value="D:/project/finance/srb_log/core" />
<!--控制台日志格式：彩色日志-->
<!-- magenta:洋红 -->
<!-- boldMagenta:粗红-->
<!-- cyan:青色 -->
<!-- white:白色 -->
<!-- magenta:洋红 -->
<property name="CONSOLE_LOG_PATTERN"
          value="%yellow(%date{yyyy-MM-dd HH:mm:ss}) %highlight([%-5level]) %green(%logger) %msg%n"/>
<!--文件日志格式-->
<property name="FILE_LOG_PATTERN"
          value="%date{yyyy-MM-dd HH:mm:ss} [%-5level] %thread %file:%line %logger %msg%n" />
<!--编码-->
<property name="ENCODING"
          value="UTF-8" />
```

## 4、appender

<appender>是<configuration>的子节点，是负责写日志的组件

<appender>有两个必要属性name和class：name指定appender名称，class指定appender的全限定名

<encoder>对日志进行格式化

<pattern>定义日志的具体输出格式

<charset>编码方式

### 控制台日志配置

```
<!-- 控制台日志 -->
<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
        <pattern>${CONSOLE_LOG_PATTERN}</pattern>
        <charset>${ENCODING}</charset>
    </encoder>
</appender>
```

### 文件日志配置 

<file>表示日志文件的位置，如果上级目录不存在会自动创建，没有默认值。

<append>默认 true，日志被追加到文件结尾，如果是 false，服务重启后清空现存文件。

```
<!-- 文件日志 -->
<appender name="FILE" class="ch.qos.logback.core.FileAppender">
    <file>${log.path}/log.log</file>
    <append>true</append>
    <encoder>
        <pattern>${FILE_LOG_PATTERN}</pattern>
        <charset>${ENCODING}</charset>
    </encoder>
</appender>
```

## 5、logger

<logger>可以是<configuration>的子节点，用来设置某一个包或具体某一个类的日志打印级别、指定<appender>

name：用来指定受此logger约束的某一个包或者具体的某一个类

level：用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF。默认继承上级的级别

<logger>可以包含零个或多个<appender-ref>元素，标识这个appender将会添加到这个logger

```
<!-- 日志记录器  -->
<logger name="com.atguigu" level="INFO">
    <appender-ref ref="CONSOLE" />
    <appender-ref ref="FILE" />
</logger>
```

# 三、多环境配置

## springProfile

在一个基于Spring boot开发的项目里，常常需要有多套环境的配置：开发，测试以及产品。使用springProfile 可以分别配置开发（dev），测试（test）以及生产（prod）等不同的环境

 

```
<!-- 开发环境和测试环境 -->
<springProfile name="dev,test">
    <logger name="com.atguigu" level="INFO">
        <appender-ref ref="CONSOLE" />
    </logger>
</springProfile>
<!-- 生产环境 -->
<springProfile name="prod">
    <logger name="com.atguigu" level="ERROR">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </logger>
</springProfile>
```

# 滚动日志

问题：生产环境下，如果系统长时间运行，那么日志文件会变得越来越大，系统读取和写入日志的时间会越来越慢，严重的情况会耗尽系统内存，导致系统宕机。

解决方案：可以设置滚动日志。

## 1、设置时间滚动策略

RollingFileAppender是Appender的另一个实现，表示滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将旧日志备份到其他文件

<rollingPolicy>是<appender>的子节点，用来定义滚动策略。

TimeBasedRollingPolicy：最常用的滚动策略，根据时间来制定滚动策略。

<fileNamePattern>：包含文件名及转换符， “%d”可以包含指定的时间格式，如：%d{yyyy-MM-dd}。如果直接使用 %d，默认格式是 yyyy-MM-dd。<maxHistory>：可选节点，控制保留的归档文件的最大数量，超出数量就删除旧文件。假设设置每个月滚动，且<maxHistory>是6，则只保存最近6个月的文件，删除之前的旧文件。注意，删除旧文件是，那些为了归档而创建的目录也会被删除。

 

```
<appender name="ROLLING_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <!--  要区别于其他的appender中的文件名字  -->
    <file>${log.path}/log-rolling.log</file>
    <encoder>
        <pattern>${FILE_LOG_PATTERN}</pattern>
        <charset>${ENCODING}</charset>
    </encoder>
    <!-- 设置滚动日志记录的滚动策略 -->
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <!-- 日志归档路径以及格式 -->
        <fileNamePattern>${log.path}/info/log-rolling-%d{yyyy-MM-dd}.log</fileNamePattern>
        <!--归档日志文件保留的最大数量-->
        <maxHistory>15</maxHistory>
    </rollingPolicy>
</appender>
```

## 2、设置触发滚动时机

放在<rollingPolicy>的子节点的位置，基于实践策略的触发滚动策略

<maxFileSize>设置触发滚动条件：单个文件大于100M时生成新的文件

**注意：修改日志文件名** 此时 <fileNamePattern>${log.path}/info/log-rolling-%d{yyyy-MM-dd}**.%i**.log</fileNamePattern>

```java
<timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
    <maxFileSize>1KB</maxFileSize>
</timeBasedFileNamingAndTriggeringPolicy>
```



## 完整的日志配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <contextName>atguiguSrb</contextName>
    <!-- 日志的输出目录 -->
    <property name="log.path" value="D:/project/test/srb_log/core" />
    <!--控制台日志格式：彩色日志-->
    <!-- magenta:洋红 -->
    <!-- boldMagenta:粗红-->
    <!-- cyan:青色 -->
    <!-- white:白色 -->
    <!-- magenta:洋红 -->
    <property name="CONSOLE_LOG_PATTERN"
              value="%yellow(%date{yyyy-MM-dd HH:mm:ss}) %highlight([%-5level]) %green(%logger) %msg%n"/>
    <!--文件日志格式-->
    <property name="FILE_LOG_PATTERN"
              value="%date{yyyy-MM-dd HH:mm:ss} [%-5level] %thread %file:%line %logger %msg%n" />
    <!--编码-->
    <property name="ENCODING"
              value="UTF-8" />
    <!-- 控制台日志 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>${ENCODING}</charset>
        </encoder>
    </appender>
    <!-- 文件日志 -->
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>${log.path}/log.log</file>
        <append>true</append>
        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>${ENCODING}</charset>
        </encoder>
    </appender>
    <appender name="ROLLING_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--  要区别于其他的appender中的文件名字  -->
        <file>${log.path}/log-rolling.log</file>
        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>${ENCODING}</charset>
        </encoder>
        <!-- 设置滚动日志记录的滚动策略 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志归档路径以及格式 -->
            <fileNamePattern>${log.path}/info/log-rolling-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <!--归档日志文件保留的最大数量-->
            <maxHistory>15</maxHistory>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>1KB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
    </appender>
    <!--    <logger name="com.atguigu" level="INFO">-->
    <!--        <appender-ref ref="CONSOLE" />-->
    <!--        <appender-ref ref="FILE" />-->
    <!--    </logger>-->
    <!-- 开发环境和测试环境 -->
    <springProfile name="dev,test">
        <logger name="com.atguigu" level="INFO">
            <appender-ref ref="CONSOLE" />
        </logger>
    </springProfile>
    <!-- 生产环境 -->
    <springProfile name="prod">
        <logger name="com.atguigu" level="ERROR">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="ROLLING_FILE" />
        </logger>
    </springProfile>
</configuration>
```

## 

## nginx的配置

```
server {
    listen       80;
    server_name  localhost;
    location ~ /core/ {           
        proxy_pass http://localhost:8110;
    }
    location ~ /sms/ {           
        proxy_pass http://localhost:8120;
    }
    location ~ /oss/ {           
            proxy_pass http://localhost:8130;
    }
}
```

nginx的命令

```nginx
start nginx #启动
nginx -s stop #停止
nginx -s reload #重新加载配置
```



## 数据字典

何为数据字典？数据字典负责管理系统常用的分类数据或者一些固定数据，例如：省市区三级联动数据、民族数据、行业数据、学历数据等，数据字典帮助我们方便的获取和适用这些通用数据。

- parent_id：上级id，通过id与parent_id构建上下级关系，例如：我们要获取所有行业数据，那么只需要查询parent_id=20000的数据
- name：名称，例如：填写用户信息，我们要select标签选择民族，“汉族”就是数据字典的名称
- value：值，例如：填写用户信息，我们要select标签选择民族，“1”（汉族的标识）就是数据字典的值
- dict_code：编码，编码是我们自定义的，全局唯一，例如：我们要获取行业数据，我们可以通过parent_id获取，但是parent_id是不确定的，所以我们可以根据编码来获取行业数据



# 一、easyexcel

## 1、创建一个普通的maven项目

项目名：alibaba-easyexcel

## 2、pom中引入xml相关依赖

```
<dependencies>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>easyexcel</artifactId>
        <version>2.1.7</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.5</version>
    </dependency>
    <dependency>
        <groupId>org.apache.xmlbeans</groupId>
        <artifactId>xmlbeans</artifactId>
        <version>3.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.12</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
</dependencies>
```

# 

# 二、写

## 1、创建实体类

```
package com.atguigu.easyexcel.dto;
@Data
public class ExcelStudentDTO {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("生日")
    private Date birthday;
    @ExcelProperty("薪资")
    private Double salary;
}
```

## 2、最简单的写

```
package com.atguigu.easyexcel;
public class ExcelWriteTest {
    @Test
    public void simpleWriteXlsx() {
        String fileName = "d:/excel/simpleWrite.xlsx"; //需要提前新建目录
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ExcelStudentDTO.class).sheet("模板").doWrite(data());
    }
    
    //辅助方法
    private List<ExcelStudentDTO> data(){
        List<ExcelStudentDTO> list = new ArrayList<>();
        //算上标题，做多可写65536行
        //超出：java.lang.IllegalArgumentException: Invalid row number (65536) outside allowable range (0..65535)
        for (int i = 0; i < 65535; i++) {
            ExcelStudentDTO data = new ExcelStudentDTO();
            data.setName("Helen" + i);
            data.setBirthday(new Date());
            data.setSalary(123456.1234);
            list.add(data);
        }
        return list;
    }
}
```

## 3、不同版本的写

```
@Test
public void simpleWriteXls() {
    String fileName = "d:/excel/simpleWrite.xls";
    // 如果这里想使用03 则 传入excelType参数即可
    EasyExcel.write(fileName, ExcelStudentDTO.class).excelType(ExcelTypeEnum.XLS).sheet("模板").doWrite(data());
}
```

## 4、写入大数据量

xls 版本的Excel最多一次可写0 ...65535行

xlsx 版本的Excel最多一次可写0...1048575行

# 三、读

## 1、参考文档

https://www.yuque.com/easyexcel/doc/read

## 2、创建监听器

```
package com.atguigu.easyexcel.listener;
@Slf4j
public class ExcelStudentDTOListener extends AnalysisEventListener<ExcelStudentDTO> {
    
    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(ExcelStudentDTO data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
    }
    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }
}
```

## 3、测试用例

```
package com.atguigu.easyexcel;
public class ExcelReadTest {
    /**
     * 最简单的读
     */
    @Test
    public void simpleReadXlsx() {
        String fileName = "d:/excel/simpleWrite.xlsx";
        // 这里默认读取第一个sheet
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener()).sheet().doRead();
    }
    @Test
    public void simpleReadXls() {
        String fileName = "d:/excel/simpleWrite.xls";
        EasyExcel.read(fileName, ExcelStudentDTO.class, new ExcelStudentDTOListener()).excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }
}
```



## Redis

https://github.com/MicrosoftArchive/redis/releases

#### 执行下面命令

```
redis-server.exe redis.windows.conf --maxmemory 200M
```

#### 常用的写入键值对命令和开启密码登录redis操作

```
start redis:
redis-cli.exe

set name  ....

get name ...

config get requirepass  =》 check password  default without password

config set requirepass ...

auth ...     => login by password

```

#### 注册开机自启动服务

```
#注册安装服务
redis-server --service-install redis.windows.conf --loglevel verbose
#卸载服务
#redis-server --service-uninstall
```









## **一、用户身份认证**

## 1、单一服务器模式

![img](file:///C:/Users/Minty/Documents/My Knowledge/temp/97897437-1ef9-4bb7-ad9c-1a50f99bd36f/128/index_files/c18626d3-0e57-43b5-90ba-89a9cf0b4235.jpg)

**一般过程如下：**



1. 用户向服务器发送用户名和密码。
2. 验证服务器后，相关数据（如用户名，用户角色等）将保存在当前会话（session）中。
3. 服务器向用户返回session_id，session信息都会写入到用户的Cookie。
4. 用户的每个后续请求都将通过在Cookie中取出session_id传给服务器。
5. 服务器收到session_id并对比之前保存的数据，确认用户的身份。

**缺点：**



- 单点性能压力，无法扩展。
- 分布式架构中，需要session共享方案，session共享方案存在性能瓶颈。

**session共享方案：**

session广播：性能瓶颈，不推荐

redis代替session：推荐，性能高





## 2、SSO（Single Sign On）模式

CAS单点登录、OAuth2

![img](file:///C:/Users/Minty/Documents/My Knowledge/temp/97897437-1ef9-4bb7-ad9c-1a50f99bd36f/128/index_files/346672f4-9c52-42bf-8fa3-464bf34a8873.jpg)

分布式，SSO(single sign on)模式：单点登录英文全称Single Sign On，简称就是SSO。它的解释是：在多个应用系统中，只需要登录一次，就可以访问其他相互信任的应用系统。

- 如图所示，图中有3个系统，分别是业务A、业务B、和SSO。
- 业务A、业务B没有登录模块。
- 而SSO只有登录模块，没有其他的业务模块。

**一般过程如下：**

1. 当业务A、业务B需要登录时，将跳到SSO系统。
2. SSO从用户信息数据库中获取用户信息并校验用户信息，SSO系统完成登录。
3. 然后将用户信息存入缓存（例如redis）。
4. 当用户访问业务A或业务B，需要判断用户是否登录时，将跳转到SSO系统中进行用户身份验证，SSO判断缓存中是否存在用户身份信息。
5. 这样，只要其中一个系统完成登录，其他的应用系统也就随之登录了。这就是单点登录（SSO）的定义。

**优点 ：**  

用户身份信息独立管理，更好的分布式管理。可以自己扩展安全策略

**缺点：**

认证服务器访问压力较大。

## 3、Token模式

![img](file:///C:/Users/Minty/Documents/My Knowledge/temp/97897437-1ef9-4bb7-ad9c-1a50f99bd36f/128/index_files/0.9709434306629741.png)

**优点：**

- 无状态： token是无状态，session是有状态的
- 基于标准化：你的API可以采用标准化的 JSON Web Token (JWT)

**缺点：**

- 占用带宽
- 无法在服务器端销毁

 







# 一、访问令牌的类型

![img](file:///C:/Users/Minty/Documents/My Knowledge/temp/87a0ac78-2d6a-42e3-ab02-2744eaf144b7/128/index_files/4c5b3db1-6052-441b-8384-8800fa4c288a.jpg)

# 二、JWT令牌

## 1、什么是JWT令牌

```
JWT是JSON Web Token的缩写，即JSON Web令牌，是一种自包含令牌。 
JWT的使用场景：
一种情况是webapi，类似之前的阿里云播放凭证的功能
另一种情况是多web服务器下实现无状态分布式身份验证
JWT官网有一张图描述了JWT的认证过程


JWT的作用：
JWT 最重要的作用就是对 token信息的防伪作用


JWT的原理：
一个JWT由三个部分组成：JWT头、有效载荷、签名哈希最后由这三者组合进行base64编码得到JWT
```

## 2、JWT令牌的组成

典型的，一个JWT看起来如下图：

https://jwt.io/

![img](file:///C:/Users/Minty/Documents/My Knowledge/temp/87a0ac78-2d6a-42e3-ab02-2744eaf144b7/128/index_files/3402e929-2225-4c64-8f2e-4471b63366d0.png)

该对象为一个很长的字符串，字符之间通过"."分隔符分为三个子串。

每一个子串表示了一个功能块，总共有以下三个部分：JWT头、有效载荷和签名

***\*J\**WT****头**

JWT头部分是一个描述JWT元数据的JSON对象，通常如下所示。



 

```
{
  "alg": "HS256",
  "typ": "JWT"
}
```

在上面的代码中，alg属性表示签名使用的算法，默认为HMAC SHA256（写为HS256）；typ属性表示令牌的类型，JWT令牌统一写为JWT。最后，使用Base64 URL算法将上述JSON对象转换为字符串保存。

**有效载荷**

有效载荷部分，是JWT的主体内容部分，也是一个JSON对象，包含需要传递的数据。 JWT指定七个默认字段供选择。



 

```
sub: 主题
iss: jwt签发者
aud: 接收jwt的一方
iat: jwt的签发时间
exp: jwt的过期时间，这个过期时间必须要大于签发时间
nbf: 定义在什么时间之前，该jwt都是不可用的.
jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
```

除以上默认字段外，我们还可以自定义私有字段，如下例：



 

```
{
  "name": "Helen",
  "admin": true,
  "avatar": "helen.jpg"
}
```

请注意，默认情况下JWT是未加密的，任何人都可以解读其内容，因此不要构建隐私信息字段，存放保密信息，以防止信息泄露。

JSON对象也使用Base64 URL算法转换为字符串保存。

**签名哈希**

签名哈希部分是对上面两部分数据签名，通过指定的算法生成哈希，以确保数据不会被篡改。

首先，需要指定一个密码（secret）。该密码仅仅为保存在服务器中，并且不能向用户公开。然后，使用标头中指定的签名算法（默认情况下为HMAC SHA256）根据以下公式生成签名。



 

```
HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(claims), secret)    ==>   签名hash
```

在计算出签名哈希后，JWT头，有效载荷和签名哈希的三个部分组合成一个字符串，每个部分用"."分隔，就构成整个JWT对象。

**Base64URL算法**

如前所述，JWT头和有效载荷序列化的算法都用到了Base64URL。该算法和常见Base64算法类似，稍有差别。

作为令牌的JWT可以放在URL中（例如api.example/?token=xxx）。 Base64中用的三个字符是"+"，"/"和"="，由于在URL中有特殊含义，因此Base64URL中对他们做了替换："="去掉，"+"用"-"替换，"/"用"_"替换，这就是Base64URL算法。

**注意：**base64编码，并不是加密，只是把明文信息变成了不可见的字符串。但是其实只要用一些工具就可以把base64编码解成明文，所以不要在JWT中放入涉及私密的信息。



## 3、JWT的用法

客户端接收服务器返回的JWT，将其存储在Cookie或localStorage中。

此后，客户端将在与服务器交互中都会带JWT。如果将它存储在Cookie中，就可以自动发送，但是不会跨域，因此一般是将它放入HTTP请求的Header Authorization字段中。

当跨域时，也可以将JWT放置于POST请求的数据主体中。

### 三、JWT问题和趋势

1、JWT默认不加密，但可以加密。生成原始令牌后，可以使用该令牌再次对其进行加密。

2、当JWT未加密时，一些私密数据无法通过JWT传输。

3、JWT不仅可用于认证，还可用于信息交换。善用JWT有助于减少服务器请求数据库的次数。

4、JWT的最大缺点是服务器不保存会话状态，所以在使用期间不可能取消令牌或更改令牌的权限。也就是说，一旦JWT签发，在有效期内将会一直有效。

5、JWT本身包含认证信息，因此一旦信息泄露，任何人都可以获得令牌的所有权限。为了减少盗用，JWT的有效期不宜设置太长。对于某些重要操作，用户在使用时应该每次都进行身份验证。

6、为了减少盗用和窃取，JWT不建议使用HTTP协议来传输代码，而是使用加密的HTTPS协议进行传输。



# 一、运行Nacos注册中心

## 2、Windows启动Nacos

参考：https://github.com/alibaba/nacos

解压：将下载的压缩包解压



启动：startup.cmd -m standalone

## 3、访问

http://localhost:8848/nacos

用户名密码：nacos/nacos
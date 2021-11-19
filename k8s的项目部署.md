# k8s的项目部署

# 1 容器交付流程

## 1.1 开发阶段



- 编写代码。

- 测试。

- 编写Dockerfile。



## 1.2 持续集成



- 代码编译、打包。

- 制作镜像。

- 将镜像上传到镜像仓库。



## 1.3 应用部署



- 环境准备。

- 创建Pod、Service、Ingress。



## 1.4 运维



- 监控。

- 故障排查。

- 应用升级及优化。

- ……



# 2 k8s中部署Java项目的流程



- ① 通过Dockerfile制作镜像。

- ② 将镜像推送到镜像仓库，比如阿里云镜像仓库等。

- ③ Pod控制器部署镜像。

- ④ 创建Service或Ingress对外暴露应用。

- ⑤ 对集群进行监控、升级等。



# 3 k8s中部署Java项目



## 3.1 前提说明



- 本人是在Windows进行开发的，部署在Linux（CentOS7）中的k8集群。



## 3.2 准备Java项目,并将项目进行打包



### 3.2.1 概述



- 准备一个Java项目，将Java项目进行打包，本次使用SpringBoot项目为例，使用的JDK的版本是11。



### 3.2.2 准备工作



- JDK 11 。

- Maven 3.6x。



### 3.2.3 演示的SpringBoot项目



- pom.xml



```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.7.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>springboot2</artifactId>
    <version>1.0</version>
    <name>springboot2</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>11</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```



- HelloController.java



```java
package com.example.springboot2.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 许大仙
 * @version 1.0
 * @since 2021-01-12 09:18
 */
@RestController
public class HelloController {

    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

}
```



- 启动类：



```java
package com.example.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2Application.class, args);
    }
}
```



### 3.2.4 使用Maven进行打包



- 打包命令：



```shell
mvn clean install
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420425249-694ef6d4-ec2a-47f2-8c2b-0f09206ee5d8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_55%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.5 在项目的根目录下新建Dockerfile文件



- Dockerfile：



```dockerfile
FROM openjdk
VOLUME /tmp
COPY ./target/springboot2-1.0.jar springboot2-1.0.jar
RUN bash -c "touch /springboot2-1.0.jar"
# 声明时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8080
ENTRYPOINT ["java","-jar","/springboot2-1.0.jar"]
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420437609-5b5cc05a-220b-4138-909f-a09e7a0aa583.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_41%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.3 制作镜像



- 将整个项目通过ftp上传到k8s集群所在的服务器中（其实完全可以只上传jar包和Dockerfile文件）。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420451307-495a9c61-339d-4561-aeda-af31d0f36391.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 进入springboot2目录：



```shell
cd springboot2
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420462421-cfb6ceff-3c6d-42ca-b76e-fdcaeffc6402.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 使用docker build构建镜像：



```shell
# springboot是镜像的名称
docker build -t springboot2 .
```



![img](https://cdn.nlark.com/yuque/0/2021/gif/513185/1610491886202-ec3da00e-ae74-411a-a912-6c961ab49995.gif)



## 3.4 推送镜像



- 阿里云创建命名空间：



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420475135-0364112a-0e5d-4476-b886-5a608cd00410.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_43%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420481922-f0f6b104-c425-40f4-8c73-3bbd2fa76c13.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 阿里云创建镜像仓库：



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420496463-ec5e4de4-c1e5-4257-9f61-ca09dca54b9a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420504236-a12ba66e-280a-43c4-aef3-688c919719d6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420510358-d75e7d13-7fb5-4a60-b297-8605341b61b8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_49%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 登录阿里云Docker Registry：



```shell
sudo docker login --username=阿里云账号 registry.cn-hangzhou.aliyuncs.com
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420525482-1194fbda-bf4c-4ed4-a043-4d4aebcc75af.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看上传的Docker镜像的id：



```shell
docker images
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420536052-1802154e-ffaf-4530-baea-ccb92c3ee172.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 给镜像打tag：



```shell
sudo docker tag bc56e4a83ff7 registry.cn-hangzhou.aliyuncs.com/k8s-test-123/springboot2:latest
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420546654-900ee5e3-2c1f-4de8-b878-6e273219bae0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 推送镜像：



```shell
sudo docker push registry.cn-hangzhou.aliyuncs.com/k8s-test-123/springboot2:latest
```



![img](https://cdn.nlark.com/yuque/0/2021/gif/513185/1610420558042-aa013db4-b65f-4200-941d-d7833f4fa149.gif)



## 3.5 部署镜像暴露应用



- 创建deployment.yaml文件，内容如下：



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: springboot2
  name: springboot2
spec:
  replicas: 3
  selector:
    matchLabels:
      app: springboot2
  template:
    metadata:
      labels:
        app: springboot2
    spec:
      containers:
      - image: registry.cn-hangzhou.aliyuncs.com/k8s-test-123/springboot2:latest
        name: springboot2
```



- 创建Deployment：



```shell
kubectl create -f deployment.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420571263-f815041f-e79f-4cd0-97b3-9a8ccd82025e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Deployment和Pod：



```shell
kubectl get deploy,pod
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420582399-b8bfffd9-8ad9-4efd-a48c-ce410f88d2b4.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建service.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Service
metadata:
  labels:
    app: springboot2
  name: svc
spec:
  ports:
  - port: 8080
    protocol: TCP
    targetPort: 8080
    nodePort: 30091
  selector:
    app: springboot2
  type: NodePort
```



- 创建Service：



```shell
kubectl create -f service.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420594576-6349d6fa-2b3e-4cde-a583-c2b21edbc625.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Service：



```shell
kubectl get service
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610420604495-580d7d10-f7f2-4cd6-89ef-35f7f8e6b01e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)


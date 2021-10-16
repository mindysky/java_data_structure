# Spring Cloud

1. 什么是微服务

   - 提倡将单一应用程序划分成一组小的服务，每个服务运行在其独立的自己的进程中，服务之间互相协调，互相配合，为用户提供最终价值。服务之间采用轻量级的通信机制互相沟通，通常是基于HTTP的restful API. 每个服务都围绕具体业务进行构建，并且能够被独立的部署到生产环境，类生产环境等。应尽量避免统一的，集中式的服务管理机制，对具体的一个服务而言，应根据上下文，选择合适的语言，工具对其进行构建，可以有非常轻量级的集中式管理来协调这些服务，可以使用不同的语言来编写服务。也可以使用不同的数据存储。

2. 微服务和微服务架构

   - 微服务： 强调的是服务的大小，它关注的是某一个点，是具体解决某一个问题、提供落地对应服务的一个服务应用，可以看作maven里的一个小的Module, 一个模块就做一件事情。

   - 微服务架构： single application as a suite of small services, each running in its own process and communicating with light weight mechanism often HTTP resource API. These services are built around biz capabilities and independently deployable by fully automated deployment machinery.

     There is a bare minimum of centralized management of these service, they can be written in different programming languages.

3. 微服务之间是如何独立通讯的

4. SpringCloud和Dubbo有哪些区别

5. SpringBoot和SpringCloud

6. 什么是服务熔断，什么是服务降级

7. 微服务优缺点分别是什么？ 项目开发中遇到的实际问题

   - 

8. 你所知道的微服务技术栈有哪些？

   - 服务开发 ： Spring， SpringBoot , SpringMVC
   - 服务配置与管理 ：Nextflix: Archaius, aLi=>Diamond

   - 服务注册与发现： Eureka, Consul, Zookeeper
   - 服务调用: Rest, RPC, gRPC
   - 服务熔断器： Hystrix, Envoy
   - 服务负载均衡： Ribbon, Nginx
   - 服务接口调用（客户端调用服务的简化工具）： Feign
   - 消息队列： Kafka, RabbitMQ, ActiveMQ
   - 服务配置中心管理：SpringCloudConfig, Chef
   - 服务路由（API网关）: Zuul
   - 服务监控:  Zabbix, Nagios, Metrics, Spectator
   - 全链路追踪： Zipkin, Brave, Dapper
   - 服务部署： Docker, OpenStack, Kubernetes
   - 数据流操作开发包： SpringCloud Stream
   - 事件消息总线： Spring Cloud Bus

9. eureka和zookeeper都可以提供服务注册和发现功能，请说说两个的区别？

   

   


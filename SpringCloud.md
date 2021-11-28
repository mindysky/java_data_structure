# Spring Cloud

1. 什么是微服务

   - 提倡将单一应用程序划分成一组小的服务，每个服务运行在其独立的自己的进程中，服务之间互相协调，互相配合，为用户提供最终价值。服务之间采用轻量级的通信机制互相沟通，通常是基于HTTP的restful API. 每个服务都围绕具体业务进行构建，并且能够被独立的部署到生产环境，类生产环境等。应尽量避免统一的，集中式的服务管理机制，对具体的一个服务而言，应根据上下文，选择合适的语言，工具对其进行构建，可以有非常轻量级的集中式管理来协调这些服务，可以使用不同的语言来编写服务。也可以使用不同的数据存储。

2. 微服务和微服务架构

   - 微服务： 强调的是服务的大小，它关注的是某一个点，是具体解决某一个问题、提供落地对应服务的一个服务应用，可以看作maven里的一个小的Module, 一个模块就做一件事情。

   - 微服务架构： single application as a suite of small services, each running in its own process and communicating with light weight mechanism often HTTP resource API. These services are built around biz capabilities and independently deployable by fully automated deployment machinery.

     There is a bare minimum of centralized management of these service, they can be written in different programming languages.

3. 微服务之间是如何独立通讯的

4. 为什么选择SpringCloud？

   - 整体解决方案和框架成熟
   - 社区热度
   - 可维护性
   - 学习曲线

5. SpringBoot和SpringCloud

   - SpringCloud是基于SpringBoot提供的一套微服务解决方案，包括服务注册与发现， 配置中心，全链路监控，服务网关，负载均衡，熔断器等组件。

6. 什么是服务熔断，什么是服务降级

7. 微服务优缺点分别是什么？ 项目开发中遇到的实际问题

   ###### 优点：

   - 每个服务足够内聚，足够小，代码容易理解，这样能聚焦一个指定的业务需求
   - 开发简单，开发效率高，一个服务专一的干一件事
   - 微服务能够被小团队单独开发
   - 微服务是松耦合的，是有功能意义的服务，无论是开发阶段或者是部署阶段都是独立的
   - 微服务能使用不同的语言进行开发
   - 易于与第三方框架集成，微服务允许容易且灵活的方式集成部署，通过持续集成工具如jenkins,hudson, bamboo;
   - 微服务易于理解，修改和维护，可以更好的体现工作成果
   - 微服务允许融合最新技术
   - 微服务只是业务逻辑的代码，不会和其他组件混合
   - 每个微服务都有自己的存储能力，可以有自己的数据库，也可以用统一的数据库

   ###### 缺点：

   - 开发人员要处理分布式系统的复杂性
   - 多服务运维难度，随着服务的增加，运维压力也在增大
   - 系统部署依赖
   - 服务间通信成本
   - 数据一致性
   - 系统集成测试
   - 性能监控

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

9. zuul  路由网关

10. hystrix  断路器， 服务熔断，服务降级

11. ribbon 负载均衡

12. config 分布式配置中心：

    - 

    

    

    


AOP应用场景

1. 日志记录
2. 异常处理
3. 权限验证
4. 缓存处理
5. 事务处理
6. 数据持久化
7. 效率检查
8. 内容分发



aspect： 切面，切面有切点和通知组成，即包括横切逻辑的定义也包括连接点的定义

pointcut: 切点，每个类都拥有多个连接点，可以理解是连接点的集合

joinpoint: 连接点，程序执行的某个特定的位置，如某个方法调用前后

weaving:  植入





##### advice

- Before advice
- after returning advice
- after throwing advice
- after finally advice
- around advice 
  - 环绕通知，最强大的通知类型，可以控制目标方法

spring AOP 动态



1. 定义一个切面

2. 定义切点pointcut

   - 采用@Pointcut 注解完成
   - @Pointcut（public com...()）

3. 定义advice通知

   - @Before

   - @After

   - @AfterReturning

   - @AfterThrowing

   - @Around

     eg: @Before('myPointcut')
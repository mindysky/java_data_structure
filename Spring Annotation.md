# Spring Annotation

#### IOC容器

//配置类

##### config类

@Configuration  配置文件

@Bean  给容器中注册一个Bean组件, 类型为返回值类型，ID默认为方法源
@Component （把普通pojo实例化到spring容器中，相当于配置文件中的  `<bean id="" class=""/>`）

@Documented

@Retention(RententionPolicy.RUNTIME)   
@ComponentScan  包扫描

@Scope
@Lazy-bean
@Import    
@Conditional
@SuppressWarings("xxx")   

##### Service类

@Service   服务（注入dao）

此注注解属于业务逻辑层，service或者manager层
默认按照名称进行装配，如果名称可以通过name属性指定，如果没有name属性，注解写在字段上时，默认去字段名进行查找，如果注解写在setter方法上，默认按照方法属性名称进行装配。当找不到匹配的bean时，才按照类型进行装配，如果name名称一旦指定就会按照名称进行装配

Service层叫服务层，被称为服务，可以理解就是对一个或多个DAO进行的再次封装，封装成一个服务，所以这里也就不会是一个原子操作了，需要事物控制。
service层主要负责业务模块的应用逻辑应用设计。同样是首先设计接口，再设计其实现类，接着再Spring的配置文件中配置其实现的关联。这样我们就可以在应用中调用service接口来进行业务处理。service层的业务实，具体要调用已经定义的dao层接口，封装service层业务逻辑有利于通用的业务逻辑的独立性和重复利用性。程序显得非常简洁。

##### rest control类

@Controller    控制器（注入服务）



@repository（实现dao访问）



@Autowired顾名思义，就是自动装配，其作用是为了消除代码Java代码里面的getter/setter与bean属性中的property



##### 区分一下@Autowired和@Resource两个注解的区别：

1、@Autowired默认按照byType方式进行bean匹配，@Resource默认按照byName方式进行bean匹配

2、@Autowired是Spring的注解，@Resource是J2EE的注解，这个看一下导入注解的时候这两个注解的包名就一清二楚了

Spring属于第三方的，J2EE是Java自己的东西，因此，建议使用@Resource注解，以减少代码和Spring之间的耦合。



如果 Web 应用程序采用了经典的三层分层结构的话，最好在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释，而用 @Component 对那些比较中立的类进行注释。 



1.  `@Component`是任何Spring管理的组件或bean的通用`@Component`型。 
2.  `@Repository`是持久层的`@Repository`型。 
3.  `@Service`是服务层的`@Service`型。 
4.  `@Controller`是表示层（spring-MVC）的`@Controller`型。 
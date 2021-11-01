# Spring Annotation

#### IOC容器

//配置类

##### config类

@Configuration  配置文件

@Bean  给容器中注册一个Bean组件, 类型为返回值类型，ID默认为方法源
@Component

@Documented

@Retention(RententionPolicy.RUNTIME)   
@ComponentScan  包扫描


   
@Scope
@Lazy-bean
@Import    
@Conditional
@SuppressWarings("xxx")   

##### Service类

@Service

##### rest control类

@Controller
# Java Design Pattern

设计原则的核心思想：

1. 找出应用中可能需要变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起。
2. 针对接口编程，而不是针对实现编程
3. 为了交互对象之间的松耦合设计而努力

#### FAQ

1. 请使用UML类图画出原型模式核心角色

2. 原型设计模式的深拷贝和浅拷贝是什么，并写出深拷贝的两种方式的源码（重写clone方法实现深拷贝，使用序列化来实现深拷贝）

3. 在spring框架中哪里使用到原型模式，并对源码进行分析

   beans.xml

   ```java
   <bean id="id" class="com.abc.spring.bean.Monster" scope="prototype"/>
   ```

4. 在Spring中原型bean的创建，就是原型模式的应用
5. 在实际项目开发中，在哪里使用到ocp原则  =》  factory pattern
6. 介绍解释器设计模式是什么？
7. 画出解释器设计模式UML类图，分析设计模式中各个角色是什么？
8. 请说明Spring框架中，哪里使用到了解释器设计模式，并做到源码级别分析
   - Spring框架中SpelExpressionParser就使用到了解释器模式
9. 单例设计模式一共有几种实现方式？ 请分别用代码实现，并说明各个实现方式的优点和缺点？
   - 饿汉式  两种
   - 懒汉式  三种
   - 双重检查 多线程  doublecheck
   - 静态内部类
   - 枚举
10. 实际项目中使用过什么设计模式，怎么使用的，解决了什么问题
11. 设计模式在软件中哪里 ？ 面向对象OO => 功能模块【设计模式+算法（数据结构）】=>  框架【使用多种设计模式】=>  架构【服务器集群】
12. 

##### Erich Gamma 引入

扩展性，稳定性，可读性，可维护性，规范性高，重用性，可靠性

使程序呈现高内聚，低耦合



## 七大设计原则

#### 1. 单一职责原则

- 对类来说，一个类应该只负责一项职责

- 提高类的可读性，可维护性
- 降低变更引起的风险
- 通常情况下，我们应当遵守单一职责原则，只有逻辑足够简单，才可以在代码级违反单一职责原则；
- 只有类中方法数量足够少，可以在方法级别保持单一原则



#### 2. 接口隔离原则 interface segragation principle

- 客户端不应该依赖它不需要的接口，即一个类对另一个类的依赖应该建立在最小接口上

#### 3. 依赖倒转原则（Dependence Inversion Principle）

1. 高层模块不应该依赖低层模块，二者都应该依赖其抽象
2. 抽象不应该依赖细节，细节应该依赖抽象
3. 依赖倒转（倒置）的中心思想是面向接口编程
4. 依赖倒转原则是基于这样的设计理念，相对于细节的多变性，抽象的东西要稳定的多。以抽象为基础搭建的架构比以细节为基础搭建的架构要稳定的多。在java中，抽象是指接口或者抽象类，细节就是具体的实现类
5. 使用接口或抽象类的目的是制定好规范，而不涉及任何具体的操作，把展现细节的任务交给他们的实现类去完成

##### 依赖关系的传递方式：

1. 通过接口传递实现依赖
2. 通过构造方法实现依赖传递
3. 通过setter方法传递

##### 依赖倒转原则的注意事项：

1. 低层模块尽量都要有抽象类或者接口，或者两者都有，程序稳定性更好
2. 变量的声明类型尽量是抽象类或者接口，这样我们的变量引用和实际对象间就存在缓存层，利于程序扩展和优化
3. 继承时遵循里氏替换原则

#### 4. 里氏替换原则（Liskov Substitution Principle）

1. 如果对每个类型为T1对象o1， 都有类型为T2的对象o2, 使得以T1定义的所有程序P在所有的对象o1都替换成o2时，程序P的行为没有发生变化，那么类型T2是类型T1的子类型。换句话说，所有引用基类的地方必须透明地使用其子类的对象。
2. 在使用继承时，遵循里氏替换原则，在子类中尽量不要重写父类的方法
3. 里氏替换原则告诉我们，继承实际上让两个类耦合性增强了，在适当的情况下，可以通过聚合，组合，依赖来解决问题。

#### 5. 开闭原则 （Open Closed Principle）OCP

1. 开闭原则是编程中最基础，最重要的设计原则
2. 一个软件实体如类，模块和函数应该对扩展开放（对提供方）， 对修改关闭（对使用方），用抽象构建框架，用实现扩展细节
3. 当软件需要变化时，尽量通过扩展软件实体的行为来实现变化，而不是通过修改已有的代码来实现变化
4. 编程中遵循其他原则，以及使用设计模式的目的就是遵循开闭原则

#### 6. 迪米特法则 （Demeter Principle）

1. 一个对象应该对其他对象保持最少的了解
2. 类与类关系越密切，耦合度越大
3. 迪米特法则又叫最少知道原则，即一个类对自己依赖的类知道的越少越好，对外除了提供public方法外，不对外泄露任何信息
4. 迪米特法则还有一个更简单的定义： 只与直接的朋友通信
5. 直接的朋友： 每个对象都会与其他对象有耦合关系只要两个对象之间有耦合关系，我们就说这两个对象之间是朋友关系。耦合的方式很多，依赖，关联，组合，聚合等，其中，我们称出现成员变量，方法参数，方法返回值中的类为直接的朋友，而出现在局部变量中的类不是直接的朋友，也就是说，陌生的类最好不要以局部变量的形式出现在类的内部。

#### 7. 合成复用原则

- 尽量使用合成/聚合的方式，而不是使用继承

#### 8. UML类图

unified modeling language

rational rose

-  依赖关系dependency： 只要在类中用到了对方，那么他们之间就存在依赖关系
  - 类中用到对方
  - 如果是类的成员属性
  - 如果是方法的返回类型
  - 是方法的接收类型
  - 方法中使用到
- 泛化generalization： 实际是继承关系，是依赖关系的特例
  - 泛化关系就是继承关系
- 实现关系implementation: 实现关系实际上就是A类实现B类，也是依赖关系的特例
- 关联关系association : 关联关系实际上就是类与类之间的联系，他是依赖关系的特例
  - 关系具有导航性： 即双向关系或单向关系
  - 关系具有多重性： 如1表一有且有一个，0..表示0个或多个
- 聚合关系aggregation:  聚合关系表示的是整体和部分的关系，整体和部分都可以分开。聚合关系时关联关系的特例，具有关联的导航性和多重性。
- 组合关系Composition: 如果整体和部分不能分开，就升级为组合关系
  - 如果一个类定义了对另一个类的级联删除，那他们就是组合关系

#### 设计模式

设计模式的本质是提高软件的维护性，通用性和扩展性，并降低软件的复杂性。

##### 创建型模式：

1. 单例模式

   - 采取一定方法保证整个软件系统中，对某个类只能存在一个对象实例，并且该类只提供一个取得其对象实例的方法（静态方法）

   - 如Hibernate的SessionFactory，它充当数据存储源的代理，并负责创建Session对象。SessionFactory并不是轻量级的，一般情况下，一个项目通常只需要一个SessionFactory，就会用到单例模式。

   - 想实例化一个单例类，必须记住使用相应的获取对象的方法，而不是使用new

   - 单例模式使用场景： 需要频繁的进行创建和销毁的对象，创建对象时耗时过多或耗费资源过多（即重量级对象），但又经常用到的对象，工具类对象，频繁访问数据库或文件的对象(如：数据源，session工厂等)

   - 单例的八种方式

     1. 饿汉式（静态常量）

        ```java
        class SingleTon{
            //1.private consturctor, disable new instance
        	private SingleTon(){}
            //2.create instance
            private final static SingleTon instance = new SingleTon();
            //3.static factory method
            public static SingleTOn getInstance(){
                return instance;
            }
        }
        //优点：写法简单，在类装载时就完成实例化，避免了线程同步问题。
        //缺点：在类装载时就完成实例化，没有达到Lazy Loading的效果。如果从未使用过这个实例，则会造成内存浪费。
        //这种方式基于classloader机制避免了多线程同步问题
        //结论： 可能会造成内存浪费，确定会用到该实例时使用
        ```

     2. 饿汉式（静态代码块）

        ```java
        class SingleTon{
            //1.init instance
            private static SingleTon instance;
            //2.create instance in static block
            static{
                instance = new SingleTon();
            }
         	//3.private consturctor, disable new instance
        	private SingleTon(){}
            //4.static factory method
            public static SingleTOn getInstance(){
                return instance;
            }
        }
        //这种方式和第一种类似，只是将类的实例化放到了静态代码块中，也是在类装载时初始化实例，优缺点与第一种相同。
        ```

     3. 懒汉式（线程不安全）

        ```java
        class SingleTon{
            //1.init instance
            private static SingleTon singleton;
            //2.private consturctor, disable new instance
        	private SingleTon(){}
         
            //3.static factory method, create instance when invocate getInstance
            public static SingleTOn getInstance(){
                if(singleton==null){
                    singleton = new SingleTon()； 
                }
                return singleton;
            }
        }
        //能起到Lazy Loading的效果，但只能单线程使用
        //如在多线程中，一个线程进入if语句，还未来得及执行，另一个线程也通过了该判断语句，这时会产生多个实例，在实际多线程环境下不可以使用这种方式
        //结论: 在实际开发中不要使用
        ```

        

     4. 懒汉式（线程安全，同步方法）

        ```java
        class SingleTon{
            //1.init instance
            private static SingleTon singleton;
            //2.private consturctor, disable new instance
        	private SingleTon(){}
         
            //3.static factory method, create instance when invocate getInstance
            //add sychronized
            public static sychronized SingleTOn getInstance(){
                if(singleton==null){
                    singleton = new SingleTon()； 
                }
                return singleton;
            }
        }
        //解决了线程不安全问题，但是效率太低，每个线程想获取实例的时候，执行getInstance方法都要进行同步，而这个方法其实只执行一次就够了
        //结论: 在实际开发中不推荐使用
        ```

     5. 懒汉式（线程安全，同步代码块）

        ```java
        class SingleTon{
            //1.init instance
            private static SingleTon singleton;
           
            //2.private consturctor, disable new instance
        	private SingleTon(){}
         
            //3.static factory method, create instance when invocate getInstance
            //add sychronized
            public static SingleTOn getInstance(){
                if(singleton==null){
                    sychronized(Singleton.class){
                         singleton = new SingleTon()； 
                    }
                }
                return singleton;
            }
        }
        //未解决了线程安全问题
        //结论: 在实际开发中不能使用
        ```

     6. 双重检查

        ```java
        class SingleTon{
            //1.init instance
            //volatile 可以及时更新到主程，轻量的sychronized
            private static volatile SingleTon singleton;
            //2.private consturctor, disable new instance
        	private SingleTon(){}
         
            //3.static factory method, create instance when invocate getInstance
            public static sychronized SingleTOn getInstance(){
                if(singleton==null){
                     sychronized(Singleton.class){
                         if(singleton==null){
                             singleton = new SingleTon()； 
                         }
                    } 
                }
                return singleton;
            }
        }
        //能解决线程安全问题，也能解决效率问题
        //结论: 在实际开发中推荐使用
        ```

     7. 静态内部类

        ```java
        class SingleTon{
            //1.private consturctor, disable new instance
        	private SingleTon(){}
            //2.init instance
            private static class SingletonInstance{
                //特点：外部类进行类装载时静态内部类不会装载
                //用到内部类时静态内部类才装载，只装载一次，线程安全
                private static final Singleton INSTANCE = new Singleton();
            }
            //3.static factory method，直接返回成员类
            public static sychronized SingleTOn getInstance(){
                return SingletonInstance.INSTANCE;
            }
        }
        //采用了类装载的机制来保证初始化时只有一个线程
        //类的静态属性只会在第一次加载类的时候初始化，在类初始化时，别的线程无法进入，JVM帮我们保证了线程安全
        //能起到Lazy Loading的效果，也能保证线程安全，效率高
        //结论: 在实际开发中推荐使用
        ```

     8. 枚举

        ```java
        enum SingleTon{
            INSTANCE;
        	public void method(){}
        }
        //借助JDK1.5中添加的枚举来实现单例模式，不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
        //能起到Lazy Loading的效果，也能保证线程安全，效率高
        //结论: 在实际开发中推荐使用
        ```

2. 抽象工厂模式

   - 抽象工厂模式：定义一个interface用于创建相关或有依赖关系的对象簇，而无需指明具体的类。
   - 抽象工厂模式可以将简单工厂模式和工厂方法模式进行整合
   - 从设计层面看，抽象工厂模式就是对简单工厂模式的改进（或称为进一步抽象）
   - 将工厂抽象成两层，AbsFactory(抽象工厂)和具体实现的工厂子类，可以根据创建对象类型使用对应的工厂子类。这样将单个的简单工厂类变成了工厂簇，更利于代码的维护和扩展。

3. 工厂模式

   - 设计模式的依赖抽象原则
     - 创建对象实例时，不要直接new类，而是把这个new类的动作放在一个工厂方法中，并返回。变量不要直接持有具体类的引用。
     - 不要让类继承具体类，而是继承抽象类或者实现interface接口
     - 不要覆盖基类中已经实现的方法。

   - 简单工厂模式： 
     - 简单工程模式是属于创建型模式，是工厂模式的一种，简单工厂模式是由一个工厂对象决定创建出哪一种产品类的实例。
     - 简单工厂模式： 定义一个创建对象的类，由这个类来封装实例化对象的行为
     - 在软件开发中，我们会用到大量的创建某种，某类，或者某批对象时，就会使用到工厂模式
     - 简单工厂模式又叫静态工厂模式
   - 工厂方法模式
     - 将对象实例化功能抽象成抽象方法，在子类中具体实现。
     - 定义一个创建对象的抽象方法，由子类决定要实例化的类，将对象实例化推迟到子类中实现。

4. 原型模式

   - Spring中的原型bean创建，就是用的原型模式

   - 原型模式prototype是指：用原型实例指定创建对象的种类，并且通过拷贝这些原型，创建新的对象。
   - 原型模式是一种创建型设计模式，允许一个对象再创建另外一个可定制的对象， 无需知道如何创建的细节
   - 工作原理： 通过将一个原型对象传给那个要发动创建的对象，这个要发动创建的对象通过请求原型对象拷贝它们自己来实施创建，即 对象.clone()
   - 创建新的对象比较复杂时，可以利用原型模式简化对象的创建过程，同时也能够提高效率
   - 不用重新初始化对象，而是动态的获取对象运行时的状态
   - 如果原始对象发送变化（增加或者减少属性），其他克隆对象也会发生相应的变化，无需修改代码
   - 实现深克隆可能需要比较复杂的代码
   - 缺点： 需要为每个类配备一个克隆方法，这对全新的类来说不难，但是对已有的类进行改造需要修改其源码，违背OCP原则。

   ###### 浅拷贝：shallow copy

   - 对于数据类型是基本数据类型的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象。
   - 对于数据类型是引用数据类型的成员变量，比如说成员变量是某个数组，某个类的对象等，那么浅拷贝会进行引用传递，也就是只是将该成员变量的引用值（内存地址）复制一份给新的对象。因为实际上两个对象的该成员变量都指向同一个实例，在一个对象中修改该成员变量会影响到另一个对象的该成员变量值。
   - 浅拷贝使用默认的clone方法来实现

   ###### 深拷贝：deep copy

5. 建造者模式builder pattern

   - 是一种对象构建模式，它可以将负责对象的构建过程抽象出来（抽象类别）  ，使这个抽象过程的不同实现方法可以构造出不同的表现（属性）对象。
   - 建造者模式是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建它们，用户不需要知道内部的构建细节
   - 建造者模式的四个角色：
     1. Product 产品对象
     2. Builder 抽象建造者，创建一个product对象的各个部件指定的接口/抽象类
     3. ConcreteBuilder 具体建造者，实现接口，构建和装配的各个部件
     4. Director 指挥者，构建一个使用Builder接口的对象，它主要是用于创建复杂的对象。主要有两个作用：一是隔离用户与对象的生产过程，二是： 负责控制产品对象的生产过程。
   - JDK应用： StringBuilder

##### 结构性模式

1. 适配器模式 Adapter Pattern

   - 将某个类的接口转换成客户端期望的另一个接口表示，主要的目的是兼容性，让原本因接口不匹配不能一起工作的两个类可以协同工作。 其别名为包装器Wrapper
   - 类适配器 :  使用继承实现
   - 对象适配器 :  根据合成复用原则，使用组合替代继承，所以解决了类适配器必须继承的局限性问题，也不再要求dst必须是接口
     - 使用成本更低，更灵活
   - 接口适配器
     - Default Adapter Pattern, 缺省适配器模式
     - 当不需要全部实现接口提供的方法时，可先设计一个抽象类实现接口，并为该接口中每个方法提供一个默认实现（空方法）。那么该抽象类的子类可有选择的覆盖父类的某些方法来实现需求
     - 适用于一个接口不想使用其所有方法的情况
   - SpringMVC的handleAdapter

2. 桥接模式

3. 装饰者模式

4. 组合模式

5. 外观模式

6. 享元模式

7. 代理模式

##### 行为型模式

1. 模板方法模式
2. 命令模式
3. 访问者模式
4. 迭代器模式
5. 观察者模式
6. 中介者模式
7. 备忘录模式
8. 解释器模式（Interpreter模式）
9. 状态模式
10. 策略模式
11. 职责链模式（责任链模式）


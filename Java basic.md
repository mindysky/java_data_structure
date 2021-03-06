## Java  Basic

JDK（java development kit）

JRE (java runtime environment)

JVM(java virtual machine)

JDK = JRE + JVM+ ....

#### Enum and Annotation

- 如何自定义枚举类

  ```java
  //自定义枚举类
  class Season{
      //声明Season对象的属性
      private final String seasonName;
      private final String seasonDes;
      //私有化类的构造器
      private Season(String seasonName,String seasonDes){
          this.seasonName= seasonName;
          this.seasonDes= seasonDes;
      }
      
      public static final Season SPRING = new Season("Spring","Spring des");
      public static final Season SUMMER = new Season("Summer","Summer des");
      public static final Season AUTUMN = new Season("Autumn","Autumn des");
      public static final Season WINTER = new Season("Winter","Winter des");
      
      public String getSeasonName(){
          return this.seasonName;
      }
       public String getSeasonDes(){
          return this.seasonDes;
      }
  }
  ```

- 如何使用关键字enum定义枚举类

  ```java
  #默认继承自java.lang.Enum类
  enum Season{
      SPRING("Spring","Spring des"),
      SUMMER("Summer","Summer des"),
      AUTUMN("Autumn","Autumn des"),
      WINTER("Winter","Winter des");
      
      //声明Season对象的属性
      private final String seasonName;
      private final String seasonDes;
      //私有化类的构造器
      private Season(String seasonName,String seasonDes){
          this.seasonName= seasonName;
          this.seasonDes= seasonDes;
      }
  }
  ```

- Enum类的主要方法

  ```java
  values()    enum常量数组
  valueOf(String objName)   根据提供的obj Name， 返回obj name 对应的对象   
  toString()  返回当前枚举常量的名称
  equals
  hashCode
  getDeclaringClass
  name
  ordinal
  compareTo
  clone
  ```

- 实现接口的枚举类

  ```java
  interface info(){
      void show();
  }
  enum Season implements info{
      SPRING("Spring","Spring des"){
          @Override
          pubilic void show(){
              System.out.println("spring show")
          }
      },
      SUMMER("Summer","Summer des"),
      AUTUMN("Autumn","Autumn des"),
      WINTER("Winter","Winter des");
      
      //声明Season对象的属性
      private final String seasonName;
      private final String seasonDes;
      //私有化类的构造器
      private Season(String seasonName,String seasonDes){
          this.seasonName= seasonName;
          this.seasonDes= seasonDes;
      }
  }
  ```

###### 特点： 

- 类的对象只有有限个数，确定的。
  - 星期
  - 性别
  - 季节
  - 支付方式
  - 线程状态
  - 订单状态

- 当需要定义一组常量时，强烈建议使用枚举类

- 如果枚举类中只有一个对象，则可以作为单例模式的实现方法

  



#### Collection

- 向Collection接口的实现类的对象中添加数据obj时， 要求obj所在类要重写equals

1. add   object
2. remove  -> return boolean
3. removeAll
4. retrainAll    获取集合交集 并返回给新的集合（保留一样的，删除不一样的）
5. equals(obj)
6. hashCode  --> return obj hashcode value
7. toArray   集合可以转换为数组     Object[] arr = coll.toArray();
8. Arrays.asList()  数组转集合   调用Arrays静态类的方法
9. iterator   返回iterator 接口的实例，用于遍历Collection集合的对象
10. iterator仅用于遍历集合，iterator本身并不提供承装对象的能力，如果需要创建iterator对象，则必须有一个被迭代的集合
11. 集合对象每次调用iterator（）方法都得到一个全新的迭代器对象，默认游标都在集合的第一个元素之前
12. iterator内部定义了remove(),可以在遍历的时候，删除集合内的元素，不是集合对象的remove
13. foreach循环内部仍然调用了迭代器

##### List接口是Collection的子接口

有序，可重复, 动态数组

实现类有ArrayList, LinkedList, Vector

###### list常用方法：

1. void add(int index,Object ele)   在index位置插入ele元素
2. boolean addAll(int index, Collection eles)  从index的位置开始将eles中的所有元素添加进来
3. Object get(int index)  获取指定index位置的元素
4. int indexOf(Object obj)  返回object在集合中首次出现的位置
5. int lastIndexOf(Object obj)   返回obj在当前集合中末次出现的位置
6. Object remove(int index)    移除指定index位置的元素，并返回此元素
7. void  remove(Object obj)  移除List中的obj对象
8. Object set(int index, Object ele)    设置指定index位置的元素为ele
9. List subList(int fromIndex, int toIndex)  返回从fromIndex到toIndex位置的子集合
10. int size()     返回list长度
11. foreach/fori/iterator

##### ArrayList 

线程不安全，效率高

底层使用Object[ ]  elementData存放数组

建议开发中使用带参数的构造器，避免扩容，效率高

jdk7:  创建一个长度为10的数组

jdk8:  初始化时并没有创建长度为10的数组，第一次调用add时才创建长度为10 的数组，并将数据添加到数组

添加和扩容相同

创建对象类似单例的懒汉式，延迟了数组创建时间，节省内存

##### LinkedList

底层使用双向链表存储

对于频繁插入和删除操作使用此类，比使用ArrayList效率高

##### Vector

线程安全，效率低

底层使用Object[ ] 存放数组，创建长度为10的数组，默认扩容为原来数组的2倍

##### Set接口是Collection子接口

存储无序，不可重复数据

实现类HashSet, LinkedHashSet, TreeSet

Set接口中没有新定义的方法，全部都是Collection方法

无序性不等于随机性

不可重复性： 保证添加的元素按照equals()判断时，不能返回true, 即相同的元素只能添加一个

##### HashSet

作为set接口的主要实现类，线程不安全，可以存储null

存储的数据再底层数组中并非按照数组索引的顺序添加，而是根据数据的Hash值决定的

不可重复性也是根据hash值是否

添加时才创建数组，底层数组，长度16

##### LinkedHashSet  

作为HashSet子类，遍历其内部数据时，可以按照添加的顺序遍历

##### TreeSet

放入TreeSet中的对象比较是同一个类

可以按照添加对象的指定属性进行排序



#### Collections 常用方法

1. reverse(List)   反转list元素
2. shuffle(List)  对list元素进行随机排序
3. sort(List)  根据元素的自然顺序对指定的list集合元素按升序排序
4. sort(List, Comparator):  根据指定的Comparator产生的顺序对List集合元素进行排序
5. swap(List, int, int)  将指定list集合中的i处元素和j处元素进行交换
6. Object  max(Collection)  根据元素的自然顺序，返回给定集合中的最大元素
7. Object max(Collection,Comparator)  根据Comparator指定的顺序，返回给定集合中的最大元素
8. Object min(Collection)  
9. Object min(Collection, Comparator)
10. int frequency(Collection, Object)  返回指定集合中指定元素出现的次数
11. void copy(List dest, List src)  将src中的内容复制到dest中
12. boolean replaceAll(List list, Object oldVal, Object newVal)  使用新值替换list对象
13. List synchronizedList(List)  转换为线程安全的list  

##### ArrayList

线程不安全



##### Map

key不能重复，无序，用Set存储，key所在的类要重写equals和hashcode方法

Value可重复，无序，用Colletion存储

一个键值对key-value构成一个entry对象，

entry无序，不可重复，用Set存储

##### TreeSet

底层红黑树

##### HashMap

Map的主要实现类，线程不安全，效率高，可以存储null的key和value

底层：

###### jdk7： 数组+链表：

 在实例化以后，创建了长度是16的一维Entry[]  table

map.put(key1,value1):  调用key1所在类的hashCode()计算key1哈希值，此哈希值经过某种算法计算后，得到在Entry数组中的存放位置

1. 如果此位置上的数据为空，此时key1-value1添加成功

2. 如果此位置上的数据不为空，意味着此位置存在一个或多个数据（以链表形式存在），比较当前key1和已经存在的一个或多个数据的哈希值，如果key1的哈希值与已经存在的数据的哈希值都不相同，此时key1-value1添加成功
3. 如果key1和已经存在的某个数据的哈希值相同，继续比较,  调用key1所在类的equals(key2)，比较：
   - 如果equals返回false,  Key1-value1添加成功 
   - 如果equals返回true，使用value1替换value2

在不断添加过程中，会涉及到扩容问题，默认的扩容方式，扩容为原来容量的2倍，并将原有的数据复制过来。

###### jdk8：  数组+链表+红黑树

1. new HashMap(),  底层没有创建一个长度为16的数组
2. jdk8 底层的数组时Node[], 而非Entry[]
3. 首次调用put方法，底层创建长度为16的数组
4. 底层结构： 数组+链表+红黑树
5. 当数组的某一个索引位置上的元素以链表形式存在数据个数大于8，且当前这个数组长度还超过64，此时此索引位置上的所有数据改为使用红黑树存储

##### LinkedHashMap

保证遍历map元素时，可以按照添加的顺序实现遍历

原因： 在原有的hashMap底层结构的基础上，添加了一堆指针，指向前一个和后一个元素

对于频繁的遍历操作，此类执行效率高于HashMap

LinkedHashMap内部类 Entry

Entry<k,v>带有before,after指针

##### TreeMap

有序的map，保证按照添加的key-value对进行排序，实现排序遍历，要求key必须是由同一个类创建的对象

此时考虑key的自然排序或者定制排序

底层使用红黑树

##### Map常用方法

1. 添加 put(key,value)  当key相同，会替换
2. 添加所有putAll(Map m)
3. 删除remove(Object key)
4. 清空clear()  清空所有
5. map.size()    返回大小 int
6. 查询  map.get(Object key)
7. containsKey(Object key)   return boolean  是否包含指定的key
8. isEmpty()   判断当前map是否为空  boolean
9. equals(Object obj)   判断当前map和参数对象obj是否相等  boolean
10. Set keySet()  返回key集合
11. Collection values()  返回所有values构成的Collection
12. Set entrySet()   返回所有key-value对构成的Set集合  

###### Hashtable

 古老的实现类，线程安全，效率低，不能存储null的key和value

- Properties: 常用来处理配置文件， 是Hashtable的子类，key和value都是String类型



FQA:

1. HashMap的底层实现原理？

2. HashMap和Hashtable的异同？

3. CurrentHashMap和Hashtable的异同

4. HashMap中put/get方法

5. HashMap扩容机制，默认大小，什么是负载因子，或填充比，什么是吞吐临界值，或阈值threshold?
   - Default initial capacity = 16
   - load factor  0.75
   - threshold 16*0.75 = 12    在临界值开始扩容： 容量\* 扩容因子
   - treeify_threshold : bucket中链表长度大于该默认值，转换为红黑树： 8
   - min_treeify_capacity  64
   
6. Collection和Collections的区别
   - Collection 是一个集合接口
   - Collections是一个操作集合的工具类
   
7. ArrayList, LinkedList, Vector三者的异同？

   - 同： 三个类都实现了List接口，存储数据的特点相同

   - 不同： 

     - ArrayList作为List接口的主要实现类
     - LinkedList 
     - Vector 作为List接口的古老实现类
     - 
     

##### String常用方法：

1. intern()   返回常量池中的字符串
2. length()
3. boolean isEmpty()
4. String toLowerCase()
5. String toUpperCase()
6. String trim()
7. boolean equals(Object obj)
8. boolean equlasIgnoreCase(String str)
9. String concat(String str)   将指定字符串连接到此字符串的结尾，等价于“+”
10. int compareTo(String str)  比较两个字符串的大小, 字符串排序
11. String substring(int beginIndex)  返回新的字符串，从此字符串的beginIndex开始截取到最后
12. String substring(int beginIndex, int endIndex) 返回新的字符串，包含beginIndex, 不包含endIndex
13. 

StringBuffer

StringBuilder

##### 日期API  

JDK8 时间日期线程更安全

- java.time

LocalDate

LocalTime

LocalDateTime

Instant

DateTimeFormatter

OffsetDate

OffsetDateTime

Period

Duration

ZoneId

ZoneOffset

- java.time.fomat
- java.time.zone

##### Java比较器

Comparable接口:  自然排序 java.lang.Comparable

1. String 实现了Comparable接口，重写了compareTo()方法，给出比较两个对象大小的方法
2. 重写compareTo()的规则：
   - 如果当前对象this大于形参对象，则返回正整数
   - 如果当前对象this小于形参对象，则返回负整数
   - 如果当前对象this等于形参对象，则返回零
3. 对于自定义类，如果需要排序，可以让自定义类实现Comparable接口，重写compareTo(), 在compareTo()方法中指明如何排序

Comparator接口： 定制排序 java.util.Comparator

1. 当元素类型没有实现comparable接口，而又不方便修改代码，或者实现了comparable接口的排序规则不合适当前的操作，那么就可以考虑使用comparator的对象来排序，强行对多个对象进行整体的排序比较
2. 重写compare(Object o1, Object o2)方法，比较o1, o2的大小，如果方法返回正整数，则表示O1大于O2， 如果返回0, 表示相等，如果返回负整数，则表示O1小于O2
3. 可以将Comparator传递给Sort方法，从而允许在排序顺序上实现精确控制
4. 还可以使用Comparator来控制数据结构（如有序Set或者有序映射）的顺序，或者为那些没有自然顺序的对象collection提供排序

##### System类

private构造器，无法实例化，方法都是静态工厂方法，可通过类直接调用

方法： 

- currentTimeMillis()
- exit
- gc()   //请求系统进行垃圾回收，至于是否立即回收，则取决于系统中垃圾回收算法的实现及系统执行情况
- getProperty(String key):  version, home, os.name, os.version, user.name, user.home, user.dir

##### Math类

- abs
- acos,asin,stan,cos,sin,tan 三角函数
- sqrt平方根
- pow(double a, double b)  a的b次幂
- log 自然对数
- exp  e为底指数
- max(double a, double b)
- min(double a, double b)
- random()   返回0.0 到1.0的随机数
- long round(double a)   double型数据a转换为long型
- toDegrees(double angrad)   弧度 ->  角度
- toRadians(double angdeg)    角度 ->  弧度

##### BigInteger 

- Integer类作为int的包装类，能存储的最大整型值为2^31-1 , Long类也是有限的，最大的为2\^63-1, 如果要表示再大的整数，不管基本数据类型还是他们的包装类型都无法运算
- java.math包中的BigInterger可以表示不可变得任意精度的整数，BigInteger提供所有Java的基本整数操作符的对应物，并提供java.lang.Math的所有相关方法。另外BigInteger还提供以下运算： 模算术，GCD计算，质数测试，素数生成，位操作以及一些其他操作
- 构造器BigInteger(String val): 根据字符串构建BigInteger对象
- 常用方法： 
  - add  +
  - divide  /
  - mutilply *
  - subtract  -

##### BigDecimal

- 一般的Float类和Double类可以用来做科学计算或者工程计算，但是在商业计算中， 要求数字精度比较高，故用到java.math.BigDecimal类

- BigDecimal类支持不可变的，任意精度的有符号十进制定点数

- 构造器  

  - public BigDecimal(double val)
  - public BigDecimal(String val)

  常用方法： 

  - add(BigDecimal augend)
  - subtract(BigDecimal subtrahend)
  - multiply(BigDecimal multiplicand)
  - divide(BigDecimal divisor, int scale, int roundingMode)

  

##### IO流

1. 图片视频处理使用字节流  FileInputStream/FileOutputStream
2. 文字处理使用字符流  FileReader/FileWriter
3. 处理流套接在已有流的基础上，BufferedInputStream/BufferedOutputStream/BufferedReader/BufferedWriter
4. 转换流输入字符流：字节流转字符流 InputStreamReader/OutputStreamWriter
   - InputStreamReade 将输入字节的输入流转换为字符的输入流
   - OutputStreamWriter 将一个字符的输出流转换为字节的输出流

##### Java反射机制

- reflection（反射）是被视为动态语言的关键，反射机制允许程序在执行期借助于Reflection API 取得任何类的内部信息，并能直接操作任意对象的内部属性及方法。

- 加载完类之后，在堆内存得方法区中就会产生一个Class类型的对象（一个类只有一个Class对象），这个对象就包括了完整的类的结构信息，我们可以通过这个对象看到类的结构

- 什么时候用反射：  反射的特性： 动态性

  - 编译时不确定new哪个类的对象

  

##### java.lang.Class类的理解

1. 类的加载过程
   - 程序经过javac.exe命令后， 就会生成一个或者是多个字节码文件（.class)结尾。接着我们使用java.exe命令对某个字节码文件进行解释运行，相当于将某个字节码文件加载到内存中，此过程就称为类的加载，加载到内存中的类， 我们就称为运行时类，此运行时类，就作为Class的一个实例。
   - Class的实例就对应着一个运行时类
   - 加载到内存中的运行时类，会缓存一定的时间，在此时间之内，我们可以通过不同的方式来获取运行时类。
2. 哪些类型可以有Class对象
   1. class: 外部类，成员（成员内部类，静态内部类），局部内部类，匿名内部类
   2. interface： 接口
   3. []： 数组
   4. enum
   5. annotation:   注解@interface
   6. primitive type:  基本数据类型
   7. void



##### 运算符

比较运算符：==   !=  >  <  >=  <=  instanceof

赋值运算符:

算术运算符：

位运算符：

逻辑运算符：

三元运算符：



##### Stream ：数据源操作

stream focuses on data calculation  => CPU

collection focuses on data storage => memory

1. create stream
2. operate stream: 
   - filter(Lambda)  过滤  /limit(n)  截断流 /skip(n)  跳过元素   /distinct()  筛选,去重
   - map(fn)/flatMap(fn) 将流中的每个值都换成另一个流，然后把所有的流连成一个流，集合套集合用此方法
   - sorted() 自然排序/ sorted(Comparator com) 自定义排序
   - allMatch(Predicate p)/anyMatch(Predicate p)/noneMatch(Predicate p)/findFirst()/findAny()
   - reduce(T iden, BinaryOperator b) 将流中元素反复结合起来，得到一个值，返回T
   - reduce(BinaryOperator b)  可以将流中元素反复结合起来，得到一个值，返回Optional<T>
   - collect(Collector c)  将流转换为其他形式，接受一个Collector接口的实现，用于给stream中元素做汇总的方法
     - toList  /   toSet /  toCollection / counting / summingInt / averagingInt / summarizingInt /joining....
3. end stream

###### collection:

collection.stream()

collection.parallelStream(): 并行流，并行操作，同时去取数据

###### Array:

arr1= []

Arrays.stream(arr1)

###### Stream:

Stream.of(1,2,3,4)

##### Optional

- Optional<T> 类时一个容器类，可以保存类型T的值，代表这个值存在，或者仅仅保存Null，表示这个值不存在
- 以往用null表示一个值不存在，现在optional可以更好的表达，并且可以避免空指针



##### Lambda

本质是一个对象



##### Exception

```
printStackTrace()方法的意思是：在命令行打印异常信息在程序中出错的位置及原因
```



##### Generic

- 所谓泛型，就是允许在定义类，接口时通过一个标识表示类中某个属性的类型或者是某个方法的返回值及参数类型。这个参数类型将在使用时确定。
- 参数化类型  parameterized type  ， 允许我们在创建集合时再指定结合元素的类型。List<String>表明List只能保存字符串类型的对象
- 

```vbnet
VO:value object
PO:persistant object 持久对象
DTO: data trasfer object
POJO: plain ordinary java object/pure old java object
BO/DO: bussiness object/Domain Object
DAO: data access object
```



#### Jave NIO

NIO能够高效的实现文件读写

JDK7  add NIO2    based on buffer

在以前的IO操作都是

```java
File file = new File("index.html")
```

在Java7中, 可以这样写：

```
Path path = Paths.get("index.html");
```



java.nio.channels.Channel 

- FileChannel: 处理本地文件
- 

##### IP 地址：InetAddress

 唯一的标识 Internet 上的计算机（通信实体） 本地回环地址(hostAddress)：127.0.0.1 主机名(hostName)：localhost IP地址分类方式1：IPV4 和 IPV6

 IPV4：4个字节组成，4个0-255。大概42亿，30亿都在北美，亚洲4亿。2011年初已 经用尽。以点分十进制表示，如192.168.0.1 

IPV6：128位（16个字节），写成8个无符号整数，每个整数用四个十六进制位表示， 数之间用冒号（：）分开，如：3ffe:3201:1401:1280:c8ff:fe4d:db39:1984 

IP地址分类方式2：公网地址(万维网使用)和私有地址(局域网使用)。192.168. 开头的就是私有址址，范围即为192.168.0.0--192.168.255.255，专门为组织机 构内部使



##### 端口号标识正在计算机上运行的进程（程序）

 不同的进程有不同的端口号 

 被规定为一个 16 位的整数 0~65535。

 端口分类： 

​	 公认端口：0~1023。被预先定义的服务通信占用（如：HTTP占用端口 80，FTP占用端口21，Telnet占用端口23） 

​	 注册端口：1024~49151。分配给用户进程或应用程序。（如：Tomcat占 用端口8080，MySQL占用端口3306，Oracle占用端口1521等）。 

​	 动态/私有端口：49152~65535。

##### InetAdress类 

- InetAddress类没有提供公共的构造器，而是提供了如下几个静态方法来获取 InetAddress实例 public static InetAddress getLocalHost() 

  public static InetAddress getByName(String host) 

- InetAddress提供了如下几个常用的方法 

  public String getHostAddress()：返回 IP 地址字符串（以文本表现形式）。 

  public String getHostName()：获取此 IP 地址的主机名 

  public boolean isReachable(int timeout)：测试是否可以达到该地址

  

- 网络通信协议 

  计算机网络中实现通信必须有一些约定，即通信协议，对速率、传输代码、代 码结构、传输控制步骤、出错控制等制定标准。 

- 问题：网络协议太复杂 计算机网络通信涉及内容很多，比如指定源地址和目标地址，加密解密，压缩 解压缩，差错控制，流量控制，路由控制，如何实现如此复杂的网络协议呢？

- 通信协议分层的思想 在制定协议时，把复杂成份分解成一些简单的成份，再将它们复合起来。最常 用的复合方式是层次方式，即同层间可以通信、上一层可以调用下一层，而与 再下一层不发生关系。各层互不影响，利于系统的开发和扩展。

##### TCP/IP协议簇 

- 传输层协议中有两个非常重要的协议： 
  1. 传输控制协议TCP(Transmission Control Protocol)
  2.  用户数据报协议UDP(User Datagram Protocol)。 

TCP/IP 以其两个主要协议：传输控制协议(TCP)和网络互联协议(IP)而得 名，实际上是一组协议，包括多个具有不同功能且互为关联的协议。

 IP(Internet Protocol)协议是网络层的主要协议，支持网间互连的数据通信。

 TCP/IP协议模型从更实用的角度出发，形成了高效的四层体系结构，即 物理链路层、IP层、传输层和应用层。



##### TCP 和 UDP

-  TCP协议：

  - 使用TCP协议前，须先建立TCP连接，形成传输数据通道
  - 传输前，采用“三次握手”方式，点对点通信，是可靠的
  - TCP协议进行通信的两个应用进程：客户端、服务端。
  - 在连接中可进行大数据量的传输
  - 传输完毕，需释放已建立的连接，效率低

- UDP协议：

  - 将数据、源、目的封装成数据包，不需要建立连接
  - 每个数据报的大小限制在64K内
  - 发送不管对方是否准备好，接收方收到也不确认，故是不可靠的
  - 可以广播发送
  - 发送数据结束时无需释放资源，开销小，速度快

  #####  Socket

  - 利用套接字(Socket)开发网络应用程序早已被广泛的采用，以至于成为事实 上的标准。
  - 网络上具有唯一标识的IP地址和端口号组合在一起才能构成唯一能识别的标 识符套接字。
  - 通信的两端都要有Socket，是两台机器间通信的端点。 
  - 网络通信其实就是Socket间的通信。
  - Socket允许程序把网络连接当成一个流，数据在两个Socket间通过IO传输。 
  - 一般主动发起通信的应用程序属客户端，等待通信请求的为服务端。 
  - Socket分类： 
    - 流套接字（stream socket）：使用TCP提供可依赖的字节流服务
    - 数据报套接字（datagram socket）：使用UDP提供“尽力而为”的数据报服务 S

###### Socket类的常用构造器： 

- public Socket(InetAddress address,int port)创建一个流套接字并将其连接到指定 IP 地址的指定端口号。 
- public Socket(String host,int port)创建一个流套接字并将其连接到指定主机上的指定端口号。 

###### Socket类的常用方法：

- public InputStream getInputStream()返回此套接字的输入流。可以用于接收网络消息 
-  public OutputStream getOutputStream()返回此套接字的输出流。可以用于发送网络消息 
-  public InetAddress getInetAddress()此套接字连接到的远程 IP 地址；如果套接字是未连接的，则返回 null。 
- public InetAddress getLocalAddress()获取套接字绑定的本地地址。 即本端的IP地址 
- public int getPort()此套接字连接到的远程端口号；如果尚未连接套接字，则返回 0。
- public int getLocalPort()返回此套接字绑定到的本地端口。 如果尚未绑定套接字，则返回 -1。即本端的 端口号。 
- public void close()关闭此套接字。套接字被关闭后，便不可在以后的网络连接中使用（即无法重新连接 或重新绑定）。需要创建新的套接字对象。 关闭此套接字也将会关闭该套接字的 InputStream 和 OutputStream。 
- public void shutdownInput()如果在套接字上调用 shutdownInput() 后从套接字输入流读取内容，则流将 返回 EOF（文件结束符）。 即不能在从此套接字的输入流中接收任何数据。 
- public void shutdownOutput()禁用此套接字的输出流。对于 TCP 套接字，任何以前写入的数据都将被发 送，并且后跟 TCP 的正常连接终止序列。 如果在套接字上调用 shutdownOutput() 后写入套接字输出流， 则该流将抛出 IOException。 即不能通过此套接字的输出流发送任何数据。

##### 基于Socket的TCP编程 

###### 客户端Socket的工作过程包含以下四个基本的步骤：

-  创建 Socket：根据指定服务端的 IP 地址或端口号构造 Socket 类对象。若服务器端 响应，则建立客户端到服务器的通信线路。若连接失败，会出现异常。
-  打开连接到 Socket 的输入/出流： 使用 getInputStream()方法获得输入流，使用 getOutputStream()方法获得输出流，进行数据传输 
- 按照一定的协议对 Socket 进行读/写操作：通过输入流读取服务器放入线路的信息 （但不能读取自己放入线路的信息），通过输出流将信息写入线程。
- 关闭 Socket：断开客户端到服务器的连接，释放线路

###### 客户端创建Socket对象 

- 客户端程序可以使用Socket类创建对象，创建的同时会自动向服务器方发起连 接。Socket的构造器是： 
  - Socket(String host,int port)throws UnknownHostException,IOException：向服务器(域名是 host。端口号为port)发起TCP连接，若成功，则创建Socket对象，否则抛出异常。
  - Socket(InetAddress address,int port)throws IOException：根据InetAddress对象所表示的 IP地址以及端口号port发起连接

- 客户端建立socketAtClient对象的过程就是向服务器发出套接字连接请求

###### 服务器程序的工作过程包含以下四个基本的步骤： 

- 调用 ServerSocket(int port) ：创建一个服务器端套接字，并绑定到指定端口 上。用于监听客户端的请求。
- 调用 accept()：监听连接请求，如果客户端请求连接，则接受连接，返回通信 套接字对象。
- 调用 该Socket类对象的 getOutputStream() 和 getInputStream ()：获取输出 流和输入流，开始网络数据的发送和接收。 
- 关闭ServerSocket和Socket对象：客户端访问结束，关闭通信套接字。

##### 服务器建立 ServerSocket 对象 

- ServerSocket 对象负责等待客户端请求建立套接字连接，类似邮局某个窗口 中的业务员。也就是说，服务器必须事先建立一个等待客户请求建立套接字 连接的ServerSocket对象。 

- 所谓“接收”客户的套接字请求，就是accept()方法会返回一个 Socket 对象

##### UDP网络通信 

- 类 DatagramSocket 和 DatagramPacket 实现了基于 UDP 协议网络程序。
- UDP数据报通过数据报套接字 DatagramSocket 发送和接收，系统不保证 UDP数据报一定能够安全送到目的地，也不能确定什么时候可以抵达。 
- DatagramPacket 对象封装了UDP数据报，在数据报中包含了发送端的IP 地址和端口号以及接收端的IP地址和端口号。
- UDP协议中每个数据报都给出了完整的地址信息，因此无须建立发送方和 接收方的连接。如同发快递包裹一样。

##### URL类 

- URL(Uniform Resource Locator)：统一资源定位符，它表示 Internet 上某一 资源的地址。 
- 它是一种具体的URI，即URL可以用来标识一个资源，而且还指明了如何locate 这个资源。
- 通过 URL 我们可以访问 Internet 上的各种网络资源，比如最常见的 www，ftp  站点。浏览器通过解析给定的 URL 可以在网络上查找相应的文件或其他资源。 
- URL的基本结构由5部分组成： <传输协议>://<主机名>:<端口号>/<文件名>#片段名?参数列表
- 为了表示URL，java.net 中实现了类 URL。
- URL类的构造器都声明抛出非运行时异常，必须要对这一异常进行处理，通 常是用 try-catch 语句进行捕获。
- 一个URL对象生成后，其属性是不能被改变的，但可以通过它给定的方法来获取这些属性

##### 针对HTTP协议的URLConnection类

- URL的方法 openStream()：能从网络上读取数据

- 若希望输出数据，例如向服务器端的 CGI （公共网关接口-Common Gateway  Interface-的简称，是用户浏览器和服务器端的应用程序进行连接的接口）程序发送一 些数据，则必须先与URL建立连接，然后才能对其进行读写，此时需要使用 URLConnection 。

- URLConnection：表示到URL所引用的远程对象的连接。当与一个URL建立连接时， 首先要在一个 URL 对象上通过方法 openConnection() 生成对应的 URLConnection 对象。如果连接过程失败，将产生IOException.  

  - URL netchinaren = new URL ("http://www.atguigu.com/index.shtml");  

  - URLConnectonn u = netchinaren.openConnection( ); 

- 通过URLConnection对象获取的输入流和输出流，即可以与现有的CGI 程序进行交互。

##### URI、URL和URN的区别

URI，是uniform resource identifier，统一资源标识符，用来唯一的标识一个 资源。而URL是uniform resource locator，统一资源定位符，它是一种具体 的URI，即URL可以用来标识一个资源，而且还指明了如何locate这个资源。 而URN，uniform resource name，统一资源命名，是通过名字来标识资源， 比如mailto:java-net@java.sun.com。也就是说，URI是以一种抽象的，高层 次概念定义统一资源标识，而URL和URN则是具体的资源标识的方式。URL 和URN都是一种URI。

在Java的URI中，一个URI实例可以代表绝对的，也可以是相对的，只要它符 合URI的语法规则。而URL类则 不仅符合语义，还包含了定位该资源的信息， 因此它不能是相对的。


## Java  Basic

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

##### HashSet

作为set接口的主要实现类，线程不安全，可以存储null

存储的数据再底层数组中并非按照数组索引的顺序添加，而是根据数据的Hash值决定的

底层数组

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

     



##### String

StringBuffer

StringBuilder

##### 日期API

LocalDate

LocalTime

LocalDateTime

Instant

DateTimeFormatter

##### Java比较器

Comparable接口

Comparator接口

##### System类

##### Math类

##### BigInteger & BigDecimal








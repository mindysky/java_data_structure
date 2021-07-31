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
11. 集合对象每次调用iterator（）方法都得到一个iterator实例



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

有序的map，保证按照添加的key-value对进行排序，实现排序遍历

此时考虑key的自然排序或者定制排序

底层使用红黑树

##### Map常用方法

1. 添加 put(key,value)  当key相同，会替换
2. 添加所有putAll(Map m)
3. 删除remove(Object key)
4. 清空clear()  清空所有

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








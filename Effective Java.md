# Effective Java

## Item 15 minimize the accessibility of classes and members

信息隐藏（information hiding）或者封装（encapsulation）:

hide its internal data and other implementation details from other components

components then communicate only through their APIs and are oblivious to each others' inner workings.

一个设计良好的组件隐藏了它所有的实现细节，把它的API与它的实现清晰地隔离开来，然后组件之间通过它们各自的API进行交互，彼此之间不知道别的组件内部是如何工作的

**Advantage of information hiding**

1. Decouples the components, allow them to be developed, tested,   optimized,used, understood and modified in isolation.
2. Eases the burden of maintainance as components can be understood more quickly and debugged or replaced without harming other components
3. Enable effective performance tuning
4. Increase  software  reuse
5. Decrease the risk in building large systems

**Access control**

* Make each class or member as inaccessible as possible
* For top-level classes and interfaces  only 2 possible access level: package-private & public
* If a class or interface can be made package-private , it should be.
* If a top-level class or interface used by only one class, consider making the class a private static nested class

For members: fields, methods,nested class, nested interface

**Access modifiers:**

- **私有的（private）**— 成员只能被其声明的顶层类访问。
- **包级私有（package-private）**—成员可以被所声明的类所在包的类访问。从技术上，被称为“默认”的访问级别，也就是你没有指定访问修饰符时的默认访问级别。（接口成员除外，它们的默认访问级别是公有的）
- **受保护的（protected）**—成员可以被其声明的类的子类所访问，还可以被所声明的类所在包的类访问。
- **公有的（public）**—成员在任何地方都可以被访问。

**Instance fields of public classes should rarely be public**

公有类的实体域（非静态域）一般情况都不应该是公有的

**Classes with public mutable fields are not generally thread-safe**

**it is wrong for a class to have a public static final array field, or an accesort that return such a field**

client will be able to modify th contents of the array,  security holes, 2 ways to solve it:

1. make the array private & add a public immutable list:

   ```java
   private static final Thing[] PRIVATE = {....}
   public static final List<Thing> VALUSES  = Collections.unmodifiableList(Arrays.asList(PRIVATE ))

2. make the array private and add a public mehod that return a copy of a private array:

   ```
   private static final Thing[] PRIVATE = {....}
   public static final Thing[] values(){
   	return PRIVATE.clone();
   }
   ```

Java9 

module  => a group of packages

module-info  java

 

## Item 16 In public classes, use accessor methods ,not public fields

not offer benefits of encapsulation

accessor  methods: getters

mutator methods: setters

if a class is accessible outside its package, provide accessor methods to prevent the flexibility to change the class's internal 

representation

if a class is package-private or is a private nested class, there is nothing inherently wrong with exposing its data fields

## Item17 Minimize mutability

Five rules:

1. Don’t provide methods that modify the object’s state
2. Ensure that the class can’t be extended.  making the class final
3. Make all fields final
4. Make all fields private
5. Ensure exclusive access to any mutable components. 

**Advantage**:

1. Immutable objects are inherently thread-safe; they require no synchronization.
2.  Immutable objects can be shared freely.
3. Not only can you share immutable objects, but they can share their internals.
4. Immutable objects make great building blocks for other objects.
5. Immutable objects provide failure atomicity for free.  失败原子机制，不可能出现临时的不一致性

**Disadvantage**：

​	- Immutable classes require a separate object for each distinct value.

**Summary:**

1. Classes should be immutable unless there’s a very good reason to make them mutable. 
2. If a class cannot be made immutable, limit its mutability as much as possible.
3. Declare every field private final unless there’s a good reason to do otherwise.
4. Constructors should create fully initialized objects with all of their invariants established














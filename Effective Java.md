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

- **私有的（private）**— The member is accessible only from the top-level class where it is declared.
- **包级私有（package-private）**—The member is accessible from any class in the package where it is declared. Technically known as default access, this is the access level you get if no access modifier is specified (except for interface members, which are public by default).
- **受保护的（protected）**—The member is accessible from subclasses of the class where it is declared (subject to a few restrictions [JLS, 6.6.2]) and from any class in the package where it is declared.
- **公有的（public）**—The member is accessible from anywhere.

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

If a class is accessible outside its package, provide accessor methods to prevent the flexibility to change the class's internal 

representation.

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
2. Immutable objects can be shared freely.
3. Not only can you share immutable objects, but they can share their internals.
4. Immutable objects make great building blocks for other objects.
5. Immutable objects provide failure atomicity for free.  失败原子机制，不可能出现临时的不一致性

**Disadvantage**：

 - Immutable classes require a separate object for each distinct value.

   reason: create object can be costly, especial larger

   mutable companion class: StringBuilder

   和 String 类不同的是，StringBuffer 和 StringBuilder 类的对象能够被多次的修改，并且不产生新的未使用对象。

   字符串连接: 方便和直接的方式是通过"+"符号来实现，但是这种方式达到目的的效率比较低，且每执行一次都会创建一个String对象，即耗时，又浪费空间, 使用StringBuilder类就可以避免这种问题的发生。

**Summary:**

1. Classes should be immutable unless there’s a very good reason to make them mutable. 
2. If a class cannot be made immutable, limit its mutability as much as possible.
3. Declare every field private final unless there’s a good reason to do otherwise.
4. Constructors should create fully initialized objects with all of their invariants established

## Item 20: Prefer interfaces to abstract classes（接口优于抽象类）



## Item 22: Use interfaces only to define types

disadvantage:

1. Implementing a constant interface causes this implementation detail to leak into the class’s exported API
2.  it represents a commitment: if in a future release the class is modified so that it no longer needs to use the constants, it still must implement the interface to ensure binary compatibility.
3. If a nonfinal class implements a constant interface, all of its subclasses will have their namespaces polluted by the constants in the interface.

reasonable choices:

1. If the constants are strongly tied to an existing class or interface, you should add them to the class or interface

2. export the constants with a noninstantiable utility class

3. ```
   // Use of static import to avoid qualifying constants
   import static com.effectivejava.science.PhysicalConstants.*;
   ```

In summary, interfaces should be used only to define types. They should not be used merely to export constants.

## Item 23: Prefer class hierarchies to tagged classes（类层次结构优于带标签的类）

Disadvantage:

1. Tagged classes are verbose, error-prone, and inefficient.
2. A tagged class is just a pallid imitation of a class hierarchy.

```java
// Class hierarchy replacement for a tagged class
abstract class Figure {
    abstract double area();
}

class Circle extends Figure {
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

class Rectangle extends Figure {
    final double length;
    final double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}
```

advantage of class hierarchies:

1. class hierarchy corrects every shortcoming of tagged classes noted previously
2.  they can be made to reflect natural hierarchical relationships among types, allowing for increased flexibility and better compile-time type checking

If you’re tempted to write a class with an explicit tag field, think about whether the tag could be eliminated and the class replaced by a hierarchy. When you encounter an existing class with a tag field, consider refactoring it into a hierarchy.

## Item 24: Favor static member classes over nonstatic（静态成员类优于非静态成员类）

There are four kinds of nested classes: static member classes, nonstatic member classes, anonymous classes, and local classes.

##### static member classes： 

One common use of a static member class is as a public helper class, useful only in conjunction with its outer class.

 If an instance of a nested class can exist in isolation from an instance of its enclosing class, then the nested class must be a static member class.

A common use of private static member classes is to represent components of the object represented by their enclosing class.

##### nonstatic member classes：

One common use of a nonstatic member class is to define an Adapter.

Each instance of a nonstatic member class is implicitly associated with an enclosing instance of its containing class.

The association between a nonstatic member class instance and its enclosing instance is established when the member class instance is created and cannot be modified thereafter.

##### anonymous classes：

1. You can’t instantiate them except at the point they’re declared
2. You can’t perform instanceof tests or do anything else that requires you to name the class
3. You can’t declare an anonymous class to implement multiple interfaces or to extend a class and implement an interface at the same time

anonymous classes were the preferred means of creating small function objects and process objects on the fly, but lambdas are now preferred.

Another common use of anonymous classes is in the implementation of static factory methods

##### local classes： 

**If you declare a member class that does not require access to an enclosing instance, always put the static modifier in its declaration**.

When to use:

If a nested class needs to be visible outside of a single method or is too long to fit comfortably inside a method, use a member class.

 If each instance of a member class needs a reference to its enclosing instance, make it nonstatic;otherwise, make it static.

Assuming the class belongs inside a method, if you need to create instances from only one location and there is a preexisting type that characterizes the class, make it an anonymous class; otherwise, make it a local class.

## Item 25: Limit source files to a single top-level class（源文件仅限有单个顶层类）

disadvantage:

1. defining multiple top-level classes in a source file makes it possible to provide multiple definitions for a class,Which definition gets used is affected by the order in which the source files are passed to the compiler

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }
}
//Utensil
// Two classes defined in one file. Don't ever do this!
class Utensil {
    static final String NAME = "pan";
}

class Dessert {
    static final String NAME = "cake";
}

//Dessert
// Two classes defined in one file. Don't ever do this!
class Utensil {
    static final String NAME = "pot";
}

class Dessert {
    static final String NAME = "pie";
}

```



Fixing the problem is as simple as splitting the top-level classes (Utensil and Dessert, in the case of our example) into separate source files.

If you are tempted to put multiple top-level classes into a single source file, consider using static member classes (Item 24) as an alternative to splitting the classes into separate source files. 

```java
// Static member classes instead of multiple top-level classes
public class Test {

    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }

    private static class Utensil {
        static final String NAME = "pan";
    }

    private static class Dessert {
        static final String NAME = "cake";
    }
}
```

Never put multiple top-level classes or interfaces in a single source file.





## Item 26: Don’t use raw types（不要使用原始类型）

**If you use raw types, you lose all the safety and expressiveness benefits of generics**

the raw type List and the parameterized type `List<Object>`

`Set<E>` is `Set<?>` (read “set of some type”)

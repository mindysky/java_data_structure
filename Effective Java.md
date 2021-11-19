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
   ```

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

has access to all the enclosing class's members even those declared private.

private static :  accessible within the enclosing class

One common use of a static member class is as a public helper class, useful only in conjunction with its outer class.

 If an instance of a nested class can exist in isolation from an instance of its enclosing class, then the nested class must be a static member class.

A common use of private static member classes is to represent components of the object represented by their enclosing class.

example: Map's entry  

```java
Outer.Inner inner=new Outer.Inner（);
```

example:  calculator   operation enum 

​                   Builder

##### nonstatic member classes：

can not exist isolation:  must associated with enclosing instance

One common use of a nonstatic member class is to define an Adapter.

example:  Map's  entrySet  keySet   values method   use nonstatic class to implement their iterators

Each instance of a nonstatic member class is implicitly associated with an enclosing instance of its containing class.

The association between a nonstatic member class instance and its enclosing instance is established when the member class instance is created and cannot be modified thereafter.

takes up space and time

```java
Outer outer=new Outer（);

Outer.Inner inner=outer.new Inner（);
```

retain in memory would be eligible  for GC

memory leak

##### anonymous classes：

1. You can’t instantiate them except at the point they’re declared
2. You can’t perform instanceof tests or do anything else that requires you to name the class
3. You can’t declare an anonymous class to implement multiple interfaces or to extend a class and implement an interface at the same time

anonymous classes were the preferred means of creating small function objects and process objects on the fly, but lambdas are now preferred.

Another common use of anonymous classes is in the implementation of static factory methods

should be short or will harm readability

```java
Runnable run1 = new Runnable(){
    @Override
    void method(){
        print('run')
    }
}

Runnable run2 = ()->print("run");
    
```

##### local classes：

can be declared anywhere a local variables can declared

obey the same scope rules

```java
method(){
    class A(){
    }
} 
```

Only can use in current method

can not use access modifer

can not declare static member in a local class

can access all the outer class member



**If you declare a member class that does not require access to an enclosing instance, always put the static modifier in its declaration**.

When to use:

If a nested class needs to be visible outside of a single method or is too long to fit comfortably inside a method, use a member class.

 If each instance of a member class needs a reference to its enclosing instance, make it nonstatic;otherwise, make it static.

Assuming the class belongs inside a method, if you need to create instances from only one location and there is a preexisting type that characterizes the class, make it an anonymous class; otherwise, make it a local class.

## Item 25: Limit source files to a single top-level class（源文件仅限有单个顶层类）

disadvantage:

1. defining multiple top-level classes in a source file makes it possible to provide multiple definitions for a class,Which definition gets used is affected by the order in which the source files are passed to the compiler.
2. effect factor:  which command or commands used, in which order the command be seen by compliler, which complier used
3. unpredictable behavior

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

`List<String>` (read “list of string”) 

String is the actual type parameter corresponding to the formal type parameter E.

the raw type corresponding to `List<E>` is List

it pays to discover errors as soon as possible after they are made, ideally at compile time. In this case, you don’t discover the error until runtime, long after it has happened, and in code that may be distant from the code containing the error. Once you see the ClassCastException, you have to search through the codebase looking for the method invocation that put the coin into the stamp collection

**you lose type safety if you use a raw type such as List, but not if you use a parameterized type such as List<Object>.**

 a few minor exceptions to the rule that you should not use raw types: 

**You must use raw types in class literals.**

 List.class, String[].class, and int.class

wildcard type `Set<?>`

using raw types can lead to exceptions at runtime, so don’t use them

| Term                    | Example                            | Item                                                         |
| ----------------------- | ---------------------------------- | ------------------------------------------------------------ |
| Parameterized type      | `List<String>`                     | [Item-26](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-26-Do-not-use-raw-types.md) |
| Actual type parameter   | `String`                           | [Item-26](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-26-Do-not-use-raw-types.md) |
| Generic type            | `List<E>`                          | [Item-26](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-26-Do-not-use-raw-types.md), [Item-29](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-29-Favor-generic-types.md) |
| Formal type parameter   | `E`                                | [Item-26](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-26-Do-not-use-raw-types.md) |
| Unbounded wildcard type | `List<?>`                          | [Item-26](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-26-Do-not-use-raw-types.md) |
| Raw type                | `List`                             | [Item-26](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-26-Do-not-use-raw-types.md) |
| Bounded type parameter  | `<E extends Number>`               | [Item-29](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-29-Favor-generic-types.md) |
| Recursive type bound    | `<T extends Comparable<T>>`        | [Item-30](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-30-Favor-generic-methods.md) |
| Bounded wildcard type   | `List<? extends Number>`           | [Item-31](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-31-Use-bounded-wildcards-to-increase-API-flexibility.md) |
| Generic method          | `static <E> List<E> asList(E[] a)` | [Item-30](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-30-Favor-generic-methods.md) |
| Type token              | `String.class`                     | [Item-33](https://github.com/clxering/Effective-Java-3rd-edition-Chinese-English-bilingual/blob/dev/Chapter-5/Chapter-5-Item-33-Consider-typesafe-heterogeneous-containers.md) |

### Item 27: Eliminate unchecked warnings（消除 unchecked 警告）

unchecked cast warnings, 

unchecked method invocation warnings, 

unchecked parameterized vararg type warnings, 

and unchecked conversion warnings

```java
Set<Lark> exaltation = new HashSet();

Venery.java:4: warning: [unchecked] unchecked conversion
Set<Lark> exaltation = new HashSet();
^ required: Set<Lark>
found: HashSet


diamond operator  (<>)
Set<Lark> exaltation = new HashSet<>();

```

**Eliminate every unchecked warning that you can**

**If you can’t eliminate a warning, but you can prove that the code that provoked the warning is typesafe, then (and only then) suppress the warning with an @SuppressWarnings("unchecked") annotation.**

 **Always use the SuppressWarnings annotation on the smallest scope possible.**

```java
public <T> T[] toArray(T[] a) {
    if (a.length < size)
        return (T[]) Arrays.copyOf(elements, size, a.getClass());
    System.arraycopy(elements, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}

ArrayList.java:305: warning: [unchecked] unchecked cast
return (T[]) Arrays.copyOf(elements, size, a.getClass());
^ required: T[]
found: Object[]


// Adding local variable to reduce scope of @SuppressWarnings
public <T> T[] toArray(T[] a) {
    if (a.length < size) {
        // This cast is correct because the array we're creating
        // is of the same type as the one passed in, which is T[].
        @SuppressWarnings("unchecked") T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
        return result;
    }
    System.arraycopy(elements, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}

```

**Every time you use a @SuppressWarnings("unchecked") annotation, add a comment saying why it is safe to do so.** 

### Item 28: Prefer lists to arrays（list 优于数组）

1. arrays are covariant  

   ```java
   // Fails at runtime!
   Object[] objectArray = new Long[1];
   objectArray[0] = "I don't fit in"; // Throws ArrayStoreException
   
   // Won't compile!
   List<Object> ol = new ArrayList<Long>(); // Incompatible types
   ol.add("I don't fit in");
   
   //with an array you find out that you’ve made a mistake at runtime; with a list, you find out at compile time
   ```

2. arrays are reified  

   ```java
   
   ```
```
   

   
   ```java
   / Chooser - a class badly in need of generics!
   public class Chooser {
     private final Object[] choiceArray;
   
     public Chooser(Collection choices) {
       choiceArray = choices.toArray();
   }
   
     public Object choose() {
       Random rnd = ThreadLocalRandom.current();
       return choiceArray[rnd.nextInt(choiceArray.length)];
     }
   }
   
   
   // A first cut at making Chooser generic - won't compile
   public class Chooser<T> {
     private final T[] choiceArray;
   
     public Chooser(Collection<T> choices) {
       choiceArray = choices.toArray();
     }
   
     // choose method unchanged
   }
   
   Chooser.java:9: error: incompatible types: Object[] cannot be converted to T[]
   choiceArray = choices.toArray();
   ^ where T is a type-variable:
   T extends Object declared in class Chooser
       
   
   // List-based Chooser - typesafe
   public class Chooser<T> {
       private final List<T> choiceList;
   
       public Chooser(Collection<T> choices) {
           choiceList = new ArrayList<>(choices);
       }
   
       public T choose() {
           Random rnd = ThreadLocalRandom.current();
           return choiceList.get(rnd.nextInt(choiceList.size()));
       }
   } 
       
```

   

In summary, arrays and generics have very different type rules. Arrays are covariant and reified; generics are invariant and erased. As a consequence, arrays provide runtime type safety but not compile-time type safety, and vice versa for generics. 

If you find yourself mixing them and getting compile-time errors or warnings, your first impulse should be to replace the arrays with lists.

### Item 29: Favor generic types（优先使用泛型）

In summary, generic types are safer and easier to use than types that require casts in client code. When you design new types, make sure that they can be used without such casts.

### Item 30: Favor generic methods（优先使用泛型方法）

```java
 The type parameter list, which declares the type parameters, goes between a method’s modifiers and its return type.
// The type parameter list is <E>
// The return type is Set<E>
// Generic method
public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
    Set<E> result = new HashSet<>(s1);
    result.addAll(s2);
    return result;
}
     
```

generic singleton factory:

function objects :   Collections.reverseOrder

collections:   Collections.emptySet

```java
The type bound <E extends Comparable<E>> may be read as “any type E that can be compared to itself"
// Using a recursive type bound to express mutual comparability
public static <E extends Comparable<E>> E max(Collection<E> c);
```

In summary, generic methods, like generic types, are safer and easier to use than methods requiring their clients to put explicit casts on input parameters and return values.

### Item 31: Use bounded wildcards to increase API flexibility（使用有界通配符增加 API 的灵活性）

unbounded type parameter: <E>

unbounded wildcard:<?>

bounded wildcard type:  有界通配符类型

Iterable of some subtype of E:   Iterable<? extends E>.

collection of some supertype of E:  Collection<? super E>

For maximum flexibility, use wildcard types on input parameters that represent producers or consumers.

**PECS stands for producer-extends, consumer-super.**

**Do not use bounded wildcard types as return types**

 Comparables are always consumers, so you should generally **use `Comparable<? super T>` in preference to `Comparable<T>`.** The same is true of comparators; therefore, you should generally **use `Comparator<? super T>` in preference to `Comparator<T>`.**

**if a type parameter appears only once in a method declaration, replace it with a wildcard.** 

all comparables and comparators are consumers.

### Item 32: Combine generics and varargs judiciously（明智地合用泛型和可变参数）

Heap pollution occurs when a variable of a parameterized type refers to an object that is not of that type

**it is unsafe to store a value in a generic varargs array parameter.**

methods with varargs parameters of generic or parameterized types can be very useful in practice, so the language designers opted to live with this inconsistency

```java
Arrays.asList(T... a)、
Collections.addAll(Collection<? super T> c, T... elements)
EnumSet.of(E first, E... rest)
```

**the SafeVarargs annotation constitutes a promise by the author of a method that it is typesafe**

Before Java 7 :     @SuppressWarnings("unchecked") 

After Java7:    @SafeVarargs

```java
static <T> T[] pickTwo(T a, T b, T c) {
  switch(ThreadLocalRandom.current().nextInt(3)) {
    case 0: return toArray(a, b);
    case 1: return toArray(a, c);
    case 2: return toArray(b, c);
  }
  throw new AssertionError(); // Can't get here
} 

String[] attributes = pickTwo("Good", "Fast", "Cheap");

//ClassCastException  The cast fails, because Object[] is not a subtype of String[]
```

```
static <T> List<T> pickTwo(T a, T b, T c) {
  switch(rnd.nextInt(3)) {
    case 0: return List.of(a, b);
    case 1: return List.of(a, c);
    case 2: return List.of(b, c);
  }
  throw new AssertionError();
}

List<String> attributes = pickTwo("Good", "Fast", "Cheap");
```



##### Use @SafeVarargs on every method with a varargs parameter of a generic or parameterized type

```
// Safe method with a generic varargs parameter
@SafeVarargs
static <T> List<T> flatten(List<? extends T>... lists) {
  List<T> result = new ArrayList<>();
  for (List<? extends T> list : lists)
    result.addAll(list);
  return result;
}
```



带有泛型或参数化类型

1. it doesn’t store anything in the varargs parameter array
2. it doesn’t make the array (or a clone) visible to untrusted code. If either of these prohibitions is violated, fix it.

```java
// List as a typesafe alternative to a generic varargs parameter
static <T> List<T> flatten(List<List<? extends T>> lists) {
  List<T> result = new ArrayList<>();
  for (List<? extends T> list : lists)
    result.addAll(list);
  return result;
}
```

Though generic varargs parameters are not typesafe, they are legal. If you choose to write a method with a generic (or parameterized) varargs parameter, first ensure that the method is typesafe, and then annotate it with @SafeVarargs so it is not unpleasant to use.

### Item 33: Consider typesafe heterogeneous containers（考虑类型安全的异构容器）

Parameterized  container 

collections:   `Set<E>` and `Map<K,V>`

single-element containers:  `ThreadLocal<T> `    `AtomicReference<T>`

A Set has a single type parameter, representing its element type; a Map has two, representing its key and value types; and so forth.

### Item 34: Use enums instead of int constants（用枚举类型代替 int 常量）

Before enum types were added to the language, a common pattern for representing enumerated types was to declare a group of named int constants, one for each member of the type.

Enum pattern: provide nothing in the way of type safety and little in the way of expressive power.

String constants:

int constants: 

##### enum type

instance-controlled  实例受控的类

Enums provide compile-time type safety

constant values are not compiled into the clients as they are in the int enum patterns

```java

public enum Apple { FUJI, PIPPIN, GRANNY_SMITH }
public enum Orange { NAVEL, TEMPLE, BLOOD }
```

##### rich enum type

**To associate data with enum constants, declare instance fields and write a constructor that takes the data and stores it in the fields**

Enums are by their nature immutable, so all fields should be final 

```java
// Enum type with data and behavior
public enum Planet {
    MERCURY(3.302e+23, 2.439e6),
    VENUS (4.869e+24, 6.052e6),
    EARTH (5.975e+24, 6.378e6),
    MARS (6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN (5.685e+26, 6.027e7),
    URANUS (8.683e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.477e7);

    private final double mass; // In kilograms
    private final double radius; // In meters
    private final double surfaceGravity; // In m / s^2

    // Universal gravitational constant in m^3 / kg s^2
    private static final double G = 6.67300E-11;

    // Constructor
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double mass() { return mass; }
    public double radius() { return radius; }
    public double surfaceGravity() { return surfaceGravity; }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity; // F = ma
    }
}
```

Constant-specific method implementations特定常量方法实现

```java
// Enum type with constant-specific method implementations
public enum Operation {
    PLUS {public double apply(double x, double y){return x + y;}},
    MINUS {public double apply(double x, double y){return x - y;}},
    TIMES {public double apply(double x, double y){return x * y;}},
    DIVIDE{public double apply(double x, double y){return x / y;}};
    public abstract double apply(double x, double y);
}
```

```java
// Implementing a fromString method on an enum type
private static final Map<String, Operation> stringToEnum =Stream.of(values()).collect(toMap(Object::toString, e -> e));

// Returns Operation for string, if any
public static Optional<Operation> fromString(String symbol) {
    return Optional.ofNullable(stringToEnum.get(symbol));
}
```

the strategy enum pattern

 A minor performance disadvantage of enums is that there is a space and time cost to load and initialize enum types, but it is unlikely to be noticeable in practice.

**Use enums any time you need a set of constants whose members are known at compile time.** 

**It is not necessary that the set of constants in an enum type stay fixed for all time**

Enums are more readable, safer, and more powerful. Many enums require no explicit constructors or members, but others benefit from associating data with each constant and providing methods whose behavior is affected by this data

### Item 35: Use instance fields instead of ordinals（使用实例字段替代序数）

**Never derive a value associated with an enum from its ordinal; store it in an instance field instead:**

```java
// Abuse of ordinal to derive an associated value - DON'T DO THIS
public enum Ensemble {
    SOLO, DUET, TRIO, QUARTET, QUINTET,SEXTET, SEPTET, OCTET, NONET, DECTET;

    public int numberOfMusicians() { return ordinal() + 1; }
}



public enum Ensemble {
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),NONET(9), DECTET(10),TRIPLE_QUARTET(12);

    private final int numberOfMusicians;

    Ensemble(int size) { this.numberOfMusicians = size; }

    public int numberOfMusicians() { return numberOfMusicians; }
}
```

### Item 36: Use EnumSet instead of bit fields（用 EnumSet 替代位字段）

bit fields：

disadvantages :

have all the disadvantages of int enum constants and more

It is even harder to interpret a bit field than a simple int enum constant when it is printed as a number

no easy way to iterate over all of the elements represented by a bit field

```java
// Bit field enumeration constants - OBSOLETE!
public class Text {
    public static final int STYLE_BOLD = 1 << 0; // 1
    public static final int STYLE_ITALIC = 1 << 1; // 2
    public static final int STYLE_UNDERLINE = 1 << 2; // 4
    public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8
    // Parameter is bitwise OR of zero or more STYLE_ constants
    public void applyStyles(int styles) { ... }
}


text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
```



EnumSet class:  

This class implements the Set interface, providing all of the richness, type safety, and interoperability you get with any other Set implementation

```java
// EnumSet - a modern replacement for bit fields
public class Text {
    public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
    // Any Set could be passed in, but EnumSet is clearly best
    public void applyStyles(Set<Style> styles) { ... }
}
//The EnumSet class provides a rich set of static factories for easy set creation, one of which is illustrated in this code:
text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
```

This allows for the possibility of an unusual client to pass in some other Set implementation.

 **just because an enumerated type will be used in sets, there is no reason to represent it with bit fields.**

disadvantage of EnumSet:    not possible to create an immutable EnumSet

wrap an EnumSet with Collections.unmodifiableSet

### Item 37: Use EnumMap instead of ordinal indexing（使用 EnumMap 替换序数索引）

java.util.EnumMap

```java
// Using an EnumMap to associate data with an enum
Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle =new EnumMap<>(Plant.LifeCycle.class);

for (Plant.LifeCycle lc : Plant.LifeCycle.values())
    plantsByLifeCycle.put(lc, new HashSet<>());

for (Plant p : garden)
    plantsByLifeCycle.get(p.lifeCycle).add(p);

System.out.println(plantsByLifeCycle);

// Using a stream and an EnumMap to associate data with an enum
System.out.println(
    Arrays.stream(garden).collect(groupingBy(p -> p.lifeCycle,() -> new EnumMap<>(LifeCycle.class), toSet()))
)


// Naive stream-based approach - unlikely to produce an EnumMap!
System.out.println(Arrays.stream(garden).collect(groupingBy(p -> p.lifeCycle)));
```

The reason that EnumMap is comparable in speed to an ordinal-indexed array is that EnumMap uses such an array internally, but it hides this implementation detail from the programmer, combining the richness and type safety of a Map with the speed of an array.



ordinal indexing:

the compiler has no way of knowing the relationship between ordinals and array indices

If you make a mistake in the transition table or forget to update it when you modify the Phase or Phase.Transition enum type, your program will fail at runtime. 

ArrayIndexOutOfBoundsException, a NullPointerException, or (worse) silent erroneous behavior

 the size of the table is quadratic in the number of phases, even if the number of non-null entries is smaller.

```
// Using ordinal() to index array of arrays - DON'T DO THIS!
public enum Phase {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

        // Rows indexed by from-ordinal, cols by to-ordinal
        private static final Transition[][] TRANSITIONS = {
            { null, MELT, SUBLIME },
            { FREEZE, null, BOIL },
            { DEPOSIT, CONDENSE, null }
        };

        // Returns the phase transition from one phase to another
        public static Transition from(Phase from, Phase to) {
            return TRANSITIONS[from.ordinal()][to.ordinal()];
        }
    }
}
```



```java
// Using a nested EnumMap to associate data with enum pairs
public enum Phase {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);
        private final Phase from;
        private final Phase to;

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        // Initialize the phase transition map
        private static final Map<Phase_new, Map<Phase_new, Transition>> m = Stream.of(values())
                .collect(groupingBy(
                        t -> t.from,
                        () -> new EnumMap<>(Phase_new.class),
                        toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase_new.class))
                        )
                );

        public static Transition from(Phase from, Phase to) {
            return m.get(from).get(to);
        }
    }
}


// Initialize the phase transition map
private static final Map<Phase, Map<Phase,Transition> m =
    new EnumMap<Phase, Map<Phase ,Transition>>(Phase.class);

    static{
        for (Phase p : Phase. values())
            m.put(p,new EnumMap<Phase,Transition (Phase.class));
        for (Transition trans : Transition.values() )
            m.get(trans. src).put(trans.dst, trans) ;
    }

public static Transition from(Phase src, Phase dst) {
    return m.get(src).get(dst);
}
```

In summary, **it is rarely appropriate to use ordinals to index into arrays: use EnumMap instead.** 

If the relationship you are representing is multidimensional, use `EnumMap<..., EnumMap<...>>`. 

### Item 38: Emulate extensible enums with interfaces（使用接口模拟可扩展枚举）

```java
// Emulated extensible enum using an interface
public interface Operation {
    double apply(double x, double y);
}

public enum BasicOperation implements Operation {
    PLUS("+") {
        public double apply(double x, double y) { return x + y; }
    },
    MINUS("-") {
        public double apply(double x, double y) { return x - y; }
    },
    TIMES("*") {
        public double apply(double x, double y) { return x * y; }
    },
    DIVIDE("/") {
        public double apply(double x, double y) { return x / y; }
    };

    private final String symbol;

    BasicOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

// Emulated extension enum
public enum ExtendedOperation implements Operation {
    EXP("^") {
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        public double apply(double x, double y) {
            return x % y;
        }
    };

    private final String symbol;

    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
```



### Item 39: Prefer annotations to naming patterns（注解优于命名模式）

naming patterns disadvantage:

1. typographical errors result in silent failures
2. there is no way to ensure that they are used only on appropriate program elements
3. they provide no good way to associate parameter values with program elements

Annotations：

```java
// Marker annotation type declaration
import java.lang.annotation.*;

/**
* Indicates that the annotated method is a test method.
* Use only on parameterless static methods.
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {

}
```

##### java.lang.annotation.RetentionPolicy:

SOURCE、CLASS 和 RUNTIME

- 使用 SOURCE 保留策略的注解，只在源文件中保留，在编译期间会被抛弃。
- 使用 CLASS 保留策略的注解，在编译时被存储到 `.class` 文件中。但是，在运行时不能通过 JVM 得到这些注解。
- 使用 RUNTIME 保留策略的注解，在编译时被存储到 `.class` 文件中，并且在运行时可以通过 JVM 获取这些注解。因此，RUNTIME 保留策略提供了最永久的注解。

##### ElementType:

- ElementType.TYPE
  - Class, interface (including annotation type), or enum declaration（类、接口、注解、枚举）
- ElementType.FIELD
  - Field declaration (includes enum constants)（字段、枚举常量）
- ElementType.METHOD
  - Method declaration（方法）
- ElementType.PARAMETER
  - Formal parameter declaration（方法参数）
- ElementType.CONSTRUCTOR
  - Constructor declaration（构造）
- ElementType.LOCAL_VARIABLE
  - Local variable declaration（局部变量）
- ElementType.ANNOTATION_TYPE
  - Annotation type declaration（注解）
- ElementType.PACKAGE
  - Package declaration（包）
- ElementType.TYPE_PARAMETER
  - Type parameter declaration（泛型参数）
  - Since: 1.8
- ElementType.TYPE_USE
  - Use of a type（任意类型，获取 class 对象和 import 两种情况除外）
  - Since: 1.8
- ElementType.MODULE
  - Module declaration（[模块](https://docs.oracle.com/javase/9/whatsnew/toc.htm#JSNEW-GUID-C23AFD78-C777-460B-8ACE-58BE5EA681F6)）
  - Since: 9

marker annotation:   it has no parameters but simply “marks” the annotated element

```java
// Program containing marker annotations
public class Sample {
    @Test
    public static void m1() { } // Test should pass

    public static void m2() { }

    @Test
    public static void m3() { // Test should fail
        throw new RuntimeException("Boom");
    }

    public static void m4() { }

    @Test
    public void m5() { } // INVALID USE: nonstatic method

    public static void m6() { }

    @Test
    public static void m7() { // Test should fail
        throw new RuntimeException("Crash");
    }

    public static void m8() { }
}
```



### Item 40: Consistently use the Override annotation（坚持使用 @Override 注解）

- 执行编译器的检查，例如：`@Override` 注解。
- 分析代码：主要的用途，用来替代配置文件。

- 用在框架里面，注解开发。

  

##### JDK 内置的三种基本注解

- `@Override` ：限定重写父类方法，该注解只能用于方法。

- `@Deprecated` ：用于表示所修饰的元素（类、方法等）已经过时。通常是因为所修饰的结构危险或存在更好的选择。

- `@SuppressWarnings` ：抑制编译器警告。



 没有属性的注解称为 `标记` ，有属性的注解称为 `元数据注解` 。 



@Override. This annotation can be used only on method declarations, and it indicates that the annotated method declaration overrides a declaration in a supertype.

If you consistently use this annotation, it will protect you from a large class of nefarious bugs. 

```
@Override
public boolean equals(Bigram b) {
    return b.first == first && b.second == second;
}

Bigram.java:10: method does not override or implement a method from a supertype
@Override public boolean equals(Bigram b) {
^


@Override
public boolean equals(Object o) {
    if (!(o instanceof Bigram))
        return false;
    Bigram b = (Bigram) o;
    return b.first == first && b.second == second;
}
```

**use the Override annotation on every method declaration that you believe to override a superclass declaration**

The Override annotation may be used on method declarations that override declarations from interfaces as well as classes

In an abstract class or an interface, however, it is worth annotating all methods that you believe to override superclass or superinterface methods, whether concrete or abstract.

### Item 41: Use marker interfaces to define types（使用标记接口定义类型）

##### marker interface  标记接口

1. an interface that contains no method declarations but merely designates (or “marks”) a class that implements the interface as having some property.

##### marker annotations  标记注解

when should you use a marker annotation 

when should you use a marker interface

Difference : 

1. marker interfaces define a type that is implemented by instances of the marked class; marker annotations do not.
2. Another advantage of marker interfaces over marker annotations is that they can be targeted more precisely.
3. The chief advantage of marker annotations over marker interfaces is that they are part of the larger annotation facility
4. The existence of a marker interface type allows you to catch errors at compile time that you couldn’t catch until runtime if you used a marker annotation.


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

### Item 42: Prefer lambdas to anonymous classes

lambdas：

the best way to represent small function objects

```java
// Lambda expression as function object (replaces anonymous class)
Collections.sort(words,(s1, s2) -> Integer.compare(s1.length(), s2.length()));

//comparator construction method is used in place of a lambda
Collections.sort(words, comparingInt(String::length));
// the snippet can be made still shorter by taking advantage of the sort method that was added to the List interface in Java 8:
words.sort(comparingInt(String::length));
```

type inference:

Type inference is a Java compiler's ability to look at each method invocation and corresponding declaration to determine the type argument (or arguments) that make the invocation applicable. The inference algorithm determines the types of the arguments and, if available, the type that the result is being assigned, or returned. Finally, the inference algorithm tries to find the most specific type that works with all of the arguments.


**Omit the types of all lambda parameters unless their presence makes your program clearer**

**lambdas lack names and documentation; if a computation isn’t self-explanatory, or exceeds a few lines, don’t put it in a lambda.** 

limitations:

Lambdas are limited to functional interfaces

 a lambda cannot obtain a reference to itself.

**you should rarely, if ever, serialize a lambda**

Anonymous classes：

```java
// Anonymous class instance as a function object - obsolete!
Collections.sort(words, new Comparator<String>() {
    public int compare(String s1, String s2) {
        return Integer.compare(s1.length(), s2.length());
    }
});
```

**Don’t use anonymous classes for function objects unless you have to create instances of types that aren’t functional interfaces.** 

If you want to create an instance of an abstract class, you can do it with an anonymous class, but not a lambda. 

 you can use anonymous classes to create instances of interfaces with multiple abstract methods



In a lambda, the this keyword refers to the enclosing instance, which is typically what you want. In an anonymous class, the this keyword refers to the anonymous class instance. If you need access to the function object from within its body, then you must use an anonymous class.



Strategy pattern：策略设计模式

策略方法最重要的是直接调用具体策略实现类中的方法来完成功能

1. 提供一个策略接口
2. 提供多个策略接口的实现类
3. 提供一个策略上下文

###### 策略模式优点

- 可以自由切换算法(具体实现)
- 避免了多条件的判断(干掉了if else)
- 扩展性好可以定义新的算法提供给使用者(增加新功能时只需要增加代码而不需要修改代码)

###### 策略模式缺点

- 算法类数量增多，每个算法都是一个类，这对于初级程序员比较难以接受

```java
/**
 * 计算策略.
 *
 */
public interface CalcStrategy {

    int calc(int num1, int num2);
}

/**
 * 加法操作
 */
public class AddStrategy implements CalcStrategy {
    @Override
    public int calc(int num1, int num2) {
    	System.out.println("加法运算其它业务逻辑start");
        System.out.println("此处省略几十行代码...");
        System.out.println("加法运算其它业务逻辑end");
        return num1 + num2;
    }
}

/**
 * 减法操作.
 */
public class SubStrategy implements CalcStrategy {
    @Override
    public int calc(int num1, int num2) {
        return num1 - num2;
    }
}

//增加一个求余数的运算只需要增加一个枚举值并新增一个求余的实现类
public enum CalcTypeEnum {
    ADD(1, "加法操作"),
    SUB(2, "减法操作"),
    MUL(3, "乘法操作"),
    DIV(4, "除法操作"),
    // 求余数运算
    REM(5, "求余操作"),
    ;
}

```



### Item 43: Prefer method references to lambdas

method references:

```java
//lambdas
map.merge(key, 1, (count, incr) -> count + incr);
//method references
map.merge(key, 1, Integer::sum);
```

method references usually result in shorter, clearer code

| Method Ref Type   | Example                  | Lambda Equivalent                                  |
| ----------------- | ------------------------ | -------------------------------------------------- |
| Static            | `Integer::parseInt`      | `str ->`                                           |
| Bound             | `Instant.now()::isAfter` | `Instant then =Instant.now(); t ->then.isAfter(t)` |
| Unbound           | `String::toLowerCase`    | `str ->str.toLowerCase()`                          |
| Class Constructor | `TreeMap<K,V>::new`      | `() -> new TreeMap<K,V>`                           |
| Array Constructor | `int[]::new`             | `len -> new int[len]`                              |

**Where method references are shorter and clearer, use them; where they aren’t, stick with lambdas.**

### Item 44: Favor the use of standard functional interfaces（优先使用标准函数式接口）

```java
protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
    return size() > 100;
}

// Unnecessary functional interface; use a standard one instead.
@FunctionalInterface interface EldestEntryRemovalFunction<K,V>{
    boolean remove(Map<K,V> map, Map.Entry<K,V> eldest);
}
```

java.util.function:

| Interface           | Function Signature    | Example               |
| ------------------- | --------------------- | --------------------- |
| `UnaryOperator<T>`  | `T apply(T t)`        | `String::toLowerCase` |
| `BinaryOperator<T>` | `T apply(T t1, T t2)` | `BigInteger::add`     |
| `Predicate<T>`      | `boolean test(T t)`   | `Collection::isEmpty` |
| `Function<T,R>`     | `R apply(T t)`        | `Arrays::asList`      |
| `Supplier<T>`       | `T get()`             | `Instant::now`        |
| `Consumer<T>`       | `void accept(T t)`    | `System.out::println` |

**don’t be tempted to use basic functional interfaces with boxed primitives instead of primitive functional interfaces.**

writing a purpose-built functional interface:

- It will be commonly used and could benefit from a descriptive name.

- It has a strong contract associated with it.

- It would benefit from custom default methods.

**Always annotate your functional interfaces with the @FunctionalInterface annotation.**

### Item 45: Use streams judiciously（明智地使用流）

A stream pipeline consists of a source stream followed by zero or more intermediate operations and one terminal operation.

Stream pipelines are evaluated lazily:

 when to use streams：

```java
// Prints all large anagram groups in a dictionary iteratively
public class Anagrams {
    public static void main(String[] args) throws IOException {
        File dictionary = new File(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);
        Map<String, Set<String>> groups = new HashMap<>();
        try (Scanner s = new Scanner(dictionary)) {
            while (s.hasNext()) {
                String word = s.next();
                groups.computeIfAbsent(alphabetize(word),(unused) -> new TreeSet<>()).add(word);
            }
        }
        for (Set<String> group : groups.values())
        if (group.size() >= minGroupSize)
            System.out.println(group.size() + ": " + group);
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}

// Overuse of streams - don't do this!
public class Anagrams {
    public static void main(String[] args) throws IOException {
        Path dictionary = Paths.get(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);
        try (Stream<String> words = Files.lines(dictionary)) {
            words.collect(
            groupingBy(word -> word.chars().sorted()
            .collect(StringBuilder::new,(sb, c) -> sb.append((char) c),
            StringBuilder::append).toString()))
            .values().stream()
            .filter(group -> group.size() >= minGroupSize)
            .map(group -> group.size() + ": " + group)
            .forEach(System.out::println);
        }
    }
}


// Tasteful use of streams enhances clarity and conciseness
public class Anagrams {
    public static void main(String[] args) throws IOException {
        Path dictionary = Paths.get(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);
        try (Stream<String> words = Files.lines(dictionary)) {
            words.collect(groupingBy(word -> alphabetize(word)))
            .values().stream()
            .filter(group -> group.size() >= minGroupSize)
            .forEach(g -> System.out.println(g.size() + ": " + g));
        }
    }
    // alphabetize method is the same as in original version
    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}

```

**In the absence of explicit types, careful naming of lambda parameters is essential to the readability of stream pipelines.**

**Using helper methods is even more important for readability in stream pipelines than in iterative code**

**refactor existing code to use streams and use them in new code only where it makes sense to do so.**

There are some things you can do from code blocks that you can’t do from function objects:

- From a code block, you can read or modify any local variable in scope; from a lambda, you can only read final or effectively final variables [JLS 4.12.4], and you can’t modify any local variables.
- From a code block, you can return from the enclosing method, break or continue an enclosing loop, or throw any checked exception that this method is declared to throw; from a lambda you can do none of these things.

#####  streams make it very easy to do some things:

- Uniformly transform sequences of elements
- Filter sequences of elements
- Combine sequences of elements using a single operation (for example to add them, concatenate them, or compute their minimum)
- Accumulate sequences of elements into a collection, perhaps grouping them by some common attribute
- Search a sequence of elements for an element satisfying some criterion

```java
// Stream-based Cartesian product computation
private static List<Card> newDeck() {
    return Stream.of(Suit.values())
    .flatMap(suit ->Stream.of(Rank.values())
    .map(rank -> new Card(suit, rank)))
    .collect(toList());
}

```

### Item 46: Prefer side-effect-free functions in streams

 A pure function is one whose result depends only on its input: it does not depend on any mutable state, nor does it update any state.

side-effects：unintended *extra* result

The *side* effect is the complement of the *intended* effect.

A [side effect](http://en.wikipedia.org/wiki/Side_effect_(computer_science)) refers simply to the modification of some kind of state - for instance:

- Changing the value of a variable;

- Writing some data to disk;

- Enabling or disabling a button in the User Interface.

  

**1、Intermediate**

- 一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。常见的操作：map（mapToInt、flatMap 等）、filter、distinct、sorted、peek、limit、skip、parallel、sequential、unordered

**2、Terminal**

- 一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用「光」了，无法再被操作。所以这必定是流的最后一个操作。Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。常见的操作：forEach、forEachOrdered、toArray、reduce、collect、min、max、count、anyMatch、allMatch、noneMatch、findFirst、findAny、iterator
- 在对于一个流进行多次转换操作 (Intermediate 操作)，每次都对流的每个元素进行转换，而且是执行多次，这样时间复杂度就是 N（转换次数）个 for 循环里把所有操作都做掉的总和吗？其实不是这样的，转换操作都是 lazy 的，多个转换操作只会在 Terminal 操作的时候融合起来，一次循环完成。我们可以这样简单的理解，流里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，在 Terminal 操作的时候循环流对应的集合，然后对每个元素执行所有的函数。

**3、short-circuiting**

- 对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的流，但返回一个有限的新流。
- 对于一个 terminal 操作，如果它接受的是一个无限大的流，但能在有限的时间计算出结果。当操作一个无限大的流，而又希望在有限时间内完成操作，则在管道内拥有一个 short-circuiting 操作是必要非充分条件。常见的操作：anyMatch、allMatch、 noneMatch、findFirst、findAny、limit

```java
// Uses the streams API but not the paradigm--Don't do this!
Map<String, Long> freq = new HashMap<>();
try (Stream<String> words = new Scanner(file).tokens()) {
    words.forEach(word -> {
        freq.merge(word.toLowerCase(), 1L, Long::sum);
    });
}

// Proper use of streams to initialize a frequency table
Map<String, Long> freq;
try (Stream<String> words = new Scanner(file).tokens()) {
    freq = words.collect(groupingBy(String::toLowerCase, counting()));
}


// Pipeline to get a top-ten list of words from a frequency table
List<String> topTen = freq.keySet().stream()
    .sorted(comparing(freq::get).reversed())
    .limit(10)
    .collect(toList());
```

**The forEach operation should be used only to report the result of a stream computation, not to perform the computation.**

**It is customary and wise to statically import all members of Collectors because it makes stream pipelines more readable.**



##### collector:  Collector是专门用来作为Stream的collect方法的参数的

Collector主要包含五个参数，它的行为也是由这五个参数来定义的，如下所示

```java
public interface Collector<T, A, R> {
    // supplier参数用于生成结果容器，容器类型为A
    Supplier<A> supplier();
    // accumulator用于消费元素，也就是归纳元素，这里的T就是元素，它会将流中的元素一个一个与结果容器A发生操作
    BiConsumer<A, T> accumulator();
    // combiner用于两个两个合并并行执行的线程的执行结果，将其合并为一个最终结果A
    BinaryOperator<A> combiner();
    // finisher用于将之前整合完的结果R转换成为A
    Function<A, R> finisher();
    // characteristics表示当前Collector的特征值，这是个不可变Set
    Set<Characteristics> characteristics();
}

```

Collectors是一个工具类，是JDK预实现Collector的工具类，它内部提供了多种Collector:

```java
//toCollection
List<String> ll = list.stream().collect(Collectors.toCollection(LinkedList::new));

//toList
List<String> ll = list.stream().collect(Collectors.toList());

//toSet
Set<String> ss = list.stream().collect(Collectors.toSet());

//joining
// 无参方法
String s = list.stream().collect(Collectors.joining());
System.out.println(s);
// 指定连接符
String ss = list.stream().collect(Collectors.joining("-"));
System.out.println(ss);
// 指定连接符和前后缀
String sss = list.stream().collect(Collectors.joining("-","S","E"));
System.out.println(sss);

//mapping
List<Integer> ll = list.stream().limit(5).collect(Collectors.mapping(Integer::valueOf,Collectors.toList()));

//collectingAndThen
int length = list.stream().collect(Collectors.collectingAndThen(Collectors.toList(),e -> e.size()));

//counting
 long size = list.stream().collect(Collectors.counting());

//minBy/maxBy : 生成一个用于获取最小/最大值的Optional结果的Collector
System.out.println(list.stream().collect(Collectors.maxBy((a,b) -> a.length()-b.length())));
System.out.println(list.stream().collect(Collectors.minBy((a,b) -> a.length()-b.length())));

//summingInt/summingLong/summingDouble
//生成一个用于求元素和的Collector，首先通过给定的mapper将元素转换类型，然后再求和
int i = list.stream().limit(3).collect(Collectors.summingInt(Integer::valueOf));
long l = list.stream().limit(3).collect(Collectors.summingLong(Long::valueOf));
double d = list.stream().limit(3).collect(Collectors.summingDouble(Double::valueOf));

//averagingInt/averagingLong/averagingDouble
double i = list.stream().limit(3).collect(Collectors.averagingInt(Integer::valueOf));
double l = list.stream().limit(3).collect(Collectors.averagingLong(Long::valueOf));
double d = list.stream().limit(3).collect(Collectors.averagingDouble(Double::valueOf));

//reducing : reducing方法有三个重载方法
// 无初始值的情况，返回一个可以生成Optional结果的Collector
public static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op) {/*...*/}
// 有初始值的情况，返回一个可以直接产生结果的Collector
public static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op) {/*...*/}
// 有初始值，还有针对元素的处理方案mapper，生成一个可以直接产生结果的Collector，元素在执行结果操作op之前需要先执行mapper进行元素转换操作
public static <T, U> Collector<T, ?, U> reducing(U identity,
                                    Function<? super T, ? extends U> mapper,
                                    BinaryOperator<U> op) {/*...*/}

//groupingBy:  用于生成一个拥有分组功能的Collector，它也有三个重载方法
public final class Collectors {
    // 只需一个分组参数classifier，内部自动将结果保存到一个map中，每个map的键为?类型（即classifier的结果类型），值为一个list，这个list中保存在属于这个组的元素。
    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(
            Function<? super T, ? extends K> classifier) {/*...*/}
    // 在上面方法的基础上增加了对流中元素的处理方式的Collector，比如上面的默认的处理方法就是Collectors.toList()
    public static <T, K, A, D>Collector<T, ?, Map<K, D>> groupingBy(
            Function<? super T, ? extends K> classifier,Collector<? super T, A, D> downstream) {/*...*/}
    // 在第二个方法的基础上再添加了结果Map的生成方法。
    public static <T, K, D, A, M extends Map<K, D>>
        Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier,
                                      Supplier<M> mapFactory,
                                      Collector<? super T, A, D> downstream) {/*...*/}
}

Map<Integer,List<String>> s = list.stream().collect(Collectors.groupingBy(String::length));
Map<Integer,List<String>> ss = list.stream().collect(Collectors.groupingBy(String::length, Collectors.toList()));
Map<Integer,Set<String>> sss = list.stream().collect(Collectors.groupingBy(String::length,HashMap::new,Collectors.toSet()));

//partitioningBy
//该方法将流中的元素按照给定的校验规则的结果分为两个部分，放到一个map中返回，map的键是Boolean类型，值为元素的列表List
Map<Boolean,List<String>> map = list.stream().collect(Collectors.partitioningBy(e -> e.length()>5));
Map<Boolean,Set<String>> map2 = list.stream().collect(Collectors.partitioningBy(e -> e.length()>6,Collectors.toSet()));

//toMap
public final class Collectors {
    // 指定键和值的生成方式keyMapper和valueMapper
    public static <T, K, U>
        Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends U> valueMapper) {/*...*/}
    // 在上面方法的基础上增加了对键发生重复时处理方式的mergeFunction，比如上面的默认的处理方法就是抛出异常
    public static <T, K, U>
        Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends U> valueMapper,
                                        BinaryOperator<U> mergeFunction) {/*...*/}
    // 在第二个方法的基础上再添加了结果Map的生成方法。
    public static <T, K, U, M extends Map<K, U>>
        Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper,
                                    BinaryOperator<U> mergeFunction,
                                    Supplier<M> mapSupplier) {/*...*/}
}

Map<String,String> map = list.stream().limit(3).collect(Collectors.toMap(e -> e.substring(0,1),e -> e));
Map<String,String> map1 = list.stream().collect(Collectors.toMap(e -> e.substring(0,1),e->e,(a,b)-> b));
Map<String,String> map2 = list.stream().collect(Collectors.toMap(e -> e.substring(0,1),e->e,(a,b)-> b,HashMap::new));

//toConcurrentMap,同样三种重载方法，与toMap基本一致，只是它最后使用的map是并发Map:ConcurrentHashMap

//summarizingInt/summarizingLong/summarizingDouble
//返回值中包含有流中元素的指定结果的数量、和、最大值、最小值、平均值
IntSummaryStatistics intSummary = list.stream().collect(Collectors.summarizingInt(String::length));
LongSummaryStatistics longSummary = list.stream().limit(4).collect(Collectors.summarizingLong(Long::valueOf));
DoubleSummaryStatistics doubleSummary = list.stream().limit(3).collect(Collectors.summarizingDouble(Double::valueOf));

```



toList(), toSet(), and toCollection(collectionFactory), toMap(keyMapper, valueMapper)

```java
// Using a toMap collector to make a map from string to enum
private static final Map<String, Operation> stringToEnum =Stream.of(values()).collect(toMap(Object::toString, e -> e));
```



######  use of the three-argument form of toMap:

```java
// Collector to generate a map from key to chosen element for key
Map<Artist, Album> topHits = albums.collect(
        toMap(Album::artist, a->a, maxBy(comparing(Album::sales)
    )
));


// Collector to impose last-write-wins policy
toMap(keyMapper, valueMapper, (v1, v2) -> v2)
```

last-write-wins policy  :

toConcurrentMap:

Collectors API provides the groupingBy method, which returns collectors to produce maps that group elements into categories based on a classifier function.

```java
words.collect(groupingBy(word -> alphabetize(word)))
```



use of the two-argument form of groupingBy:

```java
Map<String, Long> freq = words.collect(groupingBy(String::toLowerCase, counting()));
```

groupingByConcurrent :

partitioningBy: 

minBy :

maxBy:

joining: 

### Item 47: Prefer Collection to Stream as a return type

```java
// Adapter from Stream<E> to Iterable<E>
public static <E> Iterable<E> iterableOf(Stream<E> stream) {
    return stream::iterator;
}
```



```java
// Adapter from Iterable<E> to Stream<E>
public static <E> Stream<E> streamOf(Iterable<E> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
}
```

**Collection or an appropriate subtype is generally the best return type for a public, sequence-returning method.** 

The Collection interface is a subtype of Iterable and has a stream method, so it provides for both iteration and stream access.



**do not store a large sequence in memory just to return it as a collection.**

AbstractList:

```java
// Returns the power set of an input set as custom collection
public class PowerSet {
    public static final <E> Collection<Set<E>> of(Set<E> s) {
        List<E> src = new ArrayList<>(s);
        if (src.size() > 30)
            throw new IllegalArgumentException("Set too big " + s);

        return new AbstractList<Set<E>>() {
            @Override
            public int size() {
                return 1 << src.size(); // 2 to the power srcSize
            }

            @Override
            public boolean contains(Object o) {
                return o instanceof Set && src.containsAll((Set)o);
            }

            @Override
            public Set<E> get(int index) {
                Set<E> result = new HashSet<>();
                for (int i = 0; index != 0; i++, index >>= 1)
                    if ((index & 1) == 1)
                        result.add(src.get(i));
                return result;
            }
        };
    }
}
```



```java
// Returns a stream of all the sublists of its input list
public class SubLists {
    public static <E> Stream<List<E>> of(List<E> list) {
        return Stream.concat(Stream.of(Collections.emptyList()),prefixes(list).flatMap(SubLists::suffixes));
    }

    private static <E> Stream<List<E>> prefixes(List<E> list) {
        return IntStream.rangeClosed(1, list.size()).mapToObj(end -> list.subList(0, end));
    }

    private static <E> Stream<List<E>> suffixes(List<E> list) {
        return IntStream.range(0, list.size()).mapToObj(start -> list.subList(start, list.size()));
    }
}



// Returns a stream of all the sublists of its input list
public static <E> Stream<List<E>> of(List<E> list) {
    return IntStream.range(0, list.size())
    .mapToObj(start ->
    IntStream.rangeClosed(start + 1, list.size())
    .mapToObj(end -> list.subList(start, end)))
    .flatMap(x -> x);
}
```

Math.signum:

The **java.lang.Math.signum(float f)** returns the signum function of the argument; zero if the argument is zero, 1.0f if the argument is greater than zero, -1.0f if the argument is less than zero.

Special Cases:

1. If the argument is NaN, then the result is NaN.
2. If the argument is positive zero or negative zero, then the result is the same as the argument





### Item 48: Use caution when making streams parallel

串行Sequential ： item->item  By default, **any stream operation in Java is processed sequentially, unless explicitly specified as parallel.** 

Sequential streams use a single thread to process the pipeline:

并行流就是一个把数据分成多个数据块，并用不不同的线程分别处理每个数据块的流，最后合并每个数据块的计算结果。

Parallel streams enable us to execute code in parallel on separate cores.

parallelizing a stream is strictly a performance optimization

将一个顺序执行的流转变成一个并发的流只要调用 parallel()方法

多线程异步任务的一种实现

The fork-join framework is in charge of **splitting the source data between worker threads and handling callback on task completion.**

you must test the performance before and after the change to ensure that it is worth doing

```java
// Prime-counting stream pipeline - benefits from parallelization
static long pi(long n) {
    return LongStream.rangeClosed(2, n)
    .mapToObj(BigInteger::valueOf)
    .filter(i -> i.isProbablePrime(50))
    .count();
}


// Prime-counting stream pipeline - parallel version
static long pi(long n) {
    return LongStream.rangeClosed(2, n)
    .parallel()
    .mapToObj(BigInteger::valueOf)
    .filter(i -> i.isProbablePrime(50))
    .count();
}
```



**parallelizing a pipeline is unlikely to increase its performance if the source is from Stream.iterate, or the intermediate operation limit is used**

**Do not parallelize stream pipelines indiscriminately.**

As a rule, **performance gains from parallelism are best on streams over ArrayList, HashMap, HashSet, and ConcurrentHashMap instances; arrays; int ranges; and long ranges**

**Not only can parallelizing a stream lead to poor performance, including liveness failures; it can lead to incorrect results and unpredictable behavior**

If a significant amount of work is done in the terminal operation compared to the overall work of the pipeline and that operation is inherently sequential, then parallelizing the pipeline will have limited effectiveness.

The best terminal operations for parallelism are reductions:  min, max, count, and sum

The short-circuiting operations anyMatch, allMatch, and noneMatch are also amenable to parallelism

the accumulator and combiner functions passed to Stream’s reduce operation must be associative, non-interfering, and stateless.

```java
forEachOrdered
    
    //并行转顺序流
    stream.parallel() .filter(...) .sequential() .map(...) .parallel() .reduce();

List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9); 
numbers.parallelStream().forEach(num->System.out.println(num));

输出：3 4 2 6 7 9 8 1 5

       List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9); 
   numbers.parallelStream() .forEach(num-
   		>System.out.println(Thread.currentThread().getName()+">>"+num)); 



```

SplittableRandom :   Fork Join Pool` 和并行 `Stream`，则使用 `SplittableRandom

ThreadLocalRandom:   相对于Random可以减少多线程资源竞争，保证了线程的安全性

如何高效使用并发流的一些建议：

1. 如果不确定， 就自己测试。
2. 尽量使用基本类型的流  IntStream, LongStream, and DoubleStream
3. 有些操作使用并发流的性能会比顺序流的性能更差，比如limit，findFirst ， 依赖元素顺序的操作在并发流中是极其消耗性能的 。findAny的性能就会好很多，应为不依赖顺序。
4. 考虑流中计算的性能(Q)和操作的性能(N)的对比, Q表示单个处理所需的时间， N表示需要处理的数量，如果Q的值越大, 使用并发流的性能就会越高。
5. 数据量不大时使用并发流，性能得不到提升。
6. 考虑数据结构：并发流需要对数据进行分解，不同的数据结构被分解的性能时不一样的。

 **流的数据源和可分解性**

| **源**          | **可分解性** |
| --------------- | ------------ |
| ArrayList       | 非常好       |
| LinkedList      | 差           |
| IntStream.range | 非常好       |
| Stream.iterate  | 差           |
| HashSet         | 好           |
| TreeSet         | 好           |

​     * 两个静态方法来从函数生成流Stream.iterate和Stream.generate, 这两个操作可以创建所谓的无限流

1. 流的特性以及中间操作对流的修改都会对数据对分解性能造成影响。 比如固定大小的流在任务分解的时候就可以平均分配，但是如果有filter操作，那么流就不能预先知道在这个操作后还会剩余多少元素。

2. 考虑最终操作的性能：如果最终操作在合并并发流的计算结果时的性能消耗太大，那么使用并发流提升的性能就会得不偿失。

3. 需要理解并发流实现机制：

   fork/join框架的目的是以递归方式将可以并行的任务拆分成更小的任务，然后将每个子任务的结果合并起来生成整体结果。它是ExecutorService接口的一个实现，它把子任务分配线程池（ForkJoinPool）中的工作线程。要把任务提交到这个线程池，必须创建RecursiveTask<R>的一个子类，如果任务不返回结果则是 RecursiveAction的子类。




do not even attempt to parallelize a stream pipeline unless you have good reason to believe that it will preserve the correctness of the computation and increase its speed.

performance measurements under realistic conditions

### Custom Thread Pool

```java
//parallize
ForkJoinPool pool = new ForkJoinPool(2);
ret = pool.submit(() -> {
    return LongStream.range(1, 50 * 1024 * 1024).boxed().collect(Collectors.toList())
            .stream()
            .parallel()
            .map(x -> x * 2)
            .filter(x -> x < 1500)
            .reduce((x,y) -> x+y)
            .get();
}).get();
//the customThreadPool object won't be dereferenced and garbage collected — instead, it will be waiting for new tasks to be assigned.
//The fix to the problem is pretty simple: shutdown the customThreadPool object after we've executed the method
```

**线程安全**

由于并行流使用多线程，则一切线程安全问题都应该是需要考虑的问题，如：资源竞争、死锁、事务、可见性等等。

**线程消费**

在虚拟机启动时，我们指定了worker线程的数量，整个程序的生命周期都将使用这些工作线程；这必然存在任务生产和消费的问题，如果某个生产者生产了许多重量级的任务（耗时很长），那么其他任务毫无疑问将会没有工作线程可用；更可怕的事情是这些工作线程正在进行IO阻塞。

本应利用并行加速处理的业务，因为工作者不够反而会额外增加处理时间，使得系统性能在某一时刻大打折扣。而且这一类问题往往是很难排查的。我们并不知道一个重量级项目中的哪一个框架、哪一个模块在使用并行流。

##### 内存局部性(Memory locality)

访问内存时，大概率会访问连续的块，而不是单一的内存地址，其实就是空间局部性在内存上的体现。目前计算机设计中，都是以块/页为单位管理调度存储，其实就是在利用空间局部性来优化性能

**串行流**：适合存在线程安全问题、阻塞任务、重量级任务，以及需要使用同一事务的逻辑。

**并行流**：适合没有线程安全问题、较单纯的数据处理任务。



#####  the overhead of managing multiple threads

```java
 IntStream.of(1, 2, 3);//返回一个intStream
 IntStream.range(1,20).forEach(i-> System.out.print(i+","));//返回一个1-19的数字流
 IntStream.rangeClosed(1,20).forEach(i-> System.out.print(i+","));//返回的一个1-20的数字流
```



```java
IntStream.rangeClosed(1, 100).reduce(0, Integer::sum);
IntStream.rangeClosed(1, 100).parallel().reduce(0, Integer::sum);


//sometimes the overhead of managing threads, sources and results is a more expensive operation than doing the actual work
```

##### Splitting Costs:

**arrays can split cheaply and evenly**, while *LinkedList* has none of these properties. [*TreeMap*](https://www.baeldung.com/java-treemap) and [*HashSet*](https://www.baeldung.com/java-hashset) split better than *LinkedList* but not as well as arrays

##### Merging Costs:

The merge operation is really cheap for some operations, such as reduction and addition, but **merge operations like grouping to sets or maps can be quite expensive**

##### memory locality:

**the more pointers we have in our data structure, the more pressure we put on the memory** to fetch the reference objects. This can have a negative effect on  parallelization, as multiple cores simultaneously fetch the data from  memory

##### The *NQ* Model:

*N* stands for the number of source data elements, while *Q* represents the amount of computation performed per data element.

The larger the product of *N\*Q*, the more likely we are to get a performance boost from parallelization. 

**As the number of computations increases, the data size required to get a performance boost from parallelism decreases.**



## Chapter 8

### Item 49: Check parameters for validity

program principle: 编程思想, 就是 **Fail-fast**   :  

 让错误尽可能早的出现, 不要等到我们很多工作执行到一半之后才抛出异常, 这样很可能使得一部分变量处于异常状态, 出现更多的错误. 

 you should attempt to detect errors as soon as possible after they occur

This chapter discusses several aspects of method design: how to treat parameters and return values, how to design method signatures, and how to document methods.

method: 

 treat parameters:

not uncommon: index values must be non-negative and object references must be non-null.

You should clearly document all such restrictions and enforce them with checks at the beginning of the method body. 

```
/**
* Returns a BigInteger whose value is (this mod m). This method
* differs from the remainder method in that it always returns a
* non-negative BigInteger.
**
@param m the modulus, which must be positive
* @return this mod m
* @throws ArithmeticException if m is less than or equal to 0
*/
public BigInteger mod(BigInteger m) {
    if (m.signum() <= 0)
        throw new ArithmeticException("Modulus <= 0: " + m);
    ... // Do the computation
}
```

If the method fails to check its parameters:

1. The method could fail with a confusing exception in the midst of processing
2. the method could return normally but silently compute the wrong result. 
3. the method could return normally but leave some object in a compromised state, causing an error at some unrelated point in the code at some undetermined time in the future.

BigInteger.signum():  This method returns -1, 0 or 1 as the value of this BigInteger is negative, zero or positive.



**The Objects.requireNonNull method, added in Java 7, is flexible and convenient, so there’s no reason to perform null checks manually anymore.**

```java
// Inline use of Java's null-checking facility
this.strategy = Objects.requireNonNull(strategy, "strategy");

/**
 * Checks that the specified object reference is not {@code null} and
 * throws a customized {@link NullPointerException} if it is. This method
 * is designed primarily for doing parameter validation in methods and
 * constructors with multiple parameters, as demonstrated below:
 * <blockquote><pre>
 * public Foo(Bar bar, Baz baz) {
 *     this.bar = Objects.requireNonNull(bar, "bar must not be null");
 *     this.baz = Objects.requireNonNull(baz, "baz must not be null");
 * }
 * </pre></blockquote>
 *
 * @param obj     the object reference to check for nullity
 * @param message detail message to be used in the event that a {@code
 *                NullPointerException} is thrown
 * @param <T> the type of the reference
 * @return {@code obj} if not {@code null}
 * @throws NullPointerException if {@code obj} is {@code null}
 */
public static <T> T requireNonNull(T obj, String message) {
    if (obj == null)
        throw new NullPointerException(message);
    return obj;
}

/**
 * Checks that the specified object reference is not {@code null}. This
 * method is designed primarily for doing parameter validation in methods
 * and constructors, as demonstrated below:
 * <blockquote><pre>
 * public Foo(Bar bar) {
 *     this.bar = Objects.requireNonNull(bar);
 * }
 * </pre></blockquote>
 *
 * @param obj the object reference to check for nullity
 * @param <T> the type of the reference
 * @return {@code obj} if not {@code null}
 * @throws NullPointerException if {@code obj} is {@code null}
 */
public static <T> T requireNonNull(T obj) {
    if (obj == null)
        throw new NullPointerException();
    return obj;
}


```

In Java 9, a range-checking facility was added to java.util.Objects. This facility consists of three methods: 

checkFromIndexSize

checkFromToIndex

checkIndex

 it is designed solely for use on list and array indices



 nonpublic methods can check their parameters using assertions, as shown below:

```java
// Private helper function for a recursive sort
private static void sort(long a[], int offset, int length) {
    assert a != null;
    assert offset >= 0 && offset <= a.length;
    assert length >= 0 && length <= a.length - offset;
    ... // Do the computation
}
```



loss of failure atomicity:

failure atomicity: if a method threw an exception, the object should still be usable afterwards. Generally, the object should be in the same state as it was before invoking the method.

###### Strive for failure atomicity:

1. 设计一个不可变的对象
2. 对计算过程进行排序，使得任何可能会失败的计算都在对象被修改之前发生
3. 编写恢复代码（recovery code），但这种做法并不长用，该代码拦截在操作中发生的失败，并使对象将其状态回滚到操作开始之前的点。 此方法主要用于持久性的（基于磁盘）的数据结构。

It is particularly important to check the validity of parameters that are not used by a method, but stored for later use. 

exception translation idiom:   

```java
/**
* Returns the element at the specified position in this list.
* @throws IndexOutOfBoundsException if the index is out of range
* ({@code index < 0 || index >= size()}).
*/
public E get(int index) {
    ListIterator<E> i = listIterator(index);
    try {
        return i.next();
    }
    catch (NoSuchElementException e) {
        throw new IndexOutOfBoundsException("Index: " + index);
    }
}
```



### Item 50: Make defensive copies when needed

 **You must program defensively, with the assumption that clients of your class will do their best to destroy its invariants.** 

**Date is obsolete and should no longer be used in new code**  :  Date is mutable

Instant (and the other java.time classes) are immutable

**it is essential to make a defensive copy of each mutable parameter to the constructor**

```java
// Repaired constructor - makes defensive copies of parameters
public Period(Date start, Date end) {
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());
    if (this.start.compareTo(this.end) > 0)
        throw new IllegalArgumentException(this.start + " after " + this.end);
}
```

**defensive copies are made before checking the validity of the parameters (Item 49), and the validity check is performed on the copies rather than on the originals.** 

**do not use the clone method to make a defensive copy of a parameter whose type is subclassable by untrusted parties.**



```java
// Repaired accessors - make defensive copies of internal fields
public Date start() {
    return new Date(start.getTime());
}

public Date end() {
    return new Date(end.getTime());
}
```



### Item 51: Design method signatures carefully

**Choose method names carefully.**

Avoid long method names

choose names consistent with the broader consensus

**Don’t go overboard in providing convenience methods.**

**Avoid long parameter lists.**

Aim for four parameters or fewer

**Long sequences of identically typed parameters are especially harmful**. 

There are three techniques for shortening overly long parameter lists

1. One is to break the method up into multiple methods, each of which requires only a subset of the parameters.
2. create helper classes to hold groups of parameters
3. combines aspects of the first two is to adapt the Builder pattern (Item 2) from object construction to method invocation

**For parameter types, favor interfaces over classes**

**Prefer two-element enum types to boolean parameters**



### Item 52: Use overloading judiciously（明智地使用重载）

**the choice of which overloading to invoke is made at compile time**

**selection among overloaded methods is static, while selection among overridden methods is dynamic**

 **A safe, conservative policy is never to export two overloadings with the same number of parameters.** 

**you can always give methods different names instead of overloading them**

**do not overload methods to take different functional interfaces in the same argument position.**

### Item 53: Use varargs judiciously（明智地使用可变参数）

Varargs methods, formally known as variable arity methods

```java
// The right way to use varargs to pass one or more arguments
static int min(int firstArg, int... remainingArgs) {
    int min = firstArg;
    for (int arg : remainingArgs)
        if (arg < min)
    min = arg;
    return min;
}
```

Exercise care when using varargs in performance-critical situations. Every invocation of a varargs method causes an array allocation and initialization. If you have determined empirically that you can’t afford this cost but you need the flexibility of varargs, there is a pattern that lets you have your cake and eat it too. 

```java
//declare five overloadings of the method, one each with zero through three ordinary parameters
public void foo() { }
public void foo(int a1) { }
public void foo(int a1, int a2) { }
public void foo(int a1, int a2, int a3) { }
public void foo(int a1, int a2, int a3, int... rest) { }
```



### Item 54: Return empty collections or arrays, not nulls（返回空集合或数组，而不是 null）

```java
// Optimization - avoids allocating empty collections
public List<Cheese> getCheeses() {
    return cheesesInStock.isEmpty() ? Collections.emptyList(): new ArrayList<>(cheesesInStock);
}

// Collections.emptySet
// Collections.emptyMap
```



### Item 55: Return optionals judiciously（明智地的返回 Optional）

Prior to Java 8, there were two approaches you could take when writing a method that was unable to return a value under certain circumstances. Either you could throw an exception, or you could return null (assuming the return type was an object reference type).

Exceptions should be reserved for exceptional conditions (Item 69), and throwing an exception is expensive because the entire stack trace is captured when an exception is created



The `Optional<T>` class represents an immutable container that can hold either a single non-null T reference or nothing at all.

```java
The Optional<T> class represents an immutable container 
    
    
    // Returns maximum value in collection - throws exception if empty
public static <E extends Comparable<E>> E max(Collection<E> c) {
    if (c.isEmpty())
        throw new IllegalArgumentException("Empty collection");
    E result = null;
    for (E e : c)
        if (result == null || e.compareTo(result) > 0)
    result = Objects.requireNonNull(e);
    return result;
}
    
    // Returns maximum value in collection as an Optional<E>
public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
    if (c.isEmpty())
        return Optional.empty();
    E result = null;
    for (E e : c)
        if (result == null || e.compareTo(result) > 0)
    result = Objects.requireNonNull(e);
    return Optional.of(result);
}
```

Optional.empty()

Optional.of(value) returns an optional containing the given non-null value

Optional.ofNullable(value)

**Never return a null value from an Optional-returning method**



```java
// Using an optional to provide a chosen default value
String lastWordInLexicon = max(words).orElse("No words...");


// Using an optional to throw a chosen exception
Toy myToy = max(toys).orElseThrow(TemperTantrumException::new);

//Java 8
streamOfOptionals.filter(Optional::isPresent).map(Optional::get)
//Java 9
streamOfOptionals..flatMap(Optional::stream)
```

**Container types, including collections, maps, streams, arrays, and optionals should not be wrapped in optionals**

As a rule, **you should declare a method to return `Optional<T>` if it might not be able to return a result and clients will have to perform special processing if no result is returned.**

 returning an `Optional<T>` is not without cost. An Optional is an object that has to be allocated and initialized, and reading the value out of the optional requires an extra indirection. 

OptionalInt, OptionalLong, and OptionalDouble

**you should never return an optional of a boxed primitive type**

**it is almost never appropriate to use an optional as a key, value, or element in a collection or array.**



### Item 56: Write doc comments for all exposed API elements（为所有公开的 API 元素编写文档注释）

**To document your API properly, you must precede every exported class, interface, constructor, method, and field declaration with a doc comment**

**The doc comment for a method should describe succinctly the contract between the method and its client.** 

```java
/**
* Returns the element at the specified position in this list.
**
<p>This method is <i>not</i> guaranteed to run in constant
* time. In some implementations it may run in time proportional
* to the element position.
**
@param index index of element to return; must be
* non-negative and less than the size of this list
* @return the element at the specified position in this list
* @throws IndexOutOfBoundsException if the index is out of range
* ({@code index < 0 || index >= this.size()})
*/
E get(int index);


/**
* Returns true if this collection is empty.
**
@implSpec
* This implementation returns {@code this.size() == 0}.
**
@return true if this collection is empty
*/
public boolean isEmpty() { ... }
```

**doc comments should be readable both in the source code and in the generated documentation**

**no two members or constructors in a class or interface should have the same summary description.** 



**When documenting a generic type or method, be sure to document all type parameters:**

```java
/**
* An object that maps keys to values. A map cannot contain
* duplicate keys; each key can map to at most one value.
**
(Remainder omitted)
**
@param <K> the type of keys maintained by this map
* @param <V> the type of mapped values
*/
public interface Map<K, V> { ... }
```

**When documenting an enum type, be sure to document the constants** as well as the type and any public methods.

```java
/**
* An instrument section of a symphony orchestra.
*/
public enum OrchestraSection {
/** Woodwinds, such as flute, clarinet, and oboe. */
WOODWIND,
/** Brass instruments, such as french horn and trumpet. */
BRASS,
/** Percussion instruments, such as timpani and cymbals. */
PERCUSSION,
/** Stringed instruments, such as violin and cello. */
STRING;
}
```

**When documenting an annotation type, be sure to document any Members** as well as the type itself. 

```java
/**
* Indicates that the annotated method is a test method that
* must throw the designated exception to pass.
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
/**
* The exception that the annotated test method must throw
* in order to pass. (The test is permitted to throw any
* subtype of the type described by this class object.)
*/
Class<? extends Throwable> value();
}
```



## Chapter 9. General Programming

### Item 57: Minimize the scope of local variables

**The most powerful technique for minimizing the scope of a local variable is to declare it where it is first used.** 

**Nearly every local variable declaration should contain an initializer.** 

The for loop has one more advantage over the while loop: it is shorter, which enhances readability

```java
for (int i = 0, n = expensiveComputation(); i < n; i++) {
    ... // Do something with i;
}
```

A final technique to minimize the scope of local variables is to keep methods small and focused

### Item 58: Prefer for-each loops to traditional for loops（for-each 循环优于传统的 for 循环）

for-each loop (officially known as the “enhanced for statement”)

```java
// The preferred idiom for iterating over collections and arrays
for (Element e : elements) {
    ... // Do something with e
}


// Preferred idiom for nested iteration on collections and arrays
for (Suit suit : suits)
for (Rank rank : ranks)
deck.add(new Card(suit, rank));
```

there are three common situations where you can’t use foreach:

- **Destructive filtering** —If you need to traverse a collection removing selected elements, then you need to use an explicit iterator so that you can call its remove method. You can often avoid explicit traversal by using Collection’s removeIf method, added in Java 8.
- **Transforming** —If you need to traverse a list or array and replace some or all of the values of its elements, then you need the list iterator or array index in order to replace the value of an element.
- **Parallel iteration** —If you need to traverse multiple collections in parallel, then you need explicit control over the iterator or index variable so that all iterators or index variables can be advanced in lockstep (as demonstrated unintentionally in the buggy card and dice examples above). If you find yourself in any of these situations, use an ordinary for loop and be wary of the traps mentioned in this item.

### Item 59: Know and use the libraries

**By using a standard library, you take advantage of the knowledge of the experts who wrote it and the experience of those who used it before you.**

**the random number generator of choice is now ThreadLocalRandom**

For fork join pools and parallel streams, use SplittableRandom.

A second advantage of using the libraries is that you don’t have to waste your time writing ad hoc solutions to problems that are only marginally related to your work.

A third advantage of using standard libraries is that their performance tends to improve over time, with no effort on your part.

A fourth advantage of using libraries is that they tend to gain functionality over time.

A final advantage of using the standard libraries is that you place your code in the mainstream.

**Numerous features are added to the libraries in every major release, and it pays to keep abreast of these additions.** 

**every programmer should be familiar with the basics of java.lang, java.util, and java.io, and their subpackages**



### Item 60: Avoid float and double if exact answers are required

**The float and double types are particularly ill-suited for monetary calculations**

**use BigDecimal, int, or long for monetary calculations**

In summary, don’t use float or double for any calculations that require an exact answer. Use BigDecimal if you want the system to keep track of the decimal point and you don’t mind the inconvenience and cost of not using a primitive type. Using BigDecimal has the added advantage that it gives you full control over rounding, letting you select from eight rounding modes whenever an operation that entails rounding is performed. This comes in handy if you’re performing business calculations with legally mandated rounding behavior. If performance is of the essence, you don’t mind keeping track of the decimal point yourself, and the quantities aren’t too big, use int or long. If the quantities don’t exceed nine decimal digits, you can use int; if they don’t exceed eighteen digits, you can use long. If the quantities might exceed eighteen digits, use BigDecimal.

### Item 61: Prefer primitive types to boxed primitives

primitives ：   int, double, and boolean

reference types：   String and List.

The boxed primitives corresponding to int, double, and boolean are Integer, Double, and Boolean.



 three major differences between primitives and boxed primitives：

 First, primitives have only their values, whereas boxed primitives have identities distinct from their values. 

Second, primitive types have only fully functional values, whereas each boxed primitive type has one nonfunctional value, which is null, in addition to all the functional values of the corresponding primitive type.

Last, primitives are more time and space efficient than boxed primitives. 

**Applying the == operator to boxed primitives is almost always wrong.**

**when you mix primitives and boxed primitives in an operation, the boxed primitive is auto-unboxed.** 

 **Autoboxing reduces the verbosity, but not the danger, of using boxed primitives.**

### Item 62: Avoid strings where other types are more appropriate

**Strings are poor substitutes for other value types.** 

**Strings are poor substitutes for enum types.**

- enums make far better enumerated type constants than strings.

**Strings are poor substitutes for aggregate types.**

- A better approach is simply to write a class to represent the aggregate, often a private static member class (Item 24).

**Strings are poor substitutes for capabilities.**

Types for which strings are commonly misused include primitive types, enums, and aggregate types.

### Item 63: Beware the performance of string concatenation

Using **the string concatenation operator repeatedly to concatenate n strings requires time quadratic in n.** 

使用 **字符串串联运算符重复串联 n 个字符串需要 n 的平方级时间**

**To achieve acceptable performance, use a StringBuilder in place of a String** to store the statement under construction:

**Don’t use the string concatenation operator to combine more than a few strings** 

### Item 64: Refer to objects by their interfaces

 **If appropriate interface types exist, then parameters, return values, variables, and fields should all be declared using interface types.**

**If you get into the habit of using interfaces as types, your program will be much more flexible.** 

**It is entirely appropriate to refer to an object by a class rather than an interface if no appropriate interface exists.** 

**If there is no appropriate interface, just use the least specific class in the class hierarchy that provides the required functionality.**

### Item 65: Prefer interfaces to reflection

- **You lose all the benefits of compile-time type checking,** including exception checking. If a program attempts to invoke a nonexistent or inaccessible method reflectively, it will fail at runtime unless you’ve taken special precautions.
- **The code required to perform reflective access is clumsy and verbose.** It is tedious to write and difficult to read.
- **Performance suffers.** Reflective method invocation is much slower than normal method invocation. Exactly how much slower is hard to say, as there are many factors at work. On my machine, invoking a method with no input parameters and an int return was eleven times slower when done reflectively.

**You can obtain many of the benefits of reflection while incurring few of its costs by using it only in a very limited form.** 

If you are writing a program that has to work with classes unknown at compile time, you should, if at all possible, use reflection only to instantiate objects, and access the objects using some interface or superclass that is known at compile time.

### Item 66: Use native methods judiciously

Native methods are used to write performance-critical parts of applications in native languages for improved performance.

**It is rarely advisable to use native methods for improved performance.** I

decrease performance    降低性能

It is rare that you need to use them for improved performance. If you must use native methods to access low-level resources or native libraries, use as little native code as possible and test it thoroughly.

A single bug in the native code can corrupt your entire application.

### Item 67: Optimize judiciously

 **good programs rather than fast ones.** 

**Strive to avoid design decisions that limit performance.** 

**Consider the performance consequences of your API design decisions.** 

 **It is a very bad idea to warp an API to achieve good performance.** 

**measure performance before and after each attempted optimization.** 

The first step is to examine your choice of algorithms: no amount of low-level optimization can make up for a poor choice of algorithm. Repeat this process as necessary, measuring the performance after every change, until you’re satisfied.

### Item 68: Adhere to generally accepted naming conventions

naming conventions fall into two categories: typographical and grammatical.

Package and module names should be hierarchical with the components separated by periods.

The name of any package that will be used outside your organization should begin with your organization’s Internet domain name with the components reversed, for example, edu.cmu, com.google

Components should be short, generally eight or fewer characters.

util rather than utilities

 first letter capitalized

constant fields：

 whose names should consist of one or more uppercase words separated by the underscore character, for example, VALUES or NEGATIVE_INFINITY.

Type parameter names usually consist of a single letter. 

T for an arbitrary type, E for the element type of a collection, K and V for the key and value types of a map, and X for an exception.

For quick reference, the following table shows examples of typographical conventions.

| Identifier Type    | Example                                              |
| ------------------ | ---------------------------------------------------- |
| Package or module  | `org.junit.jupiter.api`, `com.google.common.collect` |
| Class or Interface | Stream, FutureTask, LinkedHashMap,HttpClient         |
| Method or Field    | remove, groupingBy, getCrc                           |
| Constant Field     | MIN_VALUE, NEGATIVE_INFINITY                         |
| Local Variable     | i, denom, houseNum                                   |
| Type Parameter     | T, E, K, V, X, R, U, V, T1, T2                       |

Instantiable classes, including enum types, are generally named with a singular noun or noun phrase, such as Thread, PriorityQueue, or ChessPiece.

Non-instantiable utility classes (Item 4) are often named with a plural noun, such as Collectors or Collections.

Interfaces are named like classes, for example, Collection or Comparator, or with an adjective ending in able or ible, for example, Runnable, Iterable, or Accessible.

Instance methods that convert the type of an object, returning an independent object of a different type, are often called toType, for example, toString or toArray

Methods that return a view (Item 6) whose type differs from that of the receiving object are often called asType, for example, asList. 

Methods that return a primitive with the same value as the object on which they’re invoked are often called typeValue, for example, intValue.

Common names for static factories include from, of, valueOf, instance, getInstance, newInstance, getType, and newType.

## Chapter 10. Exceptions

best advantage, exceptions can improve a program’s readability, reliability, and maintainability. 

### Item 69: Use exceptions only for exceptional conditions

**Exceptions are, as their name implies, to be used only for exceptional conditions; they should never be used for ordinary control flow.** 

- Because exceptions are designed for exceptional circumstances, there is little incentive for JVM implementors to make them as fast as explicit tests.
- Placing code inside a try-catch block inhibits certain optimizations that JVM implementations might otherwise perform.
- The standard idiom for looping through an array doesn’t necessarily result in redundant checks. Many JVM implementations optimize them away.

 **A well-designed API must not force its clients to use exceptions for ordinary control flow.**

 For example, the Iterator interface has the state-dependent method next and the corresponding state-testing method hasNext. This enables the standard idiom for iterating over a collection with a traditional for loop (as well as the for-each loop, where the hasNext method is used internally):

### Item 70: Use checked exceptions for recoverable conditions and runtime exceptions for programming errors

Java provides three kinds of throwables: checked exceptions, runtime exceptions, and errors.

**use checked exceptions for conditions from which the caller can reasonably be expected to recover.** 

There are two kinds of unchecked throwables: runtime exceptions and errors. 

If a program throws an unchecked exception or an error, it is generally the case that recovery is impossible and continued execution would do more harm than good. 

**Use runtime exceptions to indicate programming errors.** 

**all of the unchecked throwables you implement should subclass RuntimeException** (directly or indirectly).

Provide methods on your checked exceptions to aid in recovery.

### Item 71: Avoid unnecessary use of checked exceptions

### Item 72: Favor the use of standard exceptions

The most commonly reused exception type is IllegalArgumentException

Another commonly reused exception is IllegalStateException. 

Another reusable exception is ConcurrentModificationException. 

A last standard exception of note is UnsupportedOperationException. 

**Do not reuse Exception, RuntimeException, Throwable, or Error directly.** 

| Exception                       | Occasion for Use                                             |
| ------------------------------- | ------------------------------------------------------------ |
| IllegalArgumentException        | Non-null parameter value is inappropriate（非空参数值不合适） |
| IllegalStateException           | Object state is inappropriate for method invocation（对象状态不适用于方法调用） |
| NullPointerException            | Parameter value is null where prohibited（禁止参数为空时仍传入 null） |
| IndexOutOfBoundsException       | Index parameter value is out of range（索引参数值超出范围）  |
| ConcurrentModificationException | Concurrent modification of an object has been detected where it is prohibited（在禁止并发修改对象的地方检测到该动作） |
| UnsupportedOperationException   | Object does not support method（对象不支持该方法调用）       |

### Item 73: Throw exceptions appropriate to the abstraction

**higher layers should catch lower-level exceptions and, in their place, throw exceptions that can be explained in terms of the higher-level abstraction.** 

This idiom is known as exception translation

```java
// Exception Translation
try {
    ... // Use lower-level abstraction to do our bidding
} catch (LowerLevelException e) {
    throw new HigherLevelException(...);
}


/**
* Returns the element at the specified position in this list.
* @throws IndexOutOfBoundsException if the index is out of range
* ({@code index < 0 || index >= size()}).
*/
public E get(int index) {
    ListIterator<E> i = listIterator(index);
    try {
        return i.next();
    }
    catch (NoSuchElementException e) {
        throw new IndexOutOfBoundsException("Index: " + index);
    }
}
```

A special form of exception translation called exception chaining is called for in cases where the lower-level exception might be helpful to someone debugging the problem that caused the higher-level exception. 

```java
// Exception Chaining
try {
    ... // Use lower-level abstraction to do our bidding
}
catch (LowerLevelException cause) {
    throw new HigherLevelException(cause);
}


// Exception with chaining-aware constructor
class HigherLevelException extends Exception {
    HigherLevelException(Throwable cause) {
        super(cause);
    }
}
```

**While exception translation is superior to mindless propagation of exceptions from lower layers, it should not be overused.** 

### Item 74: Document all exceptions thrown by each method

**Always declare checked exceptions individually, and document precisely the conditions under which each one is thrown** using the Javadoc @throws tag. 

**Use the Javadoc @throws tag to document each exception that a method can throw, but do not use the throws keyword on unchecked exceptions.** 

**If an exception is thrown by many methods in a class for the same reason, you can document the exception in the class’s documentation comment** rather than documenting it individually for each method. 

document every exception that can be thrown by each method that you write. This is true for unchecked as well as checked exceptions, and for abstract as well as concrete methods. 

### Item 75: Include failure capture information in detail messages

**To capture a failure, the detail message of an exception should contain the values of all parameters and fields that contributed to the exception.** 

**do not include passwords, encryption keys, and the like in detail messages.**

```java
/**
* Constructs an IndexOutOfBoundsException.
**
@param lowerBound the lowest legal index value
* @param upperBound the highest legal index value plus one
* @param index the actual index value
*/
public IndexOutOfBoundsException(int lowerBound, int upperBound, int index) {
    // Generate a detail message that captures the failure
    super(String.format("Lower bound: %d, Upper bound: %d, Index: %d",lowerBound, upperBound, index));
    // Save failure information for programmatic access
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.index = index;
}
```



### Item 76: Strive for failure atomicity（尽力保证故障原子性）

**Generally speaking, a failed method invocation should leave the object in the state that it was in prior to the invocation.** 

There are several ways to achieve this effect. 

1. The simplest is to design immutable objects (Item 17). 
2. For methods that operate on mutable objects, the most common way to achieve failure atomicity is to check parameters for validity before performing the operation (Item 49). 
3. A closely related approach to achieving failure atomicity is to order the computation so that any part that may fail takes place before any part that modifies the object.
4. A third approach to achieving failure atomicity is to perform the operation on a temporary copy of the object and to replace the contents of the object with the temporary copy once the operation is complete. 
5. A last and far less common approach to achieving failure atomicity is to write recovery code that intercepts a failure that occurs in the midst of an operation, and causes the object to roll back its state to the point before the operation began. 

 as a rule, any generated exception that is part of a method’s specification should leave the object in the same state it was in prior to the method invocation.

### Item 77: Don’t ignore exceptions

**An empty catch block defeats the purpose of exceptions,** 

**If you choose to ignore an exception, the catch block should contain a comment explaining why it is appropriate to do so, and the variable should be named ignored**



## Chapter 11. Concurrency

### Item 78: Synchronize access to shared mutable data（对共享可变数据的同步访问）

The synchronized keyword ensures that only a single thread can execute a method or block at one time. 

**Synchronization is required for reliable communication between threads as well as for mutual exclusion.**

**Do not use Thread.stop.** 

```java
// Properly synchronized cooperative thread termination
public class StopThread {
    private static boolean stopRequested;

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested())
            i++;
        });

        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }
}
```

 **Synchronization is not guaranteed to work unless both read and write operations are synchronized.** 

```java
// Cooperative thread termination with a volatile field
public class StopThread {
    private static volatile boolean stopRequested;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
        int i = 0;
        while (!stopRequested)
            i++;
    });

    backgroundThread.start();
    TimeUnit.SECONDS.sleep(1);
    stopRequested = true;
    }
}
```



**confine mutable data to a single thread.** 

There are many ways to safely publish an object reference: you can store it in a static field as part of class initialization; you can store it in a volatile field, a final field, or a field that is accessed with normal locking; or you can put it into a concurrent collection

**when multiple threads share mutable data, each thread that reads or writes the data must perform synchronization.** 

### Item 79: Avoid excessive synchronization（避免过度同步）

**To avoid liveness and safety failures, never cede control to the client within a synchronized method or block.**

**As a rule, you should do as little work as possible inside synchronized regions.** 

lock splitting, lock striping, and nonblocking concurrency control.

to avoid deadlock and data corruption, never call an alien method from within a synchronized region. 

### Item 80: Prefer executors, tasks, and streams to threads（Executor、task、流优于直接使用线程）

```java
ExecutorService exec = Executors.newSingleThreadExecutor();

Here is how to submit a runnable for execution:
exec.execute(runnable);

And here is how to tell the executor to terminate gracefully (if you fail to do this,it is likely that your VM will not exit):
exec.shutdown();
```

For a small program, or a lightly loaded server, Executors.newCachedThreadPool is generally a good choice because it demands no configuration and generally “does the right thing.”

In a heavily loaded production server, you are much better off using Executors.newFixedThreadPool, which gives you a pool with a fixed number of threads, or using the ThreadPoolExecutor class directly, for maximum control.

### Item 81: Prefer concurrency utilities to wait and notify（并发实用工具优于 wait 和 notify）

**Given the difficulty of using wait and notify correctly, you should use the higher-level concurrency utilities instead.**

The higher-level utilities in java.util.concurrent fall into three categories: the Executor Framework, which was covered briefly in Item 80; concurrent collections; and synchronizers.

**it is impossible to exclude concurrent activity from a concurrent collection; locking it will only slow the program.**

 **use ConcurrentHashMap in preference to Collections.synchronizedMap.**

 Simply replacing synchronized maps with concurrent maps can dramatically increase the performance of concurrent applications.

Synchronizers are objects that enable threads to wait for one another, allowing them to coordinate their activities. 

The most commonly used synchronizers are CountDownLatch and Semaphore. 

Less commonly used are CyclicBarrier and Exchanger. 

The most powerful synchronizer is Phaser.

**For interval timing, always use System.nanoTime rather than System.currentTimeMillis.**

standard idiom for using the wait method: 

```java
// The standard idiom for using the wait method
synchronized (obj) {
    while (<condition does not hold>)
        obj.wait(); // (Releases lock, and reacquires on wakeup)
    ... // Perform action appropriate to condition
}
```

**Always use the wait loop idiom to invoke the wait method; never invoke it outside of a loop.**

There are several reasons a thread might wake up when the condition does not hold:

- Another thread could have obtained the lock and changed the guarded state between the time a thread invoked notify and the waiting thread woke up.
- Another thread could have invoked notify accidentally or maliciously when the condition did not hold. Classes expose themselves to this sort of mischief by waiting on publicly accessible objects. Any wait in a synchronized method of a publicly accessible object is susceptible to this problem.
- The notifying thread could be overly “generous” in waking waiting threads. For example, the notifying thread might invoke notifyAll even if only some of the waiting threads have their condition satisfied.
- The waiting thread could (rarely) wake up in the absence of a notify. This is known as a spurious wakeup

**There is seldom, if ever, a reason to use wait and notify in new code.**

### Item 82: Document thread safety（文档应包含线程安全属性）

**The presence of the synchronized modifier in a method declaration is an implementation detail, not a part of its API.**

**To enable safe concurrent use, a class must clearly document what level of thread safety it supports.** 

- **Immutable** —Instances of this class appear constant. No external synchronization is necessary. Examples include String, Long, and BigInteger (Item 17).
- **Unconditionally thread-safe** —Instances of this class are mutable, but the class has sufficient internal synchronization that its instances can be used concurrently without the need for any external synchronization. Examples include AtomicLong and ConcurrentHashMap.
- **Conditionally thread-safe** —Like unconditionally thread-safe, except that some methods require external synchronization for safe concurrent use. Examples include the collections returned by the Collections.synchronized wrappers, whose iterators require external synchronization.
- **Not thread-safe** —Instances of this class are mutable. To use them concurrently, clients must surround each method invocation (or invocation sequence) with external synchronization of the clients’ choosing. Examples include the general-purpose collection implementations, such as ArrayList and HashMap.
- **Thread-hostile** —This class is unsafe for concurrent use even if every method invocation is surrounded by external synchronization. Thread hostility usually results from modifying static data without synchronization. No one writes a thread-hostile class on purpose; such classes typically result from the failure to consider concurrency. When a class or method is found to be thread-hostile, it is typically fixed or deprecated. The generateSerialNumber method in Item 78 would be thread-hostile in the absence of internal synchronization, as discussed on page 322.



 **Lock fields should always be declared final.**

The private lock object idiom can be used only on unconditionally thread-safe classes. 

 every class should clearly document its thread safety properties with a carefully worded prose description or a thread safety annotation. 

### Item 83: Use lazy initialization judiciously（明智地使用延迟初始化）

As is the case for most optimizations, the best advice for lazy initialization is “don’t do it unless you need to” 

**Under most circumstances, normal initialization is preferable to lazy initialization.** 

**If you use lazy initialization to break an initialization circularity, use a synchronized accessor** 

**If you need to use lazy initialization for performance on a static field, use the lazy initialization holder class idiom.** 

```java
// Lazy initialization holder class idiom for static fields
private static class FieldHolder {
    static final FieldType field = computeFieldValue();
}
private static FieldType getField() { return FieldHolder.field; }
```

**If you need to use lazy initialization for performance on an instance field, use the double-check idiom.** 

```java
// Double-check idiom for lazy initialization of instance fields
private volatile FieldType field;
private FieldType getField() {
    FieldType result = field;
    if (result == null) { // First check (no locking)
        synchronized(this) {
            if (field == null) // Second check (with locking)
                field = result = computeFieldValue();
        }
    }
    return result;
}
```



### Item 84: Don’t depend on the thread scheduler（不要依赖线程调度器）

**Any program that relies on the thread scheduler for correctness or performance is likely to be nonportable.**

**Threads should not run if they aren’t doing useful work.** 

**resist the temptation to “fix” the program by putting in calls to Thread.yield.** 

 **Thread.yield has no testable semantics.** 

**Thread priorities are among the least portable features of Java.** 



## Chapter 12. Serialization

### Item 85: Prefer alternatives to Java serialization（优先选择 Java 序列化的替代方案）

RMI (Remote Method Invocation)

 JMX (Java Management Extension)

 JMS (Java Messaging System). 

Deserialization of untrusted streams can result in remote code execution (RCE), denial-of-service (DoS)

**The best way to avoid serialization exploits is never to deserialize anything.** 

**There is no reason to use Java serialization in any new system you write.** 

The most significant differences between JSON and protobuf are that JSON is text-based and human-readable, whereas protobuf is binary and substantially more efficient; 

**never deserialize untrusted data.**

**Prefer whitelisting to blacklisting,** 

serialization is dangerous and should be avoided.

### Item 86: Implement Serializable with great caution

**A major cost of implementing Serializable is that it decreases the flexibility to change a class’s implementation once it has been released.**

A simple example of the constraints on evolution imposed by serializability concerns stream unique identifiers, more commonly known as serial version UIDs. Every serializable class has a unique identification number associated with it. If you do not specify this number by declaring a static final long field named serialVersionUID, the system automatically generates it at runtime by applying a cryptographic hash function (SHA-1) to the structure of the class. This value is affected by the names of the class, the interfaces it implements, and most of its members, including synthetic members generated by the compiler. If you change any of these things, for example, by adding a convenience method, the generated serial version UID changes. If you fail to declare a serial version UID, compatibility will be broken, resulting in an InvalidClassException at runtime.

**A second cost of implementing Serializable is that it increases the likelihood of bugs and security holes**

**A third cost of implementing Serializable is that it increases the testing burden associated with releasing a new version of a class.** 

**Implementing Serializable is not a decision to be undertaken lightly.** 

**Classes designed for inheritance (Item 19) should rarely implement Serializable, and interfaces should rarely extend it.** 

```java
// readObjectNoData for stateful extendable serializable classes
private void readObjectNoData() throws InvalidObjectException {
    throw new InvalidObjectException("Stream data required");
}
```

**Inner classes (Item 24) should not implement Serializable.** 

### Item 87: Consider using a custom serialized form

**Do not accept the default serialized form without first considering whether it is appropriate.** 

**The default serialized form is likely to be appropriate if an object’s physical representation is identical to its logical content.**

```java
// Good candidate for default serialized form
public class Name implements Serializable {
    /**
    * Last name. Must be non-null.
    * @serial
    */
    private final String lastName;

    /**
    * First name. Must be non-null.
    * @serial
    */
    private final String firstName;

    /**
    * Middle name, or null if there is none.
    * @serial
    */
    private final String middleName;
    ... // Remainder omitted
}
```

**Even if you decide that the default serialized form is appropriate, you often must provide a readObject method to ensure invariants and security.** 

**Using the default serialized form when an object’s physical representation differs substantially from its logical data content has four disadvantages:**

- **It permanently ties the exported API to the current internal representation.** In the above example, the private StringList.Entry class becomes part of the public API. If the representation is changed in a future release, the StringList class will still need to accept the linked list representation on input and generate it on output. The class will never be rid of all the code dealing with linked list entries, even if it doesn’t use them anymore.

- **It can consume excessive space.** In the above example, the serialized form unnecessarily represents each entry in the linked list and all the links. These entries and links are mere implementation details, not worthy of inclusion in the serialized form. Because the serialized form is excessively large, writing it to disk or sending it across the network will be excessively slow.
- **It can consume excessive time.** The serialization logic has no knowledge of the topology of the object graph, so it must go through an expensive graph traversal. In the example above, it would be sufficient simply to follow the next references.
- **It can cause stack overflows.** The default serialization procedure performs a recursive traversal of the object graph, which can cause stack overflows even for moderately sized object graphs. Serializing a StringList instance with 1,000–1,800 elements generates a StackOverflowError on my machine. Surprisingly, the minimum list size for which serialization causes a stack overflow varies from run to run (on my machine). The minimum list size that exhibits this problem may depend on the platform implementation and command-line flags; some implementations may not have this problem at all.

**Before deciding to make a field nontransient, convince yourself that its value is part of the logical state of the object.** 

**you must impose any synchronization on object serialization that you would impose on any other method that reads the entire state of the object.** 

```java
// writeObject for synchronized class with default serialized form
private synchronized void writeObject(ObjectOutputStream s) throws IOException {
    s.defaultWriteObject();
}
```

**Regardless of what serialized form you choose, declare an explicit serial version UID in every serializable class you write.** 

```java
private static final long serialVersionUID = randomLongValue;
```

It is not required that serial version UIDs be unique. 

**Do not change the serial version UID unless you want to break compatibility with all existing serialized instances of a class.**

 if you have decided that a class should be serializable (Item 86), think hard about what the serialized form should be. Use the default serialized form only if it is a reasonable description of the logical state of the object; otherwise design a custom serialized form that aptly describes the object. 

### Item 88: Write readObject methods defensively

**When an object is deserialized, it is critical to defensively copy any field containing an object reference that a client must not possess.** 

```java
// readObject method with defensive copying and validity checking
private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    s.defaultReadObject();
    // Defensively copy our mutable components
    start = new Date(start.getTime());
    end = new Date(end.getTime());
    // Check that our invariants are satisfied
    if (start.compareTo(end) > 0)
        throw new InvalidObjectException(start +" after "+ end);
}
```

 anytime you write a readObject method, adopt the mindset that you are writing a public constructor that must produce a valid instance regardless of what byte stream it is given.

- For classes with object reference fields that must remain private, defensively copy each object in such a field. Mutable components of immutable classes fall into this category.

- Check any invariants and throw an InvalidObjectException if a check fails. The checks should follow any defensive copying.
- If an entire object graph must be validated after it is deserialized, use the ObjectInputValidation interface (not discussed in this book).
- Do not invoke any overridable methods in the class, directly or indirectly.

### Item 89: For instance control, prefer enum types to readResolve

**if you depend on readResolve for instance control, all instance fields with object reference types must be declared transient.** 

**The accessibility of readResolve is significant.** 

use enum types to enforce instance control invariants wherever possible. 

### Item 90: Consider serialization proxies instead of serialized instances

serialization proxy

```java
// Serialization proxy for Period class
private static class SerializationProxy implements Serializable {
    private final Date start;
    private final Date end;
    SerializationProxy(Period p) {
        this.start = p.start;
        this.end = p.end;
    }
    private static final long serialVersionUID =234098243823485285L; // Any number will do (Item 87)
}


// writeReplace method for the serialization proxy pattern
private Object writeReplace() {
    return new SerializationProxy(this);
}
```

Consider the serialization proxy pattern whenever you find yourself having to write a readObject or writeObject method on a class that is not extendable by its clients. This pattern is perhaps the easiest way to robustly serialize objects with nontrivial invariants.


# JVM

由于跨平台的设计，java指令都是根据栈来设计的

不同平台CPU架构不同，所以不能设计为给予寄存器的

栈：

跨平台性，指令集小，指令多；执行性能比寄存器差

javap -v  file   反编译

##### 虚拟机的启动

Java虚拟机的启动是通过引导类加载器(bootstrap class loader)创建一个初始类（initial class）来完成的，这个类是由虚拟机的具体实现指定的。

##### 虚拟机的执行

- 一个运行中的Java虚拟机有着清晰的任务： 执行Java程序
- 程序开始执行时他才运行，程序结束时他就停止
- 执行一个所谓的Java程序的时候，真正在执行的是一个叫做Java虚拟机的进程

jps打印当前程序进程

##### 虚拟机的退出

- 程序正常执行结束
- 程序在执行的过程中遇到了异常或错误而异常终止
- 某线程调用Runtime类或者System类的exit方法，或Runtime类的halt方法，并且Java安全管理器也允许这次exit或halt操作
- 除此之外，JNI（Java Native Interface）规范描述了用JNI Invocation API来加载或者卸载Java虚拟机时，Java虚拟机的退出情况
# Java Question

1. 将字符串“2018-10-08”转换为对应的java.sql.Date类的对象

   ```java
   //实例化
   DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-DD");
   //解析    
   dft.parse()
   ```

#### 
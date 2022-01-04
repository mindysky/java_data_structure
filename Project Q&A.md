# Project Q&A

##### IDEA的maven项目报错：Disconnected from the target VM, address: ‘127.0.0.1:52315’, transport: ‘socket’

1. 端口号占用:

   - ```nginx
     taskkill /f /im java.exe
     
     netstat -ano|findstr 8110
     
     //查看所有端口的占用情况
     netstat -ano 
     ```

2. 破解Jrebel:

   ```java
   1. generate GUI
   https://www.guidgen.com/
   
   2. add guigen to the url below:
   https://jrebel.qekang.com/
   ```


3. debug时启动类错误，找到启动类选择debug启动

4. pom.xml缺少web包, 或缺少tomcat包

   ```
   <dependency>
   	<groupId>org.springframework.boot</groupId>
   	<artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   ```

   




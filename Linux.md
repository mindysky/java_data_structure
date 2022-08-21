# Linux

```
ps -ef   所有进程
pwd 
ls -f
top 
tail
touch
cat
vi
vim


```

#### 用户管理

##### 添加用户：

useradd  jack

useradd -d /home/hello  tom

##### 设置密码：

passwd  用户名

##### 删除用户： 

userdel 用户名

1. 删除用户，保留Home目录： userdel 用户名
2. 删除用户，同时删除用户主目录： userdel -r 用户名

实际开发中保留家目录

##### 查询切换用户

cd /home/

id root

当用户不存在，返回“无此用户”

##### 切换用户

su - 切换用户名

exit退出当前用户

查看当前用户： whoami

##### 用户管理：组的管理

###### 增加组

groupadd  groupName

###### 删除组

groupdel   groupName

增加用户时直接加上组

useradd -g usergroup  username

###### 修改用户组

usermod -g usergroup  username

#### 实用指令

##### 运行级别

0 : 关机

1：单用户

2：多用户无网络服务

3：多用户有网络服务

4：保留

5：图形界面

6：重启

系统运行级别的配置文件   run level

/etc/inittab

切换到指定运行级别的指令

init [0123456]

##### 如何找回丢失的root密码

进入到单用户模式，然后修改密码

因为进入单用户模式，root不需要密码就可以登录

开始在引导时输入回车键

看到界面输入小e

再看到一个新界面，选第二行编辑内核kenel

再输入小e

再这行最后输入1

再输入回车，确认输入b

进入到单用户模式

##### 帮助指令

man [命令/配置文件]

man ls 



help [命令]

help cd

##### 文件目录类指令

pwd: 显示当前目录的绝对路径

ls  -al  详情

 ls -lh 文件名   显示文件

cd ~  回到root目录

###### 创建目录：

创建多级目录

mkdir - p  /xxx/xxx/xx

###### 删除目录：

rmdir  dirName

rmdir [option] dirName

rmdir 删除的是空目录，目录下有内容就无法删除

rm -rf dirName  删除非空目录

##### touch指令 : 创建空文件

touch  fileName

touch helle.txt

##### cp指令： 拷贝文件到指定目录

cp [option] source dest

cp - r   递归拷贝

cp /home/aaa.txt  /home/bbb/

将整个目录拷贝到其他目录

cp  -r  test/ zwj/

使用细节：强制覆盖

- 强制覆盖不提示的方法 \cp -r test/ zwj/

##### rm指令

rm -r  递归删除整个文件夹

rm -f  强制删除，不提示

rm -rf  强制删除整个目录

##### mv指令

mv移动文件与目录或重命名

mv oldName  newName   重命名

mv /temp/moveFile /targetFolder   移动文件

##### cat指令  查看文件内容

cat -n  fileName  显示行号，显示文件

是以只读的方式打开，不能修改，为了浏览方便，会加上管道命令 | more

cat -n fileName  | more

按空格查看下一页

##### more指令

空格键 向下翻页

enter 逐行显示

q 代表立刻离开more, 不再显示该文件内容

ctrl+F  向下滚动一屏

ctrl+B  返回上一屏

=  输出当前行的行号

:f  输出文件名和当前行的行号

##### less指令

用来分屏查看

空格键 向下翻页

enter 逐行显示

q 代表立刻离开less, 不再显示该文件内容

pagedown  向下滚动一屏

pageup  返回上一屏

/字串  向下搜索【字串】的功能，n: 向下查找 N：向上查找

？字串  向上搜索【字串】的功能，n: 向下查找 N：向上查找

##### >指令 : 输出重定向， 会将原来的文件的内容覆盖

##### >>指令 : 追加，不会覆盖原来的文件，而是追加到文件的尾部

ls -l > 文件  ， 列表内容写入到文件中，覆盖写

ls -al >> 文件 ， 列表内容追加到末尾

cat 文件1 > 文件2  将文件1的内容覆盖到文件2

echo “content” >> 文件  将内容写入到文件



##### echo指令

echo输出内容到控制台

echo [opt] [content]

echo $PATH  输出当前环境变量路径

echo "hello"  直接输出文本

##### head指令

显示文件的开头部分，默认head指令显示文件的前10行

head  fileName  查看文件前10行

head -n 5 fileName  查看前5行

##### tail指令

显示文件的尾部部分内容，默认tail指令显示文件的后10行

tail  fileName  查看文件后10行

tail -n 5 fileName  查看后5行

tail -f fileName  实时追踪该文档的所有更新

##### ln指令

软链接，符号链接，类似windows的快捷方式，主要是存放了链接其他文件的路径

ln -s /[源文件目录/]/[软链接/]

ln -s /root linkToRoot

cd linkToRoot  --- to root directory

pwd  ---/home/linkToRoot

rm -rf linkToRoot 删除软链接不能带/， 否则提示资源忙

##### history指令

查看已经执行过得历史命令，也可以执行历史指令

history  显示所有执行过得指令

history   10 显示最近10个

!178   执行历史编号为178的指令

#### 时间日期类

##### date指令

显示当前日期

date 显示当前时间

date+%Y  显示当前年份

date+%m 显示当前月

date+%d  显示当前日

date “+%Y %m %d" 显示年月日

date  “+%Y %m %d   %H:%M:%S"  显示年月日时分秒

##### 设置日期

date -s  字符串时间

date -s "2018-10-10 11:20:20"

##### cal指令

查看日历

cal  [opt]

cal 2020  显示一年的日历

##### find指令

find指令将从指定目录向下递归地遍历各个子目录，将满足条件的文件或者目录显示在终端

find [搜索范围] [选项]

find /home -name *.txt  --- 按名字搜索/home下的.txt文件

find /opt -user nobody  

find / -size +20M   查找大于20M的文件

find / -size -20M   查找小于20M的文件

find / -size 20M   查找等于20M的文件 

##### locate指令

可以快速定位文件路径

locate 搜索文件   由于locate指令基于数据库进行查询，第一次运行前，必须使用updatedb指令创建locate数据库。

##### grep指令 和管道符号 |

grep 过滤查找 , 管道符号 | , 表示将前一个命令的处理结果输出传递给后面的命令处理

grep [opt] 查找内容 源文件

- -n  显示匹配行及行号
- -i  忽略字母大小写

cat hello.txt | grep -n yes  区分大小写，显示行号

cat hello.txt | grep -ni yes  显示行号，不区分大小写

##### 压缩和解压指令

##### gzip/gunzip

gzip  file   压缩文件，只能压缩为.gz文件   得到对应文件名带gz, 不会保留原来的文件

gunzip  file.gz    解压文件

##### zip/unzip

zip  [opt]  xxx.zip   压缩文件

​	-r 递归压缩

eg: zip -r  package.zip /home/    将home下的内容递归压缩到package.zip中

unzip [opt] xxx.zip  解压文件

​	-d <目录>   指定解压目录

eg: unzip -d  /opt/tmp/  package.zip 将package.zip解压到opt下的tmp目录下

##### tar指令

打包指令，最后打包的文件是.tar.gz文件

tar [opt]  xxx.tar.gz

​	-c  产生.tar打包文件

​	-v 显示详细信息

​	-f  指定压缩后的文件名

​	-z 打包同时压缩

​	-x 解包.tar文件

eg: 

压缩：

- tar -zcvf a.tar.gz  a1.txt a2.txt   将文件a1,a2压缩打包到a.tar.gz
- tar -zcvf myhome.tar.gz /home/  将home下的所有文件打包到myhome.tar.gz

解压

- tar -zxvf  a.tar.gz    解压到当前目录
- tar -zxvf myhome.tar.gz  -C /opt/tmp2/  解压到tmp2 , 指定解压的目录要存在

##### 组管理

###### 组的基本介绍

- 所有者
- 所在组
- 其他组
- 改变用户所在的组

文件/目录的所有者

一般为文件的创建者，谁创建了该文件，就自然成为该文件的所有者。

###### 查看文件的所有者：

- ls  -ahl  

###### 修改文件的所有者：

- chown 用户名  文件名

  chown  tom apple.txt

组的创建

groupadd 组名

groupadd monster

useradd -g monster fox  添加用户fox到组monster

###### 修改文件所在组

chgrp 组名称 文件名

chgrp monster orange.txt

###### 改变用户所在组：

- usermod  -g  组名  用户名

  usermod  -d  目录名  用户名  改变该用户登录的初始目录 

##### 权限管理

-rw-r--r--

文件类型

-普通文件

d目录

l软链接

c字符设备：键盘，鼠标

b块文件，硬盘

rw-  文件所有者的权限rw 、  rwx

r-- 文件所在组的用户的权限， 只有读的权限

r--表示文件其他组用户的权限，只有制度的权限

###### 0-9位说明：

1. 第0位确定文件类型 (d,-,l,c,b)
2. 第1-3位确定所有者(该文件所有者)拥有该文件的权限 --user
3. 第4-6位确定所属组（同用户组的）拥有该文件的权限 --group
4. 第7-9位确定其他用户拥有该文件的权限 --other

###### rwx作用到文件

1. r: 可读取，查看
2. w: 可以修改，但是不代表可以删除该文件，删除一个文件的前提条件是该文件所在的目录有读写权限，才能删除该文件
3. x: 可以被执行

###### rwx作用到目录

1. r： 代表可读取，ls查看目录内容
2. w：代表可以修改，目录内创建，删除，重命名目录
3. x：代表可以进入该目录

##### 修改权限chmod

修改文件或者目录权限

user - u, group - g, other -o  ， a所有人（u,g,o）

-R 递归修改文件权限

chmod u=rwx，g=rx，o=x  文件目录名

chmod o+w 文件目录名   增加权限

chmod a-x  文件目录名   减少权限

##### 通过数字变更权限

规则： r=4, w=2, x=1

rwx = 4+2+1 = 7

r-x = 4+1 =5

rw- = 4+2 = 6

chmod 756 文件目录名

##### 修改文件所有者-chown

chown newowner file 改变文件的所有者

chown newowner:newgroup  file   改变用户的所有者和所有组

-R 如果是目录，则使其下所有子文件或目录递归生效

##### 改变文件所在组-chgrp

chgrp newgroup file  改变文件所在组

--------------

##### crond任务调度

crontab进行定时任务的设置

crontab [option]

- -e 编辑crontab定时任务
- -l  列出当前crontab定时任务
- -r 终止所有的定时任务
- service crond restart  重启任务调度

#### 磁盘分区

##### 分区的方式

1. mbr分区

   - 最多支持四个主分区
   - 系统只能安装在主分区
   - 扩展分区要占一个主分区
   - MBR最大只支持2TB，但拥有最好的兼容性

2. gtp分区

   - 支持无线多个主分区（但操作系统可能限制，比如windows下最多128个分区）
   - 最大支持18EB的大容量（1EB=1024PB， 1PB=1024TB）
   - windows7 64位以后支持gtp

   ##### linux分区

   1. Linux来说无论有几个分区，分给哪一个目录使用，它归根结底就只有一个根目录，一个独立且唯一的文件结构，linux中每个分区都是用来组成整个文件系统的一部分。
   2. Linux采用了一种叫“载入”的处理方法，它的整个文件系统中包含了一整套的文件和目录，且将一个分区和一个目录联系起来，这时要载入的一个分区将它的存储空间在一个目录下获得。

   ##### 硬盘的说明

   1. linux硬盘分IDE硬盘和SCSI硬盘，目前基本上是SCSI硬盘
   2. 对于IDE硬盘，驱动器标识符为 ‘hdx~’ , 其中，‘hd’表明分区所在设备的类型，这里是指IDE硬盘了。‘x’为盘号，（a为基本盘，b为基本从属盘，c为辅助主盘，d为辅助从属盘），‘~’代表分区，前四个分区用数字1到4表示，它们是主分区或扩展分区，从5开始就是逻辑分区。例，hda3表示为第一个IDE硬盘上的第三个主分区或扩展分区，hdb2表示为第二个IDE硬盘上的第二个主分区或扩展分区。
   3. 对于SCSI硬盘则标识为‘sdx~’, SCSI硬盘是用 ‘sd’来表示分区所在设备的类型的，其余则和IDE硬盘的表示方法一样。

   ##### lsblk -f  查看硬盘分区

   ##### 如何增加一块硬盘

   1. 虚拟机添加硬盘
   2. 分区 fdisk /dev/sdb
   3. 格式化  mkfs -t ext4 /dev/sdb1
   4. 挂载  先创建/home/newdisk   
      - mount /dev/sdb1   /home/newdisk   临时挂载
   5. 设置可以自动挂载 ， 永久挂载
      - vim /etf/fstab  修改fstab文件
        - /dev/sdb1   /home/newdisk  ext4 defaults 0 0
      - mount -a   自动挂载

   ##### 解除挂载

   umount /dev/sdb1  

##### 磁盘查询指令

查询系统整体磁盘使用情况

df  -lh  查看磁盘使用情况

du  -h /目录       查询指定目录占用磁盘的情况

​	-s  指定目录占用的大小汇总

​	-h 带计量单位

​	-a 含文件

​	--max-depth=1  子目录深度

​	-c 列出明细的同时，增加汇总值

du -ach --max-depth=1 /opt   汇总磁盘用量

##### 网络配置原理和说明

修改配置文件指定IP

vi  /etc/sysconfig/network-scripts/ifcfg-eth0

eth0 网卡的配置文件

ONBOOT = yes   启用Boot获取IP

BOOTPRORO=static   以静态方式获取IP

IPADDR=   设置IP

GATEWAY=    设置网关

DNS1=     DNS和网关保持一致

###### service network restart    重启网络服务

#### 进程管理

1. 在linux中，每个执行的程序（代码）都称为一个进程，每个进程都分配一个ID号。
2. 每个进程，都会对应一个父进程，而这个父进程可以复制多个子进程，例如www服务器。
3. 每个进程都可能以两种方式存在的。前台和后台，所谓前台进程就是用户目前屏幕上可以进行操作的。后台进程则是实际在操作，但由于屏幕上无法看到的进程，通常使用后台方式执行。
4. 一般系统服务都是以后台进程的方式存在，而且都会常驻在系统中，直到关机才结束。



###### 显示系统执行的进程

ps 命令用来查看目前系统中的进程

- -a 显示当前终端的所有进程信息
- -u 以用户的各式显示进程信息
- -x 显示后台进程运行的参数

ps -aux | more

ps -ef  以全格式显示当前的所有进程

-e 显示所有进程，-f 全格式

ps -ef |grep xxx  

##### 终止进程

kill -9  pid   表示强迫进程立即停止

kill [option]  进程号

killall  进程名称，也支持通配符，系统负载过大而变得很慢时非常有用

##### pstree [option]  查看进程树

- -p  显示进程的pid
- -u  显示进程的所属用户（用户ID）

#### 服务管理

service本质就是进程，但是运行在后台的，通常都会监听某个端口，等待其他程序的请求。比如（mysql,sshd防火墙等），因此我们又称为守护进程。是linux中国非常重要的知识点

service  服务名称  start / stop / restart / reload / status

service iptables status 查看防火墙状态

通过telnet 测试端口是否在监听

telnet ip  port 

###### 查看服务名称

- setup ->  系统服务
- /etc/init.d/服务名称
- ls -l   /etc/init.d/     列出系统有哪些服务



##### 运行级别runlevel

vi  /etc/inittab 修改

(1)运行级别0：系统停机状态，系统默认运行级别不能设为0，否则不能正常启动。其实就是关机。

(2)运行级别1：单用户工作状态，root权限，用于系统维护，禁止远程登陆.在忘记root密码时一般用这个运行级别，进去修改root密码。

(3)运行级别2：多用户状态(没有NFS)，没有网络连接。

(4)运行级别3：完全的多用户状态(有NFS)，登陆后进入控制台命令行模式。 linux很常见的运行级别

(5)运行级别4：系统未使用，保留。

(6)运行级别5：X11控制台，登陆后进入图形GUI模式。就是图形模式。

(7)运行级别6：系统正常关闭并重启，默认运行级别不能设为6，否则不能正常启动。

##### chkconfig指令

通过chkconfig指令可以给每个服务的各个运行级别设置自启动/关闭

1. 查看服务chkconfig --list | grep xxx
2. chkconfig 服务名 --list
3. chkconfig --level 5 服务名  on/off

##### 监控服务top

top与ps命令很相似。它们都是用来显示正在执行的进程。

top与ps最大的不同之处在于top在执行一段时间可以更新正在运行的进程。

top [option]

- -d 秒数，指定top命令每隔几秒更新，默认是3秒在top命令的交互模式当中可以执行的命令
- -i 使top不显示任何闲置或者僵死的进程
- -p 通过指定监控进程ID来仅仅监控某个进程的状态

##### 监控网络状态netstat

netstat [option]

- -an  按一定顺序排列输出
- -p 显示哪个进程在调用

查看所有的网络服务

netstat -anp | more

netstat -anp | grep sshd  过滤显示sshd 网络

#### rpm包管理

rpm redhat package manager

一种用于互联网下载包的打包和安装工具， 它包含在linux分发版中。

它生成具有.RPM扩展名的文件。

查看已安装的rpm 列表

rpm -qa |grep xx

rpm -qi 软件包名  查询软件安装包

rpm -ql firefox  查询软件包安装了哪些文件, 安装到哪里去了

rpm -qf  文件全路径名  查询文件输入哪个软件包

```linux
rpm [-acdhilqRsv][-b<完成阶段><套间档>+][-e<套件挡>][-f<文件>+][-i<套件档>][-p<套件档>＋][-U<套件档>][-vv][--addsign<套件档>+][--allfiles][--allmatches][--badreloc][--buildroot<根目录>][--changelog][--checksig<套件档>+][--clean][--dbpath<数据库目录>][--dump][--excludedocs][--excludepath<排除目录>][--force][--ftpproxy<主机名称或IP地址>][--ftpport<通信端口>][--help][--httpproxy<主机名称或IP地址>][--httpport<通信端口>][--ignorearch][--ignoreos][--ignoresize][--includedocs][--initdb][justdb][--nobulid][--nodeps][--nofiles][--nogpg][--nomd5][--nopgp][--noorder][--noscripts][--notriggers][--oldpackage][--percent][--pipe<执行指令>][--prefix<目的目录>][--provides][--queryformat<档头格式>][--querytags][--rcfile<配置档>][--rebulid<套件档>][--rebuliddb][--recompile<套件档>][--relocate<原目录>=<新目录>][--replacefiles][--replacepkgs][--requires][--resign<套件档>+][--rmsource][--rmsource<文件>][--root<根目录>][--scripts][--setperms][--setugids][--short-circuit][--sign][--target=<安装平台>+][--test][--timecheck<检查秒数>][--triggeredby<套件档>][--triggers][--verify][--version][--whatprovides<功能特性>][--whatrequires<功能特性>]
```

rmp -e rpm包名  卸载rpm 软件包

安装rpm包  

rpm -ivh rpm包名称

i=install

v=verbose 提示

h=hash 进度条

##### yum包管理

yum -list | grep xxx     列出rmp 安装包

安装yum包

yum install  xxx 下载安装

##### 安装JDK

配置环境变量

vim /etc/profile

末尾G

JAVA_HOME=/opt/jdk1.8

PATH=/opt/jdk1.8/bin:$PATH

export JAVA_HOME PATH    输出环境变量

注销用户，环境变量才能生效

如果在运行级别3， logout

如果在运行级别5，可退出command window

javac 编译

java 运行

##### 安装tomcat

1. 解压到/opt
2. 启动tomcat ./startup.sh
3. 开发端口 vim /etc/sysconfig/iptables
4. 测试是否安装成功： http://linuxip:8080

##### MySQL安装

rpm -qa | grep mysql  查询是否有mysql

rpm -e mysql_libs  普通删除

rpm -e --nodeps mysql_libs  强力删除

yum -y install make gcc-c++ cmake bison-devel ncurses-devel

#### Shell脚本

##### 预定义变量

$$  当前进程的进程号PID

$！ 后台运行的最后一个进程的进程号PID

$?  最后一次执行命令的返回状态，如果这个变量的值为0，证明上一个命令正确执行；

如果这个变量的值为非0，则证明上一个命令执行不正确了。

##### 运算符

$((运算式))  

$[运算式]      优先

expr m + n   expr运算符间要有空格

expr m - n  

expr  \* / %  乘， 除，取余

##### 条件判断

[ condition ]

非空返回true, 可使用$? 验证， 0为true, >1为false

##### 读取控制台内容

read(选项)(参数)



 












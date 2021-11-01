# Docker

1. linux 命令, Maven, git
2. Docker :   swarm/compose/machine/mesos/k8s/ci/cd jenkins

安装的时候，把原始环境一模一样地复制过来



#### What's Docker？

基于Go语言实现的云开源项目

主要目标： Build, ship and run in any app, anywhere    使用户App及其运行环境达到一次封装，到处运行

解决了运行环境和配置问题的软件容器，方便做持续集成并有助于整体发布的容器虚拟化技术。

#### What docker can do?

虚拟机(virtual machine)是带环境安装的一种解决方案

虚拟机缺点：资源占用多，冗余步骤多，启动慢

linux容器不是模拟一整个完整的操作系统，不需要捆绑一整套操作系统，只需要装需要的软件

Docker和传统虚拟机的不同：

- 传统虚拟技术是虚拟出一套硬件，在其上运行一个完整的操作系统，在该系统上再运行所需要的应用进程。
- 容器内的应用进程直接运行于宿主内核，容器内没有自己的内核，而且也没有进行硬件虚拟，因此容器壁传统虚拟机更为轻便。
- 每个容器互相隔离，每个容器都有自己的文件系统，容器之间进程不会相互影响，能区分计算资源

#### Why Docker

更轻量：基于容器的虚拟化，仅包含业务运行所需的runtime环境，CentOS/Ubuntu基础镜像仅170M，宿主机可部署100~1000个容器

更高效：无操作系统虚拟化开销

更敏捷、更灵活： 分层存储，包管理，devops,  支持多种网络配置

#### Docker 三要素

image 镜像  就是一个只读的模板，镜像可以用来创建Docker容器，一个镜像可以创建很多个容器

container  容器   ：  容器是用镜像创建的运行实例对象

repository  仓库:    集中存放镜像文件的场所

registry  注册服务器： 仓库注册服务器上可存放多个仓库，每个仓库包含多个镜像，每个镜像有不同的标签

最大的公开仓库Docker hub

国内镜像： 阿里云，网易云 

```shell
uname  用于打印当前系统相关信息
检测CentOS 版本
cat /etc/redhat-release

#install
yum 
yum install docker-io
systermctl start docker
docker version

//uninstall
sudo yum remove docker


https://aa25jngu.mirror.aliyuncs.com

ps -ef|grep docker

```

Docker有着比虚拟机更少的抽象层。

Docker利用的是宿主机的内核，不需要Guest OS。

当新建一个容器时，docker不需要像虚拟机一样重载一个操作系统内核。

#### Docker command

```shell
#help command
docker version
docker info
docker --help

#image command
docker images 
options: 
	-a   列出全部
	-qa  列出全部的镜像ID
	-q   只显示镜像ID
	--digests   显示镜像摘要信息
	--no-trunc 显示完整的镜像信息

#search
docker search 镜像名 https://xxxx.com
options:
	--no-trunc 显示完整的镜像信息
	-s number  列出收藏数不小于指定值的镜像   
	--automated 只列出automated build类型的镜像
eg: docker search -s 30 tomcat --automated

docker pull 镜像名
eg: docker pull tomcat  = docker pull tomcat:lastest

#delete images
docker rmi -f 镜像名:tag  
docker rmi -f 镜像ID
docker rmi 镜像名1:tag  镜像名2:tag    删除多个
docker rmi -f $(docker images -qa)   批量删除
	
#container commands
docker run hello-world  
options:
	-i  以交互模式运行容器，通常与-t同时使用
	-t  为容器重新分配一个伪输入终端，通过与-i一起使用
	-d  后台运行容器，并返回容器ID，也即启动守护式容器
	--name "new name"  别名
	-P  随机端口映射
	-p 指定端口映射
		ip:hostPort:containerPort
		ip::containerPort
		hostPort:containerPort
		containerPort
		
#列出全部容器	
docker ps
options:
	-a 列出所有正在运行的容器+历史上运行过的
	-l 显示最近创建的容器
    -n 显示最近n个创建的容器
    -q 静默模式，只显示容器编号
    --no-trunc 不截断输出

#退出容器
eixt 容器停止退出
ctrl+P+Q 容器不停止退出

#启动容器
docker start id/name

#重启容器
docker restart id/name

#停止容器
docker stop id/name

#强制停止容器
docker kill id/name

#删除已停止的容器
docker rm id/name  删除已关闭的
docker rm -f id/name  强制删除
docker rm -f $(docker ps -a -q)  删除多个
docker ps -a -q | xargs docker rm

#启动守护式容器
docker run -d name

#查看容器日志
docker logs -f -t --tail ID

#查看容器内运行的进程
docker top ID

#查看容器内的细节
docker inspect ID

#进入正在运行的容器并以命令行交互
docker exec -it ID /bin/bash bashShell
docker attach ID  重新进入容器
attach  直接进入容器启动命令的终端，不会启动新的进程
exec 是在容器中打开新的终端，并且可启动新的进程

#从容器内拷贝文件到主机上
docker pc 容器ID:容器内路徑 宿主機路徑

```

#### Docker Images

UnionFS(联合文件系统)： union文件系统是一种分层，轻量级并且高性能的文件系统，它支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一虚拟文件系统下（unite serveral directories into single virtual filesystem）.

Union文件系统是Docker镜像的基础，镜像可以通过分层来进行继承，基于基础镜像，可以制作各种具体的应用镜像。

Docker镜像的最底层是bootfs

rootfs(root file system), 在bootfs之上，包含Linux系统中的/dev,/proc,/bin, /etc等标准目录和文件。

rootfs就是各种不同操作系统发行版，比如CentOS ， Ubuntu等等。

对于不同的linux发行版本，bootfs基本一致，可以共用，rootfs不一样。

###### docker镜像为什么采用分层结构？

**资源共享**

容器层之下都是镜像层，镜像层层叠加

```shell
//根据已有镜像， 提交新的镜像
docker commit -a="xxx" -m="comment" containerID namespace/imageName
```

#### Docker容器数据卷

数据持久化+数据继承+数据共享

Docker容器产生的数据，如果不通过docker commit生成新的镜像，使得数据作为镜像的一部分保存下来，那么容器删除后，数据就丢失了。

为了能保存数据在docker中，我们使用卷。

数据卷就是目录或者文件，存在于一个或者多个容器中国，由docker挂载到容器，但不属于联合文件系统，因此能够绕过Union File System提供一些用于持久存储或共享数据的特性。

卷的设计目的就是数据的持久化，完全独立于容器的生命周期，因此Docker不会在容器删除时删除其挂载的数据卷。

特点：

1. 数据卷可在容器之间共享或者重用数据
2. 卷中的更改可以直接生效
3. 数据卷中的更改不会包含在镜像的更新中
4. 数据卷的声明周期一直持续到没有容器使用到它为止

```shell
容器内添加数据卷两种方法：
1. 直接命令添加
Docker run -it -v /宿主机的绝对目录:/容器内的目录  imageName --privileged=true
容器内卷只读
Docker run -it -v /宿主机的绝对目录:/容器内的目录 ro imageName

2. DockerFile添加
对镜像的描述文件
根目录新建mydocker文件夹
VOLUME[""]
```

##### 数据卷容器

--volumes-from

#### Dockerfile

Docker文件的构建文件

```shell
#dockerfile体系结构
FROM  基础镜像，当前新镜像是基于那个镜像的
MAINTAINER  镜像维护邮箱
ADD  将宿主机目录下的文件拷贝进镜像且ADD命令会自动处理URL和加压tar压缩包
COPY 类似ADD 拷贝文件和目录到镜像中，将从构建上下文目录<源路径>的文件/目录赋值到新的一层镜像内的<目标路径>
LABEL 
ENV  用来在构建镜像过程中设置环境变量
WORKDIR  指定在创建容器后，终端默认登录进来的工作目录，落脚点
RUN  容器构建时需要运行的命令
VOLUME 容器数据卷，用于数据的保存和持久化工作
ONBUILD   当构建一个被继承的dockerfile时运行命令，父镜像在被子继承后父镜像的onbuild被触发
ENTRYPOINT 指定一个容器启动时要运行的命令， entrypoint的目的和CMD医院，都是在指定容器启动程序及参数
EXPOSE 8080  当前容器对外暴露的端口
CMD   指定一个容器启动时要运行的命令, dockerfile中可以有多个CMD命令，但是只有最后一个生效，CMD会被Docker run之后的参数替换

#example
FROM centos
VOLUME ["/dataVolumeContainer1"]
CMD echo "finished"
CMD ["/bin/bash"]

#build
docker build -f /mydocker/Dockerfile -t mydocker/centos .

#install tool
RUN yum -y install vim
RUN yum -y install net-tools

#check history
docker history

#CMD/ENTRYPOINT
#ONBUILD



```






# Kubernetes

## 集群类型：

- 一主多从： 一台Master节点和多台Node节点，搭建简单，但是有单机故障风险，适用于测试环境
- 多主多从： 多台Master节点和多台Node节点，搭建麻烦，安全性高，适合用于生产环境

## 安装方式

- minikube: 一个用于快速搭建单节点kubernetes的工具
- kubeadm: 一个用于快速搭建kubernetes集群的工具
- 二进制包：从官网下载每个组件的二进制包，依次去安装，此方式对于理解kubernetes组件更加有效

## 主机规划

| 作用   | IP地址          | 操作系统                 | 配置                  |
| ------ | --------------- | ------------------------ | --------------------- |
| Master | 192.168.109.100 | Centos7.5 基础设施服务器 | 2核CPU 2G内存 50G硬盘 |
| Node1  | 192.168.109.101 | Centos7.5 基础设施服务器 | 2核CPU 2G内存 50G硬盘 |
| Node2  | 192.168.109.102 | Centos7.5 基础设施服务器 | 2核CPU 2G内存 50G硬盘 |

## 环境搭建

本次环境搭建需要三台centos服务器（一主二从）， 然后在每台服务器中分别安装docker(18.06.03),

kubeadm(1.17.4)、kubelet(1.17.4)，kubectl(1.17.4)



MobaXterm professional 同时连接多个服务器 , 可同时运行命令在不同服务器

systemctl start chronyd



搭建集群监控平台

从零搭建高可用集群

在集群环境部署项目

RBAC

### 制作镜像

1. mvn clean package 
2. add Dockerfile
3. add to VM 
4. docker build -t  jave-demo:latest  .       :         //build image
5. docker images   :  show images
6. docker run -d -p 8111 jave-demo:latest  -t    :   启动项目
7. netstat -tunlp | grep 
8. kill  -9 xxxx

### 上传镜像到镜像服务器

1. 创建命名空间
2. 创建镜像仓库
3. 登录镜像服务器  docker login
4. sudo docker tag  [ImageID]  registry:version   =>   add image version
5. sudo docker push registry 
6. sudo docker pull registry  拉取镜像

### 部署镜像

1. kubectl get pods -o wide
2. kubectl create deployment  javedemo  --image= registry    --dr y-run -o yaml  >javademo.yaml
3. kubectl apply -f  jave-demo.yaml
4. kubectl scale deployment javedemo --replicas=3   //扩容
5. kubectl expose deployment javademo --port=8111 --target-port=8111 --type=NodePort    暴露端口
6. kubectl get svc   查看端口

# k8s集群环境的搭建

# 1 环境规划

## 1.1 集群类型



- Kubernetes集群大致分为两类：一主多从和多主多从。

- 一主多从：一个Master节点和多台Node节点，搭建简单，但是有单机故障风险，适合用于测试环境。

- 多主多从：多台Master和多台Node节点，搭建麻烦，安全性高，适合用于生产环境。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609119870944-20c07dd5-9a76-42c6-b1df-6cc206e516e6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_36%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



为了测试方便，本次搭建的是一主多从类型的集群。



## 1.2 安装方式



- kubernetes有多种部署方式，目前主流的方式有kubeadm、minikube、二进制包。



- ① minikube：一个用于快速搭建单节点的kubernetes工具。

- ② kubeadm：一个用于快速搭建kubernetes集群的工具。

- ③ 二进制包：从官网上下载每个组件的二进制包，依次去安装，此方式对于理解kubernetes组件更加有效。



- 我们需要安装kubernetes的集群环境，但是又不想过于麻烦，所以选择kubeadm方式。



## 1.3 主机规划

| 角色   | IP地址         | 操作系统                   | 配置                    |
| ------ | -------------- | -------------------------- | ----------------------- |
| Master | 192.168.18.100 | CentOS7.8+，基础设施服务器 | 2核CPU，2G内存，50G硬盘 |
| Node1  | 192.168.18.101 | CentOS7.8+，基础设施服务器 | 2核CPU，2G内存，50G硬盘 |
| Node2  | 192.168.18.102 | CentOS7.8+，基础设施服务器 | 2核CPU，2G内存，50G硬盘 |



# 2 环境搭建



## 2.1 前言



- 本次环境搭建需要三台CentOS服务器（一主二从），然后在每台服务器中分别安装Docker（18.06.3）、kubeadm（1.18.0）、kubectl（1.18.0）和kubelet（1.18.0）。



没有特殊说明，就是三台机器都需要执行。



## 2.2 环境初始化



### 2.2.1 检查操作系统的版本



- 检查操作系统的版本（要求操作系统的版本至少在7.5以上）：



```shell
cat /etc/redhat-release
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609119895230-a96ce20f-d417-4ecf-8ed0-cfdd7f287c78.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_12%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 2.2.2 关闭防火墙和禁止防火墙开机启动



- 关闭防火墙：



```shell
systemctl stop firewalld
```



- 禁止防火墙开机启动：



```shell
systemctl disable firewalld
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609119906228-47b792cc-60fa-4dfc-b7ca-ad8bcf85de7b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_17%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 2.2.3 设置主机名



- 设置主机名：



```shell
hostnamectl set-hostname <hostname>
```



- 设置192.168.18.100的主机名：



```shell
hostnamectl set-hostname k8s-master
```



- 设置192.168.18.101的主机名：



```shell
hostnamectl set-hostname k8s-node1
```



- 设置192.168.18.102的主机名：



```shell
hostnamectl set-hostname k8s-node2
```



### 2.2.4 主机名解析



- 为了方便后面集群节点间的直接调用，需要配置一下主机名解析，企业中推荐使用内部的DNS服务器。



```shell
cat >> /etc/hosts << EOF
192.168.18.100 k8s-master
192.168.18.101 k8s-node1
192.168.18.102 k8s-node2
EOF
```



### 2.2.5 时间同步



- kubernetes要求集群中的节点时间必须精确一致，所以在每个节点上添加时间同步：



```shell
yum install ntpdate -y
```



```shell
ntpdate time.windows.com
```



### 2.2.6 关闭selinux



- 查看selinux是否开启：



```shell
getenforce
```



- 永久关闭selinux，需要重启：



```shell
sed -i 's/enforcing/disabled/' /etc/selinux/config
```



- 临时关闭selinux，重启之后，无效：



```shell
setenforce 0
```



### 2.2.7 关闭swap分区



- 永久关闭swap分区，需要重启：



```shell
sed -ri 's/.*swap.*/#&/' /etc/fstab
```



- 临时关闭swap分区，重启之后，无效：：



```shell
swapoff -a
```



### 2.2.8 将桥接的IPv4流量传递到iptables的链



- 在每个节点上将桥接的IPv4流量传递到iptables的链：



```shell
cat > /etc/sysctl.d/k8s.conf << EOF
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.ipv4.ip_forward = 1
vm.swappiness = 0
EOF
```



```shell
# 加载br_netfilter模块
modprobe br_netfilter
```



```shell
# 查看是否加载
lsmod | grep br_netfilter
```



```shell
# 生效
sysctl --system
```



### 2.2.9 开启ipvs



- 在kubernetes中service有两种代理模型，一种是基于iptables，另一种是基于ipvs的。ipvs的性能要高于iptables的，但是如果要使用它，需要手动载入ipvs模块。

- 在每个节点安装ipset和ipvsadm：



```shell
yum -y install ipset ipvsadm
```



- 在所有节点执行如下脚本：



```shell
cat > /etc/sysconfig/modules/ipvs.modules <<EOF
#!/bin/bash
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh
modprobe -- nf_conntrack_ipv4
EOF
```



- 授权、运行、检查是否加载：



```shell
chmod 755 /etc/sysconfig/modules/ipvs.modules && bash /etc/sysconfig/modules/ipvs.modules && lsmod | grep -e ip_vs -e nf_conntrack_ipv4
```



- 检查是否加载：



```shell
lsmod | grep -e ipvs -e nf_conntrack_ipv4
```



### 2.2.10 重启三台机器



- 重启三台Linux机器：



```shell
reboot
```



## 2.3 每个节点安装Docker、kubeadm、kubelet和kubectl



### 2.3.1 安装Docker



- 安装Docker：



```shell
wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo
```



```shell
yum -y install docker-ce-18.06.3.ce-3.el7
```



```shell
systemctl enable docker && systemctl start docker
```



```shell
docker version
```



- 设置Docker镜像加速器：



```shell
sudo mkdir -p /etc/docker
```



```shell
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "exec-opts": ["native.cgroupdriver=systemd"],	
  "registry-mirrors": ["https://b9pmyelo.mirror.aliyuncs.com"],	
  "live-restore": true,
  "log-driver":"json-file",
  "log-opts": {"max-size":"500m", "max-file":"3"}
}
EOF
```



```shell
sudo systemctl daemon-reload
```



```shell
sudo systemctl restart docker
```



### 2.3.2 添加阿里云的YUM软件源



- 由于kubernetes的镜像源在国外，非常慢，这里切换成国内的阿里云镜像源：



```shell
cat > /etc/yum.repos.d/kubernetes.repo << EOF
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
```



### 2.3.3 安装kubeadm、kubelet和kubectl



- 由于版本更新频繁，这里指定版本号部署：



```shell
yum install -y kubelet-1.18.0 kubeadm-1.18.0 kubectl-1.18.0
```



- 为了实现Docker使用的cgroup drvier和kubelet使用的cgroup drver一致，建议修改"/etc/sysconfig/kubelet"文件的内容：



```shell
vim /etc/sysconfig/kubelet
```



```shell
# 修改
KUBELET_EXTRA_ARGS="--cgroup-driver=systemd"
KUBE_PROXY_MODE="ipvs"
```



- 设置为开机自启动即可，由于没有生成配置文件，集群初始化后自动启动：



```shell
systemctl enable kubelet
```



## 2.4 查看k8s所需镜像



- 查看k8s所需镜像：



```shell
kubeadm config images list
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609119949729-6f352b60-f4c8-439b-927a-7e8fa522fc24.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 2.5 部署k8s的Master节点



- 部署k8s的Master节点（192.168.18.100）：



```shell
# 由于默认拉取镜像地址k8s.gcr.io国内无法访问，这里需要指定阿里云镜像仓库地址
kubeadm init \
  --apiserver-advertise-address=192.168.18.100 \
  --image-repository registry.aliyuncs.com/google_containers \
  --kubernetes-version v1.18.0 \
  --service-cidr=10.96.0.0/12 \
  --pod-network-cidr=10.244.0.0/16
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609119960853-b9b93c52-fb59-4bda-ad0d-a1886b4f6742.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609119965703-f3424796-68fd-4705-8a75-21146ea09737.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 根据提示消息，在Master节点上使用kubectl工具：



```shell
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```



## 2.6 部署k8s的Node节点



- 根据提示，在192.168.18.101和192.168.18.102上添加如下的命令：



```shell
kubeadm join 192.168.18.100:6443 --token jv039y.bh8yetcpo6zeqfyj \
    --discovery-token-ca-cert-hash sha256:3c81e535fd4f8ff1752617d7a2d56c3b23779cf9545e530828c0ff6b507e0e26
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609119980189-9bfd2754-df63-46dc-ad9b-422157a8aeea.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 默认的token有效期为24小时，当过期之后，该token就不能用了，这时可以使用如下的命令创建token：



```shell
kubeadm token create --print-join-command
```



```shell
# 生成一个永不过期的token
kubeadm token create --ttl 0 --print-join-command
```



## 2.7 部署CNI网络插件



- 根据提示，在Master节点上使用kubectl工具查看节点状态：



```shell
kubectl get nodes
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609120015683-b7d090c1-80fa-4f6f-92a6-088b9027e95b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- kubernetes支持多种网络插件，比如flannel、calico、canal等，任选一种即可，本次选择flannel，如果网络不行，可以使用本人提供的[📎kube-flannel.yml](https://www.yuque.com/attachments/yuque/0/2021/yml/513185/1609860138490-0ef90b45-9b0e-47e2-acfa-0c041f083bf9.yml)，当然，你也可以安装calico，请点这里[📎calico.yaml](https://www.yuque.com/attachments/yuque/0/2021/yaml/513185/1612184315393-f2d1b11a-d9fa-481e-ba77-06cf1ab526f0.yaml)，推荐安装calico。
- 在Master节点上获取flannel配置文件(可能会失败，如果失败，请下载到本地，然后安装)：



```shell
wget https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
```



- 使用配置文件启动flannel：



```shell
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
```



- 查看部署CNI网络插件进度：



```shell
kubectl get pods -n kube-system
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609120069943-62a38004-a7b1-401c-af71-4ac3745b1b78.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 再次在Master节点使用kubectl工具查看节点状态：



```shell
kubectl get nodes
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609120086535-0c17e14c-af98-45f6-9287-215da82f22c0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看集群健康状况：



```shell
kubectl get cs
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609120104310-b77968a1-5a05-40c2-928d-8f46b1fab939.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



```shell
kubectl cluster-info
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609120109875-85be6a3b-e356-4664-a298-f3884b9dc1d0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 3 服务部署



## 3.1 前言



- 在Kubernetes集群中部署一个Nginx程序，测试下集群是否正常工作。



## 3.2 步骤



- 部署Nginx：



```shell
kubectl create deployment nginx --image=nginx:1.14-alpine
```



- 暴露端口：



```shell
kubectl expose deployment nginx --port=80 --type=NodePort
```



- 查看服务状态：



```shell
kubectl get pods,svc
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609120130827-8bd526a1-c07c-4950-b7c5-ccd2c977cdc4.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609120142631-e8f86203-cf1f-4df4-a2b7-47dc775a540e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_41%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

# 4 kubernetes中kubectl命令自动补全

```shell
yum install -y bash-completion
source /usr/share/bash-completion/bash_completion
source <(kubectl completion bash)
echo “source <(kubectl completion bash)” >> ~/.bashrc
 vim /root/.bashrc 
source /usr/share/bash-completion/bash_completion
source <(kubectl completion bash)
```



# k8s的资源管理

# 1 资源管理介绍

- 在Kubernetes中，所有的内容都抽象为资源，用户需要通过操作资源来管理Kubernetes。



- Kubernetes的本质就是一个集群系统，用户可以在集群中部署各种服务。所谓的部署服务，其实就是在Kubernetes集群中运行一个个的容器，并将指定的程序跑在容器中。

- Kubernetes的最小管理单元是Pod而不是容器，所以只能将容器放在`Pod`中，而Kubernetes一般也不会直接管理Pod，而是通过`Pod控制器`来管理Pod的。

- Pod提供服务之后，就需要考虑如何访问Pod中的服务，Kubernetes提供了`Service`资源实现这个功能。

- 当然，如果Pod中程序的数据需要持久化，Kubernetes还提供了各种`存储`系统。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132417114-28600d3c-185f-4ded-9f5e-c0d77fb86d80.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_33%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



学习kubernets的核心，就是学习如何对集群中的`Pod`、`Pod控制器`、`Service`、`存储`等各种资源进行操作。



# 2 YAML语法介绍



## 2.1 YAML语法介绍



- YAML是一个类似于XML、JSON的标记性语言。它强调的是以“数据”为中心，并不是以标记语言为重点。因而YAML本身的定义比较简单，号称是“一种人性化的数据格式语言”。

- YAML的语法比较简单，主要有下面的几个：

- - 大小写敏感。

- - 使用缩进表示层级关系。

- - 缩进不允许使用tab，只允许空格（低版本限制）。

- - 缩进的空格数不重要，只要相同层级的元素左对齐即可。

- - ‘#’表示注释。

- YAML支持以下几种数据类型：

- - 常量：单个的、不能再分的值。

- - 对象：键值对的集合，又称为映射/哈希/字典。

- - 数组：一组按次序排列的值，又称为序列/列表。



## 2.2 YAML语法示例



### 2.2.1 YAML常量



```yaml
#常量，就是指的是一个简单的值，字符串、布尔值、整数、浮点数、NUll、时间、日期
# 布尔类型
c1: true
# 整型
c2: 123456
# 浮点类型
c3: 3.14
# null类型
c4: ~ # 使用~表示null
# 日期类型
c5: 2019-11-11 # 日期类型必须使用ISO 8601格式，即yyyy-MM-dd
# 时间类型
c6: 2019-11-11T15:02:31+08.00 # 时间类型使用ISO 8601格式，时间和日期之间使用T连接，最后使用+代表时区
# 字符串类型
c7: haha # 简单写法，直接写值，如果字符串中间有特殊符号，必须使用双引号或单引号包裹
c8: line1
    line2 # 字符串过多的情况可以折成多行，每一行都会转换成一个空格
```



### 2.2.2 对象



```yaml
# 对象
# 形式一（推荐）：
xudaxian:	
	name: 许大仙
	age: 16
# 形式二（了解）：
xuxian: { name: 许仙, age: 18 }
```



### 2.2.3 数组



```yaml
# 数组
# 形式一（推荐）：
address:
	- 江苏
	- 北京
# 形式二（了解）：
address: [江苏,上海]
```



# 3 资源管理方式

## 3.1 资源管理方式

- 命令式对象管理：直接使用命令去操作kubernetes的资源。

```shell
kubectl run nginx-pod --image=nginx:1.17.1 --port=80
```

- 命令式对象配置：通过命令配置和配置文件去操作kubernetes的资源。

```shell
kubectl create/patch -f nginx-pod.yaml
```

- 声明式对象配置：通过apply命令和配置文件去操作kubernetes的资源, 适用于创建和更新，不能删除

```shell
kubectl apply -f nginx-pod.yaml
```

| 类型           | 操作 | 适用场景 | 优点           | 缺点                               |
| -------------- | ---- | -------- | -------------- | ---------------------------------- |
| 命令式对象管理 | 对象 | 测试     | 简单           | 只能操作活动对象，无法审计、跟踪   |
| 命令式对象配置 | 文件 | 开发     | 可以审计、跟踪 | 项目大的时候，配置文件多，操作麻烦 |
| 声明式对象配置 | 目录 | 开发     | 支持目录操作   | 意外情况下难以调试                 |

## 3.2 命令式对象管理

### 3.2.1 kubectl命令

- kubectl是kubernetes集群的命令行工具，通过它能够对集群本身进行管理，并能够在集群上进行容器化应用的安装和部署。

- kubectl命令的语法如下：

```shell
kubectl [command] [type] [name] [flags]
```

- command：指定要对资源执行的操作，比如create、get、delete。

- type：指定资源的类型，比如deployment、pod、service。

- name：指定资源的名称，名称大小写敏感。

- flags：指定额外的可选参数。

- 示例：查看所有的pod

```shell
kubectl get pods
```

- 示例：查看某个pod

```shell
kubectl get pod pod_name
```

- 示例：查看某个pod，以yaml格式展示结果

```shell
kubectl get pod pod_name -o yaml
```



### 3.2.2 操作（command）

- kubernetes允许对资源进行多种操作，可以通过--help查看详细的操作命令：

```shell
kubectl --help
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132452142-a3538cec-425e-4bc3-9e51-4d412251b508.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_40%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 经常使用的操作如下所示：

- ① 基本命令：

| 命令    | 翻译 | 命令作用     |
| ------- | ---- | ------------ |
| create  | 创建 | 创建一个资源 |
| edit    | 编辑 | 编辑一个资源 |
| get     | 获取 | 获取一个资源 |
| patch   | 更新 | 更新一个资源 |
| delete  | 删除 | 删除一个资源 |
| explain | 解释 | 展示资源文档 |



- ② 运行和调试：

| 命令      | 翻译     | 命令作用                   |
| --------- | -------- | -------------------------- |
| run       | 运行     | 在集群中运行一个指定的镜像 |
| expose    | 暴露     | 暴露资源为Service          |
| describe  | 描述     | 显示资源内部信息           |
| logs      | 日志     | 输出容器在Pod中的日志      |
| attach    | 缠绕     | 进入运行中的容器           |
| exec      | 执行     | 执行容器中的一个命令       |
| cp        | 复制     | 在Pod内外复制文件          |
| rollout   | 首次展示 | 管理资源的发布             |
| scale     | 规模     | 扩（缩）容Pod的数量        |
| autoscale | 自动调整 | 自动调整Pod的数量          |



- ③ 高级命令：

| 命令  | 翻译 | 命令作用               |
| ----- | ---- | ---------------------- |
| apply | 应用 | 通过文件对资源进行配置 |
| label | 标签 | 更新资源上的标签       |



- ④ 其他命令：

| 命令         | 翻译     | 命令作用                     |
| ------------ | -------- | ---------------------------- |
| cluster-info | 集群信息 | 显示集群信息                 |
| version      | 版本     | 显示当前Client和Server的版本 |



### 3.2.3 资源类型（type）



- kubernetes中所有的内容都抽象为资源，可以通过下面的命令进行查看：



```shell
kubectl api-resources
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132476284-17b0580b-a8b4-4732-af14-e6494fc32bb5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_35%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 经常使用的资源如下所示：

- ① 集群级别资源：

| 资源名称   | 缩写 | 资源作用     |
| ---------- | ---- | ------------ |
| nodes      | no   | 集群组成部分 |
| namespaces | ns   | 隔离Pod      |



- ② Pod资源：

| 资源名称 | 缩写 | 资源作用 |
| -------- | ---- | -------- |
| Pods     | po   | 装载容器 |



- ③ Pod资源控制器：

| 资源名称                 | 缩写   | 资源作用    |
| ------------------------ | ------ | ----------- |
| replicationcontrollers   | rc     | 控制Pod资源 |
| replicasets              | rs     | 控制Pod资源 |
| deployments              | deploy | 控制Pod资源 |
| daemonsets               | ds     | 控制Pod资源 |
| jobs                     |        | 控制Pod资源 |
| cronjobs                 | cj     | 控制Pod资源 |
| horizontalpodautoscalers | hpa    | 控制Pod资源 |
| statefulsets             | sts    | 控制Pod资源 |



- ④ 服务发现资源：

| 资源名称 | 缩写 | 资源作用        |
| -------- | ---- | --------------- |
| services | svc  | 统一Pod对外接口 |
| ingress  | ing  | 统一Pod对外接口 |



- ⑤ 存储资源：

| 资源名称               | 缩写 | 资源作用 |
| ---------------------- | ---- | -------- |
| volumeattachments      |      | 存储     |
| persistentvolumes      | pv   | 存储     |
| persistentvolumeclaims | pvc  | 存储     |



- ⑥ 配置资源：

| 资源名称   | 缩写 | 资源作用 |
| ---------- | ---- | -------- |
| configmaps | cm   | 配置     |
| secrets    |      | 配置     |



### 3.2.4 应用示例



- 示例：创建一个namespace



```shell
kubectl create namespace dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132496332-e0dc3115-9384-44d1-946e-9afb6a9cb1d0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：获取namespace



```shell
kubectl get namespace
```



```shell
kubectl get ns
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132506289-2db77a2f-a4d8-4763-af63-20585c1c6a4d.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_48%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：在刚才创建的namespace下创建并运行一个Nginx的Pod



```shell
kubectl run nginx --image=nginx:1.17.1 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132518916-bdaef955-1dac-44ce-91c8-5422217df3ab.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：查看名为dev的namespace下的所有Pod，如果不加-n，默认就是default的namespace



```shell
kubectl get pods -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132531429-ec78b6fe-085a-4722-9f84-81b54e3dbb47.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：删除指定namespace下的指定Pod



```shell
kubectl delete pod nginx -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132549427-4b2b564a-1ed0-46f3-a699-7ad0551a0ea8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：删除指定的namespace



```shell
kubectl delete namespace dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132559205-39f23c72-c344-4dd5-8fc6-2b1c19ffb828.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.3 命令式对象配置



### 3.3.1 概述



- 命令式对象配置：通过命令配置和配置文件去操作kubernetes的资源。



### 3.3.2 应用示例



- 示例：

- ① 创建一个nginxpod.yaml，内容如下：



```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: dev
---
apiVersion: v1
kind: Pod
metadata:
  name: nginxpod
  namespace: dev
spec:
  containers:
    - name: nginx-containers
      image: nginx:1.17.1
```



- ② 执行create命令，创建资源：



```shell
kubectl create -f nginxpod.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132583140-1cdcba9d-5497-4380-8907-70de767546ba.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- ③ 执行get命令，查看资源：



```shell
kubectl get -f nginxpod.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132595681-cad68f84-f43d-4b2c-8dc8-6246eaba60e0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- ④ 执行delete命令，删除资源：



```shell
kubectl delete -f nginxpod.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132604920-318af0a0-031f-4346-8b31-34cb819d1e98.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.3.3 总结



- 命令式对象配置的方式操作资源，可以简单的认为：命令+yaml配置文件（里面是命令需要的各种参数）。



## 3.4 声明式对象配置



### 3.4.1 概述



- 声明式对象配置：通过apply命令和配置文件去操作kubernetes的资源。

- 声明式对象配置和命令式对象配置类似，只不过它只有一个apply命令。

- apply相当于create和patch。



### 3.4.2 应用示例



- 示例：



```shell
kubectl apply -f nginxpod.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609132623003-5d19a1eb-46e2-4f35-b16f-3180e66b58fb.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.4.3 总结



- 声明式对象配置就是使用apply描述一个资源的最终状态（在yaml中定义状态）。

- 使用apply操作资源：

- - 如果资源不存在，就创建，相当于kubectl create。

- - 如果资源存在，就更新，相当于kubectl patch。



## 3.5 使用方式推荐



- 创建和更新资源使用声明式对象配置：kubectl apply -f xxx.yaml。

- 删除资源使用命令式对象配置：kubectl delete -f xxx.yaml。

- 查询资源使用命令式对象管理：kubectl get(describe) 资源名称。



## 3.6 扩展：kubectl可以在Node上运行



- kubectl的运行需要进行配置，它的配置文件是$HOME/.kube，如果想要在Node节点上运行此命令，需要将Master节点的.kube文件夹复制到Node节点上，即在Master节点上执行下面的操作：



```shell
scp -r $HOME/.kube k8s-node1:$HOME
```



# **4 如何快速的编写yaml文件**



## 4.1 使用kubectl create命令生成yaml文件

- 此种方式适用于没有真正部署资源。
- 使用kubectl create命令生成yaml文件：

```yaml
kubectl create deployment nginx --image=nginx:1.17.1 --dry-run=client -n dev -o yaml
```

- 如果yaml文件太长，可以写入到指定的文件中。

```yaml
kubectl create deployment nginx --image=nginx:1.17.1 --dry-run=client -n dev -o yaml > test.yaml
```

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610081069410-98424916-b511-48c8-ad55-30472f5d4c32.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 4.2 使用kubectl get命令导出yaml文件（此种方式已经不建议使用）

- 此种方式适合于资源已经部署，动态的导出yaml文件。
- 创建一个Deployment：

```yaml
kubectl create deployment nginx --image=nginx:1.17.1 -n dev
```

- 使用kubectl get命令导出yaml文件：

```yaml
kubectl get deployment nginx -n dev -o yaml --export > test2.yaml
```

![img](https://cdn.nlark.com/yuque/0/2021/gif/513185/1610081130575-00a0c28a-24e0-41bc-ac54-454527456010.gif)

此种方式会在未来版本中删除，因此不再建议使用。



# k8s的实战入门

# 1 前言

- 介绍如何在kubernetes集群中部署一个Nginx服务，并且能够对其访问。



# 2 Namespace



## 2.1 概述



- Namespace是kubernetes系统中一种非常重要的资源，它的主要作用是用来实现`多套系统的资源隔离`或者`多租户的资源隔离`。

- 默认情况下，kubernetes集群中的所有Pod都是可以相互访问的。但是在实际中，可能不想让两个Pod之间进行互相的访问，那么此时就可以将两个Pod划分到不同的Namespace下。kubernetes通过将集群内部的资源分配到不同的Namespace中，可以形成逻辑上的“组”，以方便不同的组的资源进行隔离使用和管理。

- 可以通过kubernetes的授权机制，将不同的Namespace交给不同租户进行管理，这样就实现了多租户的资源隔离。此时还能结合kubernetes的资源配额机制，限定不同租户能占用的资源，例如CPU使用量、内存使用量等等，来实现租户可用资源的管理。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137920951-be890509-fe17-4be4-935b-f4f687745a1b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- kubernetes在集群启动之后，会默认创建几个namespace。



```shell
kubectl get namespace
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137081489-a574c4fb-e7de-4107-9388-9229b3b905bd.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_36%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- default：所有未指定的Namespace的对象都会被分配在default命名空间。

- kube-node-lease：集群节点之间的心跳维护，v1.13开始引入。

- kube-public：此命名空间的资源可以被所有人访问（包括未认证用户）。

- kube-system：所有由kubernetes系统创建的资源都处于这个命名空间。



## 2.2 应用示例



- 示例：查看所有的命名空间



```shell
kubectl get namespace
```



```shell
kubectl get ns
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137101603-e9ccc847-2be3-4f20-b237-da67964ae72c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：查看指定的命名空间



```shell
kubectl get namespace default
```



```shell
kubectl get ns default
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137112293-cb6e39a9-8b6f-4ce1-b19a-ecc75a3a09d9.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：指定命名空间的输出格式



```shell
kubectl get ns default -o wide
```



```shell
kubectl get ns default -o json
```



```shell
kubectl get ns default -o yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137136147-675f20da-cc67-47ab-be73-080abaea9161.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：查看命名空间的详情



```shell
kubectl describe namespace default
```



```shell
kubectl describe ns default
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137161996-23a6c32a-ac9b-47df-871d-7279d2830c3d.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：创建命名空间



```shell
kubectl create namespace dev
```



```shell
kubectl create ns dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137198529-d28e10db-998f-421e-9eaf-05879f2c1081.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：删除命名空间



```shell
kubectl delete ns dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137212517-7e87aba7-a9b5-4d07-8313-eb66aacbe865.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：命令式对象配置

- ① 新建ns-dev.yaml：



```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: dev
```



- ② 通过命令式对象配置进行创建和删除：



```shell
kubectl create -f ns-dev.yaml
```



```yaml
kubectl delete -f ns-dev.yaml
```



# 3 Pod



## 3.1 概述



- Pod是kubernetes集群进行管理的最小单元，程序要运行必须部署在容器中，而容器必须存在于Pod中。

- Pod可以认为是容器的封装，一个Pod中可以存在一个或多个容器。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137223448-715c6ece-0158-4ee2-9efa-fcff15e143ed.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_18%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- kubernetes在集群启动之后，集群中的各个组件也是以Pod方式运行的，可以通过下面的命令查看：



```shell
kubectl get pods -n kube-system
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137238518-90d030d2-c0e5-4017-82f0-0fee5e83da5b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.2 语法及应用示例



- 语法：创建并运行Pod



```shell
kubectl run (Pod的名称) [参数]
# --image 指定Pod的镜像
# --port 指定端口
# --namespace 指定namespace
```



- 示例：在名称为dev的namespace下创建一个Nginx的Pod



```shell
kubectl run nginx --image=nginx:1.17.1 --port=80 --namespace=dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137263286-82b089b0-dc26-4719-b1b5-9ff0a247d11d.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法： 查询所有Pod的基本信息



```shell
kubectl get pods [-n 命名空间的名称]
```



- 示例：查询名称为dev的namespace下的所有Pod的基本信息



```shell
kubectl get pods -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137285411-2be6d1ae-ae74-4ae6-940d-c87f78b0e077.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：查看Pod的详细信息



```shell
kubectl describe pod pod的名称 [-n 命名空间名称]
```



- 示例：查看名称为dev的namespace下的Pod的名称为nginx的详细信息



```shell
kubectl describe pod nginx -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137301673-4a1d80cf-b21e-4702-b428-97ab9b9c98ba.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)





- 语法：Pod的访问



```shell
# 获取Pod的IP
kubectl get pods [-n dev] -o wide
```



```shell
# 通过curl访问
curl ip:端口
```



- 示例：访问Nginx的Pod



```shell
kubectl get pods -n dev -o wide
```



```shell
curl 10.244.2.7:80
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137317128-31564b38-47d5-4793-b05f-e82e326cb9bb.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：删除指定的Pod



```shell
kubectl delete pod pod的名称 [-n 命名空间]
```



- 示例：删除Nginx的Pod



```shell
kubectl delete pod nginx -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137335232-87b53a63-7d32-4e6d-b49d-05617e0f0282.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：命令式对象配置

- ① 新建pod-nginx.yaml：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  namespace: dev
spec:
  containers:
  - image: nginx:1.17.1
    imagePullPolicy: IfNotPresent
    name: pod
    ports: 
    - name: nginx-port
      containerPort: 80
      protocol: TCP
```



- ② 执行创建和删除命令：



```shell
kubectl create -f pod-nginx.yaml
```



```shell
kubectl delete -f pod-nginx.yaml
```



# 4 Label



## 4.1 概述



- Label是kubernetes的一个重要概念。它的作用就是在资源上添加标识，用来对它们进行区分和选择。

- Label的特点：

- - 一个Label会以key/value键值对的形式附加到各种对象上，如Node、Pod、Service等。

- - 一个资源对象可以定义任意数量的Label，同一个Label也可以被添加到任意数量的资源对象上去。

- - Label通常在资源对象定义时确定，当然也可以在对象创建后动态的添加或删除。

- 可以通过Label实现资源的多纬度分组，以便灵活、方便地进行资源分配、调度、配置和部署等管理工作。



一些常用的Label标签示例如下：

- 版本标签：“version”:”release”,”version”:”stable”。。。

- 环境标签：“environment”:”dev”,“environment”:”test”,“environment”:”pro”。。。

- 架构标签：“tier”:”frontend”,”tier”:”backend”。。。



- 标签定义完毕之后，还要考虑到标签的选择，这就要用到Label Selector，即：

- - Label用于给某个资源对象定义标识。

- - Label Selector用于查询和筛选拥有某些标签的资源对象。

- 当前有两种Label Selector：

- - 基于等式的Label Selector。

- - - name=slave：选择所有包含Label中的key=“name”并且value=“slave”的对象。

- - - env!=production：选择所有包含Label中的key=“name”并且value!=“production”的对象。

- - 基于集合的Label Selector。

- - - name in (master,slave)：选择所有包含Label中的key=“name”并且value=“master”或value=“slave”的对象。

- - - name not in (master,slave)：选择所有包含Label中的key=“name”并且value!=“master”和value!=“slave”的对象。

- 标签的选择条件可以使用多个，此时将多个Label Selector进行组合，使用逗号（,）进行分隔即可。

- - name=salve,env!=production。

- - name not in (master,slave),env!=production。



## 4.2 语法及应用示例



- 语法：为资源打标签



```shell
kubectl label pod xxx key=value [-n 命名空间]
```



- 示例：为Nginx的Pod打上标签



```shell
kubectl label pod nginx version=1.0 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137364444-cb73dadf-2b15-47a8-a7ef-0bcf4d670a50.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：更新资源的标签



```shell
kubectl label pod xxx key=value [-n 命名空间] --overwrite
```



- 示例：为Nginx的Pod更新标签



```shell
kubectl label pod nginx version=2.0 -n dev --overwrite
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137377890-978c9e46-71e6-4f05-84cb-c0eb5106f56e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：查看标签



```shell
kubectl get pod xxx [-n 命名空间] --show-labels
```



- 示例：显示Nginx的Pod的标签



```shell
kubectl get pod nginx -n dev --show-labels
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137392580-cf9820b7-aa20-436a-b164-b9c7d508e42d.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：筛选标签



```shell
kubectl get pod -l key=value [-n 命名空间] --show-labels
```



- 示例：筛选版本号是2.0的在名称为dev的namespace下的Pod



```shell
kubectl get pod -l version=2.0 -n dev --show-labels
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137407577-e7d132ca-27ac-45cf-b35d-6fd4c4e8c0a6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：删除标签



```shell
kubectl label pod xxx key- [-n 命名空间]
```



- 示例：删除名称为dev的namespace下的Nginx的Pod上的标签



```shell
kubectl label pod nginx version- -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137422373-0d66c6ff-4d8e-4079-8f11-155f581cd709.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：命令式对象配置

- ① 新建pod-nginx.yaml：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  namespace: dev
  labels:
    version: "3.0"
    env: "test"        
spec:
  containers:
  - image: nginx:1.17.1
    imagePullPolicy: IfNotPresent
    name: pod
    ports: 
    - name: nginx-port
      containerPort: 80
      protocol: TCP
```



- ② 执行创建和删除命令：



```shell
kubectl create -f pod-nginx.yaml
```



```yaml
kubectl delete -f pod-nginx.yaml
```



# 5 Deployment



## 5.1 概述



- 在kubernetes中，Pod是最小的控制单元，但是kubernetes很少直接控制Pod，一般都是通过Pod控制器来完成的。

- Pod控制器用于Pod的管理，确保Pod资源符合预期的状态，当Pod的资源出现故障的时候，会尝试进行重启或重建Pod。

- 在kubernetes中Pod控制器的种类有很多，本章节只介绍一种：Deployment。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137432883-b2ec0213-7c10-4efa-9689-066a4e239a74.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 5.2 语法及应用示例



特别注意：在v1.18版之后，kubectl run nginx --image=nginx --replicas=2 --port=80，会反馈Flag --replicas has been deprecated, has no effect and will be removed in the future，并且只会创建一个Nginx容器实例。



- 语法：创建指定名称的deployement



```shell
kubectl create deployment xxx [-n 命名空间]
```



```shell
kubectl create deploy xxx [-n 命名空间]
```



- 示例：在名称为test的命名空间下创建名为nginx的deployment



```shell
kubectl create deployment nginx --image=nginx:1.17.1 -n test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137451866-d2a0b045-6197-4384-9a7f-8c7453236617.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：根据指定的deplyment创建Pod



```shell
kubectl scale deployment xxx [--replicas=正整数] [-n 命名空间]
```



- 示例：在名称为test的命名空间下根据名为nginx的deployment创建4个Pod



```shell
kubectl scale deployment nginx --replicas=4 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137465946-69b99229-1584-4dc6-941a-1f809e2c36ae.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：命令式对象配置

- ① 创建一个deploy-nginx.yaml，内容如下：



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx
  namespace: dev
spec:
  replicas: 3
  selector:
    matchLabels:
      run: nginx
  template:
    metadata:
      labels:
        run: nginx
    spec:
      containers:
      - image: nginx:1.17.1
        name: nginx
        ports:
        - containerPort: 80
          protocol: TCP
```



- ② 执行创建和删除命令：



```shell
kubectl create -f deploy-nginx.yaml
```



```shell
kubectl delete -f deploy-nginx.yaml
```



- 语法：查看创建的Pod



```shell
kubectl get pods [-n 命名空间]
```



- 示例：查看名称为dev的namespace下通过deployment创建的3个Pod



```shell
kubectl get pods -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137483696-59f9408b-ef19-4f7f-8fbd-539ae7f5cc18.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：查看deployment的信息



```shell
kubectl get deployment [-n 命名空间]
```



```shell
kubectl get deploy [-n 命名空间]
```



- 示例：查看名称为dev的namespace下的deployment



```shell
kubectl get deployment -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137504229-184bca70-8720-4538-9786-3132174804fc.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)





- 语法：查看deployment的详细信息



```shell
kubectl describe deployment xxx [-n 命名空间]
```



```shell
kubectl describe deploy xxx [-n 命名空间]
```



- 示例：查看名为dev的namespace下的名为nginx的deployment的详细信息



```shell
kubectl describe deployment nginx -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137520747-da80efc3-776a-45a9-86fd-d12bf6fa7621.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：删除deployment



```shell
kubectl delete deployment xxx [-n 命名空间]
```



```shell
kubectl delete deploy xxx [-n 命名空间]
```



- 示例：删除名为dev的namespace下的名为nginx的deployment



```shell
kubectl delete deployment nginx -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137535020-5073c9a2-deb4-4b77-ad64-67fe68df1978.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 6 Service



## 6.1 概述



- 我们已经能够利用Deployment来创建一组Pod来提供具有高可用性的服务，虽然每个Pod都会分配一个单独的Pod的IP地址，但是却存在如下的问题：

- - Pod的IP会随着Pod的重建产生变化。

- - Pod的IP仅仅是集群内部可见的虚拟的IP，外部无法访问。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137564994-f5f1ef88-d569-47b4-b9dc-dde5542e8bb3.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 这样对于访问这个服务带来了难度，因此，kubernetes设计了Service来解决这个问题。

- Service可以看做是一组同类的Pod对外的访问接口，借助Service，应用可以方便的实现服务发现和负载均衡。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137571725-a4754fdb-d0a1-4a49-a7a4-169fc6cc9703.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

## 6.2 语法及应用示例



### 6.2.1 创建集群内部可访问的Service



- 语法：暴露Service



```shell
kubectl expose deployment xxx --name=服务名 --type=ClusterIP --port=暴露的端口 --target-port=指向集群中的Pod的端口 [-n 命名空间]
# 会产生一个CLUSTER-IP，这个就是service的IP，在Service的生命周期内，这个地址是不会变化的
```



- 示例：暴露名为test的namespace下的名为nginx的deployment，并设置服务名为svc-nginx1



```shell
kubectl expose deployment nginx --name=svc-nginx1 --type=ClusterIP --port=80 --target-port=80 -n test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137587835-64616fd2-6b30-4f4e-95e5-1820c6489d77.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：查看Service



```shell
kubectl get service [-n 命名空间] [-o wide]
```



- 示例：查看名为test的命名空间的所有Service



```shell
kubectl get service -n test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137601893-a49df255-f263-456a-8201-54f5f26c518a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 6.2.2 创建集群外部可访问的Service



- 语法：暴露Service



```shell
kubectl expose deployment xxx --name=服务名 --type=NodePort --port=暴露的端口 --target-port=指向集群中的Pod的端口 [-n 命名空间]
# 会产生一个外部也可以访问的Service
```



- 示例：暴露名为test的namespace下的名为nginx的deployment，并设置服务名为svc-nginx2



```shell
kubectl expose deploy nginx --name=svc-nginx2 --type=NodePort --port=80 --target-port=80 -n test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137617595-28242dec-50d7-49cc-9449-644478a023ea.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：查看Service



```shell
kubectl get service [-n 命名空间] [-o wide]
```



- 示例：查看名为test的命名空间的所有Service



```shell
kubectl get service -n test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137638210-da8b9e86-a4d0-4771-976b-67e567de6fac.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 6.2.3 删除服务



- 语法：删除服务



```shell
kubectl delete service xxx [-n 命名空间]
```



- 示例：删除服务



```shell
kubectl delete service svc-nginx1 -n test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609137649876-71177bae-5a6e-416c-bf39-ae620ed0d8d0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 6.2.4 对象配置方式



- 示例：对象配置方式

- ① 新建svc-nginx.yaml，内容如下：



```yaml
apiVersion: v1
kind: Service
metadata:
  name: svc-nginx
  namespace: dev
spec:
  clusterIP: 10.109.179.231
  ports:
  - port: 80
    protocol: TCP
    targetPort: 80
  selector:
    run: nginx
  type: ClusterIP
```



- ② 执行创建和删除命令：



```shell
kubectl  create  -f  svc-nginx.yaml
```



```shell
kubectl  delete  -f  svc-nginx.yaml
```



# k8s的Pod详解

详细介绍Pod资源的各种配置（YAML）和原理。



# 1 Pod的介绍



## 1.1 Pod的结构



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399357456-e5dc5f6d-7c2e-44bf-aae3-50d51ec951e9.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_18%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 每个Pod中都包含一个或者多个容器，这些容器可以分为两类：

- ① 用户程序所在的容器，数量可多可少。

- ② Pause容器，这是每个Pod都会有的一个根容器，它的作用有两个：

- - 可以以它为依据，评估整个Pod的健康状况。

- - 可以在根容器上设置IP地址，其它容器都共享此IP（Pod的IP），以实现Pod内部的网络通信（这里是Pod内部的通讯，Pod之间的通讯采用虚拟二层网络技术来实现，我们当前环境使用的是Flannel）。



## 1.2 Pod定义



- 下面是Pod的资源清单：



```yaml
apiVersion: v1     #必选，版本号，例如v1
kind: Pod       　 #必选，资源类型，例如 Pod
metadata:       　 #必选，元数据
  name: string     #必选，Pod名称
  namespace: string  #Pod所属的命名空间,默认为"default"
  labels:       　　  #自定义标签列表
    - name: string      　          
spec:  #必选，Pod中容器的详细定义
  containers:  #必选，Pod中容器列表
  - name: string   #必选，容器名称
    image: string  #必选，容器的镜像名称
    imagePullPolicy: [ Always|Never|IfNotPresent ]  #获取镜像的策略 
    command: [string]   #容器的启动命令列表，如不指定，使用打包时使用的启动命令
    args: [string]      #容器的启动命令参数列表
    workingDir: string  #容器的工作目录
    volumeMounts:       #挂载到容器内部的存储卷配置
    - name: string      #引用pod定义的共享存储卷的名称，需用volumes[]部分定义的的卷名
      mountPath: string #存储卷在容器内mount的绝对路径，应少于512字符
      readOnly: boolean #是否为只读模式
    ports: #需要暴露的端口库号列表
    - name: string        #端口的名称
      containerPort: int  #容器需要监听的端口号
      hostPort: int       #容器所在主机需要监听的端口号，默认与Container相同
      protocol: string    #端口协议，支持TCP和UDP，默认TCP
    env:   #容器运行前需设置的环境变量列表
    - name: string  #环境变量名称
      value: string #环境变量的值
    resources: #资源限制和请求的设置
      limits:  #资源限制的设置
        cpu: string     #Cpu的限制，单位为core数，将用于docker run --cpu-shares参数
        memory: string  #内存限制，单位可以为Mib/Gib，将用于docker run --memory参数
      requests: #资源请求的设置
        cpu: string    #Cpu请求，容器启动的初始可用数量
        memory: string #内存请求,容器启动的初始可用数量
    lifecycle: #生命周期钩子
		postStart: #容器启动后立即执行此钩子,如果执行失败,会根据重启策略进行重启
		preStop: #容器终止前执行此钩子,无论结果如何,容器都会终止
    livenessProbe:  #对Pod内各容器健康检查的设置，当探测无响应几次后将自动重启该容器
      exec:       　 #对Pod容器内检查方式设置为exec方式
        command: [string]  #exec方式需要制定的命令或脚本
      httpGet:       #对Pod内个容器健康检查方法设置为HttpGet，需要制定Path、port
        path: string
        port: number
        host: string
        scheme: string
        HttpHeaders:
        - name: string
          value: string
      tcpSocket:     #对Pod内个容器健康检查方式设置为tcpSocket方式
         port: number
       initialDelaySeconds: 0       #容器启动完成后首次探测的时间，单位为秒
       timeoutSeconds: 0    　　    #对容器健康检查探测等待响应的超时时间，单位秒，默认1秒
       periodSeconds: 0     　　    #对容器监控检查的定期探测时间设置，单位秒，默认10秒一次
       successThreshold: 0
       failureThreshold: 0
       securityContext:
         privileged: false
  restartPolicy: [Always | Never | OnFailure]  #Pod的重启策略
  nodeName: <string> #设置NodeName表示将该Pod调度到指定到名称的node节点上
  nodeSelector: obeject #设置NodeSelector表示将该Pod调度到包含这个label的node上
  imagePullSecrets: #Pull镜像时使用的secret名称，以key：secretkey格式指定
  - name: string
  hostNetwork: false   #是否使用主机网络模式，默认为false，如果设置为true，表示使用宿主机网络
  volumes:   #在该pod上定义共享存储卷列表
  - name: string    #共享存储卷名称 （volumes类型有很多种）
    emptyDir: {}       #类型为emtyDir的存储卷，与Pod同生命周期的一个临时目录。为空值
    hostPath: string   #类型为hostPath的存储卷，表示挂载Pod所在宿主机的目录
      path: string      　　        #Pod所在宿主机的目录，将被用于同期中mount的目录
    secret:       　　　#类型为secret的存储卷，挂载集群与定义的secret对象到容器内部
      scretname: string  
      items:     
      - key: string
        path: string
    configMap:         #类型为configMap的存储卷，挂载预定义的configMap对象到容器内部
      name: string
      items:
      - key: string
        path: string
```



- 语法：查看每种资源的可配置项



```shell
# 查看某种资源可以配置的一级配置
kubectl explain 资源类型 
# 查看属性的子属性
kubectl explain 资源类型.属性
```



- 示例：查看资源类型为pod的可配置项



```shell
kubectl explain pod
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399381712-137d7297-91d8-46c6-bbf7-de51f0818b45.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：查看资源类型为Pod的metadata的属性的可配置项



```shell
kubectl explain pod.metadata
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399388405-3a39ded4-b5e2-459f-8aaa-08d31b3ff5c2.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_34%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



在kubernetes中基本所有资源的一级属性都是一样的，主要包含5个部分：

- apiVersion  <string>：版本，有kubernetes内部定义，版本号必须用kubectl api-versions查询。

- kind <string>：类型，有kubernetes内部定义，类型必须用kubectl api-resources查询。

- metadata  <Object>：元数据，主要是资源标识和说明，常用的有name、namespace、labels等。

- spec <Object>：描述，这是配置中最重要的一部分，里面是对各种资源配置的详细描述。

- status  <Object>：状态信息，里面的内容不需要定义，由kubernetes自动生成。

在上面的属性中，spec是接下来研究的重点，继续看下它的常见子属性：

- containers  <[]Object>：容器列表，用于定义容器的详细信息。

- nodeName <String>：根据nodeName的值将Pod调度到指定的Node节点上。

- nodeSelector  <map[]> ：根据NodeSelector中定义的信息选择该Pod调度到包含这些Label的Node上。

- hostNetwork  <boolean>：是否使用主机网络模式，默认为false，如果设置为true，表示使用宿主机网络。

- volumes    <[]Object> ：存储卷，用于定义Pod上面挂载的存储信息。

- restartPolicy	<string>：重启策略，表示Pod在遇到故障的时候的处理策略。



# 2 Pod的配置



## 2.1 概述



- 本小节主要来研究pod.spec.containers属性，这也是Pod配置中最为关键的一项配置。

- 示例：查看pod.spec.containers的可选配置项



```shell
kubectl explain pod.spec.containers
```



```shell
# 返回的重要属性
KIND:     Pod
VERSION:  v1
RESOURCE: containers <[]Object>   # 数组，代表可以有多个容器FIELDS:
  name  <string>     # 容器名称
  image <string>     # 容器需要的镜像地址
  imagePullPolicy  <string> # 镜像拉取策略 
  command  <[]string> # 容器的启动命令列表，如不指定，使用打包时使用的启动命令
  args   <[]string> # 容器的启动命令需要的参数列表 
  env    <[]Object> # 容器环境变量的配置
  ports  <[]Object>  # 容器需要暴露的端口号列表
  resources <Object> # 资源限制和资源请求的设置
```



## 2.2 基本配置



- 创建pod-base.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-base
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
```



- 上面定义了一个比较简单的Pod的配置，里面有两个容器：

- - nginx：用的是1.17.1版本的nginx镜像创建（nginx是一个轻量级的web容器）。

- - busybox：用的是1.30版本的busybox镜像创建（busybox是一个小巧的linux命令集合）。

- 创建Pod：



```shell
kubectl apply -f pod-base.yaml
```



- 查看Pod状况：



```shell
kubectl get pod -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399422492-bbad03e0-8662-40f8-adef-34ce77c06c7b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 通过describe查看内部的详情：



```shell
# 此时已经运行起来了一个基本的Pod，虽然它暂时有问题
kubectl describe pod pod-base -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399436270-3d8fa890-c283-48c7-a5e2-67eb64c8e9e5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 2.3 镜像拉取策略



- 创建pod-imagepullpolicy.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-imagepullpolicy
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: Always # 用于设置镜像的拉取策略
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
```



- imagePullPolicy：用于设置镜像拉取的策略，kubernetes支持配置三种拉取策略：

- - Always：总是从远程仓库拉取镜像（一直远程下载）。

- - IfNotPresent：本地有则使用本地镜像，本地没有则从远程仓库拉取镜像（本地有就用本地，本地没有就使用远程下载）。

- - Never：只使用本地镜像，从不去远程仓库拉取，本地没有就报错（一直使用本地，没有就报错）。



默认值说明：

- 如果镜像tag为具体的版本号，默认策略是IfNotPresent。

- 如果镜像tag为latest（最终版本），默认策略是Always。



- 创建Pod：



```shell
kubectl apply -f pod-imagepullpolicy.yaml
```



- 查看Pod详情：



```shell
kubectl describe pod pod-imagepullpolicy -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399451865-3ff96bce-6b06-4c5d-bae7-b49b1b0890d6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 2.4 启动命令



- 在前面的案例中，一直有一个问题没有解决，就是busybox容器一直没有成功运行，那么到底是什么原因导致这个容器的故障的呢？

- 原来busybox并不是一个程序，而是类似于一个工具类的集合，kubernetes集群启动管理后，它会自动关闭。解决方法就是让其一直在运行，这就用到了command的配置。

- 创建pod-command.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-command
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
      command: ["/bin/sh","-c","touch /tmp/hello.txt;while true;do /bin/echo $(date +%T) >> /tmp/hello.txt;sleep 3;done;"]
```



command：用于在Pod中的容器初始化完毕之后执行一个命令。

这里稍微解释下command中的命令的意思：

- "/bin/sh","-c"：使用sh执行命令。

- touch /tmp/hello.txt：创建一个/tmp/hello.txt的文件。

- while true;do /bin/echo $(date +%T) >> /tmp/hello.txt;sleep 3;done：每隔3秒，向文件写入当前时间



- 创建Pod：



```shell
kubectl apply -f pod-command.yaml
```



- 查看Pod状态：



```shell
kubectl get pod pod-command -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399470089-0a8256c9-13f0-41ff-b5fb-7a67666c87ce.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 进入Pod中的busybox容器，查看文件内容：



```shell
# 在容器中执行命令
# kubectl exec -it pod的名称 -n 命名空间 -c 容器名称 /bin/sh
kubectl exec -it pod-command -n dev -c busybox /bin/sh
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399486025-6bb93ba6-2d95-4b80-9b25-7b08de78f0d3.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



特别说明：通过上面发现command已经可以完成启动命令和传递参数的功能，为什么还要提供一个args选项，用于传递参数？其实和Docker有点关系，kubernetes中的command和args两个参数其实是为了实现覆盖Dockerfile中的ENTRYPOINT的功能：

- 如果command和args均没有写，那么用Dockerfile的配置。

- 如果command写了，但是args没有写，那么Dockerfile默认的配置会被忽略，执行注入的command。

- 如果command没有写，但是args写了，那么Dockerfile中配置的ENTRYPOINT命令会被执行，使用当前args的参数。

- 如果command和args都写了，那么Dockerfile中的配置会被忽略，执行command并追加上args参数。



## 2.5 环境变量（不推荐）



- 创建pod-evn.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-env
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
      command: ["/bin/sh","-c","touch /tmp/hello.txt;while true;do /bin/echo $(date +%T) >> /tmp/hello.txt;sleep 3;done;"]
      env:
        - name: "username"
          value: "admin"
        - name: "password"
          value: "123456"
```



env：环境变量，用于在Pod中的容器设置环境变量。



- 创建Pod：



```shell
kubectl create -f pod-env.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399505900-9872420c-45b3-4503-a368-98073bc9bed1.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 进入容器，输出环境变量：



```shell
kubectl exec -it pod-env -n dev -c busybox -it /bin/sh
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399523140-75d62c44-c712-4700-8f10-256dc117ced6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



此种方式不推荐，推荐将这些配置单独存储在配置文件中，后面介绍。



## 2.6 端口设置



- 查看ports支持的子选项：



```shell
kubectl explain pod.spec.containers.ports
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399537174-fde54a9d-bc96-4644-9dee-e473772c0109.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



```yaml
KIND:     Pod
VERSION:  v1
RESOURCE: ports <[]Object>
FIELDS:
  name <string> # 端口名称，如果指定，必须保证name在pod中是唯一的
  containerPort <integer> # 容器要监听的端口(0<x<65536)
  hostPort <integer> # 容器要在主机上公开的端口，如果设置，主机上只能运行容器的一个副本(一般省略）
  hostIP <string>  # 要将外部端口绑定到的主机IP(一般省略)
  protocol <string>  # 端口协议。必须是UDP、TCP或SCTP。默认为“TCP”
```



- 创建pod-ports.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-ports
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
      ports:
        - name: nginx-port # 端口名称，如果执行，必须保证name在Pod中是唯一的
          containerPort: 80 # 容器要监听的端口 （0~65536）
          protocol: TCP # 端口协议
```



- 创建Pod：



```shell
kubectl create -f pod-ports.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399556059-3d04651f-3e7b-40c6-9e3d-8779d02c1116.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



访问Pod中的容器中的程序使用的是PodIp:containerPort。



## 2.7 资源配额



- 容器中的程序要运行，肯定会占用一定的资源，比如CPU和内存等，如果不对某个容器的资源做限制，那么它就可能吃掉大量的资源，导致其他的容器无法运行。针对这种情况，kubernetes提供了对内存和CPU的资源进行配额的机制，这种机制主要通过resources选项实现，它有两个子选项：

- - limits：用于限制运行的容器的最大占用资源，当容器占用资源超过limits时会被终止，并进行重启。

- - requests：用于设置容器需要的最小资源，如果环境资源不够，容器将无法启动。

- 可以通过上面的两个选项设置资源的上下限。

- 创建pod-resoures.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-resoures
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
      ports: # 端口设置
        - name: nginx-port # 端口名称，如果执行，必须保证name在Pod中是唯一的
          containerPort: 80 # 容器要监听的端口 （0~65536）
          protocol: TCP # 端口协议
      resources: # 资源配额
        limits: # 限制资源的上限
          cpu: "2" # CPU限制，单位是core数
          memory: "10Gi" # 内存限制
        requests: # 限制资源的下限
          cpu: "1" # CPU限制，单位是core数 
          memory: "10Mi" # 内存限制
```



cpu：core数，可以为整数或小数。

memory：内存大小，可以使用Gi、Mi、G、M等形式。



- 创建Pod：



```shell
kubectl create -f pod-resource.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399573785-e4cbf8e3-3c30-4469-be50-dc7264310331.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看发现Pod运行正常：



```shell
kubectl get pod pod-resoures -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399597891-16a87162-b68e-461b-9b58-132bb2cd500f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 接下来，停止Pod：



```shell
kubectl delete -f pod-resource.yaml
```



- 编辑Pod，修改resources.requests.memory的值为10Gi：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-resoures
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
      ports: # 端口设置
        - name: nginx-port # 端口名称，如果执行，必须保证name在Pod中是唯一的
          containerPort: 80 # 容器要监听的端口 （0~65536）
          protocol: TCP # 端口协议
      resources: # 资源配额
        limits: # 限制资源的上限
          cpu: "2" # CPU限制，单位是core数
          memory: "10Gi" # 内存限制
        requests: # 限制资源的下限
          cpu: "1" # CPU限制，单位是core数 
          memory: "10Gi" # 内存限制
```



- 再次启动Pod：



```shell
kubectl create -f pod-resource.yaml
```



- 查看Pod状态，发现Pod启动失败：



```shell
kubectl get pod pod-resoures -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399615549-6f1a7e73-ddb7-4b6b-9f9b-a69c75abc062.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情会发现，如下提示：



```shell
kubectl describe pod pod-resoures -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399635888-cd5ea7aa-c847-4e29-8955-3ab725e648d0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 3 Pod的生命周期



## 3.1 概述



- 我们一般将Pod对象从创建到终止的这段时间范围称为Pod的生命周期，它主要包含下面的过程：

- - Pod创建过程。

- - 运行初始化容器（init container）过程。

- - 运行主容器（main container）：

- - - 容器启动后钩子（post start）、容器终止前钩子（pre stop）。

- - - 容器的存活性探测（liveness probe）、就绪性探测（readiness probe）。

- - Pod终止过程。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399647590-472c8628-8b69-42ab-8a50-929c27737926.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_33%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 在整个生命周期中，Pod会出现5种状态（相位），分别如下：

- - 挂起（Pending）：API Server已经创建了Pod资源对象，但它尚未被调度完成或者仍处于下载镜像的过程中。

- - 运行中（Running）：Pod已经被调度到某节点，并且所有容器都已经被kubelet创建完成。

- - 成功（Succeeded）：Pod中的所有容器都已经成功终止并且不会被重启。

- - 失败（Failed）：所有容器都已经终止，但至少有一个容器终止失败，即容器返回了非0值的退出状态。

- - 未知（Unknown）：API Server无法正常获取到Pod对象的状态信息，通常由于网络通信失败所导致。



## 3.2 创建和终止



### 3.2.1 Pod的创建过程



![img](https://cdn.nlark.com/yuque/0/2020/jpeg/513185/1609399660203-ab0d9834-3b35-4119-b304-4394b00f0b9d.jpeg?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_35%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- ① 用户通过kubectl或其他的api客户端提交需要创建的Pod信息给API Server。

- ② API Server开始生成Pod对象的信息，并将信息存入etcd，然后返回确认信息至客户端。

- ③ API Server开始反映etcd中的Pod对象的变化，其它组件使用watch机制来跟踪检查API Server上的变动。

- ④ Scheduler发现有新的Pod对象要创建，开始为Pod分配主机并将结果信息更新至API Server。

- ⑤ Node节点上的kubelet发现有Pod调度过来，尝试调度Docker启动容器，并将结果回送至API Server。

- ⑥ API Server将接收到的Pod状态信息存入到etcd中。



### 3.2.2 Pod的终止过程



- ① 用户向API Server发送删除Pod对象的命令。

- ② API Server中的Pod对象信息会随着时间的推移而更新，在宽限期内（默认30s），Pod被视为dead。

- ③ 将Pod标记为terminating状态。

- ④ kubelete在监控到Pod对象转为terminating状态的同时启动Pod关闭过程。

- ⑤ 端点控制器监控到Pod对象的关闭行为时将其从所有匹配到此端点的service资源的端点列表中移除。

- ⑥ 如果当前Pod对象定义了preStop钩子处理器，则在其标记为terminating后会以同步的方式启动执行。

- ⑦ Pod对象中的容器进程收到停止信号。

- ⑧ 宽限期结束后，如果Pod中还存在运行的进程，那么Pod对象会收到立即终止的信号。

- ⑨ kubectl请求API Server将此Pod资源的宽限期设置为0从而完成删除操作，此时Pod对于用户已经不可用了。



## 3.3 初始化容器



- 初始化容器是在Pod的主容器启动之前要运行的容器，主要是做一些主容器的前置工作，它具有两大特征：

- - ① 初始化容器必须运行完成直至结束，如果某个初始化容器运行失败，那么kubernetes需要重启它直至成功完成。

- - ② 初始化容器必须按照定义的顺序执行，当且仅当前一个成功之后，后面的一个才能运行。

- 初始化容器有很多的应用场景，下面列出的是最常见的几个：

- - 提供主容器镜像中不具备的工具程序或自定义代码。

- - 初始化容器要先于应用容器串行启动并运行完成，因此可用于延后应用容器的启动直至其依赖的条件得到满足。

- 接下来做一个案例，模拟下面这个需求：

- - 假设要以主容器来运行Nginx，但是要求在运行Nginx之前要能够连接上MySQL和Redis所在的服务器。

- - 为了简化测试，事先规定好MySQL和Redis所在的IP地址分别为192.168.18.103和192.168.18.104（注意，这两个IP都不能ping通，因为环境中没有这两个IP）。

- 创建pod-initcontainer.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-initcontainer
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      resources:
        limits:
          cpu: "2"
          memory: "10Gi"
        requests:
          cpu: "1"
          memory: "10Mi"
  initContainers: # 初始化容器配置
    - name: test-mysql
      image: busybox:1.30
      command: ["sh","-c","until ping 192.168.18.103 -c 1;do echo waiting for mysql ...;sleep 2;done;"]
      securityContext:
        privileged: true # 使用特权模式运行容器
    - name: test-redis
      image: busybox:1.30
      command: ["sh","-c","until ping 192.168.18.104 -c 1;do echo waiting for redis ...;sleep 2;done;"]
```



- 创建Pod：



```shell
kubectl create -f pod-initcontainer.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399678517-69e66241-fc21-4fbd-8909-021dd0797323.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod状态：



```shell
kubectl describe pod pod-initcontainer -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399693516-6c5930af-fb72-48b4-8b27-6d3e817a811e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 动态查看Pod：



```shell
kubectl get pod pod-initcontainer -n dev -w
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399711402-b33fee9f-1eed-4db5-879b-94bf4ed33a0c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 接下来，新开一个shell，为当前服务器（192.168.18.100）新增两个IP，观察Pod的变化：



```shell
ifconfig ens33:1 192.168.18.103 netmask 255.255.255.0 up
```



```shell
ifconfig ens33:2 192.168.18.104 netmask 255.255.255.0 up
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399728530-c2d092d2-918a-437e-9a39-9fefc12d7ee6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.4 钩子函数



- 钩子函数能够感知自身生命周期中的事件，并在相应的时刻到来时运行用户指定的程序代码。

- kubernetes在主容器启动之后和停止之前提供了两个钩子函数：

- - post start：容器创建之后执行，如果失败会重启容器。

- - pre stop：容器终止之前执行，执行完成之后容器将成功终止，在其完成之前会阻塞删除容器的操作。

- 钩子处理器支持使用下面的三种方式定义动作：

- - ① exec命令：在容器内执行一次命令。

```yaml
……
  lifecycle:
     postStart: 
        exec:
           command:
             - cat
             - /tmp/healthy
……
```

- - ② tcpSocket：在当前容器尝试访问指定的socket。

```yaml
…… 
   lifecycle:
      postStart:
         tcpSocket:
            port: 8080
……
```

- - ③ httpGet：在当前容器中向某url发起HTTP请求。

```yaml
…… 
   lifecycle:
      postStart:
         httpGet:
            path: / #URI地址
            port: 80 #端口号
            host: 192.168.109.100 #主机地址  
            scheme: HTTP #支持的协议，http或者https
……
```

- 接下来，以exec方式为例，演示下钩子函数的使用，创建pod-hook-exec.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-hook-exec
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      resources:
        limits:
          cpu: "2"
          memory: "10Gi"
        requests:
          cpu: "1"
          memory: "10Mi"
      lifecycle: # 生命周期配置
        postStart: # 容器创建之后执行，如果失败会重启容器
          exec: # 在容器启动的时候，执行一条命令，修改掉Nginx的首页内容
            command: ["/bin/sh","-c","echo postStart ... > /usr/share/nginx/html/index.html"]
        preStop: # 容器终止之前执行，执行完成之后容器将成功终止，在其完成之前会阻塞删除容器的操作
          exec: # 在容器停止之前停止Nginx的服务
            command: ["/usr/sbin/nginx","-s","quit"]
```



- 创建Pod：



```shell
kubectl create -f pod-hook-exec.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399746272-45a4e590-4858-45b6-a4b3-2f5e2ecd4023.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod-hook-exec -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399757639-d53d168e-de51-4875-8998-83f6b984cb45.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 访问Pod：



```shell
curl 10.244.1.11
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399769407-980c3b89-77ac-46b6-afe9-b369af5f1cff.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.5 容器探测



### 3.5.1 概述



- 容器探测用于检测容器中的应用实例是否正常工作，是保障业务可用性的一种传统机制。如果经过探测，实例的状态不符合预期，那么kubernetes就会把该问题实例“摘除”，不承担业务流量。kubernetes提供了两种探针来实现容器探测，分别是：

- - liveness probes：存活性探测，用于检测应用实例当前是否处于正常运行状态，如果不是，k8s会重启容器。

- - readiness probes：就绪性探测，用于检测应用实例是否可以接受请求，如果不能，k8s不会转发流量。



livenessProbe：存活性探测，决定是否重启容器。

readinessProbe：就绪性探测，决定是否将请求转发给容器。



k8s在1.16版本之后新增了startupProbe探针，用于判断容器内应用程序是否已经启动。如果配置了startupProbe探针，就会先禁止其他的探针，直到startupProbe探针成功为止，一旦成功将不再进行探测。





- 上面两种探针目前均支持三种探测方式：

- - ① exec命令：在容器内执行一次命令，如果命令执行的退出码为0，则认为程序正常，否则不正常。

```yaml
……
  livenessProbe:
     exec:
        command:
          -	cat
          -	/tmp/healthy
……
```

- - ② tcpSocket：将会尝试访问一个用户容器的端口，如果能够建立这条连接，则认为程序正常，否则不正常。

```yaml
……
   livenessProbe:
      tcpSocket:
         port: 8080
……
```

- - ③ httpGet：调用容器内web应用的URL，如果返回的状态码在200和399之前，则认为程序正常，否则不正常。

```yaml
……
   livenessProbe:
      httpGet:
         path: / #URI地址
         port: 80 #端口号
         host: 127.0.0.1 #主机地址
         scheme: HTTP #支持的协议，http或者https
……
```



### 3.5.2 exec方式



- 创建pod-liveness-exec.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-liveness-exec
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探针
        exec:
          command: ["/bin/cat","/tmp/hello.txt"] # 执行一个查看文件的命令，必须失败，因为根本没有这个文件
```



- 创建Pod：



```shell
kubectl create -f pod-liveness-exec.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399786116-bc89d268-fad0-4212-95ef-7f6ac6942040.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情：



```shell
kubectl describe pod pod-liveness-exec -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399798766-24a3c62d-e04a-4d66-b655-d88c991c17a8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 观察上面的信息就会发现nginx容器启动之后就进行了健康检查。

- 检查失败之后，容器被kill掉，然后尝试进行重启，这是重启策略的作用。

- 稍等一会之后，再观察Pod的信息，就会看到RESTARTS不再是0，而是一直增长。



- 查看Pod信息：



```shell
kubectl get pod pod-liveness-exec -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399815428-5e5a5e0b-74f5-490f-a222-7b1e03ce313c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_23%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.5.3 tcpSocket方式



- 创建pod-liveness-tcpsocket.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-liveness-tcpsocket
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探针
        tcpSocket:
          port: 8080 # 尝试访问8080端口，必须失败，因为Pod内部只有一个Nginx容器，而且只是监听了80端口
```



- 创建Pod：



```shell
kubectl create -f pod-liveness-tcpsocket.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399830265-617437cb-7031-403c-8b4f-3ca7999348ab.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_23%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情：



```shell
kubectl describe pod pod-liveness-tcpsocket -n  dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399842301-04489bee-94c7-4af2-ac28-5b0204cf3e36.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



观察上面的信息，发现尝试访问8080端口，但是失败了

稍等一会之后，再观察Pod的信息，就会看到RESTARTS不再是0，而是一直增长。



- 查看Pod信息：



```shell
kubectl get pod pod-liveness-tcpsocket -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399859446-a57a79e0-abfd-4c02-909c-68eebc6996de.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.5.4 httpGet方式



- 创建pod-liveness-httpget.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-liveness-httpget
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探针
        httpGet: # 其实就是访问http://127.0.0.1:80/hello
          port: 80 # 端口号
          scheme: HTTP # 支持的协议，HTTP或HTTPS
          path: /hello # URI地址
          host: 127.0.0.1 # 主机地址
```



- 创建Pod：



```shell
kubectl create -f pod-liveness-httpget.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399928718-0bee02da-3f24-4beb-9f2b-0be3fe6b6935.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情：



```shell
kubectl describe pod pod-liveness-httpget -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399942114-b133dab6-281c-4b0c-9036-e3e6f5aabd2f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod信息：



```shell
kubectl get pod pod-liveness-httpget -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399961035-c8301a2d-cbed-42ad-94c4-78209ffe40db.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.5.5 容器探测的补充



- 上面已经使用了livenessProbe演示了三种探测方式，但是查看livenessProbe的子属性，会发现除了这三种方式，还有一些其他的配置。



```shell
kubectl explain pod.spec.containers.livenessProbe
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399974021-e4266d75-18b2-4622-9761-926b0a7368e3.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



FIELDS:

exec 

tcpSocket  

httpGet   

initialDelaySeconds   # 容器启动后等待多少秒执行第一次探测

timeoutSeconds    # 探测超时时间。默认1秒，最小1秒

periodSeconds    # 执行探测的频率。默认是10秒，最小1秒

failureThreshold   # 连续探测失败多少次才被认定为失败。默认是3。最小值是1

successThreshold   # 连续探测成功多少次才被认定为成功。默认是1



## 3.6 重启策略



- 在容器探测中，一旦容器探测出现了问题，kubernetes就会对容器所在的Pod进行重启，其实这是由Pod的重启策略决定的，Pod的重启策略有3种，分别如下：

- - Always：容器失效时，自动重启该容器，默认值。

- - OnFailure：容器终止运行且退出码不为0时重启。

- - Never：不论状态如何，都不重启该容器。

- 重启策略适用于Pod对象中的所有容器，首次需要重启的容器，将在其需要的时候立即进行重启，随后再次重启的操作将由kubelet延迟一段时间后进行，且反复的重启操作的延迟时长以此为10s、20s、40s、80s、160s和300s，300s是最大的延迟时长。

- 创建pod-restart-policy.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-restart-policy
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探测
        httpGet:
          port: 80
          path: /hello
          host: 127.0.0.1
          scheme: HTTP
  restartPolicy: Never # 重启策略
```



- 创建Pod：



```shell
kubectl create -f pod-restart-policy.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399990072-df160cc5-bdf3-4944-8714-1b73cf32e608.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情，发现nginx容器启动失败：



```shell
kubectl describe pod pod-restart-policy -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400016288-813442b2-f172-4b88-81de-993bf205d741.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_34%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



多等一会，观察Pod的重试次数，发现一直是0，并未重启。



- 查看Pod：



```shell
kubectl get pod pod-restart-policy -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400033371-b66de2ca-11f3-4ae6-9f46-a56426f310f7.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 4 Pod的调度



## 4.1 概述



- 在默认情况下，一个Pod在哪个Node节点上运行，是由Scheduler组件采用相应的算法计算出来的，这个过程是不受人工控制的。但是在实际使用中，这并不满足需求，因为很多情况下，我们想控制某些Pod到达某些节点上，那么应该怎么做？这就要求了解kubernetes对Pod的调度规则，kubernetes提供了四大类调度方式。

- - 自动调度：运行在哪个Node节点上完全由Scheduler经过一系列的算法计算得出。

- - 定向调度：NodeName、NodeSelector。

- - 亲和性调度：NodeAffinity、PodAffinity、PodAntiAffinity。

- - 污点（容忍）调度：Taints、Toleration。



## 4.2 定向调度



### 4.2.1 概述



- 定向调度，指的是利用在Pod上声明的nodeName或nodeSelector，以此将Pod调度到期望的Node节点上。注意，这里的调度是强制的，这就意味着即使要调度的目标Node不存在，也会向上面进行调度，只不过Pod运行失败而已。



### 4.2.2 nodeName



- nodeName用于强制约束将Pod调度到指定的name的Node节点上。这种方式，其实是直接跳过Scheduler的调度逻辑，直接将Pod调度到指定名称的节点。

- 创建一个pod-nodename.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodename
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  nodeName: k8s-node1 # 指定调度到k8s-node1节点上
```



- 创建Pod：



```shell
kubectl create -f pod-nodename.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400050057-6391b94b-d5c7-4659-a71c-3e16c116ee2f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod-nodename -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400063217-171a4311-e104-48e8-9390-8a65b0e814ba.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)





### 4.2.3 nodeSelector



- nodeSelector用于将Pod调度到添加了指定标签的Node节点上，它是通过kubernetes的label-selector机制实现的，换言之，在Pod创建之前，会由Scheduler使用MatchNodeSelector调度策略进行label匹配，找出目标node，然后将Pod调度到目标节点，该匹配规则是强制约束。

- 首先给node节点添加标签：



```shell
kubectl label node k8s-node1 nodeevn=pro
```



```shell
kubectl label node k8s-node2 nodeenv=test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400079974-170efa34-4bfb-400b-ba27-1254b45be7d2.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建pod-nodeselector.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeselector
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  nodeSelector:
    nodeenv: pro # 指定调度到具有nodeenv=pro的Node节点上
```



- 创建Pod：



```shell
kubectl create -f pod-nodeselector.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400096271-1c2af967-f8a4-4b01-b742-8befa4b0e714.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod-nodeselector -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400107729-b4464f9a-064a-4884-a998-349a28596654.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 4.3 亲和性调度



### 4.3.1 概述



- 虽然定向调度的两种方式，使用起来非常方便，但是也有一定的问题，那就是如果没有满足条件的Node，那么Pod将不会被运行，即使在集群中还有可用的Node列表也不行，这就限制了它的使用场景。

- 基于上面的问题，kubernetes还提供了一种亲和性调度（Affinity）。它在nodeSelector的基础之上进行了扩展，可以通过配置的形式，实现优先选择满足条件的Node进行调度，如果没有，也可以调度到不满足条件的节点上，使得调度更加灵活。

- Affinity主要分为三类：

- - nodeAffinity（node亲和性）：以Node为目标，解决Pod可以调度到那些Node的问题。

- - podAffinity（pod亲和性）：以Pod为目标，解决Pod可以和那些已存在的Pod部署在同一个拓扑域中的问题。

- - podAntiAffinity（pod反亲和性）：以Pod为目标，解决Pod不能和那些已经存在的Pod部署在同一拓扑域中的问题。



关于亲和性和反亲和性的使用场景的说明：

- 亲和性：如果两个应用频繁交互，那么就有必要利用亲和性让两个应用尽可能的靠近，这样可以较少因网络通信而带来的性能损耗。

- 反亲和性：当应用采用多副本部署的时候，那么就有必要利用反亲和性让各个应用实例打散分布在各个Node上，这样可以提高服务的高可用性。



### 4.3.2 nodeAffinity



- 查看nodeAffinity的可选配置项：



```yaml
pod.spec.affinity.nodeAffinity
  requiredDuringSchedulingIgnoredDuringExecution  Node节点必须满足指定的所有规则才可以，相当于硬限制
    nodeSelectorTerms  节点选择列表
      matchFields   按节点字段列出的节点选择器要求列表  
      matchExpressions   按节点标签列出的节点选择器要求列表(推荐)
        key    键
        values 值
        operator 关系符 支持Exists, DoesNotExist, In, NotIn, Gt, Lt
  preferredDuringSchedulingIgnoredDuringExecution 优先调度到满足指定的规则的Node，相当于软限制 (倾向)     
    preference   一个节点选择器项，与相应的权重相关联
      matchFields 按节点字段列出的节点选择器要求列表
      matchExpressions   按节点标签列出的节点选择器要求列表(推荐)
        key 键
        values 值
        operator 关系符 支持In, NotIn, Exists, DoesNotExist, Gt, Lt  
    weight 倾向权重，在范围1-100。
```



关系符的使用说明:

```yaml
- matchExpressions:
	- key: nodeenv # 匹配存在标签的key为nodeenv的节点
	  operator: Exists   
	- key: nodeenv # 匹配标签的key为nodeenv,且value是"xxx"或"yyy"的节点
	  operator: In    
      values: ["xxx","yyy"]
    - key: nodeenv # 匹配标签的key为nodeenv,且value大于"xxx"的节点
      operator: Gt   
      values: "xxx"
```



- 下面演示requiredDuringSchedulingIgnoredDuringExecution：

- - 创建pod-nodeaffinity-required.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeaffinity-required
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    nodeAffinity: # node亲和性配置
      requiredDuringSchedulingIgnoredDuringExecution: # Node节点必须满足指定的所有规则才可以，相当于硬规则，类似于定向调度
        nodeSelectorTerms: # 节点选择列表
          - matchExpressions:
              - key: nodeenv # 匹配存在标签的key为nodeenv的节点，并且value是"xxx"或"yyy"的节点
                operator: In
                values:
                  - "xxx"
                  - "yyy"
```

- - 创建Pod：

```shell
kubectl create -f pod-nodeaffinity-required.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400130529-8dc1ccdc-573a-4763-840f-be5ed8afd66c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod状态（运行失败）：

```shell
kubectl get pod pod-nodeaffinity-required -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400146920-f4a1d88c-9b12-4aef-9957-b92be4d36d0e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod详情（发现调度失败，提示node选择失败）：

```shell
kubectl describe pod pod-nodeaffinity-required -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400161978-fd7e982f-1eea-449e-a108-71a4e0921c91.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 删除Pod：

```shell
kubectl delete -f pod-nodeaffinity-required.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400177211-3b5b496d-e184-4266-9164-2cf73d00c95e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 修改pod-nodeaffinity-required.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeaffinity-required
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    nodeAffinity: # node亲和性配置
      requiredDuringSchedulingIgnoredDuringExecution: # Node节点必须满足指定的所有规则才可以，相当于硬规则，类似于定向调度
        nodeSelectorTerms: # 节点选择列表
          - matchExpressions:
              - key: nodeenv # 匹配存在标签的key为nodeenv的节点，并且value是"xxx"或"yyy"的节点
                operator: In
                values:
                  - "pro"
                  - "yyy"
```

- - 再次创建Pod：

```shell
kubectl create -f pod-nodeaffinity-required.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400194591-d80acca9-4570-4b1b-a822-0332db005570.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 再次查看Pod：

```shell
kubectl get pod pod-nodeaffinity-required -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400216267-7a398c8e-7e19-402b-9075-39c822a19932.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 下面演示preferredDuringSchedulingIgnoredDuringExecution：

- - 创建pod-nodeaffinity-preferred.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeaffinity-preferred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    nodeAffinity: # node亲和性配置
      preferredDuringSchedulingIgnoredDuringExecution: # 优先调度到满足指定的规则的Node，相当于软限制 (倾向)
        - preference: # 一个节点选择器项，与相应的权重相关联
            matchExpressions:
              - key: nodeenv
                operator: In
                values:
                  - "xxx"
                  - "yyy"
          weight: 1
```

- - 创建Pod：

```shell
kubectl create -f pod-nodeaffinity-preferred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400233720-5fd3aec2-ed09-4c07-b72e-92532acaa0db.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod：

```shell
kubectl get pod pod-nodeaffinity-preferred -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400249643-b64cb7e3-9d90-47cc-8679-2c4f327510ea.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



nodeAffinity的注意事项：

- 如果同时定义了nodeSelector和nodeAffinity，那么必须两个条件都满足，Pod才能运行在指定的Node上。

- 如果nodeAffinity指定了多个nodeSelectorTerms，那么只需要其中一个能够匹配成功即可。

- 如果一个nodeSelectorTerms中有多个matchExpressions，则一个节点必须满足所有的才能匹配成功。

- 如果一个Pod所在的Node在Pod运行期间其标签发生了改变，不再符合该Pod的nodeAffinity的要求，则系统将忽略此变化。



### 4.3.3 podAffinity



- podAffinity主要实现以运行的Pod为参照，实现让新创建的Pod和参照的Pod在一个区域的功能。

- PodAffinity的可选配置项：



```yaml
pod.spec.affinity.podAffinity
  requiredDuringSchedulingIgnoredDuringExecution  硬限制
    namespaces 指定参照pod的namespace
    topologyKey 指定调度作用域
    labelSelector 标签选择器
      matchExpressions  按节点标签列出的节点选择器要求列表(推荐)
        key    键
        values 值
        operator 关系符 支持In, NotIn, Exists, DoesNotExist.
      matchLabels    指多个matchExpressions映射的内容  
  preferredDuringSchedulingIgnoredDuringExecution 软限制    
    podAffinityTerm  选项
      namespaces
      topologyKey
      labelSelector
         matchExpressions 
            key    键  
            values 值  
            operator
         matchLabels 
    weight 倾向权重，在范围1-1
```



topologyKey用于指定调度的作用域，例如:

- 如果指定为kubernetes.io/hostname，那就是以Node节点为区分范围。

- 如果指定为beta.kubernetes.io/os，则以Node节点的操作系统类型来区分。



- 演示requiredDuringSchedulingIgnoredDuringExecution。

- 创建参照Pod过程：

- - 创建pod-podaffinity-target.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podaffinity-target
  namespace: dev
  labels:
    podenv: pro # 设置标签
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  nodeName: k8s-node1 # 将目标pod定向调度到k8s-node1
```

- - 创建参照Pod：

```shell
kubectl create -f pod-podaffinity-target.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400266660-28c7f4fa-0339-407c-80ee-abcb28db67ef.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看参照Pod：

```shell
kubectl get pod pod-podaffinity-target -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400280597-374da063-76d8-4e5a-bdd9-b6a6d96f98db.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 创建Pod过程：

- - 创建pod-podaffinity-requred.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podaffinity-requred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    podAffinity: # Pod亲和性
      requiredDuringSchedulingIgnoredDuringExecution: # 硬限制
        - labelSelector:
            matchExpressions: # 该Pod必须和拥有标签podenv=xxx或者podenv=yyy的Pod在同一个Node上，显然没有这样的Pod
              - key: podenv
                operator: In
                values:
                  - "xxx"
                  - "yyy"
          topologyKey: kubernetes.io/hostname
```

- - 创建Pod：

```shell
kubectl create -f pod-podaffinity-requred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400308332-4ca652bb-c472-4c8a-a9df-8ddd42d99bc2.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod状态，发现没有运行：

```shell
kubectl get pod pod-podaffinity-requred -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400325975-4a374839-d904-4b62-98b3-5f06fd066844.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod详情：

```shell
kubectl get pod pod-podaffinity-requred -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400339276-a7f00c7c-9112-49a6-b7a5-92f354e72adc.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 删除Pod：

```shell
kubectl delete -f pod-podaffinity-requred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400353599-05b0c603-8dd6-474c-a32a-8d1b2b379f4a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 修改pod-podaffinity-requred.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podaffinity-requred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    podAffinity: # Pod亲和性
      requiredDuringSchedulingIgnoredDuringExecution: # 硬限制
        - labelSelector:
            matchExpressions: # 该Pod必须和拥有标签podenv=xxx或者podenv=yyy的Pod在同一个Node上，显然没有这样的Pod
              - key: podenv
                operator: In
                values:
                  - "pro"
                  - "yyy"
          topologyKey: kubernetes.io/hostname
```

- - 再次创建Pod：

```shell
kubectl create -f pod-podaffinity-requred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400375097-b2d478c0-7da8-4a69-8890-9eb398ec0943.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 再次查看Pod：

```shell
kubectl get pod pod-podaffinity-requred -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400388672-72386db7-2b6b-432c-b3d3-803461ef0f89.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 4.3.4 podAntiAffinity



- podAntiAffinity主要实现以运行的Pod为参照，让新创建的Pod和参照的Pod不在一个区域的功能。

- 其配置方式和podAffinity一样，此处不做详细解释。

- 使用上个案例中的目标Pod：



```shell
kubectl get pod -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400403258-3847628f-b239-4efc-a6a4-c767c540d5f8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建pod-podantiaffinity-requred.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podantiaffinity-requred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    podAntiAffinity: # Pod反亲和性
      requiredDuringSchedulingIgnoredDuringExecution: # 硬限制
        - labelSelector:
            matchExpressions:
              - key: podenv
                operator: In
                values:
                  - "pro"
          topologyKey: kubernetes.io/hostname
```



- 创建Pod：



```shell
kubectl create -f pod-podantiaffinity-requred.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400418144-1a0771ef-e325-43d1-8a61-11d0ed4e5679.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400431349-7426874d-9633-4c75-a115-28defe01fb4f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 4.4 污点和容忍



### 4.4.1 污点（Taints）



- 前面的调度方式都是站在Pod的角度上，通过在Pod上添加属性，来确定Pod是否要调度到指定的Node上，其实我们也可以站在Node的角度上，通过在Node上添加`污点属性`，来决定是否运行Pod调度过来。

- Node被设置了污点之后就和Pod之间存在了一种相斥的关系，进而拒绝Pod调度进来，甚至可以将已经存在的Pod驱逐出去。

- 污点的格式为：`key=value:effect`，key和value是污点的标签，effect描述污点的作用，支持如下三个选项：

- - PreferNoSchedule：kubernetes将尽量避免把Pod调度到具有该污点的Node上，除非没有其他节点可以调度。

- - NoSchedule：kubernetes将不会把Pod调度到具有该污点的Node上，但是不会影响当前Node上已经存在的Pod。

- - NoExecute：kubernetes将不会把Pod调度到具有该污点的Node上，同时也会将Node上已经存在的Pod驱逐。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400444401-f4579175-f530-45e3-a219-19d31b1cf4a5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：

- - 设置污点：

```shell
kubectl taint node xxx key=value:effect
```

- - 去除污点：

```shell
kubectl taint node xxx key:effect-
```

- - 去除所有污点：

```shell
kubectl taint node xxx key-
```

- - 查询所有节点的污点：

```shell
wget -O jq https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64
chmod +x ./jq
cp jq /usr/bin
```

- - 列出所有节点的污点方式一：

```shell
kubectl get nodes -o json | jq '.items[].spec'
```

- - 列出所有节点的污点方式二：

```shell
kubectl get nodes -o json | jq '.items[].spec.taints'
```

- - 查看指定节点上的污点：

```shell
kubectl describe node 节点名称
```

- 接下来，演示污点效果：

- - ① 准备节点k8s-node1（为了演示效果更加明显，暂时停止k8s-node2节点）。

- - ② 为k8s-node1节点设置一个污点：`tag=xudaxian:PreferNoSchedule`，然后创建Pod1（Pod1可以）。

- - ③ 修改k8s-node1节点的污点为：`tag=xudaxian:NoSchedule`，然后创建Pod2（Pod1可以正常运行，Pod2失败）。

- - ④ 修改k8s-node1节点的污点为：`tag=xudaxian:NoExecute`，然后创建Pod3（Pod1、Pod2、Pod3失败）。

- 为k8s-node1设置污点（PreferNoSchedule）：



```shell
kubectl taint node k8s-node1 tag=xudaxian:PreferNoSchedule
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400474950-8d42f6b6-2a76-4641-9ce2-429286ed3a68.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建Pod1：



```shell
kubectl run pod1 --image=nginx:1.17.1 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400488318-5871a81b-b88f-4ddf-bd2b-4523f66ee587.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod1 -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400501427-73a72847-217c-4057-898f-2e7ab305bef8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 为k8s-node1取消污点（PreferNoSchedule），并设置污点（NoSchedule）：



```shell
kubectl taint node k8s-node1 tag:PreferNoSchedule-
```



```shell
kubectl taint node k8s-node1 tag=xudaxian:NoSchedule
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400518141-15814962-cdc3-4e31-b2d9-ae021310eb0c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建Pod2：



```shell
kubectl run pod2 --image=nginx:1.17.1 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400532133-f4d9cf31-55a5-405c-8291-0bdc9186b65b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod1 -n dev -o wide
```



```shell
kubectl get pod pod2 -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400545682-4bd82344-2850-4cf6-a529-63d507f25aa5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 为k8s-node1取消污点（NoSchedule），并设置污点（NoExecute）：



```shell
kubectl taint node k8s-node1 tag:NoSchedule-
```



```shell
kubectl taint node k8s-node1 tag=xudaxian:NoExecute
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400560213-01e44395-fb0c-41a5-927f-e40d4f5f061a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建Pod3：



```shell
kubectl run pod3 --image=nginx:1.17.1 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400573500-10d4ebdf-4707-45ca-95c8-a68c18b96c8b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod1 -n dev -o wide
```



```shell
kubectl get pod pod2 -n dev -o wide
```



```shell
kubectl get pod pod3 -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400588008-d29be45c-8efd-47d2-a000-65a67ae6f70f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



使用kubeadm搭建的集群，默认就会给Master节点添加一个污点标记，所以Pod就不会调度到Master节点上。



# k8s的Pod详解

详细介绍Pod资源的各种配置（YAML）和原理。



# 1 Pod的介绍



## 1.1 Pod的结构



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399357456-e5dc5f6d-7c2e-44bf-aae3-50d51ec951e9.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_18%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 每个Pod中都包含一个或者多个容器，这些容器可以分为两类：

- ① 用户程序所在的容器，数量可多可少。

- ② Pause容器，这是每个Pod都会有的一个根容器，它的作用有两个：

- - 可以以它为依据，评估整个Pod的健康状况。

- - 可以在根容器上设置IP地址，其它容器都共享此IP（Pod的IP），以实现Pod内部的网络通信（这里是Pod内部的通讯，Pod之间的通讯采用虚拟二层网络技术来实现，我们当前环境使用的是Flannel）。



## 1.2 Pod定义



- 下面是Pod的资源清单：



```yaml
apiVersion: v1     #必选，版本号，例如v1
kind: Pod       　 #必选，资源类型，例如 Pod
metadata:       　 #必选，元数据
  name: string     #必选，Pod名称
  namespace: string  #Pod所属的命名空间,默认为"default"
  labels:       　　  #自定义标签列表
    - name: string      　          
spec:  #必选，Pod中容器的详细定义
  containers:  #必选，Pod中容器列表
  - name: string   #必选，容器名称
    image: string  #必选，容器的镜像名称
    imagePullPolicy: [ Always|Never|IfNotPresent ]  #获取镜像的策略 
    command: [string]   #容器的启动命令列表，如不指定，使用打包时使用的启动命令
    args: [string]      #容器的启动命令参数列表
    workingDir: string  #容器的工作目录
    volumeMounts:       #挂载到容器内部的存储卷配置
    - name: string      #引用pod定义的共享存储卷的名称，需用volumes[]部分定义的的卷名
      mountPath: string #存储卷在容器内mount的绝对路径，应少于512字符
      readOnly: boolean #是否为只读模式
    ports: #需要暴露的端口库号列表
    - name: string        #端口的名称
      containerPort: int  #容器需要监听的端口号
      hostPort: int       #容器所在主机需要监听的端口号，默认与Container相同
      protocol: string    #端口协议，支持TCP和UDP，默认TCP
    env:   #容器运行前需设置的环境变量列表
    - name: string  #环境变量名称
      value: string #环境变量的值
    resources: #资源限制和请求的设置
      limits:  #资源限制的设置
        cpu: string     #Cpu的限制，单位为core数，将用于docker run --cpu-shares参数
        memory: string  #内存限制，单位可以为Mib/Gib，将用于docker run --memory参数
      requests: #资源请求的设置
        cpu: string    #Cpu请求，容器启动的初始可用数量
        memory: string #内存请求,容器启动的初始可用数量
    lifecycle: #生命周期钩子
		postStart: #容器启动后立即执行此钩子,如果执行失败,会根据重启策略进行重启
		preStop: #容器终止前执行此钩子,无论结果如何,容器都会终止
    livenessProbe:  #对Pod内各容器健康检查的设置，当探测无响应几次后将自动重启该容器
      exec:       　 #对Pod容器内检查方式设置为exec方式
        command: [string]  #exec方式需要制定的命令或脚本
      httpGet:       #对Pod内个容器健康检查方法设置为HttpGet，需要制定Path、port
        path: string
        port: number
        host: string
        scheme: string
        HttpHeaders:
        - name: string
          value: string
      tcpSocket:     #对Pod内个容器健康检查方式设置为tcpSocket方式
         port: number
       initialDelaySeconds: 0       #容器启动完成后首次探测的时间，单位为秒
       timeoutSeconds: 0    　　    #对容器健康检查探测等待响应的超时时间，单位秒，默认1秒
       periodSeconds: 0     　　    #对容器监控检查的定期探测时间设置，单位秒，默认10秒一次
       successThreshold: 0
       failureThreshold: 0
       securityContext:
         privileged: false
  restartPolicy: [Always | Never | OnFailure]  #Pod的重启策略
  nodeName: <string> #设置NodeName表示将该Pod调度到指定到名称的node节点上
  nodeSelector: obeject #设置NodeSelector表示将该Pod调度到包含这个label的node上
  imagePullSecrets: #Pull镜像时使用的secret名称，以key：secretkey格式指定
  - name: string
  hostNetwork: false   #是否使用主机网络模式，默认为false，如果设置为true，表示使用宿主机网络
  volumes:   #在该pod上定义共享存储卷列表
  - name: string    #共享存储卷名称 （volumes类型有很多种）
    emptyDir: {}       #类型为emtyDir的存储卷，与Pod同生命周期的一个临时目录。为空值
    hostPath: string   #类型为hostPath的存储卷，表示挂载Pod所在宿主机的目录
      path: string      　　        #Pod所在宿主机的目录，将被用于同期中mount的目录
    secret:       　　　#类型为secret的存储卷，挂载集群与定义的secret对象到容器内部
      scretname: string  
      items:     
      - key: string
        path: string
    configMap:         #类型为configMap的存储卷，挂载预定义的configMap对象到容器内部
      name: string
      items:
      - key: string
        path: string
```



- 语法：查看每种资源的可配置项



```shell
# 查看某种资源可以配置的一级配置
kubectl explain 资源类型 
# 查看属性的子属性
kubectl explain 资源类型.属性
```



- 示例：查看资源类型为pod的可配置项



```shell
kubectl explain pod
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399381712-137d7297-91d8-46c6-bbf7-de51f0818b45.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 示例：查看资源类型为Pod的metadata的属性的可配置项



```shell
kubectl explain pod.metadata
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399388405-3a39ded4-b5e2-459f-8aaa-08d31b3ff5c2.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_34%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



在kubernetes中基本所有资源的一级属性都是一样的，主要包含5个部分：

- apiVersion  <string>：版本，有kubernetes内部定义，版本号必须用kubectl api-versions查询。

- kind <string>：类型，有kubernetes内部定义，类型必须用kubectl api-resources查询。

- metadata  <Object>：元数据，主要是资源标识和说明，常用的有name、namespace、labels等。

- spec <Object>：描述，这是配置中最重要的一部分，里面是对各种资源配置的详细描述。

- status  <Object>：状态信息，里面的内容不需要定义，由kubernetes自动生成。

在上面的属性中，spec是接下来研究的重点，继续看下它的常见子属性：

- containers  <[]Object>：容器列表，用于定义容器的详细信息。

- nodeName <String>：根据nodeName的值将Pod调度到指定的Node节点上。

- nodeSelector  <map[]> ：根据NodeSelector中定义的信息选择该Pod调度到包含这些Label的Node上。

- hostNetwork  <boolean>：是否使用主机网络模式，默认为false，如果设置为true，表示使用宿主机网络。

- volumes    <[]Object> ：存储卷，用于定义Pod上面挂载的存储信息。

- restartPolicy	<string>：重启策略，表示Pod在遇到故障的时候的处理策略。



# 2 Pod的配置



## 2.1 概述



- 本小节主要来研究pod.spec.containers属性，这也是Pod配置中最为关键的一项配置。

- 示例：查看pod.spec.containers的可选配置项



```shell
kubectl explain pod.spec.containers
```



```shell
# 返回的重要属性
KIND:     Pod
VERSION:  v1
RESOURCE: containers <[]Object>   # 数组，代表可以有多个容器FIELDS:
  name  <string>     # 容器名称
  image <string>     # 容器需要的镜像地址
  imagePullPolicy  <string> # 镜像拉取策略 
  command  <[]string> # 容器的启动命令列表，如不指定，使用打包时使用的启动命令
  args   <[]string> # 容器的启动命令需要的参数列表 
  env    <[]Object> # 容器环境变量的配置
  ports  <[]Object>  # 容器需要暴露的端口号列表
  resources <Object> # 资源限制和资源请求的设置
```



## 2.2 基本配置



- 创建pod-base.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-base
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
```



- 上面定义了一个比较简单的Pod的配置，里面有两个容器：

- - nginx：用的是1.17.1版本的nginx镜像创建（nginx是一个轻量级的web容器）。

- - busybox：用的是1.30版本的busybox镜像创建（busybox是一个小巧的linux命令集合）。

- 创建Pod：



```shell
kubectl apply -f pod-base.yaml
```



- 查看Pod状况：



```shell
kubectl get pod -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399422492-bbad03e0-8662-40f8-adef-34ce77c06c7b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 通过describe查看内部的详情：



```shell
# 此时已经运行起来了一个基本的Pod，虽然它暂时有问题
kubectl describe pod pod-base -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399436270-3d8fa890-c283-48c7-a5e2-67eb64c8e9e5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 2.3 镜像拉取策略



- 创建pod-imagepullpolicy.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-imagepullpolicy
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: Always # 用于设置镜像的拉取策略
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
```



- imagePullPolicy：用于设置镜像拉取的策略，kubernetes支持配置三种拉取策略：

- - Always：总是从远程仓库拉取镜像（一直远程下载）。

- - IfNotPresent：本地有则使用本地镜像，本地没有则从远程仓库拉取镜像（本地有就用本地，本地没有就使用远程下载）。

- - Never：只使用本地镜像，从不去远程仓库拉取，本地没有就报错（一直使用本地，没有就报错）。



默认值说明：

- 如果镜像tag为具体的版本号，默认策略是IfNotPresent。

- 如果镜像tag为latest（最终版本），默认策略是Always。



- 创建Pod：



```shell
kubectl apply -f pod-imagepullpolicy.yaml
```



- 查看Pod详情：



```shell
kubectl describe pod pod-imagepullpolicy -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399451865-3ff96bce-6b06-4c5d-bae7-b49b1b0890d6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 2.4 启动命令



- 在前面的案例中，一直有一个问题没有解决，就是busybox容器一直没有成功运行，那么到底是什么原因导致这个容器的故障的呢？

- 原来busybox并不是一个程序，而是类似于一个工具类的集合，kubernetes集群启动管理后，它会自动关闭。解决方法就是让其一直在运行，这就用到了command的配置。

- 创建pod-command.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-command
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
      command: ["/bin/sh","-c","touch /tmp/hello.txt;while true;do /bin/echo $(date +%T) >> /tmp/hello.txt;sleep 3;done;"]
```



command：用于在Pod中的容器初始化完毕之后执行一个命令。

这里稍微解释下command中的命令的意思：

- "/bin/sh","-c"：使用sh执行命令。

- touch /tmp/hello.txt：创建一个/tmp/hello.txt的文件。

- while true;do /bin/echo $(date +%T) >> /tmp/hello.txt;sleep 3;done：每隔3秒，向文件写入当前时间



- 创建Pod：



```shell
kubectl apply -f pod-command.yaml
```



- 查看Pod状态：



```shell
kubectl get pod pod-command -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399470089-0a8256c9-13f0-41ff-b5fb-7a67666c87ce.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 进入Pod中的busybox容器，查看文件内容：



```shell
# 在容器中执行命令
# kubectl exec -it pod的名称 -n 命名空间 -c 容器名称 /bin/sh
kubectl exec -it pod-command -n dev -c busybox /bin/sh
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399486025-6bb93ba6-2d95-4b80-9b25-7b08de78f0d3.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



特别说明：通过上面发现command已经可以完成启动命令和传递参数的功能，为什么还要提供一个args选项，用于传递参数？其实和Docker有点关系，kubernetes中的command和args两个参数其实是为了实现覆盖Dockerfile中的ENTRYPOINT的功能：

- 如果command和args均没有写，那么用Dockerfile的配置。

- 如果command写了，但是args没有写，那么Dockerfile默认的配置会被忽略，执行注入的command。

- 如果command没有写，但是args写了，那么Dockerfile中配置的ENTRYPOINT命令会被执行，使用当前args的参数。

- 如果command和args都写了，那么Dockerfile中的配置会被忽略，执行command并追加上args参数。



## 2.5 环境变量（不推荐）



- 创建pod-evn.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-env
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
    - name: busybox # 容器名称
      image: busybox:1.30 # 容器需要的镜像地址
      command: ["/bin/sh","-c","touch /tmp/hello.txt;while true;do /bin/echo $(date +%T) >> /tmp/hello.txt;sleep 3;done;"]
      env:
        - name: "username"
          value: "admin"
        - name: "password"
          value: "123456"
```



env：环境变量，用于在Pod中的容器设置环境变量。



- 创建Pod：



```shell
kubectl create -f pod-env.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399505900-9872420c-45b3-4503-a368-98073bc9bed1.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 进入容器，输出环境变量：



```shell
kubectl exec -it pod-env -n dev -c busybox -it /bin/sh
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399523140-75d62c44-c712-4700-8f10-256dc117ced6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



此种方式不推荐，推荐将这些配置单独存储在配置文件中，后面介绍。



## 2.6 端口设置



- 查看ports支持的子选项：



```shell
kubectl explain pod.spec.containers.ports
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399537174-fde54a9d-bc96-4644-9dee-e473772c0109.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



```yaml
KIND:     Pod
VERSION:  v1
RESOURCE: ports <[]Object>
FIELDS:
  name <string> # 端口名称，如果指定，必须保证name在pod中是唯一的
  containerPort <integer> # 容器要监听的端口(0<x<65536)
  hostPort <integer> # 容器要在主机上公开的端口，如果设置，主机上只能运行容器的一个副本(一般省略）
  hostIP <string>  # 要将外部端口绑定到的主机IP(一般省略)
  protocol <string>  # 端口协议。必须是UDP、TCP或SCTP。默认为“TCP”
```



- 创建pod-ports.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-ports
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
      ports:
        - name: nginx-port # 端口名称，如果执行，必须保证name在Pod中是唯一的
          containerPort: 80 # 容器要监听的端口 （0~65536）
          protocol: TCP # 端口协议
```



- 创建Pod：



```shell
kubectl create -f pod-ports.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399556059-3d04651f-3e7b-40c6-9e3d-8779d02c1116.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



访问Pod中的容器中的程序使用的是PodIp:containerPort。



## 2.7 资源配额



- 容器中的程序要运行，肯定会占用一定的资源，比如CPU和内存等，如果不对某个容器的资源做限制，那么它就可能吃掉大量的资源，导致其他的容器无法运行。针对这种情况，kubernetes提供了对内存和CPU的资源进行配额的机制，这种机制主要通过resources选项实现，它有两个子选项：

- - limits：用于限制运行的容器的最大占用资源，当容器占用资源超过limits时会被终止，并进行重启。

- - requests：用于设置容器需要的最小资源，如果环境资源不够，容器将无法启动。

- 可以通过上面的两个选项设置资源的上下限。

- 创建pod-resoures.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-resoures
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
      ports: # 端口设置
        - name: nginx-port # 端口名称，如果执行，必须保证name在Pod中是唯一的
          containerPort: 80 # 容器要监听的端口 （0~65536）
          protocol: TCP # 端口协议
      resources: # 资源配额
        limits: # 限制资源的上限
          cpu: "2" # CPU限制，单位是core数
          memory: "10Gi" # 内存限制
        requests: # 限制资源的下限
          cpu: "1" # CPU限制，单位是core数 
          memory: "10Mi" # 内存限制
```



cpu：core数，可以为整数或小数。

memory：内存大小，可以使用Gi、Mi、G、M等形式。



- 创建Pod：



```shell
kubectl create -f pod-resource.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399573785-e4cbf8e3-3c30-4469-be50-dc7264310331.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看发现Pod运行正常：



```shell
kubectl get pod pod-resoures -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399597891-16a87162-b68e-461b-9b58-132bb2cd500f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 接下来，停止Pod：



```shell
kubectl delete -f pod-resource.yaml
```



- 编辑Pod，修改resources.requests.memory的值为10Gi：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-resoures
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers:
    - name: nginx # 容器名称
      image: nginx:1.17.1 # 容器需要的镜像地址
      imagePullPolicy: IfNotPresent # 设置镜像拉取策略
      ports: # 端口设置
        - name: nginx-port # 端口名称，如果执行，必须保证name在Pod中是唯一的
          containerPort: 80 # 容器要监听的端口 （0~65536）
          protocol: TCP # 端口协议
      resources: # 资源配额
        limits: # 限制资源的上限
          cpu: "2" # CPU限制，单位是core数
          memory: "10Gi" # 内存限制
        requests: # 限制资源的下限
          cpu: "1" # CPU限制，单位是core数 
          memory: "10Gi" # 内存限制
```



- 再次启动Pod：



```shell
kubectl create -f pod-resource.yaml
```



- 查看Pod状态，发现Pod启动失败：



```shell
kubectl get pod pod-resoures -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399615549-6f1a7e73-ddb7-4b6b-9f9b-a69c75abc062.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情会发现，如下提示：



```shell
kubectl describe pod pod-resoures -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399635888-cd5ea7aa-c847-4e29-8955-3ab725e648d0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 3 Pod的生命周期



## 3.1 概述



- 我们一般将Pod对象从创建到终止的这段时间范围称为Pod的生命周期，它主要包含下面的过程：

- - Pod创建过程。

- - 运行初始化容器（init container）过程。

- - 运行主容器（main container）：

- - - 容器启动后钩子（post start）、容器终止前钩子（pre stop）。

- - - 容器的存活性探测（liveness probe）、就绪性探测（readiness probe）。

- - Pod终止过程。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399647590-472c8628-8b69-42ab-8a50-929c27737926.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_33%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 在整个生命周期中，Pod会出现5种状态（相位），分别如下：

- - 挂起（Pending）：API Server已经创建了Pod资源对象，但它尚未被调度完成或者仍处于下载镜像的过程中。

- - 运行中（Running）：Pod已经被调度到某节点，并且所有容器都已经被kubelet创建完成。

- - 成功（Succeeded）：Pod中的所有容器都已经成功终止并且不会被重启。

- - 失败（Failed）：所有容器都已经终止，但至少有一个容器终止失败，即容器返回了非0值的退出状态。

- - 未知（Unknown）：API Server无法正常获取到Pod对象的状态信息，通常由于网络通信失败所导致。



## 3.2 创建和终止



### 3.2.1 Pod的创建过程



![img](https://cdn.nlark.com/yuque/0/2020/jpeg/513185/1609399660203-ab0d9834-3b35-4119-b304-4394b00f0b9d.jpeg?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_35%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- ① 用户通过kubectl或其他的api客户端提交需要创建的Pod信息给API Server。

- ② API Server开始生成Pod对象的信息，并将信息存入etcd，然后返回确认信息至客户端。

- ③ API Server开始反映etcd中的Pod对象的变化，其它组件使用watch机制来跟踪检查API Server上的变动。

- ④ Scheduler发现有新的Pod对象要创建，开始为Pod分配主机并将结果信息更新至API Server。

- ⑤ Node节点上的kubelet发现有Pod调度过来，尝试调度Docker启动容器，并将结果回送至API Server。

- ⑥ API Server将接收到的Pod状态信息存入到etcd中。



### 3.2.2 Pod的终止过程



- ① 用户向API Server发送删除Pod对象的命令。

- ② API Server中的Pod对象信息会随着时间的推移而更新，在宽限期内（默认30s），Pod被视为dead。

- ③ 将Pod标记为terminating状态。

- ④ kubelete在监控到Pod对象转为terminating状态的同时启动Pod关闭过程。

- ⑤ 端点控制器监控到Pod对象的关闭行为时将其从所有匹配到此端点的service资源的端点列表中移除。

- ⑥ 如果当前Pod对象定义了preStop钩子处理器，则在其标记为terminating后会以同步的方式启动执行。

- ⑦ Pod对象中的容器进程收到停止信号。

- ⑧ 宽限期结束后，如果Pod中还存在运行的进程，那么Pod对象会收到立即终止的信号。

- ⑨ kubectl请求API Server将此Pod资源的宽限期设置为0从而完成删除操作，此时Pod对于用户已经不可用了。



## 3.3 初始化容器



- 初始化容器是在Pod的主容器启动之前要运行的容器，主要是做一些主容器的前置工作，它具有两大特征：

- - ① 初始化容器必须运行完成直至结束，如果某个初始化容器运行失败，那么kubernetes需要重启它直至成功完成。

- - ② 初始化容器必须按照定义的顺序执行，当且仅当前一个成功之后，后面的一个才能运行。

- 初始化容器有很多的应用场景，下面列出的是最常见的几个：

- - 提供主容器镜像中不具备的工具程序或自定义代码。

- - 初始化容器要先于应用容器串行启动并运行完成，因此可用于延后应用容器的启动直至其依赖的条件得到满足。

- 接下来做一个案例，模拟下面这个需求：

- - 假设要以主容器来运行Nginx，但是要求在运行Nginx之前要能够连接上MySQL和Redis所在的服务器。

- - 为了简化测试，事先规定好MySQL和Redis所在的IP地址分别为192.168.18.103和192.168.18.104（注意，这两个IP都不能ping通，因为环境中没有这两个IP）。

- 创建pod-initcontainer.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-initcontainer
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      resources:
        limits:
          cpu: "2"
          memory: "10Gi"
        requests:
          cpu: "1"
          memory: "10Mi"
  initContainers: # 初始化容器配置
    - name: test-mysql
      image: busybox:1.30
      command: ["sh","-c","until ping 192.168.18.103 -c 1;do echo waiting for mysql ...;sleep 2;done;"]
      securityContext:
        privileged: true # 使用特权模式运行容器
    - name: test-redis
      image: busybox:1.30
      command: ["sh","-c","until ping 192.168.18.104 -c 1;do echo waiting for redis ...;sleep 2;done;"]
```



- 创建Pod：



```shell
kubectl create -f pod-initcontainer.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399678517-69e66241-fc21-4fbd-8909-021dd0797323.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod状态：



```shell
kubectl describe pod pod-initcontainer -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399693516-6c5930af-fb72-48b4-8b27-6d3e817a811e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 动态查看Pod：



```shell
kubectl get pod pod-initcontainer -n dev -w
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399711402-b33fee9f-1eed-4db5-879b-94bf4ed33a0c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 接下来，新开一个shell，为当前服务器（192.168.18.100）新增两个IP，观察Pod的变化：



```shell
ifconfig ens33:1 192.168.18.103 netmask 255.255.255.0 up
```



```shell
ifconfig ens33:2 192.168.18.104 netmask 255.255.255.0 up
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399728530-c2d092d2-918a-437e-9a39-9fefc12d7ee6.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.4 钩子函数



- 钩子函数能够感知自身生命周期中的事件，并在相应的时刻到来时运行用户指定的程序代码。

- kubernetes在主容器启动之后和停止之前提供了两个钩子函数：

- - post start：容器创建之后执行，如果失败会重启容器。

- - pre stop：容器终止之前执行，执行完成之后容器将成功终止，在其完成之前会阻塞删除容器的操作。

- 钩子处理器支持使用下面的三种方式定义动作：

- - ① exec命令：在容器内执行一次命令。

```yaml
……
  lifecycle:
     postStart: 
        exec:
           command:
             - cat
             - /tmp/healthy
……
```

- - ② tcpSocket：在当前容器尝试访问指定的socket。

```yaml
…… 
   lifecycle:
      postStart:
         tcpSocket:
            port: 8080
……
```

- - ③ httpGet：在当前容器中向某url发起HTTP请求。

```yaml
…… 
   lifecycle:
      postStart:
         httpGet:
            path: / #URI地址
            port: 80 #端口号
            host: 192.168.109.100 #主机地址  
            scheme: HTTP #支持的协议，http或者https
……
```

- 接下来，以exec方式为例，演示下钩子函数的使用，创建pod-hook-exec.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-hook-exec
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      resources:
        limits:
          cpu: "2"
          memory: "10Gi"
        requests:
          cpu: "1"
          memory: "10Mi"
      lifecycle: # 生命周期配置
        postStart: # 容器创建之后执行，如果失败会重启容器
          exec: # 在容器启动的时候，执行一条命令，修改掉Nginx的首页内容
            command: ["/bin/sh","-c","echo postStart ... > /usr/share/nginx/html/index.html"]
        preStop: # 容器终止之前执行，执行完成之后容器将成功终止，在其完成之前会阻塞删除容器的操作
          exec: # 在容器停止之前停止Nginx的服务
            command: ["/usr/sbin/nginx","-s","quit"]
```



- 创建Pod：



```shell
kubectl create -f pod-hook-exec.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399746272-45a4e590-4858-45b6-a4b3-2f5e2ecd4023.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod-hook-exec -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399757639-d53d168e-de51-4875-8998-83f6b984cb45.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 访问Pod：



```shell
curl 10.244.1.11
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399769407-980c3b89-77ac-46b6-afe9-b369af5f1cff.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.5 容器探测



### 3.5.1 概述



- 容器探测用于检测容器中的应用实例是否正常工作，是保障业务可用性的一种传统机制。如果经过探测，实例的状态不符合预期，那么kubernetes就会把该问题实例“摘除”，不承担业务流量。kubernetes提供了两种探针来实现容器探测，分别是：

- - liveness probes：存活性探测，用于检测应用实例当前是否处于正常运行状态，如果不是，k8s会重启容器。

- - readiness probes：就绪性探测，用于检测应用实例是否可以接受请求，如果不能，k8s不会转发流量。



livenessProbe：存活性探测，决定是否重启容器。

readinessProbe：就绪性探测，决定是否将请求转发给容器。



k8s在1.16版本之后新增了startupProbe探针，用于判断容器内应用程序是否已经启动。如果配置了startupProbe探针，就会先禁止其他的探针，直到startupProbe探针成功为止，一旦成功将不再进行探测。





- 上面两种探针目前均支持三种探测方式：

- - ① exec命令：在容器内执行一次命令，如果命令执行的退出码为0，则认为程序正常，否则不正常。

```yaml
……
  livenessProbe:
     exec:
        command:
          -	cat
          -	/tmp/healthy
……
```

- - ② tcpSocket：将会尝试访问一个用户容器的端口，如果能够建立这条连接，则认为程序正常，否则不正常。

```yaml
……
   livenessProbe:
      tcpSocket:
         port: 8080
……
```

- - ③ httpGet：调用容器内web应用的URL，如果返回的状态码在200和399之前，则认为程序正常，否则不正常。

```yaml
……
   livenessProbe:
      httpGet:
         path: / #URI地址
         port: 80 #端口号
         host: 127.0.0.1 #主机地址
         scheme: HTTP #支持的协议，http或者https
……
```



### 3.5.2 exec方式



- 创建pod-liveness-exec.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-liveness-exec
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探针
        exec:
          command: ["/bin/cat","/tmp/hello.txt"] # 执行一个查看文件的命令，必须失败，因为根本没有这个文件
```



- 创建Pod：



```shell
kubectl create -f pod-liveness-exec.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399786116-bc89d268-fad0-4212-95ef-7f6ac6942040.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情：



```shell
kubectl describe pod pod-liveness-exec -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399798766-24a3c62d-e04a-4d66-b655-d88c991c17a8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 观察上面的信息就会发现nginx容器启动之后就进行了健康检查。

- 检查失败之后，容器被kill掉，然后尝试进行重启，这是重启策略的作用。

- 稍等一会之后，再观察Pod的信息，就会看到RESTARTS不再是0，而是一直增长。



- 查看Pod信息：



```shell
kubectl get pod pod-liveness-exec -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399815428-5e5a5e0b-74f5-490f-a222-7b1e03ce313c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_23%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.5.3 tcpSocket方式



- 创建pod-liveness-tcpsocket.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-liveness-tcpsocket
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探针
        tcpSocket:
          port: 8080 # 尝试访问8080端口，必须失败，因为Pod内部只有一个Nginx容器，而且只是监听了80端口
```



- 创建Pod：



```shell
kubectl create -f pod-liveness-tcpsocket.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399830265-617437cb-7031-403c-8b4f-3ca7999348ab.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_23%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情：



```shell
kubectl describe pod pod-liveness-tcpsocket -n  dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399842301-04489bee-94c7-4af2-ac28-5b0204cf3e36.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



观察上面的信息，发现尝试访问8080端口，但是失败了

稍等一会之后，再观察Pod的信息，就会看到RESTARTS不再是0，而是一直增长。



- 查看Pod信息：



```shell
kubectl get pod pod-liveness-tcpsocket -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399859446-a57a79e0-abfd-4c02-909c-68eebc6996de.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.5.4 httpGet方式



- 创建pod-liveness-httpget.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-liveness-httpget
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探针
        httpGet: # 其实就是访问http://127.0.0.1:80/hello
          port: 80 # 端口号
          scheme: HTTP # 支持的协议，HTTP或HTTPS
          path: /hello # URI地址
          host: 127.0.0.1 # 主机地址
```



- 创建Pod：



```shell
kubectl create -f pod-liveness-httpget.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399928718-0bee02da-3f24-4beb-9f2b-0be3fe6b6935.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情：



```shell
kubectl describe pod pod-liveness-httpget -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399942114-b133dab6-281c-4b0c-9036-e3e6f5aabd2f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod信息：



```shell
kubectl get pod pod-liveness-httpget -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399961035-c8301a2d-cbed-42ad-94c4-78209ffe40db.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.5.5 容器探测的补充



- 上面已经使用了livenessProbe演示了三种探测方式，但是查看livenessProbe的子属性，会发现除了这三种方式，还有一些其他的配置。



```shell
kubectl explain pod.spec.containers.livenessProbe
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399974021-e4266d75-18b2-4622-9761-926b0a7368e3.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



FIELDS:

exec 

tcpSocket  

httpGet   

initialDelaySeconds   # 容器启动后等待多少秒执行第一次探测

timeoutSeconds    # 探测超时时间。默认1秒，最小1秒

periodSeconds    # 执行探测的频率。默认是10秒，最小1秒

failureThreshold   # 连续探测失败多少次才被认定为失败。默认是3。最小值是1

successThreshold   # 连续探测成功多少次才被认定为成功。默认是1



## 3.6 重启策略



- 在容器探测中，一旦容器探测出现了问题，kubernetes就会对容器所在的Pod进行重启，其实这是由Pod的重启策略决定的，Pod的重启策略有3种，分别如下：

- - Always：容器失效时，自动重启该容器，默认值。

- - OnFailure：容器终止运行且退出码不为0时重启。

- - Never：不论状态如何，都不重启该容器。

- 重启策略适用于Pod对象中的所有容器，首次需要重启的容器，将在其需要的时候立即进行重启，随后再次重启的操作将由kubelet延迟一段时间后进行，且反复的重启操作的延迟时长以此为10s、20s、40s、80s、160s和300s，300s是最大的延迟时长。

- 创建pod-restart-policy.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-restart-policy
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
      livenessProbe: # 存活性探测
        httpGet:
          port: 80
          path: /hello
          host: 127.0.0.1
          scheme: HTTP
  restartPolicy: Never # 重启策略
```



- 创建Pod：



```shell
kubectl create -f pod-restart-policy.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609399990072-df160cc5-bdf3-4944-8714-1b73cf32e608.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod详情，发现nginx容器启动失败：



```shell
kubectl describe pod pod-restart-policy -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400016288-813442b2-f172-4b88-81de-993bf205d741.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_34%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



多等一会，观察Pod的重试次数，发现一直是0，并未重启。



- 查看Pod：



```shell
kubectl get pod pod-restart-policy -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400033371-b66de2ca-11f3-4ae6-9f46-a56426f310f7.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 4 Pod的调度



## 4.1 概述



- 在默认情况下，一个Pod在哪个Node节点上运行，是由Scheduler组件采用相应的算法计算出来的，这个过程是不受人工控制的。但是在实际使用中，这并不满足需求，因为很多情况下，我们想控制某些Pod到达某些节点上，那么应该怎么做？这就要求了解kubernetes对Pod的调度规则，kubernetes提供了四大类调度方式。

- - 自动调度：运行在哪个Node节点上完全由Scheduler经过一系列的算法计算得出。

- - 定向调度：NodeName、NodeSelector。

- - 亲和性调度：NodeAffinity、PodAffinity、PodAntiAffinity。

- - 污点（容忍）调度：Taints、Toleration。



## 4.2 定向调度



### 4.2.1 概述



- 定向调度，指的是利用在Pod上声明的nodeName或nodeSelector，以此将Pod调度到期望的Node节点上。注意，这里的调度是强制的，这就意味着即使要调度的目标Node不存在，也会向上面进行调度，只不过Pod运行失败而已。



### 4.2.2 nodeName



- nodeName用于强制约束将Pod调度到指定的name的Node节点上。这种方式，其实是直接跳过Scheduler的调度逻辑，直接将Pod调度到指定名称的节点。

- 创建一个pod-nodename.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodename
  namespace: dev
  labels:
    user: xudaxian
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  nodeName: k8s-node1 # 指定调度到k8s-node1节点上
```



- 创建Pod：



```shell
kubectl create -f pod-nodename.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400050057-6391b94b-d5c7-4659-a71c-3e16c116ee2f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod-nodename -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400063217-171a4311-e104-48e8-9390-8a65b0e814ba.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)





### 4.2.3 nodeSelector



- nodeSelector用于将Pod调度到添加了指定标签的Node节点上，它是通过kubernetes的label-selector机制实现的，换言之，在Pod创建之前，会由Scheduler使用MatchNodeSelector调度策略进行label匹配，找出目标node，然后将Pod调度到目标节点，该匹配规则是强制约束。

- 首先给node节点添加标签：



```shell
kubectl label node k8s-node1 nodeevn=pro
```



```shell
kubectl label node k8s-node2 nodeenv=test
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400079974-170efa34-4bfb-400b-ba27-1254b45be7d2.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建pod-nodeselector.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeselector
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  nodeSelector:
    nodeenv: pro # 指定调度到具有nodeenv=pro的Node节点上
```



- 创建Pod：



```shell
kubectl create -f pod-nodeselector.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400096271-1c2af967-f8a4-4b01-b742-8befa4b0e714.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod-nodeselector -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400107729-b4464f9a-064a-4884-a998-349a28596654.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 4.3 亲和性调度



### 4.3.1 概述



- 虽然定向调度的两种方式，使用起来非常方便，但是也有一定的问题，那就是如果没有满足条件的Node，那么Pod将不会被运行，即使在集群中还有可用的Node列表也不行，这就限制了它的使用场景。

- 基于上面的问题，kubernetes还提供了一种亲和性调度（Affinity）。它在nodeSelector的基础之上进行了扩展，可以通过配置的形式，实现优先选择满足条件的Node进行调度，如果没有，也可以调度到不满足条件的节点上，使得调度更加灵活。

- Affinity主要分为三类：

- - nodeAffinity（node亲和性）：以Node为目标，解决Pod可以调度到那些Node的问题。

- - podAffinity（pod亲和性）：以Pod为目标，解决Pod可以和那些已存在的Pod部署在同一个拓扑域中的问题。

- - podAntiAffinity（pod反亲和性）：以Pod为目标，解决Pod不能和那些已经存在的Pod部署在同一拓扑域中的问题。



关于亲和性和反亲和性的使用场景的说明：

- 亲和性：如果两个应用频繁交互，那么就有必要利用亲和性让两个应用尽可能的靠近，这样可以较少因网络通信而带来的性能损耗。

- 反亲和性：当应用采用多副本部署的时候，那么就有必要利用反亲和性让各个应用实例打散分布在各个Node上，这样可以提高服务的高可用性。



### 4.3.2 nodeAffinity



- 查看nodeAffinity的可选配置项：



```yaml
pod.spec.affinity.nodeAffinity
  requiredDuringSchedulingIgnoredDuringExecution  Node节点必须满足指定的所有规则才可以，相当于硬限制
    nodeSelectorTerms  节点选择列表
      matchFields   按节点字段列出的节点选择器要求列表  
      matchExpressions   按节点标签列出的节点选择器要求列表(推荐)
        key    键
        values 值
        operator 关系符 支持Exists, DoesNotExist, In, NotIn, Gt, Lt
  preferredDuringSchedulingIgnoredDuringExecution 优先调度到满足指定的规则的Node，相当于软限制 (倾向)     
    preference   一个节点选择器项，与相应的权重相关联
      matchFields 按节点字段列出的节点选择器要求列表
      matchExpressions   按节点标签列出的节点选择器要求列表(推荐)
        key 键
        values 值
        operator 关系符 支持In, NotIn, Exists, DoesNotExist, Gt, Lt  
    weight 倾向权重，在范围1-100。
```



关系符的使用说明:

```yaml
- matchExpressions:
	- key: nodeenv # 匹配存在标签的key为nodeenv的节点
	  operator: Exists   
	- key: nodeenv # 匹配标签的key为nodeenv,且value是"xxx"或"yyy"的节点
	  operator: In    
      values: ["xxx","yyy"]
    - key: nodeenv # 匹配标签的key为nodeenv,且value大于"xxx"的节点
      operator: Gt   
      values: "xxx"
```



- 下面演示requiredDuringSchedulingIgnoredDuringExecution：

- - 创建pod-nodeaffinity-required.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeaffinity-required
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    nodeAffinity: # node亲和性配置
      requiredDuringSchedulingIgnoredDuringExecution: # Node节点必须满足指定的所有规则才可以，相当于硬规则，类似于定向调度
        nodeSelectorTerms: # 节点选择列表
          - matchExpressions:
              - key: nodeenv # 匹配存在标签的key为nodeenv的节点，并且value是"xxx"或"yyy"的节点
                operator: In
                values:
                  - "xxx"
                  - "yyy"
```

- - 创建Pod：

```shell
kubectl create -f pod-nodeaffinity-required.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400130529-8dc1ccdc-573a-4763-840f-be5ed8afd66c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod状态（运行失败）：

```shell
kubectl get pod pod-nodeaffinity-required -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400146920-f4a1d88c-9b12-4aef-9957-b92be4d36d0e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod详情（发现调度失败，提示node选择失败）：

```shell
kubectl describe pod pod-nodeaffinity-required -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400161978-fd7e982f-1eea-449e-a108-71a4e0921c91.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 删除Pod：

```shell
kubectl delete -f pod-nodeaffinity-required.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400177211-3b5b496d-e184-4266-9164-2cf73d00c95e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 修改pod-nodeaffinity-required.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeaffinity-required
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    nodeAffinity: # node亲和性配置
      requiredDuringSchedulingIgnoredDuringExecution: # Node节点必须满足指定的所有规则才可以，相当于硬规则，类似于定向调度
        nodeSelectorTerms: # 节点选择列表
          - matchExpressions:
              - key: nodeenv # 匹配存在标签的key为nodeenv的节点，并且value是"xxx"或"yyy"的节点
                operator: In
                values:
                  - "pro"
                  - "yyy"
```

- - 再次创建Pod：

```shell
kubectl create -f pod-nodeaffinity-required.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400194591-d80acca9-4570-4b1b-a822-0332db005570.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 再次查看Pod：

```shell
kubectl get pod pod-nodeaffinity-required -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400216267-7a398c8e-7e19-402b-9075-39c822a19932.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 下面演示preferredDuringSchedulingIgnoredDuringExecution：

- - 创建pod-nodeaffinity-preferred.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-nodeaffinity-preferred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    nodeAffinity: # node亲和性配置
      preferredDuringSchedulingIgnoredDuringExecution: # 优先调度到满足指定的规则的Node，相当于软限制 (倾向)
        - preference: # 一个节点选择器项，与相应的权重相关联
            matchExpressions:
              - key: nodeenv
                operator: In
                values:
                  - "xxx"
                  - "yyy"
          weight: 1
```

- - 创建Pod：

```shell
kubectl create -f pod-nodeaffinity-preferred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400233720-5fd3aec2-ed09-4c07-b72e-92532acaa0db.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod：

```shell
kubectl get pod pod-nodeaffinity-preferred -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400249643-b64cb7e3-9d90-47cc-8679-2c4f327510ea.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



nodeAffinity的注意事项：

- 如果同时定义了nodeSelector和nodeAffinity，那么必须两个条件都满足，Pod才能运行在指定的Node上。

- 如果nodeAffinity指定了多个nodeSelectorTerms，那么只需要其中一个能够匹配成功即可。

- 如果一个nodeSelectorTerms中有多个matchExpressions，则一个节点必须满足所有的才能匹配成功。

- 如果一个Pod所在的Node在Pod运行期间其标签发生了改变，不再符合该Pod的nodeAffinity的要求，则系统将忽略此变化。



### 4.3.3 podAffinity



- podAffinity主要实现以运行的Pod为参照，实现让新创建的Pod和参照的Pod在一个区域的功能。

- PodAffinity的可选配置项：



```yaml
pod.spec.affinity.podAffinity
  requiredDuringSchedulingIgnoredDuringExecution  硬限制
    namespaces 指定参照pod的namespace
    topologyKey 指定调度作用域
    labelSelector 标签选择器
      matchExpressions  按节点标签列出的节点选择器要求列表(推荐)
        key    键
        values 值
        operator 关系符 支持In, NotIn, Exists, DoesNotExist.
      matchLabels    指多个matchExpressions映射的内容  
  preferredDuringSchedulingIgnoredDuringExecution 软限制    
    podAffinityTerm  选项
      namespaces
      topologyKey
      labelSelector
         matchExpressions 
            key    键  
            values 值  
            operator
         matchLabels 
    weight 倾向权重，在范围1-1
```



topologyKey用于指定调度的作用域，例如:

- 如果指定为kubernetes.io/hostname，那就是以Node节点为区分范围。

- 如果指定为beta.kubernetes.io/os，则以Node节点的操作系统类型来区分。



- 演示requiredDuringSchedulingIgnoredDuringExecution。

- 创建参照Pod过程：

- - 创建pod-podaffinity-target.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podaffinity-target
  namespace: dev
  labels:
    podenv: pro # 设置标签
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  nodeName: k8s-node1 # 将目标pod定向调度到k8s-node1
```

- - 创建参照Pod：

```shell
kubectl create -f pod-podaffinity-target.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400266660-28c7f4fa-0339-407c-80ee-abcb28db67ef.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看参照Pod：

```shell
kubectl get pod pod-podaffinity-target -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400280597-374da063-76d8-4e5a-bdd9-b6a6d96f98db.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 创建Pod过程：

- - 创建pod-podaffinity-requred.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podaffinity-requred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    podAffinity: # Pod亲和性
      requiredDuringSchedulingIgnoredDuringExecution: # 硬限制
        - labelSelector:
            matchExpressions: # 该Pod必须和拥有标签podenv=xxx或者podenv=yyy的Pod在同一个Node上，显然没有这样的Pod
              - key: podenv
                operator: In
                values:
                  - "xxx"
                  - "yyy"
          topologyKey: kubernetes.io/hostname
```

- - 创建Pod：

```shell
kubectl create -f pod-podaffinity-requred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400308332-4ca652bb-c472-4c8a-a9df-8ddd42d99bc2.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod状态，发现没有运行：

```shell
kubectl get pod pod-podaffinity-requred -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400325975-4a374839-d904-4b62-98b3-5f06fd066844.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 查看Pod详情：

```shell
kubectl get pod pod-podaffinity-requred -n dev
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400339276-a7f00c7c-9112-49a6-b7a5-92f354e72adc.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 删除Pod：

```shell
kubectl delete -f pod-podaffinity-requred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400353599-05b0c603-8dd6-474c-a32a-8d1b2b379f4a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 修改pod-podaffinity-requred.yaml文件，内容如下：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podaffinity-requred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    podAffinity: # Pod亲和性
      requiredDuringSchedulingIgnoredDuringExecution: # 硬限制
        - labelSelector:
            matchExpressions: # 该Pod必须和拥有标签podenv=xxx或者podenv=yyy的Pod在同一个Node上，显然没有这样的Pod
              - key: podenv
                operator: In
                values:
                  - "pro"
                  - "yyy"
          topologyKey: kubernetes.io/hostname
```

- - 再次创建Pod：

```shell
kubectl create -f pod-podaffinity-requred.yaml
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400375097-b2d478c0-7da8-4a69-8890-9eb398ec0943.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - 再次查看Pod：

```shell
kubectl get pod pod-podaffinity-requred -n dev -o wide
```

![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400388672-72386db7-2b6b-432c-b3d3-803461ef0f89.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 4.3.4 podAntiAffinity



- podAntiAffinity主要实现以运行的Pod为参照，让新创建的Pod和参照的Pod不在一个区域的功能。

- 其配置方式和podAffinity一样，此处不做详细解释。

- 使用上个案例中的目标Pod：



```shell
kubectl get pod -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400403258-3847628f-b239-4efc-a6a4-c767c540d5f8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建pod-podantiaffinity-requred.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-podantiaffinity-requred
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  affinity: # 亲和性配置
    podAntiAffinity: # Pod反亲和性
      requiredDuringSchedulingIgnoredDuringExecution: # 硬限制
        - labelSelector:
            matchExpressions:
              - key: podenv
                operator: In
                values:
                  - "pro"
          topologyKey: kubernetes.io/hostname
```



- 创建Pod：



```shell
kubectl create -f pod-podantiaffinity-requred.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400418144-1a0771ef-e325-43d1-8a61-11d0ed4e5679.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400431349-7426874d-9633-4c75-a115-28defe01fb4f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 4.4 污点和容忍



### 4.4.1 污点（Taints）



- 前面的调度方式都是站在Pod的角度上，通过在Pod上添加属性，来确定Pod是否要调度到指定的Node上，其实我们也可以站在Node的角度上，通过在Node上添加`污点属性`，来决定是否运行Pod调度过来。

- Node被设置了污点之后就和Pod之间存在了一种相斥的关系，进而拒绝Pod调度进来，甚至可以将已经存在的Pod驱逐出去。

- 污点的格式为：`key=value:effect`，key和value是污点的标签，effect描述污点的作用，支持如下三个选项：

- - PreferNoSchedule：kubernetes将尽量避免把Pod调度到具有该污点的Node上，除非没有其他节点可以调度。

- - NoSchedule：kubernetes将不会把Pod调度到具有该污点的Node上，但是不会影响当前Node上已经存在的Pod。

- - NoExecute：kubernetes将不会把Pod调度到具有该污点的Node上，同时也会将Node上已经存在的Pod驱逐。



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400444401-f4579175-f530-45e3-a219-19d31b1cf4a5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 语法：

- - 设置污点：

```shell
kubectl taint node xxx key=value:effect
```

- - 去除污点：

```shell
kubectl taint node xxx key:effect-
```

- - 去除所有污点：

```shell
kubectl taint node xxx key-
```

- - 查询所有节点的污点：

```shell
wget -O jq https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64
chmod +x ./jq
cp jq /usr/bin
```

- - 列出所有节点的污点方式一：

```shell
kubectl get nodes -o json | jq '.items[].spec'
```

- - 列出所有节点的污点方式二：

```shell
kubectl get nodes -o json | jq '.items[].spec.taints'
```

- - 查看指定节点上的污点：

```shell
kubectl describe node 节点名称
```

- 接下来，演示污点效果：

- - ① 准备节点k8s-node1（为了演示效果更加明显，暂时停止k8s-node2节点）。

- - ② 为k8s-node1节点设置一个污点：`tag=xudaxian:PreferNoSchedule`，然后创建Pod1（Pod1可以）。

- - ③ 修改k8s-node1节点的污点为：`tag=xudaxian:NoSchedule`，然后创建Pod2（Pod1可以正常运行，Pod2失败）。

- - ④ 修改k8s-node1节点的污点为：`tag=xudaxian:NoExecute`，然后创建Pod3（Pod1、Pod2、Pod3失败）。

- 为k8s-node1设置污点（PreferNoSchedule）：



```shell
kubectl taint node k8s-node1 tag=xudaxian:PreferNoSchedule
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400474950-8d42f6b6-2a76-4641-9ce2-429286ed3a68.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建Pod1：



```shell
kubectl run pod1 --image=nginx:1.17.1 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400488318-5871a81b-b88f-4ddf-bd2b-4523f66ee587.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod1 -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400501427-73a72847-217c-4057-898f-2e7ab305bef8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 为k8s-node1取消污点（PreferNoSchedule），并设置污点（NoSchedule）：



```shell
kubectl taint node k8s-node1 tag:PreferNoSchedule-
```



```shell
kubectl taint node k8s-node1 tag=xudaxian:NoSchedule
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400518141-15814962-cdc3-4e31-b2d9-ae021310eb0c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建Pod2：



```shell
kubectl run pod2 --image=nginx:1.17.1 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400532133-f4d9cf31-55a5-405c-8291-0bdc9186b65b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod1 -n dev -o wide
```



```shell
kubectl get pod pod2 -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400545682-4bd82344-2850-4cf6-a529-63d507f25aa5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 为k8s-node1取消污点（NoSchedule），并设置污点（NoExecute）：



```shell
kubectl taint node k8s-node1 tag:NoSchedule-
```



```shell
kubectl taint node k8s-node1 tag=xudaxian:NoExecute
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400560213-01e44395-fb0c-41a5-927f-e40d4f5f061a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建Pod3：



```shell
kubectl run pod3 --image=nginx:1.17.1 -n dev
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400573500-10d4ebdf-4707-45ca-95c8-a68c18b96c8b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod1 -n dev -o wide
```



```shell
kubectl get pod pod2 -n dev -o wide
```



```shell
kubectl get pod pod3 -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400588008-d29be45c-8efd-47d2-a000-65a67ae6f70f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



使用kubeadm搭建的集群，默认就会给Master节点添加一个污点标记，所以Pod就不会调度到Master节点上。



### 4.4.2 容忍（Toleration）



- 上面介绍了污点的作用，我们可以在Node上添加污点用来拒绝Pod调度上来，但是如果就是想让一个Pod调度到一个有污点的Node上去，这时候应该怎么做？这就需要使用到容忍。



污点就是拒绝，容忍就是忽略，Node通过污点拒绝Pod调度上去，Pod通过容忍忽略拒绝。



- 容忍的详细配置：



```yaml
kubectl explain pod.spec.tolerations
......
FIELDS:
  key       # 对应着要容忍的污点的键，空意味着匹配所有的键
  value     # 对应着要容忍的污点的值
  operator  # key-value的运算符，支持Equal和Exists（默认）
  effect    # 对应污点的effect，空意味着匹配所有影响
  tolerationSeconds   # 容忍时间, 当effect为NoExecute时生效，表示pod在Node上的停留时间
```





当operator为Equal的时候，如果Node节点有多个Taint，那么Pod每个Taint都需要容忍才能部署上去。

当operator为Exists的时候，有如下的三种写法：

- 容忍指定的污点，污点带有指定的effect：

- 容忍指定的污点，不考虑具体的effect：

- 容忍一切污点（慎用）：

```yaml
  tolerations: # 容忍
    - key: "tag" # 要容忍的污点的key
      operator: Exists # 操作符
      effect: NoExecute # 添加容忍的规则，这里必须和标记的污点规则相同
  tolerations: # 容忍
    - key: "tag" # 要容忍的污点的key
      operator: Exists # 操作符
 tolerations: # 容忍
    - operator: Exists # 操作符
```



- 在上面的污点中，已经给k8s-node1打上了NoExecute的污点，此时Pod是调度不上去的，此时可以通过在Pod中添加容忍，将Pod调度上去。
- 创建pod-toleration.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pod-toleration
  namespace: dev
spec:
  containers: # 容器配置
    - name: nginx
      image: nginx:1.17.1
      imagePullPolicy: IfNotPresent
      ports:
        - name: nginx-port
          containerPort: 80
          protocol: TCP
  tolerations: # 容忍
    - key: "tag" # 要容忍的污点的key
      operator: Equal # 操作符
      value: "xudaxian" # 要容忍的污点的value
      effect: NoExecute # 添加容忍的规则，这里必须和标记的污点规则相同
```



- 创建Pod：



```shell
kubectl create -f pod-toleration.yaml
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400603534-b5e62c95-e16f-4e53-819d-436e6007c399.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod：



```shell
kubectl get pod pod-toleration -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2020/png/513185/1609400615101-10102dc9-5471-448d-895d-3560564fdcf8.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 5 临时容器

## 5.1 概述



- 临时容器是一种特殊的容器，该容器可以在现有的Pod中临时运行，以便完成我们发起的操作，比如故障排查。我们应该使用临时容器来检查服务，而不是用临时容器来构建应用程序。
- Pod是kubernetes集群进行管理的最小单元，由于Pod是一次性且可以替换的，因此Pod一旦被创建，就无法将容器加入到Pod中。而且，我们通常使用Deployment来删除并替换Pod。但是，有的时候我们需要检查现有Pod的状态，比如对难以复现的故障进行排查。在这些场景中，可以在现有Pod中运行临时容器来检查其状态并运行任意命令。



## 5.2 什么是临时容器？



- 临时容器和其他容器的不同之处在于，它们缺少对资源或执行的保证，并且永远不会自动重启，因此不适合用来构建应用程序。临时容器使用和常规容器相同的`ContainerSpec`来描述，但是许多字段是不兼容或者不允许的。

- - 临时容器没有端口配置，因此像`ports`、`livenessProbe`、`readinessProbe`这样的字段是没有的。
  - Pod的资源分配是不可变的，因此`resources`这样的配置临时容器也是没有的。

- - ……

- 临时容器是使用`ephemeralcontainers`来进行创建的，而不是直接添加到`pod.spec`中，所以是无法使用`kubectl edit`来添加一个临时容器。
- 和常规容器一样，将临时容器添加到Pod后，不能更改或删除临时容器。



## 5.3 临时容器的用途



- 当由于容器奔溃或容器镜像不包含调试工具而导致`kubectl exec`无用的时候，临时容器对于交互式故障排查非常有用。
- 比如，像`distroless 镜像`允许用户部署最小的容器镜像，从而减少攻击面并减少故障和漏洞的暴露。由于`distroless 镜像`不包含Shell或任何的调试工具，因此很难单独使用`kubectl exec`命令进行故障排查。

- 使用临时容器的时候，启用[进程名字空间共享](https://kubernetes.io/zh/docs/tasks/configure-pod-container/share-process-namespace/) 很有帮助，可以查看其他容器中的进程。



## 5.4 临时容器的配置



- 目前来说，临时容器默认是关闭的。
- 查看临时容器是否开启：



```shell
kubelet -h | grep EphemeralContainers
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1612074721600-5d43e074-ac58-426e-89ab-786bbff9178a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 在每个节点（不管Master节点还是Node节点）修改kubectl的参数：



注意：kubectl的启动文件的路径是/usr/lib/systemd/system/kubelet.service.d/10-kubeadm.conf



```shell
vim /etc/sysconfig/kubelet
```



```shell
# 修改增加--feature-gates EphemeralContainers=true
KUBELET_EXTRA_ARGS="--cgroup-driver=systemd --feature-gates EphemeralContainers=true"
KUBE_PROXY_MODE="ipvs"
```



```shell
vim /var/lib/kubelet/config.yaml
```



```yaml
apiVersion: kubelet.config.k8s.io/v1beta1
authentication:
  anonymous:
    enabled: falsevim 
  webhook:
    cacheTTL: 0s
    enabled: true
  x509:
    clientCAFile: /etc/kubernetes/pki/ca.crt
authorization:
  mode: Webhook
  webhook:
    cacheAuthorizedTTL: 0s
    cacheUnauthorizedTTL: 0s
clusterDNS:
- 10.96.0.10
clusterDomain: cluster.local
cpuManagerReconcilePeriod: 0s
evictionPressureTransitionPeriod: 0s
fileCheckFrequency: 0s
healthzBindAddress: 127.0.0.1
healthzPort: 10248
httpCheckFrequency: 0s
imageMinimumGCAge: 0s
kind: KubeletConfiguration
nodeStatusReportFrequency: 0s
nodeStatusUpdateFrequency: 0s
rotateCertificates: true
runtimeRequestTimeout: 0s
staticPodPath: /etc/kubernetes/manifests
streamingConnectionIdleTimeout: 0s
syncFrequency: 0s
volumeStatsAggPeriod: 0s
# 修改部分
featureGates:
  EphemeralContainers: true
```



- 加载配置文件重启kubelet：



```shell
systemctl daemon-reload
```



```shell
systemctl stop kubelet
```



```shell
systemctl start kubelet
```



- 在Master节点修改kube-apiserver.yaml和kube-scheduler.yaml：



```shell
vim /etc/kubernetes/manifests/kube-apiserver.yaml
```



```yaml
apiVersion: v1
kind: Pod
metadata:
  annotations:
    kubeadm.kubernetes.io/kube-apiserver.advertise-address.endpoint: 192.168.49.100:6443
  creationTimestamp: null
  labels:
    component: kube-apiserver
    tier: control-plane
  name: kube-apiserver
  namespace: kube-system
spec:
  containers:
  - command:
    - kube-apiserver
    - --advertise-address=192.168.49.100
    - --allow-privileged=true
    - --authorization-mode=Node,RBAC
    - --client-ca-file=/etc/kubernetes/pki/ca.crt
    - --enable-admission-plugins=NodeRestriction
    - --enable-bootstrap-token-auth=true
    - --etcd-cafile=/etc/kubernetes/pki/etcd/ca.crt
    - --etcd-certfile=/etc/kubernetes/pki/apiserver-etcd-client.crt
    - --etcd-keyfile=/etc/kubernetes/pki/apiserver-etcd-client.key
    - --etcd-servers=https://127.0.0.1:2379
    - --insecure-port=0
    - --kubelet-client-certificate=/etc/kubernetes/pki/apiserver-kubelet-client.crt
    - --kubelet-client-key=/etc/kubernetes/pki/apiserver-kubelet-client.key
    - --kubelet-preferred-address-types=InternalIP,ExternalIP,Hostname
    - --proxy-client-cert-file=/etc/kubernetes/pki/front-proxy-client.crt
    - --proxy-client-key-file=/etc/kubernetes/pki/front-proxy-client.key
    - --requestheader-allowed-names=front-proxy-client
    - --requestheader-client-ca-file=/etc/kubernetes/pki/front-proxy-ca.crt
    - --requestheader-extra-headers-prefix=X-Remote-Extra-
    - --requestheader-group-headers=X-Remote-Group
    - --requestheader-username-headers=X-Remote-User
    - --secure-port=6443
    - --service-account-issuer=https://kubernetes.default.svc.cluster.local
    - --service-account-key-file=/etc/kubernetes/pki/sa.pub
    - --service-account-signing-key-file=/etc/kubernetes/pki/sa.key
    - --service-cluster-ip-range=10.96.0.0/12
    - --tls-cert-file=/etc/kubernetes/pki/apiserver.crt
    - --tls-private-key-file=/etc/kubernetes/pki/apiserver.key
    # 修改部分
    - --feature-gates=EphemeralContainers=true
```



```shell
vim /etc/kubernetes/manifests/kube-scheduler.yaml
```



```yaml
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    component: kube-scheduler
    tier: control-plane
  name: kube-scheduler
  namespace: kube-system
spec:
  containers:
  - command:
    - kube-scheduler
    - --authentication-kubeconfig=/etc/kubernetes/scheduler.conf
    - --authorization-kubeconfig=/etc/kubernetes/scheduler.conf
    - --bind-address=127.0.0.1
    - --kubeconfig=/etc/kubernetes/scheduler.conf
    - --leader-elect=true
    # 修改部分
    - --feature-gates=EphemeralContainers=true
```



## 5.5 使用临时容器在线debug



- 创建一个nginx.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  shareProcessNamespace: true # 这个配置非常重要，一定要配置
  containers:
  - name: nginx
    image: nginx:1.17.1
```



- 创建Pod：



```shell
kubectl apply -f nginx.yaml
```



- 创建ec.json文件，内容如下（注意：name是Pod的名称）：



```json
{
    "apiVersion": "v1",
    "kind": "EphemeralContainers",
    "metadata": {
            "name": "nginx"
    },
    "ephemeralContainers": [{
        "command": [
            "sh"
        ],
        "image": "busybox",
        "imagePullPolicy": "IfNotPresent",
        "name": "debugger",
        "stdin": true,
        "tty": true,
        "terminationMessagePolicy": "File"
    }]
}
```



- 使用下面的命令更新已经运行的容器：



```shell
kubectl replace --raw /api/v1/namespaces/default/pods/nginx/ephemeralcontainers  -f ec.json
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1612074764752-2bfd6ea6-64cc-4bbd-8056-c73dd28c9972.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 使用如下的命令查看新创建的临时容器的状态：



```shell
kubectl describe pod nginx
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1612074778479-9ae22c00-23fa-406b-9996-01cc80050db7.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 可以使用如下的命令连接临时容器：



```shell
kubectl exec -it nginx -c debugger -- sh
```



```shell
kubectl attach -it nginx -c debugger
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1612074792900-46a5b04e-86c9-462b-9838-ba589b48ac3e.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 6 服务质量Qos



## 6.1 概述



- kubernetes创建Pod的时候就会指定QoS。
- QoS分为如下的三类：

- - ① Guaranteed。
  - ② Burstable。

- - ③ BestEffort。



## 6.2 Qos之Guaranteed 



### 6.2.1 概述



- 对于 QoS 类为 Guaranteed 的 Pod：

- - Pod 中的每个容器，包含初始化容器，必须指定内存请求和内存限制，并且两者要相等。
  - Pod 中的每个容器，包含初始化容器，必须指定 CPU 请求和 CPU 限制，并且两者要相等。





### 6.2.2 应用示例



- 创建命名空间：

```shell
kubectl create namespace qos-example
```

- 创建qos-demo.yaml文件，内容如下：

```shell
vim qos-demo.yaml
apiVersion: v1
kind: Pod
metadata:
  name: qos-demo
  namespace: qos-example
spec:
  containers:
  - name: qos-demo-ctr
    image: nginx
    resources:
      limits:
        memory: "200Mi"
        cpu: "700m"
      requests:
        memory: "200Mi"
        cpu: "700m"
```

- 创建Pod：

```shell
kubectl create -f qos-demo.yaml 
```

- 查看Pod详情：

```shell
kubectl get pod qos-demo -n qos-example -o yaml
```

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1612159798000-056eae86-cbed-4a51-b547-72dde99ccfde.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_34%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 删除Pod：

```shell
kubectl delete -f qos-demo.yaml
```



## 6.3 Qos之Burstable 



### 6.3.1 概述



- 如果满足下面条件，将会指定 Pod 的 QoS 类为 Burstable：

- - Pod 不符合 Guaranteed QoS 类的标准。
  - Pod 中至少一个容器具有内存或 CPU 请求，但是值不相等。



### 6.3.2 应用示例



- 创建qos-demo-2.yaml文件，内容如下：

```shell
vim qos-demo-2.yaml
apiVersion: v1
kind: Pod
metadata:
  name: qos-demo-2
  namespace: qos-example
spec:
  containers:
  - name: qos-demo-2-ctr
    image: nginx
    resources:
      limits:
        memory: "200Mi"
      requests:
        memory: "100Mi"
```

- 创建Pod：

```shell
kubectl create -f qos-demo-2.yaml 
```

- 查看Pod详情：

```shell
kubectl get pod qos-demo-2 -n qos-example -o yaml
```

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1612159966448-676beb7b-43a0-455f-8656-21a0c2cfe23f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_34%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 删除Pod：

```shell
kubectl delete -f qos-demo2.yaml
```



## 6.4 Qos之BestEffort 



### 6.4.1 概述



- 对于 QoS 类为 BestEffort 的 Pod，Pod 中的容器必须没有设置内存和 CPU 限制或请求。



### 6.4.2 应用示例

- 创建qos-demo-3.yaml文件，内容如下：

```shell
vim qos-demo-3.yaml
apiVersion: v1
kind: Pod
metadata:
  name: qos-demo-3
  namespace: qos-example
spec:
  containers:
  - name: qos-demo-3-ctr
    image: nginx
```

- 创建Pod：

```shell
kubectl create -f qos-demo-3.yaml 
```

- 查看Pod详情：

```shell
kubectl get pod qos-demo-3 -n qos-example -o yaml
```

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1612160099696-cd0e5e8b-9bc6-4f18-8527-c187237672a5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_34%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 删除Pod：

```shell
kubectl delete -f qos-demo3.yaml
```



## 6.5 Qos的应用

- 一旦出现OOM，kubernetes为了保证服务的可用，会先删除QoS为BestEffort的Pod，然后删除QoS为Burstable的Pod，最后删除QoS为Guaranteed 的Pod。
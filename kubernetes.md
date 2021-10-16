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



MobaXterm professional 同时连接多个服务器

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




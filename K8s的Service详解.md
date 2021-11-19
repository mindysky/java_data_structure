# K8s的Service详解

主要介绍kubernetes的流量负载组件：Service和Ingress。



# 1 Service介绍



- 在kubernetes中，Pod是应用程序的载体，我们可以通过Pod的IP来访问应用程序，但是Pod的IP地址不是固定的，这就意味着不方便直接采用Pod的IP对服务进行访问。

- 为了解决这个问题，kubernetes提供了Service资源，Service会对提供同一个服务的多个Pod进行聚合，并且提供一个统一的入口地址，通过访问Service的入口地址就能访问到后面的Pod服务。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904160160-74eebf02-ec02-416b-83b7-58a2b5392c3a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_21%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- Service在很多情况下只是一个概念，真正起作用的其实是kube-proxy服务进程，每个Node节点上都运行了一个kube-proxy的服务进程。当创建Service的时候会通过API Server向etcd写入创建的Service的信息，而kube-proxy会基于监听的机制发现这种Service的变化，然后它会将最新的Service信息转换为对应的访问规则。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904171516-d7d58ebc-785b-4e71-a370-6f6f163c713d.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



```shell
# 10.97.97.97:80 是service提供的访问入口
# 当访问这个入口的时候，可以发现后面有三个pod的服务在等待调用，
# kube-proxy会基于rr（轮询）的策略，将请求分发到其中一个pod上去
# 这个规则会同时在集群内的所有节点上都生成，所以在任何一个节点上访问都可以。
[root@k8s-node1 ~]# ipvsadm -Ln
IP Virtual Server version 1.2.1 (size=4096)
Prot LocalAddress:Port Scheduler Flags
 -> RemoteAddress:Port  Forward Weight ActiveConn InActConn
 TCP 10.97.97.97:80 rr
  -> 10.244.1.39:80   Masq  1  0  0
  -> 10.244.1.40:80   Masq  1  0  0
  -> 10.244.2.33:80   Masq  1  0  0
```



- kube-proxy目前支持三种工作模式：

- - userspace模式：

- - - userspace模式下，kube-proxy会为每一个Service创建一个监听端口，发向Cluster IP的请求被iptables规则重定向到kube-proxy监听的端口上，kube-proxy根据LB算法（负载均衡算法）选择一个提供服务的Pod并和其建立连接，以便将请求转发到Pod上。

- - - 该模式下，kube-proxy充当了一个四层负载均衡器的角色。由于kube-proxy运行在userspace中，在进行转发处理的时候会增加内核和用户空间之间的数据拷贝，虽然比较稳定，但是效率非常低下。

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904185572-943d0ef3-a7ec-44af-8710-83d6c06c3179.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_24%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - iptables模式：

- - - iptables模式下，kube-proxy为Service后端的每个Pod创建对应的iptables规则，直接将发向Cluster IP的请求重定向到一个Pod的IP上。

- - - 该模式下kube-proxy不承担四层负载均衡器的角色，只负责创建iptables规则。该模式的优点在于较userspace模式效率更高，但是不能提供灵活的LB策略，当后端Pod不可用的时候无法进行重试。

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904200593-e3b0fe06-f0e9-4024-854b-5cb88551d1c5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_24%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - ipvs模式：

- - - ipvs模式和iptables类似，kube-proxy监控Pod的变化并创建相应的ipvs规则。ipvs相对iptables转发效率更高，除此之外，ipvs支持更多的LB算法。

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904216661-75919a0e-dfc0-4524-8171-4cb3a94a4e1b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_23%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- 开启ipvs（必须安装ipvs内核模块，否则会降级为iptables）：



```shell
kubectl edit cm kube-proxy -n kube-system
```



![img](https://cdn.nlark.com/yuque/0/2021/gif/513185/1609904265505-1136f319-e955-4b3a-a5a0-fa830b8f3630.gif)



```shell
kubectl delete pod -l k8s-app=kube-proxy -n kube-system
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904277986-115b2025-03d1-42b3-830e-59a0c3ba937b.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_24%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



```shell
# 测试ipvs模块是否开启成功
ipvsadm -Ln
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904291694-6e63d07c-87a3-42ab-9622-db7e5da1a734.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 2 Service类型



- Service的资源清单：



```yaml
apiVersion: v1 # 版本
kind: Service # 类型
metadata: # 元数据
  name: # 资源名称
  namespace: # 命名空间
spec:
  selector: # 标签选择器，用于确定当前Service代理那些Pod
    app: nginx
  type: NodePort # Service的类型，指定Service的访问方式
  clusterIP: # 虚拟服务的IP地址
  sessionAffinity: # session亲和性，支持ClientIP、None两个选项，默认值为None
  ports: # 端口信息
    - port: 8080 # Service端口
      protocol: TCP # 协议
      targetPort : # Pod端口
      nodePort:  # 主机端口
```



spec.type的说明：

- ClusterIP：默认值，它是kubernetes系统自动分配的虚拟IP，只能在集群内部访问。

- NodePort：将Service通过指定的Node上的端口暴露给外部，通过此方法，就可以在集群外部访问服务。

- LoadBalancer：使用外接负载均衡器完成到服务的负载分发，注意此模式需要外部云环境的支持。

- ExternalName：把集群外部的服务引入集群内部，直接使用。



# 3 Service使用



## 3.1 实验环境准备



- 在使用Service之前，首先利用Deployment创建出3个Pod，注意要为Pod设置`app=nginx-pod`的标签。

- 创建deployment.yaml文件，内容如下：



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: pc-deployment
  namespace: dev
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx-pod
  template:
    metadata:
      labels:
        app: nginx-pod
    spec:
      containers:
        - name: nginx
          image: nginx:1.17.1
          ports:
            - containerPort: 80
```



- 创建Deployment：



```shell
kubectl create -f deployment.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904316074-d8d79dd7-2557-4751-85ac-874235c1c86f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Pod信息：



```shell
kubectl get pod -n dev -o wide --show-labels
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904334089-3f0ee88c-99d4-4136-a9a6-fd8cbab3c0b3.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 为了方便后面的测试，修改三台Nginx的index.html：



```shell
kubectl exec -it  pc-deployment-7d7dd5499b-59qkm -c nginx -n dev /bin/sh
```



```shell
echo "10.244.1.30" > /usr/share/nginx/html/index.html
```



![img](https://cdn.nlark.com/yuque/0/2021/gif/513185/1609904344604-143670bb-dec6-4f1e-83a2-701e5a686852.gif)



```shell
kubectl exec -it pc-deployment-7d7dd5499b-fwpgx -c nginx -n dev /bin/sh
```



```shell
echo "10.244.1.31" > /usr/share/nginx/html/index.html
```



```shell
kubectl exec -it pc-deployment-7d7dd5499b-nb6sv -c nginx -n dev /bin/sh
```



```shell
echo "10.244.2.67" > /usr/share/nginx/html/index.html
```



- 修改完毕之后，测试访问：



```shell
curl 10.244.1.30
```



```shell
curl 10.244.1.31
```



```shell
curl 10.244.2.67
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609904362900-2d4ba1f4-4ad1-4cbb-89f7-186ad5961323.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_21%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.2 ClusterIP类型的Service



### 3.2.1 创建Service



- 创建service-clusterip.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Service
metadata:
  name: service-clusterip
  namespace: dev
spec:
  selector:
    app: nginx-pod
  clusterIP: 10.97.97.97 # service的IP地址，如果不写，默认会生成一个
  type: ClusterIP
  ports:
    - port: 80 # Service的端口
      targetPort: 80 # Pod的端口
```



- 创建Service：



```shell
kubectl create -f service-clusterip.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905234380-8dabdd96-bbf6-4fd3-8a85-fe910d1761f9.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_21%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.2 查看Service



- 查看Service：



```shell
kubectl get svc -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905247271-e3333b2e-da81-4d7b-807f-fa22de966654.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_21%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.3 查看Service的详细信息



- 查看Service的详细信息：



```shell
kubectl describe svc service-clusterip -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905254693-8c1073b8-5cb2-4a94-b93e-2d9b617fef65.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_21%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.4 查看ipvs的映射规则



- 查看ipvs的映射规则：



```shell
ipvsadm -Ln
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905268527-23b1d9fc-5ea9-4889-916f-5d6179d66d74.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_21%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.5 访问10.97.97.97:80，观察效果



- 访问10.97.97.97:80，观察效果：



```shell
curl 10.97.97.97:80
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905288189-be32a34d-d186-44e1-9559-9da15305eeda.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_21%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.6 Endpoint（实际中使用的不多）



- Endpoint是kubernetes中的一个资源对象，存储在etcd中，用来记录一个service对应的所有Pod的访问地址，它是根据service配置文件中的selector描述产生的。

- 一个service由一组Pod组成，这些Pod通过Endpoints暴露出来，Endpoints是实现实际服务的端点集合。换言之，service和Pod之间的联系是通过Endpoints实现的。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905301627-5ad470f0-7277-4fa1-9afa-1d23e8fcd453.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Endpoint：



```shell
kubectl get endpoints -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905319715-dcc1afd3-6d4b-4fd1-9cfd-63522e412a6a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.7 负载分发策略



- 对Service的访问被分发到了后端的Pod上去，目前kubernetes提供了两种负载分发策略：

- - 如果不定义，默认使用kube-proxy的策略，比如随机、轮询等。

- - 基于客户端地址的会话保持模式，即来自同一个客户端发起的所有请求都会转发到固定的一个Pod上，这对于传统基于Session的认证项目来说很友好，此模式可以在spec中添加`sessionAffinity: ClusterIP`选项。

- 查看ipvs的映射规则，rr表示轮询：



```shell
ipvsadm -Ln
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905342738-337292db-74ae-4375-924f-6baf2b30ac7c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 循环测试访问：



```shell
while true;do curl 10.97.97.97:80; sleep 5; done;
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905354325-ae011bea-756a-4616-8a69-c1b060a06135.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 修改分发策略：



```yaml
apiVersion: v1
kind: Service
metadata:
  name: service-clusterip
  namespace: dev
spec:
  selector:
    app: nginx-pod
  clusterIP: 10.97.97.97 # service的IP地址，如果不写，默认会生成一个
  type: ClusterIP
  sessionAffinity: ClientIP # 修改分发策略为基于客户端地址的会话保持模式
  ports:
    - port: 80 # Service的端口
      targetPort: 80 # Pod的端口
```



```shell
kubectl apply -f service-clusterip.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905372098-ccaf40dd-6da8-4f53-93e7-afefbff0a8fc.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 循环测试访问：



```shell
while true;do curl 10.97.97.97:80; sleep 5; done;
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905384133-30b6f113-adaa-4907-897c-cdfb88b49e3a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.2.8 删除Service



- 删除Service：



```shell
kubectl delete -f service-clusterip.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905397974-0b3d7e22-c02a-48fa-b0f7-3f073e11f049.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.3 HeadLiness类型的Service



### 3.3.1 概述



- 在某些场景中，开发人员可能不想使用Service提供的负载均衡功能，而希望自己来控制负载均衡策略，针对这种情况，kubernetes提供了HeadLinesss Service，这类Service不会分配Cluster IP，如果想要访问Service，只能通过Service的域名进行查询。



### 3.3.2 创建Service



- 创建service-headliness.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Service
metadata:
  name: service-headliness
  namespace: dev
spec:
  selector:
    app: nginx-pod
  clusterIP: None # 将clusterIP设置为None，即可创建headliness Service
  type: ClusterIP
  ports:
    - port: 80 # Service的端口
      targetPort: 80 # Pod的端口
```



- 创建Service：



```shell
kubectl create -f service-headliness.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905452740-2392277c-a138-4fb2-a00b-973e5c2bce25.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.3.3 查看Service



- 查看Service：



```shell
kubectl get svc service-headliness -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905466920-5d25b328-2eef-47b8-870a-d47dbb1ca335.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.3.4 查看Service详情



- 查看Service详情：



```shell
kubectl describe svc service-headliness -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905479250-061cc3ef-8ce5-4717-8684-8da635ef70cd.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.3.5 查看域名解析情况



- 查看Pod：



```shell
kubectl get pod -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905502021-c3572211-91d0-4ce6-b5fc-a6671d474201.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 进入Pod中，执行cat /etc/resolv.conf命令：



```shell
kubectl exec -it pc-deployment-7d7dd5499b-59qkm -n dev /bin/sh
```



```shell
cat /etc/resolv.conf
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905518484-a07a6cbd-7d0a-42c9-aaf4-abada9fc50af.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.3.6 通过Service的域名进行查询



- 通过Service的域名进行查询：



```shell
dig @10.96.0.10 service-headliness.dev.svc.cluster.local
```



## 3.4 NodePort类型的Service



### 3.4.1 概述



- 在之前的案例中，创建的Service的IP地址只能在集群内部才可以访问，如果希望Service暴露给集群外部使用，那么就需要使用到另外一种类型的Service，称为NodePort类型的Service。NodePort的工作原理就是将Service的端口映射到Node的一个端口上，然后就可以通过`NodeIP:NodePort`来访问Service了。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905553304-e36bfc3a-7164-4ef5-8e9f-e14839509818.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_20%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.4.2 创建Service



- 创建service-nodeport.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Service
metadata:
  name: service-nodeport
  namespace: dev
spec:
  selector:
    app: nginx-pod
  type: NodePort # Service类型为NodePort
  ports:
    - port: 80 # Service的端口
      targetPort: 80 # Pod的端口
      nodePort: 30002 # 指定绑定的node的端口（默认取值范围是30000~32767），如果不指定，会默认分配
```



- 创建Service：



```shell
kubectl create -f service-nodeport.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905569115-c7aa67a2-962b-461d-8bae-05bc212832e9.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.4.3 查看Service



- 查看Service：



```shell
kubectl get svc service-nodeport -n dev -o wide
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905580867-4bc5480a-a991-4326-bde1-00837629c95f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.4.4 访问



- 通过浏览器访问：http://192.168.18.100:30002/即可访问对应的Pod。



## 3.5 LoadBalancer类型的Service



- LoadBalancer和NodePort很相似，目的都是向外部暴露一个端口，区别在于LoadBalancer会在集群的外部再来做一个负载均衡设备，而这个设备需要外部环境的支持，外部服务发送到这个设备上的请求，会被设备负载之后转发到集群中。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905593459-9981ec42-89ec-4f23-ad9e-3e63554fb2b0.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_29%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 3.6 ExternalName类型的Service



### 3.6.1 概述



- ExternalName类型的Service用于引入集群外部的服务，它通过externalName属性指定一个服务的地址，然后在集群内部访问此Service就可以访问到外部的服务了。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905613110-91fc1ab8-0fd2-4dab-8cd4-33bf5a4ce4c2.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_28%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.6.2 创建Service



- 创建service-externalname.yaml文件，内容如下：



```yaml
apiVersion: v1
kind: Service
metadata:
  name: service-externalname
  namespace: dev
spec:
  type: ExternalName # Service类型为ExternalName
  externalName: www.baidu.com # 改成IP地址也可以
```



- 创建Service：



```shell
kubectl create -f service-externalname.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905633938-6fca148a-52f1-467c-84b7-25be013b6102.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_30%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 3.6.3 域名解析



- 域名解析：



```shell
dig @10.96.0.10 service-externalname.dev.svc.cluster.local
```



# 4 Ingress介绍



- 我们已经知道，Service对集群之外暴露服务的主要方式有两种：NodePort和LoadBalancer，但是这两种方式，都有一定的缺点：

- - NodePort方式的缺点是会占用很多集群机器的端口，那么当集群服务变多的时候，这个缺点就愈发明显。

- - LoadBalancer的缺点是每个Service都需要一个LB，浪费，麻烦，并且需要kubernetes之外的设备的支持。

- 基于这种现状，kubernetes提供了Ingress资源对象，Ingress只需要一个NodePort或者一个LB就可以满足暴露多个Service的需求，工作机制大致如下图所示：



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905648464-a3f21b67-099e-4c8f-9152-786b6bc3e46a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 实际上，Ingress相当于一个七层的负载均衡器，是kubernetes对反向代理的一个抽象，它的工作原理类似于Nginx，可以理解为Ingress里面建立了诸多映射规则，Ingress Controller通过监听这些配置规则并转化为Nginx的反向代理配置，然后对外提供服务。

- - Ingress：kubernetes中的一个对象，作用是定义请求如何转发到Service的规则。

- - Ingress Controller：具体实现反向代理及负载均衡的程序，对Ingress定义的规则进行解析，根据配置的规则来实现请求转发，实现的方式有很多，比如Nginx，Contour，Haproxy等。

- Ingress（以Nginx）的工作原理如下：

- - 1. 用户编写Ingress规则，说明那个域名对应kubernetes集群中的那个Service。

- - 2. Ingress控制器动态感知Ingress服务规则的变化，然后生成一段对应的Nginx的反向代理配置。

- - 3. Ingress控制器会将生成的Nginx配置写入到一个运行着的Nginx服务中，并动态更新。

- - 4. 到此为止，其实真正在工作的就是一个Nginx了，内部配置了用户定义的请求规则。



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905668517-a82f7096-bfa4-44a6-b5d6-fac18efb4111.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



# 5 Ingress使用



## 5.1 环境准备



### 5.1.1 搭建Ingress环境



- 创建文件夹，并进入到此文件夹中：



```shell
mkdir ingress-controller
```



```shell
cd ingress-controller
```



- 获取ingress-nginx，本次使用的是0.30版本，网络不行，可以下载本人提供的[📎mandatory.yaml](https://www.yuque.com/attachments/yuque/0/2021/yaml/513185/1609905742599-d0d53004-9dc0-4b0c-9980-7dec975e159d.yaml)[📎service-nodeport.yaml](https://www.yuque.com/attachments/yuque/0/2021/yaml/513185/1609905748141-536073a2-c8da-4fc9-9eaf-d69a961f3168.yaml)：



```shell
wget https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.30.0/deploy/static/mandatory.yaml
```



```shell
wget https://raw.githubusercontent.com/kubernetes/ingress-nginx/nginx-0.30.0/deploy/static/provider/baremetal/service-nodeport.yaml
```



- 创建Ingress-nginx：



```shell
kubectl apply -f ./
```



- 查看ingress-nginx：



```shell
kubectl get pod -n ingress-nginx
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905781813-1fcc744c-7348-4355-861d-f8207f80d641.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Service：



```shell
kubectl get svc -n ingress-nginx
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905798555-a3b53cfa-14e9-4fbb-b7e8-bdbba07797b1.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 5.1.2 准备Service和Pod



- 为了后面的实验比较方便，创建如下图所示的模型：



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905816952-62872b94-d5a2-4ee1-81da-ee144817c151.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建tomcat-nginx.yaml文件，内容如下：



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  namespace: dev
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx-pod
  template:
    metadata:
      labels:
        app: nginx-pod
    spec:
      containers:
      - name: nginx
        image: nginx:1.17.1
        ports:
        - containerPort: 80

---

apiVersion: apps/v1
kind: Deployment
metadata:
  name: tomcat-deployment
  namespace: dev
spec:
  replicas: 3
  selector:
    matchLabels:
      app: tomcat-pod
  template:
    metadata:
      labels:
        app: tomcat-pod
    spec:
      containers:
      - name: tomcat
        image: tomcat:8.5-jre10-slim
        ports:
        - containerPort: 8080

---

apiVersion: v1
kind: Service
metadata:
  name: nginx-service
  namespace: dev
spec:
  selector:
    app: nginx-pod
  clusterIP: None
  type: ClusterIP
  ports:
  - port: 80
    targetPort: 80

---

apiVersion: v1
kind: Service
metadata:
  name: tomcat-service
  namespace: dev
spec:
  selector:
    app: tomcat-pod
  clusterIP: None
  type: ClusterIP
  ports:
  - port: 8080
    targetPort: 8080
```



- 创建Service和Pod：



```shell
kubectl create -f tomcat-nginx.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905838761-6fe8090e-0682-438e-b716-64b388351830.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看Service和Pod：



```shell
kubectl get svc,pod -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905848879-725e94d8-4a34-4b55-becb-8196bec0f55d.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 5.2 Http代理



- 创建ingress-http.yaml文件，内容如下：



```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-http
  namespace: dev
spec:
  rules:
  - host: nginx.xudaxian.com
    http:
      paths:
      - path: /
        backend:
          serviceName: nginx-service
          servicePort: 80
  - host: tomcat.xudaxian.com
    http:
      paths:
      - path: /
        backend:
          serviceName: tomcat-service
          servicePort: 8080
```



- 创建：



```shell
kubectl create -f ingress-http.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905868340-5639985c-14cc-436c-987e-63c833c2dc0f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看：



```shell
kubectl get ingress ingress-http -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905877053-c54636e4-55d9-445d-b82a-d52fbbfc8d50.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看详情：



```shell
kubectl describe ingress ingress-http -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905888703-feb27831-b1ef-4943-8483-e15d4b28f3cd.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 在本机的hosts文件中添加如下的规则（192.168.209.100为Master节点的IP地址）：



```shell
192.168.209.100 nginx.xudaxian.com
192.168.209.100 tomcat.xudaxian.com
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905905942-bd87253f-54aa-43bd-b46d-5b8f76cfb14a.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看ingress-nginx的端口（本次测试http的端口是30378，https的端口是31125）：



```shell
kubectl get svc -n ingress-nginx
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905945716-33c2f149-2df7-429d-a607-3647d24020ce.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 本机通过浏览器输入下面的地址访问：



```shell
http://nginx.xudaxian.com:30378
```



```shell
http://tomcat.xudaxian.com:30378
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905952553-42f5a151-96db-43b3-bfe6-2b9d18d9cd3f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905965014-cc50a668-2f40-40a4-9f28-aa3b6805f011.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_27%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 5.3 Https代理



- 生成证书：



```shell
openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -keyout tls.key -out tls.crt -subj "/C=CN/ST=BJ/L=BJ/O=nginx/CN=xudaxian.com"
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905976229-e9b249f3-ae18-46d2-8c97-0221a28a9dc7.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建密钥：



```shell
kubectl create secret tls tls-secret --key tls.key --cert tls.crt
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609905989855-f4d191a3-9c64-41f2-b158-b268b3418666.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 创建ingress-https.yaml文件，内容如下：



```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-https
  namespace: dev
spec:
  tls:
    - hosts:
      - nginx.xudaxian.com
      - tomcat.xudaxian.com
      secretName: tls-secret # 指定秘钥
  rules:
  - host: nginx.xudaxian.com
    http:
      paths:
      - path: /
        backend:
          serviceName: nginx-service
          servicePort: 80
  - host: tomcat.xudaxian.com
    http:
      paths:
      - path: /
        backend:
          serviceName: tomcat-service
          servicePort: 8080
```



- 创建：



```shell
kubectl create -f ingress-https.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609906008189-950ae6d5-536d-4e89-b2e5-2e615d4a6e2c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看：



```shell
kubectl get ingress ingress-https -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609906021857-d958ed2f-8691-4129-a1b1-70c2879cbaeb.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看详情：



```shell
kubectl describe ingress ingress-https -n dev
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1609906034564-69ac396f-346a-4595-9f92-3b85b7ec13e5.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_22%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 在本机的hosts文件中添加如下的规则（192.168.209.100为Master节点的IP地址）：略。

- 本机通过浏览器输入下面的地址访问：



```shell
https://nginx.xudaxian.com:31125
```



```shell
https://tomcat.xudaxian.com:31125
```
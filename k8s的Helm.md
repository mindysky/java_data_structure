# k8s的Helm

Helm v3版本



# 1 引入



- kubernetes上的应用对象，都是由特定的资源描述组成，包括Deployment、Service等，都保存在各自文件中或者集中写在一个配置文件，然后通过`kubectl apply -f` 部署。如果应用只由一个或几个这样的服务组成，上面的部署方式就足够了。但是对于一个复杂的应用，会有很多类似上面的资源描述文件，例如微服务架构应用，组成应用的服务可能多达几十、上百个，如果有更新或回滚应用的需求，可能要修改和维护所涉及到大量的资源文件，而这种组织和管理应用的方式就显得力不从心了。并且由于缺少对发布过的应用进行版本管理和控制，使得kubernetes上的应用维护和更新面临诸多的挑战，主要面临以下的问题：

- - ①如何将这些服务作为一个整体管理？

- - ②这些资源文件如何高效复用？

- - ③应用级别的版本如何管理？



# 2 概述



- Helm是一个kubernetes的包管理工具，就像Linux下的包管理器，如yum、apt等，可以很方便的将之前打包好的yaml文件部署到kubernetes上。

- Helm有3个重要概念：

- - helm：一个命令行客户端工具，主要用于kubernetes应用chart的创建、打包、发布和管理。

- - chart：应用描述，一系列用于描述kubernetes资源相关文件的集合。

- - release：基于chart的部署实体，一个chart被Helm运行后将会生成对应的一个release，将在kubernetes中创建出真实运行的资源对象。



# 3 Helm v3变化



- 2019年11月13日，Helm团队发布Helm v3的第一个稳定版本。

- 该版本主要变化如下：

- - ①最明显的变化是Tiller删除。

![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375112048-68dae493-7a2c-42bd-ae92-3f26a34e3efc.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_26%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)

- - ②release名称可以在不同的命名空间重用。

- - ③支持将chart推动到Docker镜像仓库中。

- - ④使用JSONSchema验证chart values。

- - ⑤其他。



# 4 Helm客户端



## 4.1 部署Helm客户端



- 下载Helm（如果网速太慢，请点这里[📎helm-v3.2.1-linux-amd64.tar.gz](https://www.yuque.com/attachments/yuque/0/2021/gz/513185/1610375141403-a6ee0241-1106-4977-8734-feddd6897bec.gz)）：



```shell
wget https://get.helm.sh/helm-v3.2.1-linux-amd64.tar.gz
```



- 解压Helm到/usr/bin目录：



```shell
tar -zxvf helm-v3.2.1-linux-amd64.tar.gz
```



```shell
cd linux-amd64/
```



```shell
cp helm /usr/bin/
```



## 4.2 配置国内的chart仓库



### 4.2.1 仓库概述



- 微软仓库：http://mirror.azure.cn/kubernetes/charts，推荐，基本上官网上的chart这里都有。

- 阿里云仓库：https://kubernetes.oss-cn-hangzhou.aliyuncs.com/charts。

- 官方仓库：https://hub.kubeapps.com/charts/incubator，强烈不推荐。



### 4.2.2 添加仓库



- 添加仓库命令：



```shell
helm repo add 仓库名 仓库地址。
```



- 更新仓库命令：



```shell
helm repo update
```



- 示例：添加微软仓库，并更新仓库



```shell
helm repo add stable http://mirror.azure.cn/kubernetes/charts
```



```shell
helm repo add aliyun https://kubernetes.oss-cn-hangzhou.aliyuncs.com/charts
```



```shell
helm repo update
```



### 4.2.3 删除存储库



- 删除存储库：



```shell
helm repo remove 仓库名
```



### 4.2.4 查看配置的存储库



- 查看配置的存储库：



```shell
helm repo list
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375188606-c8541df5-a359-4309-bf44-d05e97bc2b59.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 4.3 helm的常用命令

| 命令       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| create     | 创建一个chart并指定名字                                      |
| dependency | 管理chart依赖                                                |
| get        | 下载一个release。可用的子命令：all、hooks、manifest、notes、values。 |
| history    | 获取release历史。                                            |
| install    | 安装一个chart。                                              |
| list       | 列出release。                                                |
| package    | 将chart目录打包到chart存档文件中。                           |
| pull       | 从远程仓库中下载chart并解压到本地。比如：helm install stable/mysql --untar。 |
| repo       | 添加、列出、移除、更新和索引chart仓库。可用的子命令：add、index、list、remove、update。 |
| rollback   | 从之前的版本回退。                                           |
| search     | 根据关键字搜索chart。可用的子命令：all、chart、readme、values。 |
| show       | 查看chart的详细信息。可用的子命令：all、chart、readme、values。 |
| status     | 显示已命名版本的状态。                                       |
| template   | 本地呈现模板。                                               |
| uninstall  | 卸载一个release。                                            |
| upgrade    | 更新一个release。                                            |
| version    | 查看Helm客户端版本。                                         |



# 5 Helm基本使用



## 5.1 使用chart部署一个应用



### 5.1.1 根据关键字搜索chart



- 语法：



```shell
helm search repo|hub chart名称
```



- 示例：查询名为weave的chart



```shell
helm search repo weave
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375201449-501279f4-23d5-44fa-a17a-1e5a6b218b00.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 5.1.2 查看chart信息



- 语法：



```shell
helm show chart 仓库名/chart名称
```



- 示例：查看名为stable的仓库中的名为mysql的chart



```shell
helm show chart stable/mysql
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375216458-266896ac-8b40-415d-9779-fc950b1510d7.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 5.1.3 安装chart，形成release



- 语法：



```shell
helm install 安装之后的名称 仓库名/chart的名称(即搜索之后的应用名称)
```



- 示例：安装stable/weave-scope，并将其命名为ui



```shell
helm install ui stable/weave-scope
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375228538-f16c964f-a658-4c57-a8ae-23b053aaab63.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 5.1.4 查看release列表



- 语法：



```shell
helm list
```



- 示例：查看当前的release列表



```shell
helm list
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375240126-f3fc4c0b-ac09-42d6-94c2-30d5ba1d0b98.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 5.1.5 查看已命名release的状态



- 语法：



```shell
helm status 安装之后的名称
```



- 示例：查看安装chart的release为ui的状态



```shell
helm status ui
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375252076-8c2851ff-f3b6-4e7a-ba59-7617952d3826.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



### 5.1.6 查看Service，并将其改为NodePort



- 语法：



```shell
kubectl edit svc xxx
```



- 示例：查看Service，并将其改为NodePort



```shell
kubectl edit svc ui-weave-scope
```



![img](https://cdn.nlark.com/yuque/0/2021/gif/513185/1610375265204-1ada7693-79ed-496e-81e0-eb670c5780e1.gif)



## 5.2 安装前自定义chart配置选项



### 5.2.1 概述



- 自定义选项是因为并不是所有的chart都能按照默认配置运行成功，可能会需要一些环境依赖，例如PV。

- 所以我们需要自定义chart配置选项，安装过程中有两种方法可以传递配置数据：

- - ①`--values`（或`-f`）：指定带有覆盖的YAML文件。这里可以多次指定，最右边的文件优先。

- - ②`--set`：在命令行上指定替代。如果两种都用，那么`--set`的优先级高。



### 5.2.2 --values的使用（不推荐，太麻烦）



安装可能报错，需要自己手动安装PV。



- 先将修改的变量写到一个文件中，并修改此文件。



```shell
helm show values stable/mysql > config.yaml
```



- 查看这个文件：



```shell
cat config.yaml
```



```yaml
-- 修改部分
persistence:
  enabled: true
  accessMode: ReadWriteOnce
  size: 8Gi

mysqlUser: "k8s"
mysqlPassword: "123456"
mysqlDatabase: "k8s"
```



- 使用--values来替换默认的配置：



```shell
helm install -f config.yaml self-mysql stable/mysql
```



### 5.2.3 命令行替代变量（推荐）



- 可以使用命令行替代变量：



```shell
helm install db --set persistence.storageClass="managed-nfs-storage" stable/mysql
```



# 6 构建一个Helm Chart



## 6.1 开发步骤



- ①使用helm create创建chart：



```shell
helm create chart名称
```



- ②进入自定义chart目录的templates目录中，修改其中的deployment.yaml和service.yaml等文件。



```shell
cd chart名称/templates
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375292021-79f1bfea-4aa4-4040-b310-9d91d703012d.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- ③通过刚才创建的chart目录，将其部署：



```shell
helm install xxx chart名称
```



- ④打包chart，让别人共享：



```shell
helm package chart名称
```



## 6.2 应用示例



- 示例：本人是在root目录下操作的

- 创建chart：



```shell
helm create nginx
```



![创建一个Helm Chart应用示例之创建Chart](images/创建一个Helm Chart应用示例之创建Chart.png)



- 进入chart目录，修改values.yaml文件，内容如下：



```shell
cd nginx
```



```shell
vim values.yaml
```



```yaml
replicas: 3
image: nginx 
tag: 1.17 
serviceport: 80 
targetport: 80 
label: nginx
```



- 进入templates目录：



```shell
cd emplates/
```



![创建一个Helm Chart应用示例之进入templates目录](images/创建一个Helm Chart应用示例之进入templates目录.png)



- 删除templates目录中的所有文件和文件夹：



```shell
rm -rf *
```



- 修改deployment.yaml文件，内容如下：



```shell
vim deployment.yaml
```



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: {{ .Values.label }} 
  name: {{ .Release.Name }}
spec:
  replicas: {{ .Values.replicas }} 
  selector:
    matchLabels:
      app: {{ .Values.label }} 
  strategy: {}
  template:
    metadata:
      labels:
        app: {{ .Values.label }} 
    spec:
      containers:
      - image: {{ .Values.image }}:{{ .Values.tag }} 
        name: {{ .Values.image }}
```



- 修改service.yaml文件，内容如下：



```shell
vim service.yaml
```



```yaml
apiVersion:   v1
kind: Service
metadata:
  labels:
    app: {{ .Values.label }} 
  name:  {{ .Release.Name }}
spec: 
  ports:
    -  port: {{ .Values.serviceport }} 
       protocol: TCP
       targetPort: {{ .Values.targetport }} 
  selector:
    app: {{ .Values.label }} 
  type: NodePort
```



- 切换到chart目录的上一层目录：



```shell
cd ~
```



- 安装应用：



```shell
helm install nginx nginx/
```



## 6.3 调试



- Helm也提供了`--dry-run`和`--debug`调试参数，帮助你验证模板的正确性。在执行helm install的时候带上这两个参数就可以把对应的values值和渲染的资源清单打印出来，而不是真正的做部署一个release。

- 示例：



```shell
helm install nginx nginx/ --dry-run --debug
```



## 6.4 内置对象



- 上面我们使用的`{{ .Release.Name }}`将release的名称插入到模板中，这里的Release就是Helm的内置对象，下面是一些常用的内置对象：

| 内置对象          | 描述                             |
| ----------------- | -------------------------------- |
| Release.Name      | release的名称                    |
| Release.Namespace | release的命名空间                |
| Release.Service   | release的服务的名称              |
| Release.Revision  | release的修订版本号，从1开始累加 |



## 6.5 Values



- Values对象是为Chart模板提供值，这个对象的值有4个来源：

- - chart包中的values.yaml文件。

- - 父chart包的values.yaml文件。

- - 通过`helm install`或者`helm upgrade`的`-f`或者`--values`参数传入的自定义的yaml文件。

- - 通过`--set`参数传入的值。

- Chart的values.yaml提供的值可以被用户提供的values文件覆盖，而该文件同样可以被`--set参`数所覆盖，换言之，`--set`参数的优先级高。



## 6.6 升级、回滚和删除



### 6.6.1 升级



- 发布新版本的chart时，或者当我们需要更改发布的配置，可以使用`helm upgrade`命令：



```shell
helm upgrade --set imageTag=1.18 nginx nginx
```



```plain
helm upgrade -f values.yaml nginx nginx
```



### 6.6.2 回滚



- 如果在发布后没有达到预期的效果，则可以使用`helm rollback`回滚到之前的版本：



```shell
helm rollback nginx 1
```



### 6.6.3 卸载发行版本



- 卸载发行版本，可以使用`helm uninstall`命令：



```shell
helm uninstall nginx
```



### 6.6.4 查看历史版本配置信息



- 查看历史版本配置信息：



```shell
helm get all --revision 1 nginx
```



## 6.7 管道和函数



### 6.7.1 管道



- 在上面的案例中，其实是将值传递给模板引擎进行渲染，模板引擎还支持对拿到的数据进行二次处理。

- 示例：从`.Values`中读取的值变成字符串，可以使用quote函数实现。



```shell
vi templates/deployment.yaml
```



```yaml
# 略
app: {{ quote  .Values.label.app    }}
# 略
```



```shell
helm install --dry-run nginx ../nginx/ app:"nginx"
```



quote  .Values.label.app将后面的值作为参数传递过quote函数。

模板函数调用语法为：functionName arg1 arg2...



### 6.7.2 default函数



- default函数运行在模板中指定默认值，以防止该值会忽略掉。如果忘记定义，执行`helm install`的时候会因为缺少字段而无法创建资源，这时就可以定义一个默认值了。

- 示例：



```shell
vim templates/deployment.yaml
```



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: {{ .Values.label }}
  name: {{ .Release.Name }}
spec:
  replicas: {{ .Values.replicas }}
  selector:
    matchLabels:
      app: {{ .Values.label }}
  strategy: {}
  template:
    metadata:
      labels:
        app: {{ .Values.label }}
    spec:
      containers:
      - image: {{ .Values.image| default "nginx" }}:{{ .Values.tag }}
        name: {{ .Values.image }}
```



### 6.7.3 其他函数



- 缩进函数：



```yaml
{{ .Values.resources | indent 12 }}
```



- 大写：



```yaml
{{ upper .Values.resources }}
```



- 首字母大写：



```yaml
{{ title .Values.resources }}
```



# 7 流程控制



## 7.1 概述



- 流程控制是为模板提供了一种能力，满足更复杂的数据逻辑处理。

- Helm模板语言提供以下流程控制语句：

- - if/else条件块。

- - with指定范围。

- - range循环块。



## 7.2 if/else



- if/else块是用于在模板有条件的包含文本块的方法，条件块的基本结构如下：



```shell
{{ if 条件表达式}}
# xxx
{{ else if 条件表达式}}
# xxx
{{ else }}
# xxx
{{ end }}
```



- 条件判断：就是判断条件是否为真，如果值为以下几种情况则为false，否则为true：

- - 一个布尔类型的false。

- - 一个数字0。

- - 一个空的字符串。

- - 一个空的集合（map、slice、tuple、dict、array）。

- 示例：



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: {{ .Values.label }}
  name: {{ .Release.Name }}
spec:
  replicas: {{ .Values.replicas }}
  selector:
    matchLabels:
      app: {{ .Values.label }}
  strategy: {}
  template:
    metadata:
      labels:
        app: {{ .Values.label }}
    spec:
      containers:
        - image: {{ .Values.image| default "nginx" }}:{{ .Values.tag }}
          name: {{ .Values.image }}
          env:
            {{ if eq .Values.devops "k8s" }}
          - name : hello
            value: "123"
            {{ else }}
          - name : hello
            value: "456"
            {{ end }}
```



还可以使用ne、lt、gt、and、or等运算符。



- 通过模板引擎渲染一下，会得到如下的结果：



```shell
helm install --dry-run nginx nginx
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375347412-aa0bc802-8108-4196-8264-57acc321d381.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 可以看到渲染出来会有多余的空行，这是因为当模板引擎运行的时候，会将控制指令删除，所以之前占的位置也就空白了，需要使用`{{- if ...}}`的方式消除此空行



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: {{ .Values.label }}
  name: {{ .Release.Name }}
spec:
  replicas: {{ .Values.replicas }}
  selector:
    matchLabels:
      app: {{ .Values.label }}
  strategy: {}
  template:
    metadata:
      labels:
        app: {{ .Values.label }}
    spec:
      containers:
        - image: {{ .Values.image| default "nginx" }}:{{ .Values.tag }}
          name: {{ .Values.image }}
          env:
            {{- if eq .Values.devops "k8s" }}
          - name : hello
            value: "123"
            {{- else }}
          - name : hello
            value: "456"
            {{- end }}
```



- 通过模板引擎渲染一下，会得到如下的结果：



```shell
helm install --dry-run nginx nginx
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375361603-83ab5bf3-6e95-4f8f-ae47-d7af71b07d33.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 如果使用了`{{- if ... -}}`那么就需要谨慎了，比如：



```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: {{ .Values.label }}
  name: {{ .Release.Name }}
spec:
  replicas: {{ .Values.replicas }}
  selector:
    matchLabels:
      app: {{ .Values.label }}
  strategy: {}
  template:
    metadata:
      labels:
        app: {{ .Values.label }}
    spec:
      containers:
        - image: {{ .Values.image| default "nginx" }}:{{ .Values.tag }}
          name: {{ .Values.image }}
          env:
            {{- if eq .Values.devops "k8s" -}}
          - name : hello
            value: "123"
            {{- else }}
          - name : hello
            value: "456"
            {{- end }}
```



- 通过模板引擎渲染一下，会得到如下的结果：



```shell
helm install --dry-run nginx nginx
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610375373939-97702852-a584-45a8-bc1c-eef58d49927f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_31%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 相当于下面这种格式：



```yaml
NAME: nginx
LAST DEPLOYED: Mon Jan 11 20:46:10 2021
NAMESPACE: default
STATUS: pending-install
REVISION: 1
TEST SUITE: None
HOOKS:
MANIFEST:
---
# Source: nginx/templates/service.yaml
apiVersion:   v1
kind: Service
metadata:
  labels:
    app: nginx 
  name:  nginx
spec: 
  ports:
    -  port: 80 
       protocol: TCP
       targetPort: 80 
  selector:
    app: nginx 
  type: NodePort
---
# Source: nginx/templates/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: nginx
  name: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  strategy: {}
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
        - image: nginx:1.17
          name: nginx
          env: - name: hello value: 123
```



- 当然不对，因为`{{- if ... -}}`删除了双方的换行符。



## 7.3 range



- 在Helm模板语言中，使用`range`关键字来进行循环操作。

- 示例：

- 在values.yaml中添加一个变量列表



```yaml
test:
  - 1 
  - 2 
  - 3
```



- 循环打印该列表：



```yaml
apiVersion: v1 
kind: ConfigMap 
metadata:
  name:    {{   .Release.Name    }} 
data:
  test:    
    {{- range . Values.test }}
      {{   .    }}
    {{-   end   }}
```



## 7.4 with



- with可以用来控制变量作用域。

- 在前面我们使用`{{ .Release.xxx }}`或者`{{ .Values.xxx }}`，其中`.`就是表示对当前范围的引用，`.values`就是告诉模板在当前范围中查找`Values`对象的值。

- with语句就可以用来控制变量的作用域范围，其语法和一个简单的if语句类似：



```shell
{{ with 条件表达式 }}
# xxx
{{ end }}
```



- with语句可以允许将当前范围的`.`设置为特定的对象，比如我们前面一直使用的`.Values.label`，我们可以使用`with`来将`.`范围指向`.Values.label`。

- 示例：

- values.yaml



```yaml
nodeSelector: 
  team: a 
  gpu:   yes
```



- deployment.yaml



```yaml
apiVersion:   apps/v1
kind:   Deployment 
metadata:
  name: {{ .Release.Name }}-deployment 
spec:
  replicas: 1 
  selector:
    matchLabels: 
      app: nginx
  template: 
    metadata:
      labels: 
        app: nginx
    spec:
      {{- with .Values.nodeSelector }}
      nodeSelector:
        team: {{  .team }}
        gpu: {{  .gpu  }}
      {{- end }}
```



## 7.5 命名模板



- 需要复用代码的地方可以使用命名模板。

- 命名模板：使用define定义，template引入，在templates目录中默认下划线开头的文件为公共模板（比如`_helpers.tpl`）。

- 示例：

- _helpers.tpl：



```yaml
{{-   define   "demo.fullname"   -}}
{{- .Chart.Name -}}-{{ .Release.Name }}
{{-   end   -}}
```



- deployment.yaml



```yaml
apiVersion:   apps/v1 
kind:   Deployment 
metadata:
  name:  {{   template"demo.fullname"   .    }}
# 其他略
```



template指令是将一个模板包含在另一个模板中的方法。但是，template函数不能用于Go模板管道，为了解决该问题，增加了include功能。



- _helpers.tpl：



```yaml
{{-   define   "demo.labels"    -}}
app:   {{   template"demo.fullname"   .   }}
chart:   "{{   .Chart.Name   }}-{{    .Chart.Version   }}" 
release:   "{{   .Release.Name   }}"
{{-   end   -}}
```



- deployment.yaml



```yaml
apiVersion:   apps/v1
kind:   Deployment 
metadata:
  name:    {{   include"demo.fullname"   .    }} 
  labels:
    {{-   include   "demo.labels"   .    |   nindent   4    }}
...
```



上面包含一个名为demo.labels的模板，然后将值  . 传递给模板，最后将该模板的 输出传递给nindent函数。



# 8 开发自己的chart



- 创建模板。

- 修改Chart.yaml，Values.yaml，添加常用的变量。

- 在templates目录下创建部署镜像所需要的yaml文件，并使用变量引用yaml文件里面经常变动的字段。
# 搭建DashBoard

# 1 概述

- 之前在kubernetes中完成的所有操作都是通过命令行工具kubectl完成的。其实，为了提供更丰富的用户体验，kubernetes还开发了一个基于web的用户界面（DashBoard）。用户可以使用DashBoard部署容器化的应用，而且还可以监控应用的状态，执行故障排查以及管理kubernetes中的各种资源。



# 2 部署DashBoard



## 2.1 下载yaml，并运行DashBoard



- 下载yaml（网络不行，请点这里[📎recommended.yaml](https://www.yuque.com/attachments/yuque/0/2021/yaml/513185/1610074789462-62fcedfc-3d50-42f8-b0dc-1a0ad889de63.yaml)）：



```yaml
wget https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml
```



- 修改kubernetes-dashboard的Service类型



```shell
vim recommended.yaml
```



```yaml
kind: Service
apiVersion: v1
metadata:
  labels:
    k8s-app: kubernetes-dashboard
  name: kubernetes-dashboard
  namespace: kubernetes-dashboard
spec:
  type: NodePort # 新增
  ports:
    - port: 443
      targetPort: 8443
      nodePort: 30009 # 新增
  selector:
    k8s-app: kubernetes-dashboard
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074805832-af197ffa-41e2-48d7-99c5-706c5120460f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 部署DashBoard：



```shell
kubectl create -f recommended.yaml
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074822296-37e40474-7725-4710-a62b-5bbbf671914c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 查看namespace为kubernetes-dashboard下的资源：



```shell
kubectl get pod,svc -n kubernetes-dashboard
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074835429-c8e41260-09bb-4ade-88ae-f3118aba2098.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 2.2 创建账户，获取token



- 创建账户：



```shell
kubectl create serviceaccount dashboard-admin -n kubernetes-dashboard
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074846489-72326696-f145-4280-9297-19f392d81df4.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 授权：



```plain
kubectl create clusterrolebinding dashboard-admin-rb --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:dashboard-admin
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074857715-e7937cdc-6f07-443d-a97f-ef8dbfc4a35f.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 获取账号token：



```shell
kubectl get secrets -n kubernetes-dashboard | grep dashboard-admin
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074872989-519a33c5-e544-4271-a576-b18dcef34891.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



```shell
kubectl describe secrets dashboard-admin-token-b992l -n kubernetes-dashboard
```



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074885859-afdeaabf-255c-4373-9b2c-0d1792a3765c.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_25%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



## 2.3 通过浏览器访问DashBoard的UI



- 在登录页面上输入上面的token，访问地址为https://192.168.18.100:30009/#/login：



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074900379-20661743-2dbc-40cd-86e8-d19ce778ffcb.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_33%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)



- 出现下面的页面代表成功部署DashBoard：



![img](https://cdn.nlark.com/yuque/0/2021/png/513185/1610074913273-f726e410-c6ad-4295-8d6c-9693ea6025e7.png?x-oss-process=image%2Fwatermark%2Ctype_d3F5LW1pY3JvaGVp%2Csize_33%2Ctext_6K645aSn5LuZ%2Ccolor_FFFFFF%2Cshadow_50%2Ct_80%2Cg_se%2Cx_10%2Cy_10)
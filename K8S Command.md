K8S Command

查看资源类型为pod的可配置项

```nginx
kubectl explain pod
```

查看资源类型为Pod的metadata的属性的可配置项

```shell
kubectl explain pod.metadata
```

查看pod.spec.containers的可选配置项

```shell
kubectl explain pod.spec.containers
```

创建Pod

```
kubectl apply -f pod-base.yaml
```

查看Pod状况

```
kubectl get pod -n dev
```

通过describe查看内部的详情：

```
kubectl describe pod pod-base -n dev
```
## Spring Cloud Stream Sample App

Build Image:

```
mvn clean install
docker build -t scs-service-bus:1.0.0  .
```

## Kubectl Commands

```
kubectl create ns test-azure
kubectl apply -f k8s -n test-azure
kubectl delete -f k8s -n test-azure
kubectl delete ns test-azure
```
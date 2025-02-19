# Запуск в кластере k8s

Запуск в локальном кластере k8s:

Запуск [Kind](https://kind.sigs.k8s.io/):

```shell
$ kind create cluster --config kind.yml
$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
$ echo "127.0.0.1    todo-list.local" | sudo tee -a /etc/hosts

```

Запуск [Minikube](https://minikube.sigs.k8s.io/):

```shell
$ minikube start --vm-driver=hyperkit --memory=8G --cpus=3
$ minikube addons enable ingress
$ echo "$(minikube ip)    todo-list.local" | sudo tee -a /etc/hosts
```

Деплой сервисов TODO list:

```shell
$ helm repo add romanow https://romanow.github.io/helm-charts/
$ helm search repo romanow

$ kubectl create secret generic credentials \
    --from-literal=auth0-client-id=<client-id> \
    --from-literal=auth0-client-secret=<client-secret>

$ helm install postgres -f postgres/values.yaml romanow/postgres
$ helm install backend-todo-list -f todo-list/backend.yaml romanow/java-service
$ helm install frontend-todo-list -f todo-list/frontend.yaml romanow/frontend --set ingres.domain=ru
```

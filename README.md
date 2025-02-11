# TODO list backed

[![CI](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)
[![Release](https://img.shields.io/github/v/release/Romanow/backend-todo-list?logo=github&sort=semver)](https://github.com/Romanow/backend-todo-list/releases/latest)
[![Docker Pulls](https://img.shields.io/docker/pulls/romanowalex/backend-todo-list?logo=docker)](https://hub.docker.com/r/romanowalex/backend-todo-list)
[![License](https://img.shields.io/github/license/Romanow/backend-todo-list)](https://github.com/Romanow/backend-todo-list/blob/main/LICENSE)

## Настройка авторизации

### Google

Зайти в [Google Cloud Platform](https://console.cloud.google.com/):

1. `Select a project` -> `New Project`: TODO List.
2. `APIs & Services` -> `Credentials` -> `Create Credentials` -> `Create OAuth client ID` -> `Web Application` ->
   name: `TODO list OAuth Provider`.
3. Authorized redirect URIs -> `http://localhost:8080/login/oauth2/code/google`.
4. Берем `Client ID` и `Client Secret` и добавляем их в secret.

## Сборка

### Локальный запуск

```shell
$ ./graldew clean build
$ docker compose \
    -f docker-compose.yml \
    -f docker-compose.frontend.yml \
    up -d --wait

$ echo "127.0.0.1    todo-list.local" | sudo tee -a /etc/hosts
```

### Запуск в локальном кластере k8s

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

```shell
$ helm repo add romanow https://romanow.github.io/helm-charts/
$ helm search repo romanow

$ kubectl create secret generic credentials \
    --from-literal=google-client-id=<client-id> \
    --from-literal=google-client-secret=<client-secret>

$ helm install postgres -f postgres/values.yaml romanow/postgres
$ helm install backend-todo-list -f todo-list/backend.yaml romanow/java-service
$ helm install frontend-todo-list -f todo-list/frontend.yaml romanow/frontend --set ingres.domain=ru

```

## Тестирование

### Локальный запуск UI тестов

```shell
$ brew install --cast chromedriver
$ docker compose \
  -f docker-compose.yml \
  -f docker-compose.frontend.yml \
  up -d --wait

$ ./gradlew selenide

```

### Запуск UI тестов в k8s

```shell
$ minikube start --cpus=4 --memory=8G --driver=hyperkit
$ minikube addons enable ingress

$ helm repo add aerokube https://charts.aerokube.com/
$ helm search repo romanow
$ helm upgrade --install moon aerokube/moon2 --values moon/values.yaml --set ingress.host=moon.local

$ echo "$(minikube ip)    moon.local" | sudo tee -a /etc/hosts
```

### Интеграционное тестирование

```shell
$ newman run -e kind-environment.json collection.json

```

### Нагрузочное тестирование

```shell
$ helm install prometheus-stack prometheus-community/kube-prometheus-stack -f prometheus-stack/backend.yaml
$ kubectl apply -f prometheus-stack/todo-list.yml
$ echo "$(minikube ip)    grafana.local" | sudo tee -a /etc/hosts

$ brew install k6
$ K6_WEB_DASHBOARD=true K6_WEB_DASHBOARD_EXPORT=report.html k6 run \
    --out influxdb=http://localhost:32086/k6 \
    -e HOSTNAME=todo-list.local \
    -e USERNAME=ronin@romanow-alex.ru \
    -e PASSWORD=Qwerty123 \
    -e CLIENT_ID=7uHBa1xYenYPjX7UhOonuGhOWvxLUwYM \
    -e CLIENT_SECRET=TbNIL8SJx38sDDweRqWsRaqWKU7Q6UrWr0f6DzKwlLh48892GE4KfoKR1cfIe87e \
    k6-load.js

```

# TODO list backed

[![Build project](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml)

## Создание авторизации в Google

Зайти в [Google Cloud Platform](https://console.cloud.google.com/):

1. `Select a project` -> `New Project`: TODO List.
2. `APIs & Services` -> `Credentials` -> `Create Credentials` -> `Create OAuth client ID` -> `Web Application` ->
   name: `TODO list OAuth Provider`.
3. Authorized redirect URIs -> `http://localhost:8080/login/oauth2/code/google`.
4. Берем `Client ID` и `Client Secret` и добавляем их в secret.

### Локальный запуск

```shell
# сборка
$ ./graldew clean build
# запуск Postgres 13 в docker
$ docker compose up postgres -d
# локальный запуск
$ ./gradlew bootRun --args='--spring.profiles.active=local'   
```

### Deploy to k8s

```shell
$ kind create cluster --config kind.yml

$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

$ helm repo add romanow https://romanow.github.io/helm-charts/
$ helm search romanow

$ kind load docker-image postgres:15
$ helm install postgres -f postgres/values.yaml romanow/postgres

$ kubectl create secret generic credentials \
    --from-literal=google-client-id=<client-id> \
    --from-literal=google-client-secret=<client-secret>

$ kind load docker-image romanowalex/backend-todo-list:v2.0
$ helm install backend-todo-list -f backend/values.yaml romanow/java-service

# (опционально) устанавливаем frontend
$ kind load docker-image romanowalex/frontend-todo-list:v2.0
$ helm install frontend-todo-list -f frontend/values.yaml romanow/frontend --set ingress.domain=ru

$ echo "127.0.0.1        todo-list.ru" | sudo tee -a /etc/hosts
```

## Интеграционное тестирование

```shell
$ newman run -e kind-environment.json collection.json
```

## Нагрузочное тестирование

```shell
# устанавливаем prometheus + grafana и kube-state-metrics для мониторинга состояния кластера
$ kind load docker-image registry.k8s.io/kube-state-metrics/kube-state-metrics:v2.6.0
$ helm install kube-state-metrics kube-state-metrics-chart/

$ kind load docker-image prom/prometheus:v2.40.0
$ helm install prometheus prometheus-chart/ 

$ kind load docker-image grafana/grafana:8.3.4
$ helm install grafana grafana-chart/ --set domain=grafana.local 

$ kind load docker-image influxdb:1.8.4
$ helm install influxdb influxdb-chart/ 

$ brew install k6
$ k6 run \
    --out influxdb=http://localhost:32086/k6 \
    -e HOSTNAME=todo-list.ru \
    -e USERNAME=ronin@romanow-alex.ru \
    -e PASSWORD=Qwerty123 \
    -e CLIENT_ID=7uHBa1xYenYPjX7UhOonuGhOWvxLUwYM \
    -e CLIENT_SECRET=TbNIL8SJx38sDDweRqWsRaqWKU7Q6UrWr0f6DzKwlLh48892GE4KfoKR1cfIe87e \
    k6-load.js
```
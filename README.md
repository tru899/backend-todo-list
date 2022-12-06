# TODO list backed

[![Build project](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml)

## Создание авторизации в Google

1. Зайти в Google Cloud Platform -> Select a project -> New Project: TODO List.
2. APIs & Services -> Credentials -> Create Credentials -> Create OAuth client ID -> Web Application ->
   name: `TODO list OAuth Provider`.
3. Authorized redirect URIs -> .
4. Берем `Client ID` и `Client Secret` и добавляем их в secret.

```shell
kubectl create secret generic credentials \
  --from-literal=google-client-id=<client-id> \
  --from-literal=google-client-secret=<client-secret>
```

### Локальный запуск

```shell
# сборка
$ ./graldew clean build
# запуск PostgreSQL 13 в docker
$ docker compose up postgres -d
# локальный запуск
$ ./gradlew bootRun --args='--spring.profiles.active=local'   
```

### Deploy to k8s

```shell
$ helm install postgres k8s/postrges-chart

$ helm install backend-todo-list k8s/service-chart
```
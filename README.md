# TODO List

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

## Сборка, запуск и деплой

```shell
# сборка и прогон тестов
$ ./gradlew clean build

# запуск Postgres:13 для локального запуска приложения
$ docker compose up -d

# запуск с локальным профилем
$ ./gradlew bootRun --args='--spring.profiles.active=local'

# деплой postgres и backend в кластер Kubernetes с помощью helm-чартов
$ cd deployment
$ helm install postgres postgres/
$ helm install backend-todo-list backend/
```

### Если используется приватный Docker Registry

```shell
kubectl create secret docker-registry \
  --docker-server=nexus.edu.inno.tech \
  --docker-username=<username> \
  --docker-password=<password> \
  --docker-email=unused \
  private-registry
```

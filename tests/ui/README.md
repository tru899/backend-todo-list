# UI тесты

Локальный запуск UI тестов:

```shell
$ brew install --cast chromedriver
$ docker compose \
  -f docker-compose.yml \
  -f docker-compose.frontend.yml \
  up -d --wait

$ ./gradlew selenide

```

Запуск UI тестов в k8s:

```shell
$ minikube start --cpus=4 --memory=8G --driver=hyperkit
$ minikube addons enable ingress

$ helm repo add aerokube https://charts.aerokube.com/
$ helm search repo romanow
$ helm upgrade --install moon aerokube/moon2 --values moon/values.yaml --set ingress.host=moon.local

$ echo "$(minikube ip)    moon.local" | sudo tee -a /etc/hosts
```

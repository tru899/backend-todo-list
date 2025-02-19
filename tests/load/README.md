# Нагрузочные тесты

```shell
$ helm install prometheus-stack prometheus-community/kube-prometheus-stack -f prometheus-stack/backend.yaml
$ kubectl apply -f prometheus-stack/todo-list.yml
$ echo "$(minikube ip)    grafana.local" | sudo tee -a /etc/hosts

# экспорт данных в influx: --out influxdb=http://localhost:32086/k6
$ brew install k6
$ K6_WEB_DASHBOARD=true K6_WEB_DASHBOARD_EXPORT=report.html k6 run \
    -e HOSTNAME=todo-list.local \
    -e USERNAME=ronin@romanow-alex.ru \
    -e PASSWORD=Qwerty123 \
    -e CLIENT_ID=7uHBa1xYenYPjX7UhOonuGhOWvxLUwYM \
    -e CLIENT_SECRET=TbNIL8SJx38sDDweRqWsRaqWKU7Q6UrWr0f6DzKwlLh48892GE4KfoKR1cfIe87e \
    k6-load.js
```

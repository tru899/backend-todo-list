#!/usr/bin/env bash

version=${1:-v2.0}

images=(
  "postgres:13"
  "romanowalex/backend-todo-list:$version"
  "influxdb:2.5.0"
  "grafana/grafana:8.3.4"
  "prom/prometheus:v2.6.0"
  "registry.k8s.io/kube-state-metrics/kube-state-metrics:v2.6.0"
)

for image in "${images[@]}"; do
  kind load docker-image "$image"
done

[![CI](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)
[![Release](https://img.shields.io/github/v/release/Romanow/backend-todo-list?logo=github&sort=semver)](https://github.com/Romanow/backend-todo-list/releases/latest)
[![Docker Pulls](https://img.shields.io/docker/pulls/romanowalex/backend-todo-list?logo=docker)](https://hub.docker.com/r/romanowalex/backend-todo-list)
[![License](https://img.shields.io/github/license/Romanow/backend-todo-list)](https://github.com/Romanow/backend-todo-list/blob/main/LICENSE)

# TODO list backed

GitHub: [romanow/backend-todo-list](https://github.com/Romanow/backend-todo-list).

## Локальный запуск

Используем [docker-compose.yml](docker-compose.yml)

```shell
$ ./graldew clean build
$ docker compose \
    -f docker-compose.yml \
    -f docker-compose.frontend.yml \
    up -d --wait

$ echo "127.0.0.1    todo-list.local" | sudo tee -a /etc/hosts
```

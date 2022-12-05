# TODO list backed service

[![Build project](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/backend-todo-list/actions/workflows/build.yml)

```shell
# сборка
$ ./graldew clean build
# запуск PostgreSQL 13 в docker
$ docker compose up postgres -d
# локальный запуск
$ ./gradlew bootRun --args='--spring.profiles.active=local'   
```
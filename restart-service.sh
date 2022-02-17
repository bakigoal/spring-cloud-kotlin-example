#!/bin/bash

function rebuild_service {
  service=$1
  echo restarting "$service"
  app=spring-cloud-kotlin-example

  docker stop "$app-$service-1"
  docker image rm -f $app"_$service"
}

services=("${@:1}")
for service in "${services[@]}"
do
  rebuild_service "$service"
done

./gradlew clean bootJar
docker-compose up -d
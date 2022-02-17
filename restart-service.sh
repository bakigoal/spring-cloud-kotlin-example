#!/bin/bash

services=("${@:1}")
app="spring-cloud-kotlin-example"

for service in "${services[@]}"
do
  docker_container="$app-$service-1"
  docker_image=$app"_$service"

  echo "stopping container $docker_container"
  docker stop "$docker_container"
  echo
  echo "removing image $docker_image"
  docker image rm -f "$docker_image"
  echo
done

echo "building services"
./gradlew clean bootJar
echo

echo "restarting docker-compose"
docker-compose up -d
# spring-cloud-kotlin-example

Used Tech Stack:
- Kotlin
- Spring Boot
- Spring Cloud


### RUN SERVICES
1) Build services: from the root folder run the following shell command
```shell
./gradlew clean bootJar
```

2) Deploy docker-compose:
```shell
docker-compose -d up
```

3) Stop and remove images:
```shell
docker-compose down --rmi all
```

### Debug

Edit Configurations -> New -> Remote JVM Debug

(Must match with docker-compose 

`-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005`)
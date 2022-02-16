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

### Keycloak

1) create realm
2) register a client app (openid-connect)
   - Access Type: Confidential
   - Service Accounts Enabled: On
   - Authorization Enabled: On
   - Valid Redirect URLs: http://localhost:80*
   - Web Origins:*
3) Setup Roles (admin, user)   
4) Add Users
5) Copy the token endpoint (Realm settings -> OpenId Endpoint Conf)
```shell
POST http://keycloak:8080/auth/realms/baki-realm/protocol/openid-connect/token
Basic Auth: (client ID / client credentials)
Body:
- grant_type = password
- username
- password
```
The JSON payload contains five attributes:
- access_token
- token_type
- refresh_token
- expires_in
- scope
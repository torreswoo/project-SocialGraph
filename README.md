# Social Graph
- ZEPL : https://github.com/ZEPL/data-engineering-challenge/blob/master/Social-Graphs.md
- SpringBoot Application
- Gradle project
- Redis

### Introduction
A social graph consists of a set of users, and a set of relationships between two users.

### Problem
We want to implement a social graph such that we can do the following action quickly and efficiently as possible:
Suppose we have two users: UserA, and UserB. 
- Find all first degree connections of UserA (my direct friends). i.e.
- Find if UserA and UserB are 1st degree connected (direct friends).
- Find if UserA and UserB are 2nd degree connected (friends of friends).
- Find if UserA and UserB are 3rd degree connected (friends of friends of friends).

## Start project
- use Redis (v4.0.6) - insert data
- build, run
```
$ ./gradlew build
$ ./gradlew bootrun
```

## demo
- insert sample data
```
# Richard - Anthony
GET http://localhost:8080/v1/insertFriend?userA=Richard&userB=Anthony

# Zuny - Mina - Marc - Torres
GET http://localhost:8080/v1/insertFriend?userA=Zuny&userB=Mina
GET http://localhost:8080/v1/insertFriend?userA=Mina&userB=Marc
GET http://localhost:8080/v1/insertFriend?userA=Marc&userB=Torres
```
- check friend degree
```
GET http://localhost:8080/v1/friendDegree?userA=Zuny&userB=Mina
{
  "userA": "Zuny",
  "userB": "Mina",
  "degree": 1
}
```

## dashboard
```
- http://localhost:8080/          # Swagger
- http://localhost:8080/dashboard # App Dashboard for JVM monitoring
```

---
## Library
| name | version  | download link |
| --- | --- | --- |
| Redis | v4.0.6 | https://redis.io/ |
| SpringBoot | v1.5.9 | https://projects.spring.io/spring-boot/ |
| Lombok | v1.16.8 | https://projectlombok.org/ |
| springfox | v2.6.0 | http://springfox.github.io/springfox/ |
| springdata-redis | | |




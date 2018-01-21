# Social Graph
- ZEPL : https://github.com/ZEPL/data-engineering-challenge/blob/master/Social-Graphs.md
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
- build, run
```
$ ./gradlew build
$ ./gradlew bootrun
```
## dashboard
```
- http://localhost:8080/          # Swagger
- http://localhost:8080/dashboard # App Dashboard for JVM monitoring
- http://localhost:8080/intro     # intro page (Thymeleaf)
```

---
## Library
| name | version  | download link |
| --- | --- | --- |
| SpringBoot | v1.5.9 | https://projects.spring.io/spring-boot/ |
| Lombok | v1.16.8 | https://projectlombok.org/ |
| springfox | v2.6.0 | http://springfox.github.io/springfox/ |




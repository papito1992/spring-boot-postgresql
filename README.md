## Run Spring Boot application
```
mvn spring-boot:run
```
By default this app uses Dev profile which runs an in memory postgresql db.
---
To generate table go to application.yml and comment out specified code to create tables locally.
---
To run locally you must have a postgresql server running, in application.properties specify configuration to match it, like username, password...


If you are having troubles signing in or token for pre created user has expired dont worry, there is signup api , use that for new user creation!
body example for api/auth/signup
------
{
    "username": "megaUser1",
    "role": [],
    "password": "megaUser1"
}



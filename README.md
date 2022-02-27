[![CircleCI](https://circleci.com/gh/umutyildiz1/creditsystem/tree/main.svg?style=svg&circle-token=0dde66f2fab619d576d4e541e25c91d011ad7c8f)](https://circleci.com/gh/umutyildiz1/creditsystem/tree/main)
# creditsystem
Credit System Application for Paycore using Spring Boot

# Front End Application 
Please take a look front end repo 
https://github.com/umutyildiz1/credit-system-ui

# EER DİAGRAM

![er](https://user-images.githubusercontent.com/56760679/155882973-4c93e1ad-5d4a-4efa-a28e-51a579083578.png)

## Models

![schemas](https://user-images.githubusercontent.com/56760679/155882990-89018363-70d4-4ce0-9919-7fbd8266fee9.png)

## API ENDPOINTS
![User](https://user-images.githubusercontent.com/56760679/155883026-bf5c3328-b865-459e-a32f-7ca3f673f9a2.png)
![credit](https://user-images.githubusercontent.com/56760679/155883027-7ffb0972-abbc-4086-af8d-9d4f83ef1db3.png)
![customer](https://user-images.githubusercontent.com/56760679/155883030-a941f3a6-ced4-47c2-a84d-ac556e75054a.png)

## Run App
### For Local
* Postgres default port : 5433
* Spring Boot default port : 8080
* Run Spring Boot App: mvn spring-boot:run
* Package app : maven package

### For Docker Image
* Please change the postgres port from pom.xml
* Build as Docker image : docker build -t creditsystem-app:v1 .
* Run Docker image : docker run --name credit-system -p 8080:8080 creditsystem-app:v1

## Swagger Endpoints
* http://localhost:8080/swagger-ui/index.html#/
* http://localhost:8080/v3/api-docs

# Information about the project

## Used Technologies & Arhitecture & Methods:
* N-Layered Architecture : More useful for starting phase of a project than Microservice architecture
* Spring Boot 
* Spring Security : It enables to easier configuration for authentication and authorization
* Postgres : Open-source database
* JWT: It provides for project that project endpoints are secured
* Swagger : Endpoint and entity documentation
* JUnit and Mockito : They provide faster implementation of unit tests for developer

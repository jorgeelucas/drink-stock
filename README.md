
# drink-stock

Spring boot REST API for Stock.


## Architecture

* Maven
* Java 11
* Spring Boot
* Spring Data
* Swagger2
* jUnit4
* Mockito
* Hamcrest
* H2
* SonarQube

## Excecution

### Clone

```console
git clone https://github.com/jorgeelucas/drink-stock.git
cd drink-stock
```

### Build

Building the project with Maven, use the commands bellow:

```shell
mvn clean install
```

The command will download all dependencies the project needs and create a *target* diretory with artifacts, including the jar file from the project.
In addition, unit tests will be executed, and if any fail, Maven will display on the console.

### Excecution

To excecute the project with **Maven Spring Boot Plugin**, use the commands bellow:


```shell
mvn spring-boot:run
```

The command will run the project and it will be available on the **8080** server port;


### Tests

```console
mvn test
```

## Usage

### Main requests
> After project running you'll be able to access all endpoints through swagger documentation in http://localhost:8080/swagger-ui/

Drink types are separated as **ALCOHOLIC** and **NON_ALCOHOLIC** so when you need to informe the type (used as Category) you need to pass exactly one of these types in order to prevent an unexpected exception.

<details><summary><b>Body (Click here)</b></summary>

1. Book a new drink: **POST**::*/v1/drinks/book*

    ```json
      {
        "drinkName": string,
        "type": "ALCOHOLIC",
        "quantity":Integer,
        "section": {
	        "id": string
	    },
	    "createdby": string
      }
    ```
    
2. For getting the sections id you should get all sections : **GET**::http://localhost:8080/v1/sections

*for more endpoints informations you can use the `stock.postman_collection.json` file located in project root inporting it to postman will help manually testing endpoints*

</details>

## Details


1. For communication it was used REST architecture, RestFull based. The endpoints produce and consume JSON.

2. In the Architecture was used MVC with Controllers, Services and Repositories layers for data manipulation. It was chosen DTO for transfer data between client and server instead of transportating entities.

3. For database H2 in memory was chosen to persist and store data for App and for the tests.

4. Swagger was used to document the endpoints and better usage. After starting the project you can access http://localhost:8080/swagger-ui

5. For tests Junit4 was used, making integrated tests, and mockito to mock repositories for not to persist the tests data.

6. For responses I have chosen ResponseEntity to manage the entire response from the api.

7. Lombok is as a dependency library in order to reduce boilerplate code in POJO objects.

8. It was used Slf4j for logging to help tracking the application

## SonarQube

> For code quality was used sonarqube, this application runs sonarqube in a docker making this available on **9000** port. It was based on this internet sample: https://medium.com/trendyol-tech/spring-boot-2-2-6-code-quality-with-sonarqube-8-2-community-70a76634bf75

#### Technical requirements

- Docker –  [https://www.docker.com/get-started](https://www.docker.com/get-started)

#### Steps
1. Run SonarQube server

in project root run the following command:
 ```console
docker-compose up
```

2. Run `docker ps` and check if a server is up and running
3. Wait for the server to start and log in to SonarQube server on [http://localhost:9000](http://localhost:9000/) using default credentials: login: `admin` password: `admin`
4. Go to: [http://localhost:9000/account/security/](http://localhost:9000/account/security/) and generate a token.
5. Copy token value and save it somewhere, since you won’t be able to see it again! You will need it later in the tutorial.
6. Insert the token in `maven-clean-install-sonar.sh` file - replace the *{HERE_GOES_THE_TOKEN}*
7. Run this file `./maven-clean-install-sonar.sh` every time you want to register or reload the analysis.

>Thanks for the opportunity :)

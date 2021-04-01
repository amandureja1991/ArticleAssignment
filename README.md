# ArticleAssignment
Allows user to create,update,delete and get articles..

## Configuration
To change the configuration parameters, like number of words human can read per minute, you can open the application.yml file in the resources folder and change it there.

## Prerequisites

- Java 8+
- Docker
- Maven

## Installation

Make sure you are in the source code directoty.

Build the application by maven command which will create the jar file.
```sh
mvn clean install
```

Create docker image and run it:
```sh
docker build -f Dockerfile -t docker-article-assignment .
docker run -p 8085:8085 docker-article-assignment
```

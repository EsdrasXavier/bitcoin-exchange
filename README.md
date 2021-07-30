# Bitcoin Rate Converter

## Requirements

For building and running the application you need:

- [JDK 11](https://adoptopenjdk.net/)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.example.bitcoin.exchange.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Documentation

After running the application you can check the docs at the following URL: _http://localhost:8080/api/docs.html_

### Example
To use the API you can make a GET request to: _http://127.0.0.1:8080/api/bitcoin/history_, using the following request params:

| Param     |  Type   | Required |
|-----------|:-------:|---------:|
| currency  |  String |    Yes   |
| beginDate |  String |    Yes   |

Example:
```shell
> curl "http://127.0.0.1:8080/api/bitcoin/history?currency=eur&beginDate=2021-07-30"
> {"currency":"eur","currentPrice":33668.2462,"lowestPriceInTheLastThirtyDays":25295.6699,"highestPriceInTheLastThirtyDays":33789.7765}
```

## Running it from Docker

You can run this project using docker. The project has been published in the DockerHub, you can find the repository [here](https://hub.docker.com/repository/docker/esdrasxavier/bitcoin-exchange).

To run it from terminal execute:
```shell
docker run -it -p 8080:8080 esdrasxavier/bitcoin-exchange
```
# Bitcoin Rate Converter CLI

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

### Example
To use this CLI you can just run the _.jar_ file.

Example:
```shell
> java -jar ./target/bitcoin.exchange-1.0.0.jar
>
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.5.3)

2021-07-30 13:43:52.509  INFO 43567 --- [           main] c.example.bitcoin.exchange.Application   : Starting Application v1.0.0 using Java 11.0.10 on manjaro with PID 43567 (/home/esdrasxavier/Documents/github/bitcoin-exchange/target/bitcoin.exchange-1.0.0.jar started by esdrasxavier in /home/esdrasxavier/Documents/github/bitcoin-exchange)
2021-07-30 13:43:52.530  INFO 43567 --- [           main] c.example.bitcoin.exchange.Application   : No active profile set, falling back to default profiles: default
2021-07-30 13:43:53.131  INFO 43567 --- [           main] c.example.bitcoin.exchange.Application   : Started Application in 1.133 seconds (JVM running for 1.542)
2021-07-30 13:43:53.134  INFO 43567 --- [           main] c.e.b.e.s.impl.CommandLineServiceImpl    :

This program will find the current Bitcoin price  and the lowest/highes Bitcoin rate in the last 30 days, for the given Currency.

To exit the program just type 'quit'

Please provide the currency code:
eur
Typed currecy: eur
The price range fot the currency eur is:
	CurrentPrice: 32878.6623
	Highest Price In The Last Thirty Days: 33789.7765
	Lowest Price In The Last Thirty Days: 25295.6699

Please provide the currency code:

```

## Running it from Docker

You can run this project using docker. The project has been published in the DockerHub, you can find the repository [here](https://hub.docker.com/repository/docker/esdrasxavier/bitcoin-exchange).

To run it from terminal execute:
```shell
docker run -it esdrasxavier/bitcoin-exchange
```

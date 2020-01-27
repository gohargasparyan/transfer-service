# Transfer Service

This project is simple transfer service, it doesn't have notion of Currency. 

# Tech Stack

The project is written in Kotlin, build with Gradle. It is using [Micronaut](https://micronaut.io/) microservices framework.
The main advantage of Micronaut is that there are no runtime penalty for holding metadata for 
configuration and dependency injection. You can think about Micronaut as Spring without any runtime 
reflection. Every information is handled at compile time using AST processors for Java and Kotlin. 
Micronaut uses internal dependency injection module inspired by Spring which leverages the official JSR-330 Context and Dependency Injection annotations.

## Build and Test

In order to build and test please run following command from project root directory 
 ```bash
 ./gradlew clean testClasses test
```
## Run
In order to run please run following command from project root directory
 ```bash
 ./gradlew clean run
```

## Next Steps
Next steps would be:
- add API endpoint to save Accounts (tests are now pre-populating accounts)
- add Currency to Account domain, let transfers between different currency Accounts

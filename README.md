# Hystrix Function Wrapper

An utility lib that allows lambdas to be wrapped in hystrix commands.

## Dependency

```
    <dependency>
        <groupId>io.appform.core</groupId>
        <artifactId>hystrix-function-wrapper</artifactId>
        <version>1.1.4</version>
    </dependency>
```

## Sample code
```
CommandFactory.<Boolean>create("test", "test")
                .executor(() -> true) //Your lambda here
                .execute();
```

## License
Apache 2.0

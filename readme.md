# API Customer

This is application created with with the propose of practicing concepts of REST architecture.


## Configuration

Set the database parameters on the CustomerAPIConfiguration.xml file.

```
<ApiCustomerConfiguration>
    <connection>
        <url>jdbc:mysql://localhost:3306</url>
        <user>test_user</user>
        <password>1234</password>
    </connection>
</ApiCustomerConfiguration>
```

## Build
Configure the database parameters on the build.gradel file under flyway section.

```
flyway {
    url = 'jdbc:mysql://localhost:3306'
    user = 'test_user'
    password = '1234'
   ....
}
```
Run the following commands:

```
$ gradlew FlywayClean
$ gradlew FlywayValidate
$ gradlew FlywayMigrate
```

## Execution
Once executed, the API will be running on:
```
localhost:4567.
```

## License
[MIT](https://choosealicense.com/licenses/mit/)

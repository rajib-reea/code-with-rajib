```
QUARKUS PROJECT START:

1. brew install quarkusio/tap/quarkus
2. brew update
3. brew upgrade quarkus
4. quarkus create app island-dev:code-with-rajib --gradle
5. open -a docker [docker needs to be run sometime]
6. quarkus dev
```

```
DATA:

A. Dependencies:
1. jdbc for postgresql
2. hibernate panache
3. cache
4. flyway for migration files
5. hibernate validator
6. rest jackson for serialization

B. Considerations:
1. create a sql file(for us this is first.sql) and now we can make connection to
database and we can see the jpa explorer.
```

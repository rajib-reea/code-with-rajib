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

```
DATABASE:
psql -U postgres
create database with_rajib;
create user rajib WITH PASSWORD 'code';
alter database with_rajib owner to rajib;
\q
connect: psql -h localhost -p 5432 -U  rajib -d with_rajib

DB Schema: src/main/resources/db/migration/v1_code_with_rajib.sql
[Have not written migration file.]
```

```
BEFORE EVERY TEST RUN:
truncate  table order_items cascade ;
truncate table orders cascade;
truncate table products cascade;
truncate table wishlists cascade ;
truncate table wishlist_items cascade ;
truncate table customers cascade ;

```

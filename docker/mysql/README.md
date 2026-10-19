# MySQL local environment

> Important: this Docker setup only starts MySQL and a helper client container.
> The OpenXava front-end is **not** part of `docker-compose.yml`; you must start
> the app separately from `com.yourcompany.untitled.run.untitled` (IDE / Java
> application) or deploy the WAR into Tomcat.

Start the database from the project root:

```bash
docker compose up -d mysql mysql-client
docker compose ps
```

OpenXava connects through the JNDI resource in
`src/main/webapp/META-INF/context.xml`. The application user is intentionally
limited to the `untitleddb` schema; Hibernate creates and updates the tables
through the `schema-generation.database.action=update` setting in
`src/main/resources/META-INF/persistence.xml`.

The MySQL init script only creates the database and user; it does **not** create
the application tables. Those tables appear after the app starts and Hibernate
initializes the schema for the first time.

The `mysql-client` service is a lightweight helper container that keeps the
MySQL client tools available inside Docker, so you do not need a local MySQL
installation for migrations or smoke tests.

The existing `data/untitled-db.script` is an HSQLDB development snapshot. It is
not executed by MySQL. To migrate its current sample records, first start
OpenXava once so it creates the MySQL schema, then run:

```bash
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password untitleddb \
  < docker/mysql/migrate-hsqldb.sql
```

The migration is idempotent (`INSERT IGNORE`) and only covers the records
present in the checked-in HSQLDB snapshot. For additional production data, use
a dedicated export/import process after mapping HSQLDB types to MySQL.
The HSQLDB configuration remains available in the JUnit persistence unit.

If the front does not show data after migration, verify these points:

1. The app is running and can connect to MySQL using `untitled_app` /
   `untitled_app_password`.
2. The datasource URL in `src/main/webapp/META-INF/context.xml` matches where
   MySQL is running. Use `localhost` when the app runs on your Mac; use the
   Docker service name `mysql` only if the app itself is inside Docker.
3. If you previously started MySQL with another configuration, reset the volume
   and start again:

```bash
docker compose down -v
docker compose up -d mysql mysql-client
```

To verify a full CRUD round-trip without leaving test data behind, run:

```bash
docker compose exec -T mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password untitleddb \
  < docker/mysql/crud-smoke.sql
```

To remove the local database and all persisted data:

```bash
docker compose down -v
```

# Starter Spring boot (2.1.0) & Angular

1. Edit file `src/main/resources/config/application.yml` and define the application name under `spring.application.name`
2. Create a PostgreSQL database named `{spring.application.name}`

3. Run the application by lauching the main method in `Application` class or run `mvn spring-boot:run` in a terminal.

=> The application should start and populate the database.

# Start Angular

1. Install Angular CLI `npm install -g @angular/cli`

2. Go to `src/main/webapp/angular-app` and run `npm install`

3. Then, simply run ` ng serve --proxy-conf=proxy.conf.json`

# Login to the app
You can login with this account:
 * system@localhost / password
 * admin@localhost / password
 * user@localhost / password
 

# Packaging the app
`mvn clean package -Dmake_asset=true` will build angular app, generate dist dir into resources/static and package jar



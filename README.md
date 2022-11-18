# Vaadin Login Project

WebApp deployed via `Heroku` at the following link:
https://vaadin-login-page.herokuapp.com/

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.<br>
Default credentials:
* User: *user*
* Password: *userpass*

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different 
IDEs](https://vaadin.com/docs/latest/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).
### Database
#### Local
When running the project locally, an h2 in-memory database is used. Data can be view following this steps:
1. Open the [application.properties](https://github.com/carlosmgv02/VaadinSpringBoot/blob/219eef3aeafe3c0c32f24f1ce82d81b7733c49f8/src/main/resources/application.properties) file.
2. Write the following code:
```java
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```
3. Go to [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/) and log in:
* `JDBC URL`: jdbc:h2:mem:testdb
* `User`: sa
* `Password`: password<br>
*We'll leave the rest of the fields as they're.*
#### On the cloud
When the app is running via the [https://vaadin-login-page.herokuapp.com/](https://vaadin-login-page.herokuapp.com/) page, a PostgreSQL database is used instead.
Data is stored using a database provided by Heroku, where the project is hosted.
## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/flowcrmtutorial-1.0-SNAPSHOT.jar`

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/docs/components/app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.
### User management
User's privileges aren't managed, it's just a demo and security is't the purpose of it.
In case of looking to add or manage the existing users to log into the page, they can be modified following the next steps:
1. Go to the [SecurityConfig.java](https://github.com/carlosmgv02/VaadinSpringBoot/blob/219eef3aeafe3c0c32f24f1ce82d81b7733c49f8/src/main/java/com/example/application/security/SecurityConfig.java) file.
2. Default user is defined using the CrmMemoryUserDetailsManager constructor. See the following arrow.
```java
private static class CrmMemoryUserDetailsManager extends InMemoryUserDetailsManager{
        public CrmMemoryUserDetailsManager(){
    ---->   createUser(new User("user","{noop}userpass", Collections.singleton(new SimpleGrantedAuthority("USER"))));
            //createUser(new User("admin","{noop}adminpass",Collections.singleton(new SimpleGrantedAuthority("ADMIN"))));
        }
    }
```
The line below the arrow shows what we would have to do if we wanted to add an admin user with admin privileges.
## Final result


<details open> 
    <summary> 
      User list:
    </summary>
    <img src="https://user-images.githubusercontent.com/76976573/202747475-99264677-cc75-4af3-a266-7d7d9eab8b02.png" width="300"/>
      

</details>

<details> 
    <summary> 
      Login page:
    </summary>
    <img src="https://user-images.githubusercontent.com/76976573/202747863-da6f0093-a6ec-4bbe-9e1b-3b2638c1ad5f.png" width="300"/>
</details>
<details>
    <summary>
    User data management:
    </summary>
    <img src="https://user-images.githubusercontent.com/76976573/202748295-78f211f2-93ae-43eb-b2ad-bb7b87e61357.png" width="300" />

</details>

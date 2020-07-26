[![Build Status](https://travis-ci.org/vladsmirn289/GoodsShop.svg?branch=master)](https://travis-ci.org/github/vladsmirn289/GoodsShop)
[![codecov](https://codecov.io/gh/vladsmirn289/GoodsShop/branch/master/graph/badge.svg)](https://codecov.io/gh/vladsmirn289/GoodsShop)
[![BCH compliance](https://bettercodehub.com/edge/badge/vladsmirn289/GoodsShop?branch=master)](https://bettercodehub.com/)
# Goods Shop

## About
This is the pet-project, that represents the Russian online shop of various **items**,
which is written by java using spring, hibernate and freemarker.
All *items*, *images*, descriptions, characteristics in this
application has taken from [my-shop], and names, surnames,
emails, addresses has random generated.

Some images:

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/MainPage.png)

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/ProgrammingBooks.png)

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/Orders.png)

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/ManagerOrders.png)

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/ListOfUsers.png)

## If you find a bug or you have any suggestions
You can follow the next link and describe your problem/suggestion: https://github.com/vladsmirn289/GoodsShop/issues

## How to run goods shop application
Firstly, you need to clone the git project:
```shell script
git clone https://github.com/vladsmirn289/GoodsShop.git
cd GoodsShop
```
Next you can use two variants:

* Run with standard command `java -jar`:

    ```shell script
    ./mvnw package
    java -jar target/*.jar
    ```
* Or run with spring boot plugin `spring-boot:run`:

    ```shell script
    ./mvnw spring-boot:run
    ```

But when you try to start the application, it can be crash because
you need to configure your database.

If tomcat server successful started, you can follow the next link: http://localhost:8080/

## Database configuration
This application uses the postgreSQL database for runtime and H2 for tests.

The table below contains the main parameters for database settings.<br/>
Additionally, you can see these parameters in [application.properties] file.

Parameter name | Alternative parameter | By default | Meaning
:---:          | :---:                 | :---:      | :---:
--spring.datasource.url | --db_url | jdbc:postgresql://localhost:5432/shop_db | This parameter is means that database is used in local machine and its name is **shop_db**
--spring.datasource.username | --username | postgres | PostgreSQL database username
--spring.datasource.password | --password | postgres | PostgreSQL database password

Parameter name and alternative parameter is identical, except length.

Before run the application you need to create the **shop_db** database by default, or use the other name
(but you need to use **--db_url** parameter for change name at start),
and after run flyway will automatically create tables, but they will be empty, except one (in the client table
will be an admin user).
For initialize the database you need to execute the [data.sql] script.

For example, the following scripts launches an application which uses *test_shop* database:
*   Using `java -jar`:
    ```shell script
    ./mvnw package
    java -jar target/*.jar --db_url=jdbc:postgresql://localhost:5432/test_shop
    ```
*   Using spring boot plugin:
    ```shell script
    ./mvnw spring-boot:run -Dspring-boot.run.arguments="--db_url=jdbc:postgresql://localhost:5432/test_shop"
    ```

## Mail configuration
Goods shop uses the new mail.ru account, it uses to send emails when you create a new account
and managers can use it for sending some information about orders.

The table contains main parameters for mail settings:

Parameter name | Alternative parameter | By default
:---:          | :---:                 | :---:     
--spring.mail.host | --mail_host | smtp.mail.ru
--spring.mail.port | --mail_port | 465
--spring.mail.username | --mail_address | goodsshopapp@mail.ru
--spring.mail.password | --mail_password | :)
--spring.mail.properties.mail.smtp.auth | --mail_smtp_auth | true
--spring.mail.protocol | --mail_protocol | smtps
--spring.mail.properties.mail.smtp.starttls.enable | - | -
--spring.mail.properties.mail.smtp.ssl.trust | - | -
--spring.mail.properties.transport.protocol | - | -

Last three parameters you can use for gmail.

## Database structure
This is the relationship between tables in the database:

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/shop_db.jpg)

All **items** have grouped by **categories**, and the parent category can have child categories, but
the child category cannot have other categories.
Among other characteristics, **items** have a main **image**, and can have **additional images**, but
at this moment **items** can have only one (main) image.

Another main entity is the **client**, which can be a **simple user**, **manager** or **admin**.
*   **Simple user** can look through items, add them to the basket, make orders and change personal info.
*   **Managers** unlike *simple users* can to process orders (change status, write to clients on email and so on).
*   **Admins** unlike *managers* can create new *categories*, *items*, and manage authorities (set the managers, admins, lock, unlock users).

Finally, the database contains the **client_items** table, it serves for only one purpose:
to save the item quantity when it changes state (add to basket or make the order).

## Test users
If you initialize your database with the [data.sql] script, you maybe will need the logins, and the passwords of test users,
in this table you can find it all:

Login | Password | Role | State
:---: | :---:    | :---:| :---:
YakovMaurov | 25oMTtm3 | USER | Without all
ProkofiyKravchuk | 1Rhm47zO | USER | Without all
TimofeyBarshev | Yn865FbJ | USER | Without all
EgorSolomonov | 92zoKcG5 | USER | With items in basket
VladislavPutilin | 2s1L8lPC | USER | With items in basket
BorislavPotemkin | UBq9H13C | USER | With items in basket
LianaKraevska | Z1BY5O6c | USER | With orders
AlbinaBudanova | v7gIe11t | USER | With orders
testUser | 12345 | USER | Without all
RomanGusev | 25oMTtm3 | MANAGER | Without all
ReginaRudova | 25oMTtm3 | MANAGER | Without all
VyacheslavYunkin | 25oMTtm3 | MANAGER | Without all
CemenBukov | 25oMTtm3 | ADMIN | Without all

## Package structure
The diagram of the package structure:

*   GoodsShop
    *   .mvn
        *   wrapper
    *   logs
    *   src
        *   [main]
            *   [java]
                *   [com.shop.GoodsShop]
                    *   [Config] (Mostly security configs)
                    *   [Controller] (Server-side logic)
                    *   [Exception] (404 status exception)
                    *   [Model] (JPA entities)
                    *   [Repositories] (Spring Data repos)
                    *   [Service] (Service logic, that delegates work to the repos)
                    *   [Utils] (File, mail, uri and validate utils)
                    *   [GoodsShopApplication.java] (Main class for, Spring Boot)
            *   [resources]
                *   [db]
                    *   [migration] (This is the flyway migrations)
                *   [static]
                    *   [css] (Background settings)
                    *   [images]
                        *   [InitBooks] (Images for books initialization)
                        *   [InitElectronics] (Images for electronics initialization)
                        *   [InitStationery] (Images for stationery initialization)
                *   [templates] (Freemarker templates)
                    *   [admin]
                    *   [basket]
                    *   [client] (Personal room templates for changing personal info)
                    *   [error] (403, 404 and *error.ftl* pages that Spring Boot intercepts)
                    *   [item]
                    *   [manager]
                    *   [messages] (Various informational messages)
                    *   [order]
                    *   [parts] (General parts that use other pages)
                    *   [security] (Login and registration pages)
                    *   [main.ftl] (This is the main page of this application)
                *   [application.properties] (Stores various properties of the database, mail, etc.)
                *   [log4j2.xml] (Stores log4j2 properties)
        *   [test]
            *   [java][java2]
                *   [com.shop.GoodsShop][comInTest]
                    *   [Controller][ControllerTest]
                    *   [Model][ModelTest]
                    *   [Repositories][RepoTest]
                    *   [Security][SecurityTest] (Login tests)
                    *   [Service][ServiceTest]
                    *   [Utils][UtilsTest]
                    *   [GoodsShopApplicationTests.java]
            *   [resources][testRes]
                *   [db][testDb]
                    *   [H2] (H2 scripts for tests)
                *   [images][testImages] (Images for tests)
                *   [application.properties][application-test.properties] (Various properties for test environment)

## Security
This project support basic authentication and registration.
If you are not authenticated, then on the navbar you can see the "Войти" (Log in) button, otherwise "Выйти" (Log out) button.
After clicking on the "Войти" (Log in) button or if you try to access the secure URL
(for example, /basket or /admin), you will be automatically
redirected to the login page:

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/LoginPage.png)

Also, if you log in as **simple user**, you will not be able to access some URLs such as */admin* or */order/manager*,
instead of this you get this error page with 403 error:

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/Forbidden.png)

This occurs because Spring Security supports roles. If you are a **simple user**, you have the role **ROLE_USER**, else if you
are a manager, then you have the role **ROLE_MANAGER**, otherwise, if you are an admin, then you have the role **ROLE_ADMIN**.
So, many URLs has mapping in the [SecurityConfig.java] file, that allows limit access to some functions:
```text
.antMatchers(
    "/",
    "/category/*",
    "/item",
    "/item/*",
    "/item/*/image",
    "/webjars/**",
    "/css/**",
    "/images/**",
    "/registration",
    "/client/activate/*",
    "/client/setNewEmail/**")
.permitAll()

.antMatchers(
        "/order/manager",
        "/order/setManager/*",
        "/order/editOrder/*",
        "/order/changeOrderStatus/*")
.hasRole("MANAGER")

.antMatchers("/admin/**")
.hasRole("ADMIN")

.anyRequest()
.authenticated()
```

Finally, from login page you can redirect to the registration page:

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/RegistrationPage.png)

After entering the registration data, you need to confirm your email address, if you follow the link from the email, you
will be automatically logged in, and you can continue the session in a new tab.
Otherwise, if you not confirm your email, you will not be able to log in.

## Validation
This project also supports validation of the entered data. Therefore, if you leave some fields empty, after clicking the button
you will see the red labels below these fields:

![img](https://raw.githubusercontent.com/vladsmirn289/Images/master/Validation.png)

## Internationalization
Internationalization is not supported for the reason that the project is a parody on a Russian shop and
all descriptions, characteristics is written in Russian, and how to create item in many languages...

## Frameworks and technologies used
*   Java SE 8
*   Spring Boot
*   Spring MVC
*   Spring Data JPA
*   Spring Security
*   Spring Mail integration
*   PostgreSQL
*   H2
*   Hibernate
*   Hibernate Validator
*   JUnit
*   Spring Security Test
*   WebJars
*   Bootstrap
*   Freemarker
*   Maven
*   Log4j2

## License
GoodsShop is the pet-project released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

[my-shop]: https://my-shop.ru/

[main]: ./src/main
[java]: ./src/main/java
[com.shop.GoodsShop]: ./src/main/java/com/shop/GoodsShop
[Config]: ./src/main/java/com/shop/GoodsShop/Config
[Controller]: ./src/main/java/com/shop/GoodsShop/Controller
[Exception]: ./src/main/java/com/shop/GoodsShop/Exception
[Model]: ./src/main/java/com/shop/GoodsShop/Model
[Repositories]: ./src/main/java/com/shop/GoodsShop/Repositories
[Service]: ./src/main/java/com/shop/GoodsShop/Service
[Utils]: ./src/main/java/com/shop/GoodsShop/Utils
[GoodsShopApplication.java]: ./src/main/java/com/shop/GoodsShop/GoodsShopApplication.java

[resources]: ./src/main/resources
[db]: ./src/main/resources/db
[migration]: ./src/main/resources/db/migration
[static]: ./src/main/resources/static
[css]: ./src/main/resources/static/css
[images]: ./src/main/resources/static/images
[docs]: ./src/main/resources/static/images/docs
[InitBooks]: ./src/main/resources/static/images/InitBooks
[InitElectronics]: ./src/main/resources/static/images/InitElectronics
[InitStationery]: ./src/main/resources/static/images/InitStationery
[templates]: ./src/main/resources/templates
[admin]: ./src/main/resources/templates/admin
[basket]: ./src/main/resources/templates/basket
[client]: ./src/main/resources/templates/client
[error]: ./src/main/resources/templates/error
[item]: ./src/main/resources/templates/item
[manager]: ./src/main/resources/templates/manager
[messages]: ./src/main/resources/templates/messages
[order]: ./src/main/resources/templates/order
[parts]: ./src/main/resources/templates/parts
[security]: ./src/main/resources/templates/security
[main.ftl]: ./src/main/resources/templates/main.ftl
[application.properties]: ./src/main/resources/application.properties
[log4j2.xml]: ./src/main/resources/log4j2.xml
[SecurityConfig.java]: ./src/main/java/com/shop/GoodsShop/Config/SecurityConfig.java

[test]: ./src/test
[testRes]: ./src/test/resources
[testDb]: ./src/test/resources/db
[H2]: ./src/test/resources/db/H2
[testImages]: ./src/test/resources/images
[application-test.properties]: ./src/test/resources/application.properties
[java2]: ./src/test/java
[comInTest]: ./src/test/java/com/shop/GoodsShop
[ControllerTest]: ./src/test/java/com/shop/GoodsShop/Controller
[ModelTest]: ./src/test/java/com/shop/GoodsShop/Model
[RepoTest]: ./src/test/java/com/shop/GoodsShop/Repositories
[SecurityTest]: ./src/test/java/com/shop/GoodsShop/Security
[Security]: ./src/test/java/com/shop/GoodsShop/Security
[ServiceTest]: ./src/test/java/com/shop/GoodsShop/Service
[UtilsTest]: ./src/test/java/com/shop/GoodsShop/Utils
[GoodsShopApplicationTests.java]: ./src/test/java/com/shop/GoodsShop/GoodsShopApplicationTests.java

[![Build Status](https://travis-ci.org/vladsmirn289/GoodsShopRest.svg?branch=master)](https://travis-ci.org/github/vladsmirn289/GoodsShopRest)
[![BCH compliance](https://bettercodehub.com/edge/badge/vladsmirn289/GoodsShopRest?branch=master)](https://bettercodehub.com/)
# GoodsShopRest

## About
This is the remake of the [GoodsShop] project to RESTful architecture. This project uses next microservices:
*   [Authentication service] for authentication in other services by JWT token.
*   [Client service] for persist, update, retrieve, delete clients, manage basket and order items.
*   [Category service] for persist, update, retrieve and delete categories.
*   [Item service] for persist, update, retrieve and delete items.

If you want to learn this project in details, you can follow to this link: [GoodsShop].

## If you find a bug, or you have any suggestions
You can follow the next link and describe your problem/suggestion: https://github.com/vladsmirn289/GoodsShopRest/issues

## Running and using
To run this project, you can use docker-compose that run all needed microservices, main app and database and nginx on next ports:
*   PostgreSQL database will be run on 5432 port.
*   Main application (goods shop rest) will be run on 8080 port.
*   Authentication service will be run on 8081 port.
*   Client service will be run on 8082 port.
*   Item service will be run on 8083 port.
*   Category service will be run on 8084 port.
*   Nginx will be listening 80 port.

Nginx was added with purpose you don't need to write ports when you want to access the main application or any microservice.
Nginx will be automatically redirects on needed microservices if you write *http://localhost/api/{name of service (
**authentication**, **clients**, **items**, **categories**)}*, also if you write *http://localhost* you will be redirected on 8080 port automatically.

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
                        *   [JWT] (Jwt filter and utils for validation)
                        *   [RestTemplate] (Rest template configs)
                    *   [Controller] (Server-side logic)
                    *   [DTO] (Request and response classes)
                    *   [Exception]
                    *   [Jackson] (JSON deserializers)
                    *   [Model] (JPA entities)
                    *   [Service] (Service logic, that delegates work to rest template)
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
                    *   [Security][SecurityTest] (Login tests)
                    *   [Utils][UtilsTest]
                    *   [GoodsShopApplicationTests.java]
                    *   [docker-compose-test.yml]
                    *   [nginx-test.conf]
            *   [resources][testRes]
                *   [db][testDb]
                    *   [H2] (H2 scripts for tests)
                *   [images][testImages] (Images for tests)
                *   [application.properties][application-test.properties] (Various properties for test environment)
                
## More about security
In this version were added two filters ([JwtFilter] and [CustomAuthenticationFilter]) in this sequence:
CustomAuthenticationFilter -> JwtFilter -> UsernamePasswordAuthenticationFilter.

Firstly, you log into the application, and the application
create the cookie with a name "JwtToken" that stores the generated token from the authentication service.
This token is needed to get access to client service.

## License
GoodsShopRest is the RESTful pet-project released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

[GoodsShop]: https://github.com/vladsmirn289/GoodsShop
[Authentication service]: https://github.com/vladsmirn289/AuthenticationService
[Client service]: https://github.com/vladsmirn289/ClientServiceRest
[Category service]: https://github.com/vladsmirn289/ClientServiceRest
[Item service]: https://github.com/vladsmirn289/ClientServiceRest

[main]: ./src/main
[java]: ./src/main/java
[com.shop.GoodsShop]: ./src/main/java/com/shop/GoodsShop
[Config]: ./src/main/java/com/shop/GoodsShop/Config
[CustomAuthenticationFilter]: ./src/main/java/com/shop/GoodsShop/Config/CustomAuthenticationFilter.java
[JWT]: ./src/main/java/com/shop/GoodsShop/Config/JWT
[JwtFilter]: ./src/main/java/com/shop/GoodsShop/Config/JWT/JwtFilter.java
[RestTemplate]: ./src/main/java/com/shop/GoodsShop/Config/RestTemplate
[Controller]: ./src/main/java/com/shop/GoodsShop/Controller
[DTO]: ./src/main/java/com/shop/GoodsShop/DTO
[Exception]: ./src/main/java/com/shop/GoodsShop/Exception
[Jackson]: ./src/main/java/com/shop/GoodsShop/Jackson
[Model]: ./src/main/java/com/shop/GoodsShop/Model
[Service]: ./src/main/java/com/shop/GoodsShop/Service
[Utils]: ./src/main/java/com/shop/GoodsShop/Utils
[GoodsShopApplication.java]: ./src/main/java/com/shop/GoodsShop/GoodsShopApplication.java

[resources]: ./src/main/resources
[db]: ./src/main/resources/db
[migration]: ./src/main/resources/db/migration
[static]: ./src/main/resources/static
[css]: ./src/main/resources/static/css
[images]: ./src/main/resources/static/images
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
[SecurityTest]: ./src/test/java/com/shop/GoodsShop/Security
[UtilsTest]: ./src/test/java/com/shop/GoodsShop/Utils
[GoodsShopApplicationTests.java]: ./src/test/java/com/shop/GoodsShop/GoodsShopApplicationTests.java
[docker-compose-test.yml]: ./src/test/java/com/shop/GoodsShop/docker-compose-test.yml
[nginx-test.conf]: ./src/test/java/com/shop/GoodsShop/nginx-test.conf
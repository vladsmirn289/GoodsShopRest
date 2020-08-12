FROM alpine/git as clone
WORKDIR /home/app
RUN git clone https://github.com/vladsmirn289/GoodsShopRest.git

FROM maven:3.5-jdk-8-alpine as build
WORKDIR /home/app
COPY --from=clone /home/app/GoodsShopRest .
RUN mvn -DskipTests=true package

FROM openjdk:8-jre-alpine
WORKDIR /home/app
COPY --from=build /home/app/target/*.jar .
COPY --from=build /home/app/src/main/resources/static/images ./images
ENV db_host db
CMD java -jar *.jar --db_url=jdbc:postgresql://${db_host}:5432/shop_db --uploadPath=/home/app/images/
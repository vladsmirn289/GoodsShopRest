FROM alpine/git as clone
WORKDIR /home/app
RUN git clone https://github.com/vladsmirn289/GoodsShop.git

FROM maven:3.5-jdk-8-alpine as build
WORKDIR /home/app
COPY --from=clone /home/app/GoodsShop .
RUN mvn package

FROM openjdk:8-jre-alpine
WORKDIR /home/app
COPY --from=build /home/app/target/*.jar .
CMD java -jar *.jar --db_url=jdbc:postgresql://db:5432/shop_db
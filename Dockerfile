#
# Build stage
#
FROM maven:3.6.1-jdk-8-alpine AS build
WORKDIR /app
COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn -Dmaven.test.skip=true clean package

#
# Package stage
#
FROM anapsix/alpine-java:8
COPY --from=build /app/target/authen-payment-service-0.0.1-SNAPSHOT.jar /usr/local/lib/authen-payment-service.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar","/usr/local/lib/authen-payment-service.jar"]
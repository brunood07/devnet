## BUILD STAGE
FROM maven:3.8.4-openjdk-17-slim AS build
ENV HOME=/usr/app
WORKDIR $HOME
COPY . .
RUN mvn clean package -DskipTests

##RUN STAGE
FROM openjdk:17-alpine
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=build /usr/app/target/*.jar ./app/runner.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app/runner.jar"]
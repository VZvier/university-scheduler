FROM eclipse-temurin:19-jdk-alpine
COPY  src/main/resources/ua/foxminded/university/    src/main/resources/ua/foxminded/university/
COPY target/*.jar university.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "university.jar"]
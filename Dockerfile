FROM adoptopenjdk/maven-openjdk11 as builder
WORKDIR /application
ARG JAR_FILE=target/*.jar
# COPY src ./src
# COPY pom.xml ./
# RUN mvn clean package -DskipTests=true
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
EXPOSE 8090
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
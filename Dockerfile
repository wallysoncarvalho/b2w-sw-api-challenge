FROM adoptopenjdk/maven-openjdk11 as builder
WORKDIR /application
COPY src ./src
COPY pom.xml ./
RUN mvn clean package -DskipTests=true
RUN java -Djarmode=layertools -jar target/swc-1.0.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./
EXPOSE 8090
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
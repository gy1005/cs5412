FROM openjdk:8-jdk-alpine
ADD ./ /data

# RUN cd /data \
#     && ./mvnw package -Dmaven.test.skip=true \
#     && cp ./target/eureka-service-0.0.1-SNAPSHOT.jar /app.jar \
#     && rm -rf /data

RUN cp /data/target/eureka-service-0.0.1-SNAPSHOT.jar /app.jar \
    && rm -rf /data

ENTRYPOINT ["java", "-jar", "/app.jar"]

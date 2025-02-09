FROM amazoncorretto:17.0.3

COPY target/stock-price-monitoring-0.0.1-SNAPSHOT.jar stock-price-monitoring.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/stock-price-monitoring.jar"]


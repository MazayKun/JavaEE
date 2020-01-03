FROM java:8-jdk-alpine
VOLUME /home/docex
COPY target/test-1.0-SNAPSHOT-jar-with-dependencies.jar /home/docex/
WORKDIR /home/docex
ENTRYPOINT ["java", "-jar", "test-1.0-SNAPSHOT-jar-with-dependencies.jar"]
FROM maven:latest

RUN mkdir -p /active-mq-lab2
WORKDIR /active-mq-lab2

COPY . /active-mq-lab2

RUN mvn clean install
CMD ["java", "-jar", "target/active-mq-lab2-1.0-SNAPSHOT.jar"]

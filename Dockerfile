FROM openjdk:21-jdk

WORKDIR /app

COPY target/p2p-insurance.jar /app/p2p-insurance.jar

ENTRYPOINT ["java","-jar","/app/p2p-insurance.jar"]

FROM ubuntu
RUN apt update && apt install -y openjdk-17-jdk
COPY ./security-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar app.jar

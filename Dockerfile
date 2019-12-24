FROM openjdk:8-jdk-stretch as builder

WORKDIR /build
COPY . .
RUN ./gradlew bootJar

FROM openjdk:8-jdk-stretch

WORKDIR /deploy
COPY --from=builder /build/build/libs/demo.jar ./demo.jar
CMD ./demo.jar

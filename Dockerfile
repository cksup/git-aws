FROM openjdk17
VOLUME /tmp
EXPOSE 4040
ARG JAR FILE
COPY put/artifacts/SpringSecurityJWT_jar/SpringSecurityJWT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
# Alpine Linux with OpenJDK JRE
FROM maven:3.6.1-jdk-8-alpine

MAINTAINER Anna Pawelczyk

WORKDIR /app

# Download sources
#RUN wget -O perf-trace-server.tar.gz --header="Accept:application/vnd.github.v3.raw" -O - https://api.github.com/repos/andziaania/perf-trace-server/tarball/master | tar xz --strip-components 1 

COPY perf-trace-server /app

RUN mvn package

EXPOSE 8080 
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "target/perftraceserver-1.0.war"]

FROM java:8

# Metadata
LABEL maintainer="michael.sober@ymail.com"
LABEL version="1.0"
LABEL description="Dockerfile for exercise1 in digital preservation"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="digital_preservation_ex_1.jar"

# Copy the digital_preservation_ex_1 app into the container
COPY build/libs/${JAR_FILE} digital_preservation_ex_1.jar

# Expose the port 8180 outside of the container
EXPOSE 8180

#Run this on container startup
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/digital_preservation_ex_1.jar"]

FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-21@sha256:9c35f219594c398f77af9bac2eeb14a504df5fbea753aa0c88a6a08db7df7e5e
COPY build/libs/*-all.jar app.jar
ENV JAVA_OPTS='-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp'
ENV LANG='nb_NO.UTF-8' LANGUAGE='nb_NO:nb' LC_ALL='nb:NO.UTF-8' TZ="Europe/Oslo"
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080

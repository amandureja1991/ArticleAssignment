FROM openjdk:8
ADD target/article-assignmet.jar article-assignmet.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "article-assignmet.jar"]
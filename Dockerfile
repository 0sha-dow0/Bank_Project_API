FROM openjdk:17.0.2
EXPOSE 8585
ADD target/bank-account-project.jar bank-account-project.jar
ENTRYPOINT ["java","-jar","/bank-account-project.jar"]
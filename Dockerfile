# Étape 1 : Construction du projet avec Maven et Java 17
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Lancement du serveur web Tomcat 10 (compatible avec vos bibliothèques Jakarta)
FROM tomcat:10.1-jre17
RUN rm -rf /usr/local/tomcat/webapps/*
# Copier le fichier .war généré et le renommer en ROOT.war pour qu'il soit la page d'accueil
COPY --from=build /app/target/CDL-Blog.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
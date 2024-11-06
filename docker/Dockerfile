# Utiliser une image Gradle officielle avec JDK 17
FROM gradle:8.10.2-jdk17

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de build
COPY ../build.gradle ../settings.gradle ./
COPY ../gradle ./gradle

# Télécharger les dépendances
RUN gradle build -x test --no-daemon

# Copier le reste des fichiers du projet
COPY .. .

# Exposer le port si nécessaire (par exemple, pour un serveur de développement)
EXPOSE 8080

# Commande par défaut pour lancer l'application
CMD ["./gradlew", "run"]

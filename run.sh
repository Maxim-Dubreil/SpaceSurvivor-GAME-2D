
DOCKER_DIR="./docker"

# Vérifier si le répertoire Docker existe
if [ ! -d "$DOCKER_DIR" ]; then
  echo "Le répertoire Docker '$DOCKER_DIR' n'existe pas."
  exit 1
fi

# Arrêter et supprimer les conteneurs précédents (optionnel)
cd "$DOCKER_DIR" || { echo "Échec du changement de répertoire vers $DOCKER_DIR"; exit 1; }

docker compose down

# Lancer Docker Compose en arrière-plan
docker compose up -d

# Vérifier si Docker Compose a démarré correctement
if [ $? -ne 0 ]; then
  echo "Échec du démarrage de Docker Compose."
  exit 1
fi

# Revenir au répertoire racine du projet
cd - >/dev/null

# Attendre quelques secondes pour s'assurer que les services sont prêts
sleep 10

# Lancer le jeu en utilisant Gradle
sudo ./gradlew run

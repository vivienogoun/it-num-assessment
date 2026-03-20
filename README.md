# Assessment

## Instructions d'installation et d'exécution du projet

```bash
git clone https://github.com/vivienogoun/it-num-assessment.git
```

### Backend

#### Prérequis:

- Java 17+
- Maven 3.5+
- Docker & Docker Compose

#### Commandes

```bash
cd Backend/it-num-assessment/

cp .env.example .env
```

Remplir les valeurs manquantes du .env: `MYSQL_PASSWORD`, `MYSQL_ROOT_PASSWORD` et `JWT_SECRET`

Note: Le `JWT_SECRET` doit respecter les standards requis. Peut être généré avec un outil en ligne.

```bash
./mvnw clean spring-boot:run
```

Vérifier que l'application Spring Boot tourne normalement.

Le backend doit être accessible sur [http://127.0.0.1:8080](http://127.0.0.1:8080)

### Frontend

Ouvrir un nouveau terminal

#### Prérequis:

- Node 20+
- npm

#### Commandes

```bash
# A exécuter à partir du dossier dans lequel le projet a été cloné
cd Frontend/it-num-assessment-web/

cp .env.example .env

npm run dev
```

Vérifier que l'application Nuxt tourne normalement.

Le frontend doit être accessible sur [http://localhost:3000/](http://localhost:3000/)

### Instructions de test

- Créer un utilisateur directement sur l'API avec curl ou Postman

```bash
curl --location 'http://127.0.0.1:8080/api/auth/signup' \
--header 'Content-Type: application/json' \
--data '{
    "username": "username",
    "password": "password"
}'
```

- Accéder au frontend et se connecter avec l'utilisateur créé
- Vérifier les fonctionnalités

Merci!

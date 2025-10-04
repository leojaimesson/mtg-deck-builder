# DeckBuilder App

DeckBuilder is an app for managing Magic The Gathering cards, combining the API, database, infrastructure, and documentation into a single repository. It allows searching, registering, and managing cards, as well as building custom decks.

## Project Structure

* `server/` - Contains the **DeckBuilder API** for card search and management.
* `database/` - Contains the **database schema** and migration scripts managed by Flyway.
* `infrastructure/` - Contains Docker Compose setup for PostgreSQL, RabbitMQ, and pgAdmin.
* `documents/` - Contains project documentation including database design, feature specifications, and related materials.

---

## Documentation Folder

All additional documentation including database design, feature specifications, and implementation notes are located in the `documents/` folder. Please consult these files for detailed guides and diagrams.

---

## Docker & Infrastructure

The project uses Docker Compose to manage services for development:

* **PostgreSQL** (port 5432)
* **Flyway** for migrations
* **RabbitMQ** (AMQP port 5672 and management console 15672)
* **pgAdmin** (web interface on port 8080)

### Setup `.env`

Create `.env` from `.env.example` before running Docker:

```bash
cp .env.example .env
```

All variables are mandatory.

Example `.env`:

```dotenv
# PostgreSQL
DATABASE_USER=root
DATABASE_PASSWORD=root
DATABASE_NAME=mtg_deck_builder_database

# RabbitMQ
RABBITMQ_USER=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_VHOST=/

# pgAdmin
PGADMIN_EMAIL=admin@example.com
PGADMIN_PASSWORD=admin
```

### Start Docker Compose

```bash
docker-compose up -d
```

### Stop Docker Compose

```bash
docker-compose down
```

* To remove volumes for a fresh start:

```bash
docker-compose down -v
```

### Access Services

* **RabbitMQ Management Console**: [http://localhost:15672](http://localhost:15672)

  * Username: `${RABBITMQ_USER}`
  * Password: `${RABBITMQ_PASSWORD}`

* **pgAdmin**: [http://localhost:8080](http://localhost:8080)

  * Email: `${PGADMIN_EMAIL}`
  * Password: `${PGADMIN_PASSWORD}`
  * Add a server with Host: `postgres`, Port: `5432`, Username: `${DATABASE_USER}`, Password: `${DATABASE_PASSWORD}`

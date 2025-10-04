# Database Schema

The database schema stores Magic The Gathering cards, faces, colors, and related data.

## Tables

* **CARDS**: Main table storing card information.
* **CARD_COLORS**: Colors associated with each card.
* **CARD_COLOR_IDENTITY**: Color identity for each card.
* **FACES**: Cards with multiple faces.
* **FACE_COLORS**: Colors associated with each face.

### Relationships

```mermaid
erDiagram
    CARDS {
        VARCHAR(255) id PK
        VARCHAR(255) scryfall_id
        VARCHAR(255) name
        TEXT description
        VARCHAR(255) type
        VARCHAR(255) mana_cost
        VARCHAR(255) total_mana_cost
        VARCHAR(50) power
        VARCHAR(50) toughness
        VARCHAR(100) rarity
        VARCHAR(255) artist
        VARCHAR(500) small
        VARCHAR(500) normal
        VARCHAR(500) large
        VARCHAR(500) png
        VARCHAR(500) art_crop
        VARCHAR(500) border_crop
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    CARD_COLORS {
        VARCHAR(255) card_id PK, FK
        VARCHAR(50) color PK
    }

    CARD_COLOR_IDENTITY {
        VARCHAR(255) card_id PK, FK
        VARCHAR(50) color_identity PK
    }

    FACES {
        VARCHAR(255) id PK
        VARCHAR(255) card_id FK
        VARCHAR(255) name
        TEXT description
        VARCHAR(255) type
        VARCHAR(255) mana_cost
        VARCHAR(50) power
        VARCHAR(50) toughness
        VARCHAR(255) artist
        VARCHAR(500) small
        VARCHAR(500) normal
        VARCHAR(500) large
        VARCHAR(500) png
        VARCHAR(500) art_crop
        VARCHAR(500) border_crop
        TIMESTAMP created_at
        TIMESTAMP updated_at
    }

    FACE_COLORS {
        VARCHAR(255) face_id PK, FK
        VARCHAR(50) color PK
    }

    CARDS ||--o{ CARD_COLORS : "has"
    CARDS ||--o{ CARD_COLOR_IDENTITY : "has"
    CARDS ||--o{ FACES : "can have"
    FACES ||--o{ FACE_COLORS : "has"
```

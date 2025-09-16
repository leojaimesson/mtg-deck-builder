# DeckBuilder API

DeckBuilder API é um serviço para busca, cadastro e gerenciamento de cartas de Magic: The Gathering.  
O sistema permite que os usuários busquem cartas, salvem no banco local e montem decks personalizados.

---

## Funcionalidades

- Buscar cartas por **nome** ou **descrição**.
- Consultar e persistir cartas no banco de dados local.
- Fallback automático para a API do [Scryfall](https://scryfall.com/) quando cartas não estão no banco.

---

## Fluxo de Busca de Cartas

O fluxo de busca da API é ilustrado abaixo:

```mermaid
sequenceDiagram
    autonumber
    participant User as "User / Client"
    participant API as "DeckBuilder API"
    participant DB as "Local Database"
    participant Scryfall as "Scryfall API"

    User->>API: Search cards (query)
    API->>DB: Search cards by name or description
    alt Cards found locally
        DB-->>API: Return cards
        API-->>User: Return cards
    else No cards found locally
        DB-->>API: Empty result
        API->>Scryfall: Search cards
        alt Scryfall returns cards
            Scryfall-->>API: Return cards
            API->>DB: Save new cards
            DB-->>API: Save complete
            API-->>User: Return cards
        else Scryfall returns nothing
            Scryfall-->>API: Empty result
            API-->>User: Return empty list
        end
    end

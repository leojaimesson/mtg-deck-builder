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
flowchart TD
    A["User (Client) - Search cards (query)"] --> B["DeckBuilder API receives request"]

    B --> C{Search in Local Database?}
    
    C -->|Cards found| D["Return cards from DB"]
    D --> E["DeckBuilder API returns cards to User"]

    C -->|No cards found| F["DeckBuilder API queries Scryfall API"]

    F --> G{Scryfall returns cards?}
    
    G -->|Yes| H["Return cards from Scryfall"]
    H --> I["Save new cards in Local Database"]
    I --> J["DeckBuilder API returns cards to User"]

    G -->|No| K["DeckBuilder API returns empty list to User"]
```
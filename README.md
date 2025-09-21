# DeckBuilder API

DeckBuilder API is a service for searching, registering, and managing Magic The Gathering cards.  
The system allows users to search for cards, save them in the local database, and build custom decks.

---
## Features

<details>
<summary><strong>Card Search<strong></summary>

- Search cards by **name** or **description**.
- Query and persist cards in the local database.
- Automatic fallback to the [Scryfall](https://scryfall.com/) API when cards are not found locally.


The API's card search flow is illustrated below:

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
</details>

//readme.md
# Dungeon Explorer

Dungeon Explorer è un gioco di avventura testuale sviluppato in Java SE, dove il giocatore esplora un dungeon composto da stanze, mostri, oggetti e trappole. L'obiettivo è sopravvivere e uscire vivo dal dungeon.

## ✅ Caratteristiche principali
- Navigazione libera tra stanze
- Combattimento con nemici
- Inventario e oggetti
- Salvataggio e caricamento del gioco
- Sicurezza: input sanitizzato, eccezioni gestite, logging

## 🧠 Tecnologie e pattern utilizzati
| Tecnologia / Pattern | Descrizione |
|----------------------|-------------|
| Factory Pattern | Creazione dinamica di entità, stanze, oggetti |
| Composite Pattern | Struttura gerarchica di stanze e oggetti |
| Iterator Pattern | Navigazione inventario, stanze |
| Exception Shielding | Classe `GameException` per gestire errori user-friendly |
| Singleton | `GameContext` per lo stato globale |
| Strategy | IA dei nemici configurabile |
| Observer | Eventi di gioco |
| Template Method | Comandi eseguiti secondo uno schema |
| Memento | Salvataggio stato del gioco |
| Stream API / Lambda | Filtraggio entità |
| Multithreading | Eventi asincroni |
| Reflection | Caricamento dinamico nemici/item |
| Custom Annotations | `@AutoLoad` per classi da riflettere |
| Inversion of Control | Configurazioni centralizzate in `GameConfig` |

## 📦 Setup e Esecuzione
1. Clona il repository
2. Compila con `javac` o usa un IDE (IntelliJ / Eclipse)
3. Esegui `Main.java`

## 📌 Limitazioni e sviluppi futuri
- Attualmente la mappa è statica
- Interfaccia solo testuale
- AI dei nemici molto semplice

## 📐 Diagrammi (UML + Architettura)
*(Da aggiungere: Class diagram e Component diagram)*
# 🧭 Dungeon Explorer

Dungeon Explorer è un gioco di avventura testuale sviluppato in **Java SE**, dove il giocatore esplora un dungeon pieno di nemici, stanze, oggetti e sorprese. Il gioco integra diversi pattern di progettazione, tecnologie Java avanzate e buone pratiche di sicurezza.

## ✅ Caratteristiche principali

- Navigazione tra stanze tramite comandi testuali
- Combattimenti a turni contro nemici con strategie
- Equipaggiamento e inventario dinamico
- Salvataggio/caricamento dello stato di gioco
- Mappa modulare (Composite Room)
- Supporto a caricamento dinamico di nemici e oggetti via reflection
- Eventi asincroni e logging
- Input sanitizzato e gestione sicura degli errori

---

## 🧠 Tecnologie e Design Pattern utilizzati

| Tecnologia / Pattern     | Descrizione |
|--------------------------|-------------|
| **Factory**              | Creazione dinamica di stanze e nemici |
| **Composite**            | Stanze e oggetti composti |
| **Iterator**             | Iterazione su stanze e inventario |
| **Exception Shielding**  | Gestione sicura degli errori con `GameException` |
| **Singleton**            | Stato globale del gioco con `GameContext` |
| **Strategy**             | Intelligenza nemici (`AggressiveStrategy`, `DefensiveStrategy`) |
| **Observer**             | Eventi di gioco come sconfitta nemico |
| **Template Method**      | Gestione comandi base (`AbstractCommandProcessor`) |
| **Memento**              | Salvataggio dello stato con `GameStateMemento` |
| **Builder**              | Costruzione oggetti (`ItemBuilder`) |
| **Reflection**           | Caricamento classi annotate con `@AutoLoad` |
| **Custom Annotation**    | `@AutoLoad` per marcare classi dinamiche |
| **Inversion of Control** | Configurazioni tramite `GameConfig` |
| **Stream / Lambda**      | Filtri su inventario e oggetti |
| **Multithreading**       | Eventi asincroni e attacchi automatici |
| **Java I/O**             | Salvataggio/caricamento file (`GameFileManager`) |
| **Logging**              | Logging eventi e azioni con `GameLogger` |
| **Input Sanitization**   | Pulizia input utente (`InputSanitizer`) |

---

## ▶️ Comandi supportati

- `guarda`: mostra la stanza e gli elementi presenti
- `muovi [direzione]`: spostati tra le stanze
- `attacca`: attacca il nemico presente
- `usa [oggetto]`: usa un oggetto dall'inventario
- `equip [oggetto]`: equipaggia arma/armatura
- `prendi [oggetto]`: raccogli un oggetto dalla stanza
- `esplora`: mostra le stanze collegate o contenute
- `combina [oggetto1] [oggetto2]`: crea oggetto composto
- `stato`: mostra HP e oggetti equipaggiati
- `salva`: salva lo stato di gioco
- `carica`: carica una partita salvata
- `exit`: esce dal gioco

---

## ⚙️ Setup e Avvio

1. Clona il repository
2. Compila il progetto:
   ```bash
   javac -d bin src/**/*.java
   ```
3. Esegui:
   ```bash
   java -cp bin manfrinmarco.Main
   ```

---

## 📌 Limitazioni e sviluppi futuri

- La mappa attualmente è statica (ma estendibile via file)
- Nessuna interfaccia grafica (solo CLI)
- Nemici con intelligenza semplice (ma strategica)
- Nessuna validazione incrociata su oggetti compositi

---

## 📐 Diagrammi UML e Architettura

🧩 Includere:
- Class Diagram (struttura delle entità)
- Component Diagram (pacchetti e flussi)

---

## 🧪 Test e copertura

- Unit test presenti per `Player`, `Enemy`, `Inventory`
- Test eseguibili via JUnit
- Mockito usato per `EventManager` e `Logger`

//classdiagram.puml
@startuml ClassDiagram
' CLASS DIAGRAM — Dungeon Explorer

package core {
  class GameContext {
    - player: Player
    - currentRoom: Room
    - score: int
    - eventManager: EventManager
    + getInstance(): GameContext
    + setPlayer(Player): void
    + setCurrentRoom(Room): void
  }

  class Game {}
  class CommandProcessor {}
  class GameLogger {}
  class GameFileManager {}
  class GameStateMemento {}
}

package entities {
  abstract class Entity {
    - name: String
    - health: int
    + takeDamage(int): void
  }

  class Player {
    - inventory: Inventory
    - equippedWeapon: Item
    - equippedArmor: Item
    + attack(Entity): void
  }

  class Enemy {
    - strategy: EnemyStrategy
    + executeStrategy(Player): void
  }

  interface EnemyStrategy {
    + attack(Enemy, Player): void
  }

  class AggressiveStrategy
  class DefensiveStrategy
}

package items {
  class Item {
    - name: String
    - type: ItemType
    - power: int
  }

  class CompositeItem
  class Inventory
  class ItemBuilder
  enum ItemType
}

package map {
  class Room {
    - name: String
    - description: String
    - exits: Map<Direction, Room>
    - items: List<Item>
    - enemy: Enemy
  }

  class CompositeRoom
  class RoomIterator
  class RoomFactory
  enum Direction
}

package events {
  class EventManager
  interface EventListener
  class GameEvent
  class ScoreListener
}

GameContext --> Player
GameContext --> Room
GameContext --> EventManager
Game --> CommandProcessor
Game --> GameContext
Game --> GameLogger
Game --> GameFileManager
Game --> GameStateMemento

Player --> Inventory
Player --> Item
Enemy --> EnemyStrategy
AggressiveStrategy ..|> EnemyStrategy
DefensiveStrategy ..|> EnemyStrategy

Inventory --> Item
CompositeItem --> Item
Room --> Item
Room --> Enemy
Room --> Room : exits
CompositeRoom --> Room

EventManager --> EventListener
EventManager --> GameEvent
ScoreListener ..|> EventListener
@enduml


//componentdiagram.UML
@startuml ComponentDiagram
' COMPONENT DIAGRAM — Dungeon Explorer

package "Application" {
  [Main] --> [Game]
}

package "Core" {
  [Game] --> [GameContext]
  [Game] --> [CommandProcessor]
  [GameContext] --> [EventManager]
  [Game] --> [GameLogger]
  [Game] --> [GameFileManager]
  [Game] --> [GameStateMemento]
}

package "Entities" {
  [GameContext] --> [Player]
  [Room] --> [Enemy]
  [Enemy] --> [EnemyStrategy]
  [Player] --> [Inventory]
  [Inventory] --> [Item]
}

package "Items" {
  [Item] --> [ItemType]
  [CompositeItem] --> [Item]
  [ItemBuilder] --> [Item]
}

package "Map" {
  [Room] --> [Item]
  [Room] --> [Enemy]
  [Room] --> [Room] : exits
  [CompositeRoom] --> [Room]
  [RoomIterator] --> [Room]
}

package "Events" {
  [EventManager] --> [GameEvent]
  [EventManager] --> [EventListener]
  [ScoreListener] ..> [EventListener]
}

package "IO & Config" {
  [GameFileManager] --> [GameContext]
  [InputSanitizer] --> [CommandProcessor]
  [ReflectionLoader] --> [@AutoLoad]
  [GameConfig] --> [DefaultGameInitializer]
  [GameLogger] --> [game.log]
}
@enduml


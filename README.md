

# 🧭 Dungeon Explorer

Dungeon Explorer è un gioco di avventura testuale sviluppato in **Java SE**, dove il giocatore esplora un dungeon pieno di nemici, stanze, oggetti e sorprese. Il gioco integra diversi pattern di progettazione, tecnologie Java avanzate e buone pratiche di sicurezza.

## ✅ Caratteristiche principali

- Navigazione tra stanze tramite comandi testuali
- Combattimenti a turni contro nemici con strategie dinamiche
- Equipaggiamento e inventario
- Combinazione di oggetti e creazione di oggetti compositi
- Caricamento dinamico di stanze, nemici e oggetti (Reflection + Annotazioni)
- Mappe modulari tramite file JSON
- Salvataggio e caricamento dello stato (Memento + Java I/O)
- Eventi di gioco asincroni e logging esteso
- Input sanitizzato e gestione sicura delle eccezioni

## 🧠 Tecnologie e Design Pattern utilizzati

| Tecnologia / Pattern     | Descrizione |
|--------------------------|-------------|
| **Factory**              | Creazione dinamica di stanze, oggetti e nemici |
| **Composite**            | Stanze composte (`CompositeRoom`) e oggetti (`CompositeItem`) |
| **Iterator**             | Iterazione su stanze e inventario |
| **Exception Shielding**  | `GameException` e log strutturati |
| **Singleton**            | `GameContext` condiviso |
| **Strategy**             | Comportamento dei nemici: aggressivo o difensivo |
| **Observer**             | Eventi di gioco: sconfitta nemico, drop oggetti, ecc. |
| **Template Method**      | Gestione comandi tramite `AbstractCommandProcessor` |
| **Memento**              | Salvataggio e ripristino stato gioco (`GameStateMemento`) |
| **Builder**              | Costruzione oggetti (`ItemBuilder`) |
| **Reflection**           | Caricamento dinamico classi annotate |
| **Custom Annotations**   | `@AutoLoad` per istanziazione automatica |
| **IoC / Config**         | Parametri caricati da `game.properties` tramite `GameConfig` |
| **Stream / Lambda**      | Ricerca oggetti in inventario |
| **Multithreading**       | Salvataggio/caricamento asincroni, potenziamenti temporanei |
| **Java I/O**             | Persistenza stato gioco su file binario |
| **Logging**              | Log avanzato con rotazione file e livelli FINE/INFO |
| **Sanitizzazione Input** | Pulizia input utente via `InputSanitizer` |

## ▶️ Comandi supportati

- `look`: osserva la stanza corrente
- `status`: mostra i tuoi HP
- `i`: mostra l'inventario
- `explore`: esplora le stanze interne o collegate
- `go <direzione>`: muoviti (es: `go north`)
- `attack`: attacca il nemico presente
- `use <nome>`: usa un oggetto
- `equip <nome>`: equipaggia un oggetto
- `take <nome>`: prendi un oggetto dalla stanza
- `combine <o1> + <o2> = <nuovo>`: combina due oggetti
- `save`: salva lo stato di gioco
- `load`: carica l'ultima partita salvata
- `help`: mostra i comandi
- `exit`: esce dal gioco

## ⚙️ Setup ed Esecuzione

1. Clona il progetto:
   ```
   git clone <repo-url>
   ```
2. Compila:
   ```
   javac -d bin src/**/*.java
   ```
3. Esegui:
   ```
   java -cp bin manfrinmarco.Main
   ```

## 📐 Diagrammi UML e Architettura

- [x] Class Diagram (entità principali e relazioni)
- [x] Component Diagram (core, mappa, oggetti, eventi, ecc.)

*(I diagrammi devono essere inclusi come immagini nel progetto)*

## 📌 Limitazioni e sviluppi futuri

- Interfaccia solo testuale (CLI)
- AI nemici semplice, ma estendibile
- Nessuna serializzazione cross-version
- Nessun supporto multiplayer o rete

## 🧪 Testing

- Test unitari con JUnit per:
  - `Player`, `Enemy`, `Inventory`, `CommandProcessor`, `GamePersistence`, `EnemyFactory`, `ItemFactory`
- Mockito per simulazione `EventManager` e `Logger`
- Logging di test configurato a livello FINE

## 📚 Requisiti soddisfatti

- **Design Patterns richiesti**: ✅ Factory, Composite, Iterator, Exception Shielding
- **Tecnologie Java SE**: ✅ Collection, Generics, Logging, Java I/O, JUnit
- **Sicurezza**: ✅ Nessun crash, input sanitizzato, eccezioni schermate
- **Caratteristiche avanzate**: ✅ Reflection, Custom Annotations, Strategy, Observer, Template Method, Memento, Stream API, Multithreading

---
© Progetto sviluppato da Marco Manfrin – progetto per l'esame di OOP at Epicode 
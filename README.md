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
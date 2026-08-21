# Dungeon Crawler

A modular, text based, arcade style Roguelike dungeon crawler created in Java. The game features dynamic scaling, procedurally generated dungeons, turn based combat, an item and perk system, 
autonomous enemy movement and actions, interesting survival mechanics, and persistent leaderboard tracking via SQLite.

This project was started in my data structures and algorithms class, driven by a custom self built implementation of a **Circular Doubly Linked List**, as well as my own implementation of an **ArrayList**.
This game demonstrates my ability to create a robust and efficient data structure as well as being able to tailor it to the specific needs of a game.

# Javadoc : https://sinbhad.github.io/Dungeon/index.html

## Table of Contents

- [Key Features](#-key-features)
- [Core Architecture & Custom Data Structures](#-core-architecture--custom-data-structures)
- [Gameplay & Systems Breakdown](#-gameplay--systems-breakdown)
    - [Turn-Based Combat](#1-tactical-turn-based-combat)
    - [Procedural Scaling & Level Progression](#2-procedural-scaling--level-progression)
    - [Perk & Economy System](#3-perk--economy-system)
    - [Itemization & Chest Traps](#4-itemization--chest-traps)
    - [Dynamic Events & Bathroom Breaks](#5-dynamic-events--hazards)
    - [Persistent SQLite High Scores](#6-persistent-sqlite-high-scores)
- [Project Directory Structure](#-project-directory-structure)
- [Prerequisites](#-prerequisites)
- [How to Run](#-how-to-run)
    - [Running via IntelliJ IDEA](#running-via-intellij-idea)
    - [Running via Command Line](#running-via-command-line)
- [Packaging & Distribution](#-packaging--distribution)
- [License](#-license)

- ## Key Features
- * **Custom Generic Data Structures**: Implements `RobertCircularlyLinkedList<T>` for seamless circular room navigation and `RobertHolder<T>` for dynamic inventory management without reliance on built-in collections.
* **Procedural Multi-Floor Labyrinths**: Floors dynamically scale in size (`7 + 5 * level`) with randomized enemy placements, loot chests, and exit gates. As the player progresses, the enemies scale in size and difficulty.
* **Tactical Stamina & Speed Combat**: Dynamic move sets, weapon scaling, stamina management, and probabilistic evasion/flee mechanics.
* **Autonomous Enemy AI**: Adversaries roam between rooms independently after player actions and during player pauses.
* **Risk/Reward Loot & Trap Mechanic**: Chests hold powerful weapons, armor, and potions, but can trigger punishing traps. The core of the chest experience is randomized, the player must be strategic about opening traps as it could make them unstoppable or end a run.
* **Milestone Perk Store**: Unlocks every 5 levels, offering permanent stat upgrades for Health, Defense, Damage, Stamina, and Speed.
* **Persistent SQLite Leaderboard**: Saves player stats, rooms explored, enemies defeated, and total calculated points to an embedded SQLite database (`dungeon_saves.db`).
* **Integrated Custom Formatting**: Customizable text formatting for console output, including color, and various other effects.

## Core Architecture & Custom Data Structures
[ Room 0 (Head) ] <=====> [ Room 1 ] <=====> [ Room 2 ] <=====> [ Room N (Tail) ] ^ ^ |================================================================| (Circular Doubly Linked Connection)



### 1. `RobertCircularlyLinkedList<T>` (`src/lib/`)
* **Circular Doubly-Linked Architecture**: Every node retains pointers to both `nextNode` and `lastNode`. The `tail` node wraps around to the `head`, and the `head` links back to `tail`.
* **Zero-Bound Navigation**: Allows cyclic traversal through dungeon rooms in either direction (`Forward` or `Backward`) without boundary exceptions.
* **Dynamic Mutation**: Supports index-based insertion (`addAtIndex`), removal, and linear array conversion.
### 2. `RobertHolder<T>` (`src/lib/`)
* **Dynamic Generic Array**: Custom auto-expanding array container with dynamic array doubling (`doubleBucketSize`) when capacity is saturated.
* **Inventory Backing**: Powers character inventory manipulation, item lookups, and deep cloning.
---
## Gameplay & Systems Breakdown
### 1. Tactical Turn-Based Combat
* **Move Selection**: Weapon moves consume Stamina. High-impact moves demand strategic resource pacing, though every weapon is equipped with a non stamina consuming move.
* **Speed Mechanics**: Speed determines who acts first in combat rounds.
* **Flee Mechanics**: Fleeing triggers a random check against enemy-specific evasion arrays. Failed escapes allow the enemy to damage the player before escape.
### 2. Procedural Scaling & Level Progression
* Floor 1 begins with **7 rooms**.
* Each completed floor rewards **100 coins**, regenerates a larger dungeon floor, and buffs enemies with `+5 * level` Attack and Health points.
### 3. Perk & Economy System
Every **5 floors**, players enter the Perk Shop to purchase permanent upgrades:
* ️ **Defense Perks**: *Slight, Decent, and Great Defense Boosts* (percentage damage mitigation).
*  **Health Perks**: *Band-aid, Healing Potion, Ultimate Healing Potion* (Max HP boosts).
*  **Damage Perks**: *Can of Spinach, Strength Training, Steroids* (Flat Attack boosts).
*  **Speed Perks**: *Running Shoes, Weight Loss Pill, Wings of Hermes* (Speed boosts).
*  **Stamina Perks** *Iron Lung, Peptides, Beet Powder* (Stamina boosts).
### 4. Itemization & Chest Traps
* **Weapons**: *Broad Sword, Short Sword, Dagger, Hammer, Two-Handed Hammer, Mace, Mage's Staff, Tactical Walking Stick*.
* **Armor**: *Scraps of Cloth, Leather Armor, Chain Mail, Knight's Armor, Plate Mail, Dragon's Breastplate, Magic Armor*.
* **Potions**: Healing potions, Speed potions, and hybrid consumables like the *Cheeseburger Deluxe*.
* **Traps**: Unsuspecting adventurers opening chests may trigger *Spray Traps, Speed Traps, Unlucky Traveler's Traps*, or the devastating *Welp Trap*.
### 5. Dynamic Events & Hazards
* **Hydration / Kidney Stone Event**: Every 15 rooms traversed, the player pauses for a break. Enemies move freely during this turn. If the player has overused potions (>3 consumed), they suffer a **10 HP kidney stone damage penalty**.
### 6. Persistent SQLite High Scores
* Connects via JDBC (`jdbc:sqlite:dungeon_saves.db`).
* Automatically initializes the `HighScores` table upon launch.
* At game over, calculates composite score (`Level * 100 + Defeated * 1000 + Rooms * 50`), persists the run, and displays the **Top 3 Hall of Fame**.
---
## Project Directory Structure
```text
Dungeon/
├── dungeon_saves.db            # SQLite High Score Database
├── src/
│   ├── Main.java               # Application entry point
│   ├── characters/             # Player, Enemy, Subclasses & Combat Entities
│   │   ├── Character.java      # Base entity class with stat & inventory logic
│   │   ├── Enemy.java          # Roaming & combat AI logic
│   │   ├── Player.java         # Player controller & movement
│   │   ├── Move.java           # Combat move definitions & stamina costs
│   │   └── [Andrew, Byron, Daniel, Jared, Jenna, Joe, Marc, Patrick, Samir, Slime].java
│   ├── dungeon/                # Core engine & dungeon mechanics
│   │   ├── DungeonBrain.java   # Main game loop, flow control, & events
│   │   ├── DungeonGenerator.java # Procedural room & enemy population
│   │   ├── Fight.java          # Turn-based combat & evasion mechanics
│   │   ├── HighScoreDB.java    # SQLite JDBC persistence layer
│   │   ├── Room.java           # Room state (enemies, items, exits)
│   │   ├── Perks.java          # Perk entity model
│   │   └── PerkLibrary.java    # Perk store catalogue
│   ├── items/                  # Itemization & Equipment
│   │   ├── Item.java           # Base item model
│   │   ├── ItemLibrary.java    # Loot tables (Potions, Traps, Armor)
│   │   ├── Weapon.java         # Weapon base class
│   │   ├── Armor.java          # Armor base class
│   │   ├── Potion.java         # Consumable stat boosters
│   │   ├── Trap.java           # Chest hazard model
│   │   └── Weapons/            # Specific weapon implementations
│   ├── lib/                    # Custom data structures & 3rd party drivers
│   │   ├── Node.java           # Generic doubly-linked node
│   │   ├── RobertCircularlyLinkedList.java # Generic circular linked list
│   │   ├── RobertHolder.java   # Generic auto-resizing dynamic array
│   │   └── sqlite-jdbc-3.51.3.0.jar # SQLite JDBC Driver
│   └── META-INF/
│       └── MANIFEST.MF         # Jar Manifest specification
└── README.md
```

## Prerequisites
Java Development Kit (JDK): JDK 21 or higher (JDK 25 compatible).
SQLite JDBC: Located at src/lib/sqlite-jdbc-3.51.3.0.jar.

How to Run
Running via IntelliJ IDEA
Open the project in IntelliJ IDEA.
Ensure the Project SDK is set to JDK 21+ (File > Project Structure > Project).
Verify that src/lib/sqlite-jdbc-3.51.3.0.jar is marked as a project library (File > Project Structure > Libraries).
Run src/Main.java.



Running via Command Line
bash


# 1. Compile
javac --enable-preview --release 25 -cp "src/lib/sqlite-jdbc-3.51.3.0.jar:src" -d out/production/Dungeon src/Main.java src/Characters/*.java src/Dungeon/*.java src/Items/*.java src/Items/Weapons/*.java src/lib/*.java
# 2. Execute
java --enable-preview -cp "out/production/Dungeon:src/lib/sqlite-jdbc-3.51.3.0.jar" Main




---
### Packaging Guide & Database Resolution
#### 1. Why the Database Gives a Cryptic Message
* **The SLF4J Message:** `sqlite-jdbc 3.51.x` calls SLF4J internally for logging. When packaged without an SLF4J provider, SLF4J outputs:
  ```text
  SLF4J: No SLF4J providers were found.
  SLF4J: Defaulting to no-operation (NOP) logger implementation
Fix (No code change): Add slf4j-nop-2.0.17.jar to your src/lib/ folder/libraries, or run java with -Dorg.slf4j.simpleLogger.defaultLogLevel=off.

Fat JAR Extraction Issue: If IntelliJ is set to "extract to target JAR", signature files (META-INF/*.SF, *.RSA) and JDBC service bindings from sqlite-jdbc get mangled, throwing SecurityException or No suitable driver found.
#### 2. How to Package Properly in IntelliJ (Clean JAR)
```Text
In IntelliJ, press Ctrl+Alt+Shift+S (Project Structure) 
→
→ Artifacts.
Click + 
→
→ JAR 
→
→ From modules with dependencies...
Select Main Class: Main.
Under JAR files from libraries, select: copy to the output directory and link via manifest.
Set META-INF/MANIFEST.MF directory to your src folder 
→
→ Click OK.
Top menu: Build 
→
→ Build Artifacts... 
→
→ Build.
The output will be in out/artifacts/DungeonGame_jar/. Run:
bash


cd out/artifacts/DungeonGame_jar
java -jar DungeonGame.jar
```

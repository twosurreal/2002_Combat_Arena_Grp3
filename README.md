# Turn-Based Combat Arena
**SC2002 Object-Oriented Design & Programming — AY25/26 Sem 2 | FDAE Group 3**

A Java-based turn-based combat game where a player-controlled character battles waves of computer-controlled enemies. The game features a clean Boundary-Control-Entity (BCE) architecture with full OOP design, strategy patterns, and a polished console UI.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [Characters & Enemies](#characters--enemies)
- [Items & Effects](#items--effects)
- [Difficulty Levels](#difficulty-levels)
- [How to Run](#how-to-run)
- [Gameplay Guide](#gameplay-guide)
- [Design Architecture](#design-architecture)
- [Team Members & Responsibilities](#team-members--responsibilities)

---

## Overview

This game simulates a turn-based combat loop between a player-controlled hero and waves of AI-controlled enemies. After selecting a character, items, and difficulty, the player engages in a combat loop where turn order is determined by each combatant's **Speed** stat.

The system is designed for **extensibility** — new characters, actions, items, and status effects can be added without modifying the core battle engine.

---

## Features

- **Two playable characters**: Warrior (Shield Bash) and Wizard (Arcane Blast)
- **Three items** to choose from at the start: Potion, Power Stone, Smoke Bomb
- **Three difficulty levels**: Easy, Medium, Hard — with multi-wave enemy configurations
- **Speed-based turn ordering** — faster combatants act first each round
- **Status effects system**: Stun, Defend, Smoke Bomb protection, and Arcane Blast ATK buffs
- **Skill cooldown system** with a `justUsedSpecialSkill` flag to prevent same-turn decrement
- **Robust input handling** — no crashes on invalid input, full cancel support
- **Console "windowing" UI** — fixed-width bordered panels for a clean HUD
- **Replay options** — replay with same settings, start fresh, or exit

---

## Project Structure

```
src/
├── Main.java
├── Boundary/
│   ├── GameStartScreen.java      # Character, item, difficulty selection UI
│   ├── GamePlayScreen.java       # In-combat HUD and action menus
│   └── InputHelper.java          # Shared Scanner singleton + input validation
├── Control/
│   ├── SetupGame.java            # Game initialisation and replay logic
│   ├── BattleEngine.java         # Core combat loop orchestrator
│   ├── ActionChoiceController.java
│   ├── ItemChoiceController.java
│   ├── TargetChoiceController.java
│   └── SkillController.java
└── Entity/
    ├── Combatant/
    │   ├── Combatant.java        # Abstract base for all fighters
    │   ├── Player.java           # Abstract player subclass
    │   ├── Warrior.java
    │   ├── Wizard.java
    │   ├── Enemy.java            # Abstract enemy subclass
    │   ├── Goblin.java
    │   └── Wolf.java
    ├── Actions/
    │   ├── Action.java           # Interface
    │   ├── BasicAttack.java
    │   ├── Defend.java
    │   ├── ShieldBash.java
    │   └── ArcaneBlast.java
    ├── Items/
    │   ├── Item.java             # Interface
    │   ├── Potion.java
    │   ├── Powerstone.java
    │   └── Smokebomb.java
    ├── Effects/
    │   ├── Effects.java          # Interface
    │   ├── DefendEffect.java
    │   ├── SmokebombEffect.java
    │   ├── Stun.java
    │   └── ArcaneBlastBuff.java
    ├── TurnOrder/
    │   ├── TurnOrderStrategy.java  # Interface
    │   └── SpeedBasedTurnOrder.java
    ├── EnemyActions/
    │   ├── EnemyActionStrategy.java  # Interface
    │   └── EnemyBasicAttack.java
    └── Level.java
```

---

## Characters & Enemies

| Character | HP  | ATK | DEF | SPD | Special Skill |
|-----------|-----|-----|-----|-----|---------------|
| Warrior   | 260 | 40  | 20  | 30  | **Shield Bash** — Deals BasicAttack damage + stuns target for 2 turns. Cooldown: 3 turns. |
| Wizard    | 200 | 50  | 10  | 20  | **Arcane Blast** — AoE damage to all enemies. Each kill grants +10 ATK for the rest of the level. Cooldown: 3 turns. |

| Enemy  | HP  | ATK | DEF | SPD |
|--------|-----|-----|-----|-----|
| Goblin | 55  | 35  | 15  | 25  |
| Wolf   | 40  | 45  | 5   | 35  |

---

## Items & Effects

| Item        | Effect |
|-------------|--------|
| **Potion**      | Restores 100 HP (capped at max HP). |
| **Power Stone** | Triggers the player's special skill for free — does not start or consume the cooldown. |
| **Smoke Bomb**  | Enemy attacks deal 0 damage this turn and next turn (2-round duration). |

> **Note:** Items are consumed immediately on selection. Using a Potion at full HP still removes it from inventory.

---

## Difficulty Levels

| Difficulty | Wave 1              | Wave 2 (Backup)         |
|------------|---------------------|-------------------------|
| **Easy**   | 3 Goblins           | —                       |
| **Medium** | 1 Goblin + 1 Wolf   | 2 Wolves                |
| **Hard**   | 2 Goblins           | 1 Goblin + 2 Wolves     |

---

## How to Run

### Prerequisites
- **Java JDK 11 or higher** installed
- A terminal / command prompt

### Steps

**1. Clone or download the repository**
```bash
git clone https://github.com/twosurreal/2002_Combat_Arena_Grp3
cd 2002_Combat_Arena_Grp3
```

**2. Compile all Java source files**

From the project root (where `src/` is located):
```bash
find src -name "*.java" -print | xargs javac -d out/
```
Or manually:
```bash
javac -d out/ src/Main.java src/Boundary/*.java src/Control/*.java src/Entity/Combatant/*.java src/Entity/Actions/*.java src/Entity/Items/*.java src/Entity/Effects/*.java src/Entity/TurnOrder/*.java src/Entity/EnemyActions/*.java src/Entity/*.java
```

**3. Run the program**
```bash
cd out
java Main
```

### Using an IDE (IntelliJ / Eclipse / VS Code)
1. Open the project folder in your IDE.
2. Mark `src/` as the **Sources Root**.
3. Run `Main.java` directly.

---

## Gameplay Guide

### Setup Phase
1. **Choose your character** — Warrior or Wizard (stats and special skill are shown)
2. **Choose 2 items** (duplicates allowed) — Potion, Power Stone, or Smoke Bomb
3. **Choose difficulty** — Easy, Medium, or Hard

### Combat Phase
Each round displays:
- Your current HP, ATK, DEF, SPD, active effects, skill cooldown, and items
- All enemy HP values
- Turn order for this round (sorted by Speed, descending)

On your turn, you choose from:
1. **Basic Attack** — Single-target damage: `max(0, yourATK - enemyDEF)`
2. **Defend** — Raises your DEF by +10 for 2 turns
3. **Use Item** — Select an item from your inventory (enter `0` to cancel)
4. **Special Skill** — Character-specific skill (shown with `[READY]` or cooldown remaining)

> When selecting a target, enter `0` to cancel and return to the action menu.

### Win / Lose Conditions
- **Victory** — All enemies across all waves are defeated
- **Defeat** — Your HP reaches 0

### After the game ends
- Option 1: Replay with same settings (character, items, difficulty)
- Option 2: Start a new game (return to character selection)
- Option 3: Exit

---

## Design Architecture

The project follows the **Boundary-Control-Entity (BCE)** pattern:

| Layer | Classes | Responsibility |
|-------|---------|----------------|
| **Boundary** | `GameStartScreen`, `GamePlayScreen`, `InputHelper` | Display game state, collect user input |
| **Control** | `SetupGame`, `BattleEngine`, `ActionChoiceController`, `ItemChoiceController`, `TargetChoiceController`, `SkillController` | Orchestrate game flow, translate inputs into actions |
| **Entity** | `Combatant`, `Action`, `Item`, `Effects`, `Level`, strategy interfaces | Core game data and behaviour |

**Key OOP Concepts Applied:**
- **Encapsulation** — Each class manages its own state; `BattleEngine` owns the combat loop
- **Abstraction** — `Action`, `Item`, `Effects`, `TurnOrderStrategy`, and `EnemyActionStrategy` are all interfaces
- **Inheritance** — `Warrior`/`Wizard` → `Player` → `Combatant`; `Goblin`/`Wolf` → `Enemy` → `Combatant`
- **Polymorphism** — The battle engine processes all combatants and actions through their abstract types

**Design Patterns Used:**
- **Strategy Pattern** — `TurnOrderStrategy` (pluggable turn ordering) and `EnemyActionStrategy` (pluggable AI)
- **Singleton Pattern** — `InputHelper.SHARED_SCANNER` prevents multi-scanner input stream conflicts

---

## Team Members & Responsibilities

| Name | Matriculation | Files Owned |
|------|--------------|-------------|
| **Decena Joshua Caleb Tabien** | U2522938E | `Main.java`, `SetupGame.java`, `BattleEngine.java`, `ActionChoiceController.java`, `ItemChoiceController.java`, `TargetChoiceController.java`, `SkillController.java`, `Level.java` |
| **Goy Tang Jung Brennen** | U2521675H | `GameStartScreen.java`, `GamePlayScreen.java`, `InputHelper.java` |
| **Jarren Shu Zhaoheng** | U2520149A | `Action.java`, `BasicAttack.java`, `Defend.java`, `ShieldBash.java`, `ArcaneBlast.java`, `TurnOrderStrategy.java`, `SpeedBasedTurnOrder.java` |
| **Javier Tan Jia Ming** | U2521252G | `Combatant.java`, `Player.java`, `Enemy.java`, `Warrior.java`, `Wizard.java`, `Goblin.java`, `Wolf.java`, `EnemyActionStrategy.java`, `EnemyBasicAttack.java` |
| **Joseph Wong Wei Jie** | U2522741F | `Effects.java`, `DefendEffect.java`, `SmokebombEffect.java`, `Stun.java`, `ArcaneBlastBuff.java`, `Item.java`, `Potion.java`, `Powerstone.java`, `Smokebomb.java` |

**Responsibility Summary:**

- **Joshua** — Control layer: entire game loop (`BattleEngine`), all controllers (`ActionChoiceController`, `ItemChoiceController`, `TargetChoiceController`, `SkillController`), game setup & replay flow (`SetupGame`), level/wave configuration (`Level`), and program entry point (`Main`)
- **Brennen** — Boundary layer: all console UI screens (`GameStartScreen`, `GamePlayScreen`), shared Scanner singleton and input validation (`InputHelper`)
- **Jarren** — Action system: `Action` interface and all four action implementations (`BasicAttack`, `Defend`, `ShieldBash`, `ArcaneBlast`); turn order strategy interface and speed-based implementation
- **Javier** — Combatant hierarchy: abstract `Combatant`, `Player`, `Enemy` and all four concrete classes (`Warrior`, `Wizard`, `Goblin`, `Wolf`); enemy AI strategy interface and `EnemyBasicAttack`
- **Joseph** — Effects system: `Effects` interface and all four effect implementations (`DefendEffect`, `SmokebombEffect`, `Stun`, `ArcaneBlastBuff`); item system: `Item` interface and all three item implementations (`Potion`, `Powerstone`, `Smokebomb`)

> **Note:** All members contributed to UML diagrams, system design discussions, integration testing, and the project report.

---

## GitHub Repository

[https://github.com/twosurreal/2002_Combat_Arena_Grp3](https://github.com/twosurreal/2002_Combat_Arena_Grp3)

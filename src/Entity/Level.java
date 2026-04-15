// Level.java: Joshua
// two wave of enemies, enemies get letters A B C as labels
// battle engine works directly with the lists returned here

package Entity;

import Entity.Combatant.Combatant;
import Entity.Combatant.Goblin;
import Entity.Combatant.Wolf;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final int levelNo; // difficulty level chosen by player
    private final List<Combatant> initialEnemies; // wave 1 enemies
    private final List<Combatant> backupEnemies; // wave 2 enemies, empty if no backup

    // build both enemy waves when level is created
    public Level(int levelNo) {
        this.levelNo = levelNo;
        this.initialEnemies = new ArrayList<>();
        this.backupEnemies  = new ArrayList<>();
        setupLevel();
    }

    private void setupLevel() {
        switch (levelNo) {
            case 1 -> {
                // easy: 3 goblins labeled A B C
                char id = 'A';
                for (int i = 0; i < 3; i++) {
                    Goblin g = new Goblin();
                    g.setName(g.getName() + " " + id);
                    initialEnemies.add(g);
                    id++;
                }
            }
            case 2 -> {
                // medium: 1 goblin + 1 wolf, backup is 2 wolves
                Goblin goblin = new Goblin();
                goblin.setName(goblin.getName() + " A");
                initialEnemies.add(goblin);

                Wolf wolf = new Wolf();
                wolf.setName(wolf.getName() + " A");
                initialEnemies.add(wolf);

                char id = 'A';
                for (int i = 0; i < 2; i++) {
                    Wolf bWolf = new Wolf();
                    bWolf.setName("Backup Wolf " + id);
                    backupEnemies.add(bWolf);
                    id++;
                }
            }
            case 3 -> {
                // hard: 2 goblins, backup is 1 goblin + 2 wolves
                char id = 'A';
                for (int i = 0; i < 2; i++) {
                    Goblin g = new Goblin();
                    g.setName(g.getName() + " " + id);
                    initialEnemies.add(g);
                    id++;
                }

                Goblin bGoblin = new Goblin();
                bGoblin.setName("Backup Goblin A");
                backupEnemies.add(bGoblin);

                id = 'A';
                for (int i = 0; i < 2; i++) {
                    Wolf bWolf = new Wolf();
                    bWolf.setName("Backup Wolf " + id);
                    backupEnemies.add(bWolf);
                    id++;
                }
            }
            default -> { /* invalid level, lists stay empty */ }
        }
    }

    // BattleEngine modifies these lists directly to remove dead enemies
    public List<Combatant> getActiveEnemies()  { return initialEnemies; }
    public List<Combatant> getBackupEnemies() { return backupEnemies; }
}

package Entity;

import Entity.Combatant.Combatant;
import Entity.Combatant.Goblin;
import Entity.Combatant.Wolf;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int levelNo;
    private final List<Combatant> initialEnemies;
    private final List<Combatant> backupEnemies;

    public Level(int levelNo) {
        this.levelNo = levelNo;
        this.initialEnemies = new ArrayList<>();
        this.backupEnemies = new ArrayList<>();
        setupLevel();
    }

    private void setupLevel() {
        switch (levelNo) {
            case 1 -> {
                // easy: 3 goblins labelled A B C
                char identifier = 'A';
                for (int i = 0; i < 3; i++) {
                    Goblin newGoblin = new Goblin();
                    newGoblin.setName(newGoblin.getName() + " " + identifier);
                    initialEnemies.add(newGoblin);
                    identifier++;
                }
            }
            case 2 -> {
                initialEnemies.add(new Goblin());
                initialEnemies.add(new Wolf());

                char identifier = 'A';
                for (int i = 0; i < 2; i++) {
                    Wolf newWolf = new Wolf();
                    newWolf.setName(newWolf.getName() + " " + identifier);
                    backupEnemies.add(newWolf);
                    identifier++;
                }
            }
            case 3 -> {
                char identifier = 'A';
                for (int i = 0; i < 2; i++) {
                    Goblin newGoblin = new Goblin();
                    newGoblin.setName(newGoblin.getName() + " " + identifier);
                    initialEnemies.add(newGoblin);
                    identifier++;
                }

                backupEnemies.add(new Goblin());

                identifier = 'A';
                for (int i = 0; i < 2; i++) {
                    Wolf newWolf = new Wolf();
                    newWolf.setName(newWolf.getName() + " " + identifier);
                    backupEnemies.add(newWolf);
                    identifier++;
                }
            }
            default -> {}
        }
    }

    public List<Combatant> getActiveEnemies() { return initialEnemies; }
    public List<Combatant> getBackupEnemies() { return backupEnemies; }
}

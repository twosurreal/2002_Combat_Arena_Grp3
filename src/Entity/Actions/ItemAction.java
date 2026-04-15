package Entity.Actions;

import Control.ItemChoiceController;
import Entity.Combatant.Combatant;
import Entity.Items.Item;

import java.util.List;

public class ItemAction implements Action {
    private Item item;
    private ItemChoiceController icc;

    public ItemAction(Item item, ItemChoiceController icc) {
        this.item = item;
        this.icc = icc;
    }

    public Boolean needsTarget() {
        return false;
    }

    public void performAction(Combatant user, Combatant enemy, List<Combatant> enemies, Boolean usedPowerStone) {
        icc.handleItemChoice(item, user, enemies);
    }
}

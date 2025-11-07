/**
 * AI Collaboration Summary:
 * Tool: ChatGPT (GPT-5 Thinking)
 *
 * What AI Helped With:
 * 1. Completed Strategy, Factory, Builder, Command, and Template Method TODOs.
 * 2. Ensured integer arithmetic rules and Java 21 switch expressions.
 *
 * What I Had to Fix:
 * 1. Verified 75% cap logic in HeavyArmorDefenseStrategy and truncation behavior.
 * 2. Normalized undo tracking to store actual deltas.
 *
 * What I Learned:
 * - How patterns interoperate in a cohesive architecture.
 * - Why encapsulating behaviors/flows improves maintainability and testability.
 *
 * Team: [List team member names and contributions]
 */
package edu.trincoll.game.command;

import edu.trincoll.game.model.Character;

/**
 * Command to heal a character.
 *
 * TODO 4b: Implement execute() and undo()
 *
 * Requirements for execute():
 * 1. Store the target's current health before healing
 * 2. Heal the target: target.heal(amount)
 * 3. Store the target's health after healing
 * 4. Calculate actual healing done (after - before)
 *
 * Requirements for undo():
 * 1. Restore health to before healing
 * 2. Use target.setHealth() to set health directly
 *    (Can't use takeDamage as it applies defense)
 *
 * Note: Need to track actual healing because you can't heal above max health.
 */
public class HealCommand implements GameCommand {
    private final Character target;
    private final int amount;
    private int actualHealingDone;

    public HealCommand(Character target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void execute() {
        int before = target.getStats().health();
        target.heal(amount);
        int after = target.getStats().health();
        actualHealingDone = after - before;
    }

    @Override
    public void undo() {
        target.setHealth(target.getStats().health() - actualHealingDone);
    }

    @Override
    public String getDescription() {
        return String.format("Heal %s for %d HP", target.getName(), amount);
    }
}

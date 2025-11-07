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
 * Command to execute an attack from one character to another.
 *
 * TODO 4a: Implement execute() and undo()
 *
 * Requirements for execute():
 * 1. Calculate damage: attacker.attack(target)
 * 2. Apply damage: target.takeDamage(calculatedDamage)
 * 3. Store the damage dealt for potential undo
 *
 * Requirements for undo():
 * 1. Heal the target for the amount of damage that was dealt
 * 2. Use target.heal(damageDealt)
 *
 * Note: This is a simplified undo - in a real game, you'd need to
 * restore mana usage, status effects, etc.
 */
public class AttackCommand implements GameCommand {
    private final Character attacker;
    private final Character target;
    private int damageDealt;

    public AttackCommand(Character attacker, Character target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void execute() {
        damageDealt = attacker.attack(target);
        target.takeDamage(damageDealt);
    }

    @Override
    public void undo() {
        target.heal(damageDealt);
    }

    @Override
    public String getDescription() {
        return String.format("%s attacks %s", attacker.getName(), target.getName());
    }
}

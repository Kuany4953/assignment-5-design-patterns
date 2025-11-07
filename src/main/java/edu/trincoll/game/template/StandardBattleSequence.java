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
package edu.trincoll.game.template;

import edu.trincoll.game.model.Character;

/**
 * Standard battle sequence - simple attack with no special actions.
 *
 * TODO 5b: Implement performAttack()
 *
 * This concrete implementation only needs to define the attack behavior.
 * The rest of the sequence is handled by the template method.
 */
public class StandardBattleSequence extends BattleSequence {

    public StandardBattleSequence(Character attacker, Character defender) {
        super(attacker, defender);
    }

    /**
     * TODO 5b: Implement performAttack()
     *
     * Requirements:
     * 1. Calculate damage: attacker.attack(defender)
     * 2. Apply damage: defender.takeDamage(calculatedDamage)
     */
    @Override
    protected void performAttack() {
        int damage = attacker.attack(defender);
        defender.takeDamage(damage);
    }
}

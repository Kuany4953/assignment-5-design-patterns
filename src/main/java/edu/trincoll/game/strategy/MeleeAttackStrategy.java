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
package edu.trincoll.game.strategy;

import edu.trincoll.game.model.Character;

/**
 * Melee attack - straightforward physical damage based on attack power.
 * Used by Warriors and Rogues.
 *
 * TODO 1a: Implement calculateDamage()
 *
 * Requirements:
 * - Base damage = attacker's attack power
 * - Add 20% bonus (multiply by 1.2)
 * - Return as integer
 *
 * Example: If attacker has 50 attack power:
 *   Base: 50
 *   With bonus: 50 * 1.2 = 60
 *   Return: 60
 */
public class MeleeAttackStrategy implements AttackStrategy {
    @Override
    public int calculateDamage(Character attacker, Character target) {
        return (int) (attacker.getStats().attackPower() * 1.2);
    }
}

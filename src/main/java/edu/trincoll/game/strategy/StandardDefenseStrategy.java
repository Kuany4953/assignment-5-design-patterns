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
 * Standard defense - reduces damage by a percentage of defense stat.
 * Used by most character types.
 *
 * TODO 1d: Implement calculateDamageReduction()
 *
 * Requirements:
 * - Calculate damage reduction: defense / 2
 * - Actual damage = incoming damage - damage reduction
 * - Ensure actual damage is never negative (minimum 0)
 *
 * Example: Defender has 20 defense, incoming damage is 50
 *   Damage reduction: 20 / 2 = 10
 *   Actual damage: 50 - 10 = 40
 *   Return: 40
 */
public class StandardDefenseStrategy implements DefenseStrategy {
    @Override
    public int calculateDamageReduction(Character defender, int incomingDamage) {
        int reduction = defender.getStats().defense() / 2;
        int actual = incomingDamage - reduction;
        return Math.max(0, actual);
    }
}

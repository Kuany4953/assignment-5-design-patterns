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
 * Heavy armor defense - better damage reduction than standard.
 * Used by Warriors.
 *
 * TODO 1e: Implement calculateDamageReduction()
 *
 * Requirements:
 * - Calculate damage reduction: defense (full defense value)
 * - Actual damage = incoming damage - damage reduction
 * - Ensure actual damage is never negative (minimum 0)
 * - Maximum 75% damage reduction (even if defense is very high)
 *
 * Example 1: Defender has 30 defense, incoming damage is 100
 *   Damage reduction: 30
 *   Actual damage: 100 - 30 = 70
 *   Return: 70
 *
 * Example 2: Defender has 80 defense, incoming damage is 100
 *   Theoretical reduction: 80 (would leave 20 damage)
 *   But max reduction is 75%, so: 100 * 0.25 = 25
 *   Return: 25
 */
public class HeavyArmorDefenseStrategy implements DefenseStrategy {
    @Override
    public int calculateDamageReduction(Character defender, int incomingDamage) {
        int reduction = defender.getStats().defense();
        int actual = incomingDamage - reduction;
        int minAllowed = (int) (incomingDamage * 0.25); // cap: at least 25% gets through
        return Math.max(minAllowed, actual);
    }
}

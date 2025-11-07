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
 * Magic attack - uses mana and attack power to calculate damage.
 * Used by Mages.
 *
 * TODO 1b: Implement calculateDamage()
 *
 * Requirements:
 * - Base damage = attacker's attack power
 * - Mana bonus = current mana / 10 (integer division)
 * - Total damage = base + mana bonus
 * - Reduce attacker's mana by 10 (use attacker.useMana(10))
 *   NOTE: If not enough mana, useMana() will throw an exception
 *
 * Example: If attacker has 60 attack power and 50 mana:
 *   Base: 60
 *   Mana bonus: 50 / 10 = 5
 *   Total: 65
 *   After attack: mana reduced by 10
 */
public class MagicAttackStrategy implements AttackStrategy {
    @Override
    public int calculateDamage(Character attacker, Character target) {
        int base = attacker.getStats().attackPower();
        int manaBonus = attacker.getStats().mana() / 10;
        int total = base + manaBonus;
        attacker.useMana(10);
        return total;
    }
}

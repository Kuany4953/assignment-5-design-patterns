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
 * Power attack sequence - charges up before attack, exhausted after.
 *
 * TODO 5c: Implement preAttackAction(), performAttack(), and postAttackAction()
 *
 * This demonstrates how hook methods can customize the template.
 */
public class PowerAttackSequence extends BattleSequence {
    private int damageBonus = 0;

    public PowerAttackSequence(Character attacker, Character defender) {
        super(attacker, defender);
    }

    /**
     * TODO 5c: Implement preAttackAction()
     *
     * Requirements:
     * 1. Calculate bonus: attacker's attack power / 4
     * 2. Store in damageBonus field
     * 3. This will be added during performAttack()
     */
    @Override
    protected void preAttackAction() {
        damageBonus = attacker.getStats().attackPower() / 4;
    }

    /**
     * TODO 5c: Implement performAttack()
     *
     * Requirements:
     * 1. Calculate base damage: attacker.attack(defender)
     * 2. Add the damage bonus calculated in preAttackAction
     * 3. Apply total damage: defender.takeDamage(baseDamage + damageBonus)
     */
    @Override
    protected void performAttack() {
        int baseDamage = attacker.attack(defender);
        defender.takeDamage(baseDamage + damageBonus);
    }

    /**
     * TODO 5c: Implement postAttackAction()
     *
     * Requirements:
     * 1. Attacker is exhausted from power attack
     * 2. Take 10% of max health as recoil damage
     * 3. Use attacker.setHealth() to apply recoil directly
     *    (Can't use takeDamage as it applies defense)
     */
    @Override
    protected void postAttackAction() {
        int recoil = (int) (attacker.getStats().maxHealth() * 0.1);
        attacker.setHealth(attacker.getStats().health() - recoil);
    }
}

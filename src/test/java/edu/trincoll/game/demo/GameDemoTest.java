package edu.trincoll.game.demo;

import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.model.Character;
import edu.trincoll.game.model.CharacterType;
import edu.trincoll.game.command.*;
import edu.trincoll.game.template.StandardBattleSequence;
import edu.trincoll.game.template.PowerAttackSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Game Demo Integration Tests")
class GameDemoTest {

    @Nested
    @DisplayName("Full Pattern Integration")
    class IntegrationTests {
        private Character warrior;
        private Character mage;
        private Character archer;
        private Character rogue;

        @BeforeEach
        void setUp() {
            warrior = CharacterFactory.createWarrior("Conan");
            mage = CharacterFactory.createMage("Gandalf");
            archer = CharacterFactory.createArcher("Legolas");
            rogue = CharacterFactory.createRogue("Rogue");
        }

        @Test
        @DisplayName("Factory creates all character types correctly")
        void testFactoryIntegration() {
            assertThat(warrior.getType()).isEqualTo(CharacterType.WARRIOR);
            assertThat(mage.getType()).isEqualTo(CharacterType.MAGE);
            assertThat(archer.getType()).isEqualTo(CharacterType.ARCHER);
            assertThat(rogue.getType()).isEqualTo(CharacterType.ROGUE);

            assertThat(warrior.getStats().maxHealth()).isEqualTo(150);
            assertThat(mage.getStats().maxHealth()).isEqualTo(80);
            assertThat(archer.getStats().maxHealth()).isEqualTo(100);
            assertThat(rogue.getStats().maxHealth()).isEqualTo(90);
        }

        @Test
        @DisplayName("Characters can attack each other through all strategies")
        void testAllStrategiesAttack() {
            int warriorDamage = warrior.attack(mage);
            int mageDamage = mage.attack(warrior);
            int archerDamage = archer.attack(rogue);
            int rogueDamage = rogue.attack(archer);

            assertThat(warriorDamage).isGreaterThan(0);
            assertThat(mageDamage).isGreaterThan(0);
            assertThat(archerDamage).isGreaterThan(0);
            assertThat(rogueDamage).isGreaterThan(0);
        }

        @Test
        @DisplayName("Characters defend using all defense strategies")
        void testAllDefensesWork() {
            int warriorDefense = warrior.defend(100);
            int mageDefense = mage.defend(100);
            int archerDefense = archer.defend(100);
            int rogueDefense = rogue.defend(100);

            assertThat(warriorDefense)
                .isLessThan(100)
                .isGreaterThanOrEqualTo(0);
            assertThat(mageDefense)
                .isLessThan(100)
                .isGreaterThanOrEqualTo(0);
            assertThat(archerDefense)
                .isLessThan(100)
                .isGreaterThanOrEqualTo(0);
            assertThat(rogueDefense)
                .isLessThan(100)
                .isGreaterThanOrEqualTo(0);
        }

        @Test
        @DisplayName("Warrior heavy armor defense is stronger than standard")
        void testHeavyArmorStronger() {
            int heavyDefense = warrior.defend(100);
            int standardDefense = mage.defend(100);

            assertThat(heavyDefense).isLessThanOrEqualTo(standardDefense);
        }

        @Test
        @DisplayName("Mage consumes mana when attacking")
        void testManaConsumption() {
            int initialMana = mage.getStats().mana();

            mage.attack(warrior);

            assertThat(mage.getStats().mana()).isLessThan(initialMana);
        }

        @Test
        @DisplayName("Ranged attack critical hit on low health target")
        void testRangedCritical() {
            archer.attack(warrior); // First attack
            archer.attack(warrior); // Second attack
            archer.attack(warrior); // Third attack - target low health

            int criticalDamage = archer.attack(warrior);

            assertThat(criticalDamage).isGreaterThan(0);
        }
    }

    @Nested
    @DisplayName("Command Pattern Battle Simulation")
    class CommandBattleTests {
        private Character hero;
        private Character enemy;
        private CommandInvoker invoker;

        @BeforeEach
        void setUp() {
            hero = CharacterFactory.createWarrior("Hero");
            enemy = CharacterFactory.createMage("Enemy");
            invoker = new CommandInvoker();
        }

        @Test
        @DisplayName("Full battle with attack and heal commands")
        void testBattleWithCommands() {
            int initialHeroHealth = hero.getStats().health();
            int initialEnemyHealth = enemy.getStats().health();

            // Hero attacks
            AttackCommand attack1 = new AttackCommand(hero, enemy);
            invoker.executeCommand(attack1);

            // Enemy attacks back
            AttackCommand attack2 = new AttackCommand(enemy, hero);
            invoker.executeCommand(attack2);

            // Hero heals
            HealCommand heal = new HealCommand(hero, 30);
            invoker.executeCommand(heal);

            assertThat(invoker.getCommandHistory()).hasSize(3);
            assertThat(enemy.getStats().health()).isLessThan(initialEnemyHealth);
            assertThat(hero.getStats().health()).isNotEqualTo(initialHeroHealth);
        }

        @Test
        @DisplayName("Undo entire battle sequence")
        void testUndoBattleSequence() {
            int heroHealthBefore = hero.getStats().health();
            int enemyHealthBefore = enemy.getStats().health();

            invoker.executeCommand(new AttackCommand(hero, enemy));
            invoker.executeCommand(new AttackCommand(enemy, hero));
            invoker.executeCommand(new HealCommand(hero, 30));

            // Undo all commands in reverse
            invoker.undoLastCommand();
            invoker.undoLastCommand();
            invoker.undoLastCommand();

            assertThat(hero.getStats().health()).isEqualTo(heroHealthBefore);
            assertThat(enemy.getStats().health()).isEqualTo(enemyHealthBefore);
            assertThat(invoker.getCommandHistory()).isEmpty();
        }

        @Test
        @DisplayName("Command history tracks multiple battles")
        void testMultipleBattles() {
            for (int i = 0; i < 5; i++) {
                invoker.executeCommand(new AttackCommand(hero, enemy));
                invoker.executeCommand(new AttackCommand(enemy, hero));
            }

            assertThat(invoker.getCommandHistory()).hasSize(10);
            assertThat(invoker.hasCommandsToUndo()).isTrue();
        }
    }

    @Nested
    @DisplayName("Template Method Battle Sequences")
    class TemplateMethodTests {
        @Test
        @DisplayName("Standard battle sequence works")
        void testStandardBattle() {
            Character attacker = CharacterFactory.createWarrior("Attacker");
            Character defender = CharacterFactory.createWarrior("Defender");

            int defenderHealthBefore = defender.getStats().health();

            StandardBattleSequence battle = new StandardBattleSequence(attacker, defender);
            battle.executeTurn();

            assertThat(defender.getStats().health()).isLessThan(defenderHealthBefore);
        }

        @Test
        @DisplayName("Power attack sequence deals more damage")
        void testPowerAttackDamage() {
            Character attacker = CharacterFactory.createWarrior("Attacker");
            Character defender1 = CharacterFactory.createWarrior("Defender1");
            Character defender2 = CharacterFactory.createWarrior("Defender2");

            int defender1HealthBefore = defender1.getStats().health();
            int defender2HealthBefore = defender2.getStats().health();

            StandardBattleSequence standardBattle = new StandardBattleSequence(attacker, defender1);
            standardBattle.executeTurn();

            int standardDamage = defender1HealthBefore - defender1.getStats().health();

            PowerAttackSequence powerBattle = new PowerAttackSequence(attacker, defender2);
            powerBattle.executeTurn();

            int powerDamage = defender2HealthBefore - defender2.getStats().health();

            assertThat(powerDamage).isGreaterThanOrEqualTo(standardDamage);
        }

        @Test
        @DisplayName("Power attack causes recoil to attacker")
        void testPowerAttackRecoil() {
            Character attacker = CharacterFactory.createWarrior("Attacker");
            Character defender = CharacterFactory.createWarrior("Defender");

            int attackerHealthBefore = attacker.getStats().health();

            PowerAttackSequence powerBattle = new PowerAttackSequence(attacker, defender);
            powerBattle.executeTurn();

            assertThat(attacker.getStats().health()).isLessThan(attackerHealthBefore);
        }

        @Test
        @DisplayName("Multiple turns of battle")
        void testMultipleTurns() {
            Character warrior = CharacterFactory.createWarrior("Warrior");
            Character mage = CharacterFactory.createMage("Mage");

            for (int i = 0; i < 3; i++) {
                StandardBattleSequence turn = new StandardBattleSequence(warrior, mage);
                turn.executeTurn();

                if (mage.isDead()) break;
            }

            assertThat(mage.getStats().health())
                .isLessThanOrEqualTo(mage.getStats().maxHealth());
        }
    }

    @Nested
    @DisplayName("Complete Scenario Tests")
    class ScenarioTests {
        @Test
        @DisplayName("Warrior vs Mage complete fight")
        void testWarriorVsMage() {
            Character warrior = CharacterFactory.createWarrior("Conan");
            Character mage = CharacterFactory.createMage("Gandalf");

            int turns = 0;
            while (warrior.isAlive() && mage.isAlive() && turns < 20) {
                StandardBattleSequence warriorTurn = new StandardBattleSequence(warrior, mage);
                warriorTurn.executeTurn();

                if (mage.isDead()) break;

                StandardBattleSequence mageTurn = new StandardBattleSequence(mage, warrior);
                mageTurn.executeTurn();

                turns++;
            }

            assertThat(turns).isGreaterThan(0);
            assertThat(turns).isLessThan(20);
        }

        @Test
        @DisplayName("All character types battle each other")
        void testFreeForAll() {
            Character warrior = CharacterFactory.createWarrior("Warrior");
            Character mage = CharacterFactory.createMage("Mage");
            Character archer = CharacterFactory.createArcher("Archer");
            Character rogue = CharacterFactory.createRogue("Rogue");

            int damage;

            damage = warrior.attack(mage);
            assertThat(damage).isGreaterThan(0);

            damage = mage.attack(archer);
            assertThat(damage).isGreaterThan(0);

            damage = archer.attack(rogue);
            assertThat(damage).isGreaterThan(0);

            damage = rogue.attack(warrior);
            assertThat(damage).isGreaterThan(0);
        }

        @Test
        @DisplayName("Characters can heal themselves")
        void testSelfHealing() {
            CommandInvoker invoker = new CommandInvoker();
            Character character = CharacterFactory.createMage("Hero");

            character.takeDamage(40);
            int healthAfterDamage = character.getStats().health();

            HealCommand heal = new HealCommand(character, 20);
            invoker.executeCommand(heal);

            assertThat(character.getStats().health()).isGreaterThan(healthAfterDamage);
        }

        @Test
        @DisplayName("Complex battle scenario with commands and templates")
        void testComplexScenario() {
            Character hero = CharacterFactory.createWarrior("Hero");
            Character boss = CharacterFactory.createMage("Boss");
            CommandInvoker invoker = new CommandInvoker();

            // Hero attacks boss
            invoker.executeCommand(new AttackCommand(hero, boss));

            // Boss attacks back using template
            PowerAttackSequence bossPowerAttack = new PowerAttackSequence(boss, hero);
            bossPowerAttack.executeTurn();

            // Hero heals
            invoker.executeCommand(new HealCommand(hero, 50));

            // Verify battle state
            assertThat(invoker.getCommandHistory()).hasSize(2);
            assertThat(hero.isAlive()).isTrue();
            assertThat(boss.isAlive()).isTrue();
        }
    }

    @Nested
    @DisplayName("Edge Cases and Stress Tests")
    class EdgeCaseTests {
        @Test
        @DisplayName("Character reaching 0 health")
        void testCharacterDeath() {
            Character character = CharacterFactory.createWarrior("Warrior");

            while (character.isAlive()) {
                character.takeDamage(30);
            }

            assertThat(character.isDead()).isTrue();
            assertThat(character.getStats().health()).isEqualTo(0);
        }

        @Test
        @DisplayName("Mana depletion for repeated attacks")
        void testManaDepleted() {
            Character mage = CharacterFactory.createMage("Mage");
            Character target = CharacterFactory.createMage("Target");

            int attackCount = 0;
            while (mage.getStats().mana() >= 10 && attackCount < 15) {
                mage.attack(target);
                attackCount++;
            }

            assertThat(attackCount).isGreaterThan(0);
            assertThat(mage.getStats().mana()).isLessThanOrEqualTo(20);
        }

        @Test
        @DisplayName("Healing above max health caps at max")
        void testHealingCap() {
            CommandInvoker invoker = new CommandInvoker();
            Character character = CharacterFactory.createWarrior("Hero");

            int maxHealth = character.getStats().maxHealth();

            HealCommand heal = new HealCommand(character, 1000);
            invoker.executeCommand(heal);

            assertThat(character.getStats().health()).isEqualTo(maxHealth);
        }

        @Test
        @DisplayName("Undo with empty history is safe")
        void testUndoEmptyHistory() {
            CommandInvoker invoker = new CommandInvoker();

            assertThatCode(() -> invoker.undoLastCommand())
                .doesNotThrowAnyException();

            assertThat(invoker.getCommandHistory()).isEmpty();
        }
    }
}

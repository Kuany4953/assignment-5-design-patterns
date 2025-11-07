package edu.trincoll.game.model;

import edu.trincoll.game.factory.CharacterFactory;
import edu.trincoll.game.strategy.MeleeAttackStrategy;
import edu.trincoll.game.strategy.StandardDefenseStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Character Model Tests")
class CharacterTest {

    @Nested
    @DisplayName("Character Creation")
    class CreationTests {
        @Test
        @DisplayName("Create character with builder")
        void testCreateCharacterWithBuilder() {
            Character character = Character.builder()
                .name("Hero")
                .type(CharacterType.WARRIOR)
                .stats(CharacterStats.create(100, 50, 20, 0))
                .attackStrategy(new MeleeAttackStrategy())
                .defenseStrategy(new StandardDefenseStrategy())
                .build();

            assertThat(character.getName()).isEqualTo("Hero");
            assertThat(character.getType()).isEqualTo(CharacterType.WARRIOR);
        }
    }

    @Nested
    @DisplayName("Builder Validation")
    class BuilderValidationTests {
        @Test
        @DisplayName("Builder rejects null name")
        void testBuilderNullName() {
            assertThatThrownBy(() ->
                Character.builder()
                    .type(CharacterType.WARRIOR)
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .attackStrategy(new MeleeAttackStrategy())
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("name");
        }

        @Test
        @DisplayName("Builder rejects null type")
        void testBuilderNullType() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("Hero")
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .attackStrategy(new MeleeAttackStrategy())
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("type");
        }

        @Test
        @DisplayName("Builder rejects null stats")
        void testBuilderNullStats() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("Hero")
                    .type(CharacterType.WARRIOR)
                    .attackStrategy(new MeleeAttackStrategy())
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("stats");
        }

        @Test
        @DisplayName("Builder rejects null attack strategy")
        void testBuilderNullAttackStrategy() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("Hero")
                    .type(CharacterType.WARRIOR)
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .defenseStrategy(new StandardDefenseStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("attackStrategy");
        }

        @Test
        @DisplayName("Builder rejects null defense strategy")
        void testBuilderNullDefenseStrategy() {
            assertThatThrownBy(() ->
                Character.builder()
                    .name("Hero")
                    .type(CharacterType.WARRIOR)
                    .stats(CharacterStats.create(100, 50, 20, 0))
                    .attackStrategy(new MeleeAttackStrategy())
                    .build()
            ).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("defenseStrategy");
        }
    }

    @Nested
    @DisplayName("Character Health Management")
    class HealthManagementTests {
        private Character character;

        @BeforeEach
        void setUp() {
            character = CharacterFactory.createWarrior("Hero");
        }

        @Test
        @DisplayName("Character heals correctly")
        void testHeal() {
            character.takeDamage(50);
            int healedHealth = character.getStats().health();

            character.heal(30);

            assertThat(character.getStats().health())
                .isGreaterThan(healedHealth);
        }

        @Test
        @DisplayName("Character set health directly")
        void testSetHealth() {
            character.setHealth(50);

            assertThat(character.getStats().health()).isEqualTo(50);
        }

        @Test
        @DisplayName("Character status checks work")
        void testAliveAndDead() {
            assertThat(character.isAlive()).isTrue();
            assertThat(character.isDead()).isFalse();

            character.setHealth(0);

            assertThat(character.isAlive()).isFalse();
            assertThat(character.isDead()).isTrue();
        }
    }

    @Nested
    @DisplayName("Character Mana Management")
    class ManaManagementTests {
        private Character character;

        @BeforeEach
        void setUp() {
            character = CharacterFactory.createMage("Mage");
        }

        @Test
        @DisplayName("Character uses mana")
        void testUseMana() {
            int initialMana = character.getStats().mana();

            character.useMana(10);

            assertThat(character.getStats().mana()).isEqualTo(initialMana - 10);
        }

        @Test
        @DisplayName("Character restores mana")
        void testRestoreMana() {
            character.useMana(30);
            int afterUse = character.getStats().mana();

            character.restoreMana(20);

            assertThat(character.getStats().mana()).isEqualTo(afterUse + 20);
        }

        @Test
        @DisplayName("Using more mana than available throws exception")
        void testInsufficientMana() {
            assertThatThrownBy(() -> character.useMana(1000))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("mana");
        }
    }

    @Nested
    @DisplayName("Character Combat")
    class CombatTests {
        private Character attacker;
        private Character defender;

        @BeforeEach
        void setUp() {
            attacker = CharacterFactory.createWarrior("Attacker");
            defender = CharacterFactory.createWarrior("Defender");
        }

        @Test
        @DisplayName("Character can attack")
        void testAttack() {
            int defendedHealth = defender.getStats().health();

            int damage = attacker.attack(defender);

            assertThat(damage).isGreaterThan(0);
        }

        @Test
        @DisplayName("Character can defend")
        void testDefend() {
            int incomingDamage = 100;

            int actualDamage = defender.defend(incomingDamage);

            assertThat(actualDamage)
                .isLessThan(incomingDamage)
                .isGreaterThanOrEqualTo(0);
        }

        @Test
        @DisplayName("Character takes damage")
        void testTakeDamage() {
            int initialHealth = defender.getStats().health();

            defender.takeDamage(50);

            assertThat(defender.getStats().health()).isLessThan(initialHealth);
        }
    }

    @Nested
    @DisplayName("Character Strategy Swapping")
    class StrategySwappingTests {
        private Character character;

        @BeforeEach
        void setUp() {
            character = CharacterFactory.createWarrior("warrior");
        }

        @Test
        @DisplayName("Character can change strategies at runtime")
        void testStrategySwapping() {
            character.setAttackStrategy(new MeleeAttackStrategy());
            character.setDefenseStrategy(new StandardDefenseStrategy());

            assertThat(character.getAttackStrategy()).isInstanceOf(MeleeAttackStrategy.class);
            assertThat(character.getDefenseStrategy()).isInstanceOf(StandardDefenseStrategy.class);
        }

        @Test
        @DisplayName("Changing null strategy throws exception")
        void testNullStrategyRejected() {
            assertThatThrownBy(() -> character.setAttackStrategy(null))
                .isInstanceOf(NullPointerException.class);

            assertThatThrownBy(() -> character.setDefenseStrategy(null))
                .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("Character String Representation")
    class StringRepresentationTests {
        @Test
        @DisplayName("Character toString includes relevant info")
        void testToString() {
            Character character = CharacterFactory.createWarrior("Hero");

            String str = character.toString();

            assertThat(str)
                .contains("Hero")
                .contains("WARRIOR")
                .contains("HP")
                .contains("ATK")
                .contains("DEF");
        }

        @Test
        @DisplayName("Character equality based on name and type")
        void testEquality() {
            Character char1 = CharacterFactory.createWarrior("Hero");
            Character char2 = CharacterFactory.createWarrior("Hero");
            Character char3 = CharacterFactory.createMage("Hero");

            assertThat(char1).isEqualTo(char2);
            assertThat(char1).isNotEqualTo(char3);
        }

        @Test
        @DisplayName("Character hashCode consistency")
        void testHashCode() {
            Character character = CharacterFactory.createWarrior("Hero");

            int hashBefore = character.hashCode();
            int hashAfter = character.hashCode();

            assertThat(hashBefore).isEqualTo(hashAfter);
        }
    }
}

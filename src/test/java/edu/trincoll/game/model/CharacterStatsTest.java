package edu.trincoll.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Character Stats Tests")
class CharacterStatsTest {

    @Nested
    @DisplayName("CharacterStats Creation")
    class CreationTests {
        @Test
        @DisplayName("Create stats with valid values")
        void testCreateStats() {
            CharacterStats stats = CharacterStats.create(100, 50, 20, 30);

            assertThat(stats.maxHealth()).isEqualTo(100);
            assertThat(stats.attackPower()).isEqualTo(50);
            assertThat(stats.defense()).isEqualTo(20);
            assertThat(stats.mana()).isEqualTo(30);
        }

        @Test
        @DisplayName("Initial health equals max health")
        void testInitialHealth() {
            CharacterStats stats = CharacterStats.create(100, 50, 20, 30);

            assertThat(stats.health()).isEqualTo(stats.maxHealth());
        }
    }

    @Nested
    @DisplayName("Health Management")
    class HealthTests {
        private CharacterStats stats;

        @BeforeEach
        void setUp() {
            stats = CharacterStats.create(100, 50, 20, 30);
        }

        @Test
        @DisplayName("Character is alive when health > 0")
        void testIsAlive() {
            assertThat(stats.isAlive()).isTrue();
        }

        @Test
        @DisplayName("Character is dead when health = 0")
        void testIsDead() {
            CharacterStats deadStats = stats.withHealth(0);
            assertThat(deadStats.isDead()).isTrue();
        }

        @Test
        @DisplayName("withHealth creates new instance with updated health")
        void testWithHealth() {
            CharacterStats updated = stats.withHealth(50);

            assertThat(updated.health()).isEqualTo(50);
            assertThat(updated.maxHealth()).isEqualTo(100);
            assertThat(updated.attackPower()).isEqualTo(50);
        }

        @Test
        @DisplayName("Health capped at max by withHealth")
        void testHealthCapAtMax() {
            CharacterStats updated = stats.withHealth(150);

            assertThat(updated.health()).isEqualTo(100);
        }

        @Test
        @DisplayName("Health can be set to zero")
        void testHealthToZero() {
            CharacterStats updated = stats.withHealth(0);

            assertThat(updated.isDead()).isTrue();
            assertThat(updated.health()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Mana Management")
    class ManaTests {
        private CharacterStats stats;

        @BeforeEach
        void setUp() {
            stats = CharacterStats.create(100, 50, 20, 30);
        }

        @Test
        @DisplayName("withMana creates new instance with updated mana")
        void testWithMana() {
            CharacterStats updated = stats.withMana(15);

            assertThat(updated.mana()).isEqualTo(15);
            assertThat(updated.health()).isEqualTo(100);
        }

        @Test
        @DisplayName("Mana can be set to zero")
        void testManaToZero() {
            CharacterStats updated = stats.withMana(0);

            assertThat(updated.mana()).isEqualTo(0);
        }

        @Test
        @DisplayName("Mana capped at max by withMana")
        void testManaCappedAtMax() {
            CharacterStats updated = stats.withMana(50);

            assertThat(updated.mana()).isEqualTo(30);
        }
    }

    @Nested
    @DisplayName("Attack Power")
    class AttackPowerTests {
        @Test
        @DisplayName("Attack power is accessible")
        void testAttackPower() {
            CharacterStats stats = CharacterStats.create(100, 60, 20, 30);

            assertThat(stats.attackPower()).isEqualTo(60);
        }

        @Test
        @DisplayName("Different attack powers create different stats")
        void testDifferentAttackPowers() {
            CharacterStats weak = CharacterStats.create(100, 10, 20, 30);
            CharacterStats strong = CharacterStats.create(100, 50, 20, 30);

            assertThat(strong.attackPower()).isGreaterThan(weak.attackPower());
        }
    }

    @Nested
    @DisplayName("Defense")
    class DefenseTests {
        @Test
        @DisplayName("Defense value is accessible")
        void testDefense() {
            CharacterStats stats = CharacterStats.create(100, 50, 25, 30);

            assertThat(stats.defense()).isEqualTo(25);
        }

        @Test
        @DisplayName("Different defense values create different stats")
        void testDifferentDefense() {
            CharacterStats light = CharacterStats.create(100, 50, 5, 30);
            CharacterStats heavy = CharacterStats.create(100, 50, 30, 30);

            assertThat(heavy.defense()).isGreaterThan(light.defense());
        }
    }

    @Nested
    @DisplayName("Validation Edge Cases")
    class EdgeCasesTests {
        @Test
        @DisplayName("Negative max health throws exception")
        void testNegativeMaxHealth() {
            assertThatThrownBy(() -> CharacterStats.create(-1, 50, 20, 30))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Zero max health throws exception")
        void testZeroMaxHealth() {
            assertThatThrownBy(() -> CharacterStats.create(0, 50, 20, 30))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Minimal valid stats")
        void testMinimalStats() {
            CharacterStats stats = CharacterStats.create(1, 0, 0, 0);

            assertThat(stats.maxHealth()).isEqualTo(1);
            assertThat(stats.health()).isEqualTo(1);
            assertThat(stats.isAlive()).isTrue();
        }
    }
}

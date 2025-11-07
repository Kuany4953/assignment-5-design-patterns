package edu.trincoll.game.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Game Demo Main Execution")
class GameDemoCallsTest {

    @Test
    @DisplayName("GameDemo main executes without errors")
    void testGameDemoMainExecution() {
        // Capture stdout
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            // Execute the demo
            GameDemo.main(new String[]{});

            // Restore stdout
            System.setOut(originalOut);

            // Get the output
            String output = outputStream.toString();

            // Verify output contains expected demo sections
            assertThat(output)
                .contains("DESIGN PATTERNS GAME DEMO")
                .contains("DEMO 1: FACTORY METHOD PATTERN")
                .contains("DEMO 2: STRATEGY PATTERN")
                .contains("DEMO 3: COMMAND PATTERN")
                .contains("DEMO 4: TEMPLATE METHOD PATTERN")
                .contains("DEMO 5: ALL PATTERNS WORKING TOGETHER")
                .contains("Demo complete");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("GameDemo demonstrates factory method pattern")
    void testGameDemoFactorySection() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            GameDemo.main(new String[]{});
            System.setOut(originalOut);

            String output = outputStream.toString();

            // Verify factory demo content
            assertThat(output)
                .contains("Conan")
                .contains("Gandalf")
                .contains("Legolas")
                .contains("Rogue");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("GameDemo demonstrates strategy pattern")
    void testGameDemoStrategySection() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            GameDemo.main(new String[]{});
            System.setOut(originalOut);

            String output = outputStream.toString();

            // Verify strategy demo content
            assertThat(output)
                .contains("MELEE")
                .contains("MAGIC")
                .contains("RANGED")
                .contains("interchangeable");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("GameDemo demonstrates command pattern")
    void testGameDemoCommandSection() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            GameDemo.main(new String[]{});
            System.setOut(originalOut);

            String output = outputStream.toString();

            // Verify command demo content
            assertThat(output)
                .contains("ATTACK")
                .contains("HEAL")
                .contains("Undoing");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("GameDemo demonstrates template method pattern")
    void testGameDemoTemplateMethodSection() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            GameDemo.main(new String[]{});
            System.setOut(originalOut);

            String output = outputStream.toString();

            // Verify template method demo content
            assertThat(output)
                .contains("STANDARD Battle")
                .contains("POWER ATTACK")
                .contains("recoil");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("GameDemo demonstrates patterns working together")
    void testGameDemoCombinedPatterns() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            GameDemo.main(new String[]{});
            System.setOut(originalOut);

            String output = outputStream.toString();

            // Verify combined demo content
            assertThat(output)
                .contains("ALL PATTERNS WORKING TOGETHER")
                .contains("Patterns Collaboration")
                .contains("flexible, maintainable code");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("GameDemo completes successfully")
    void testGameDemoCompletion() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            GameDemo.main(new String[]{});
            System.setOut(originalOut);

            String output = outputStream.toString();

            // Should not throw and should produce output
            assertThat(output)
                .isNotEmpty()
                .contains("complete")
                .contains("=".repeat(60));
        } finally {
            System.setOut(originalOut);
        }
    }
}

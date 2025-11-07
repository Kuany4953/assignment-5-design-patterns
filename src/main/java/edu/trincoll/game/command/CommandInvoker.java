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
package edu.trincoll.game.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Invoker for executing and managing commands.
 * Maintains command history for undo operations.
 *
 * TODO 4c: Implement executeCommand() and undoLastCommand()
 *
 * This class demonstrates the Command pattern's ability to:
 * - Queue commands for execution
 * - Maintain history
 * - Support undo operations
 */
public class CommandInvoker {
    private final Stack<GameCommand> commandHistory = new Stack<>();

    /**
     * TODO 4c: Implement executeCommand()
     *
     * Requirements:
     * 1. Execute the command: command.execute()
     * 2. Add the command to history: commandHistory.push(command)
     */
    public void executeCommand(GameCommand command) {
        command.execute();
        commandHistory.push(command);
    }

    /**
     * TODO 4c: Implement undoLastCommand()
     *
     * Requirements:
     * 1. Check if history is empty - if so, return
     * 2. Pop the last command from history
     * 3. Call undo() on that command
     */
   

    /**
     * Get the command history (for testing and logging).
     */
    public List<GameCommand> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    /**
     * Clear all command history.
     */
    public void clearHistory() {
        commandHistory.clear();
    }

    /**
     * Check if there are commands to undo.
     */
    public boolean hasCommandsToUndo() {
        return !commandHistory.isEmpty();
    }
}

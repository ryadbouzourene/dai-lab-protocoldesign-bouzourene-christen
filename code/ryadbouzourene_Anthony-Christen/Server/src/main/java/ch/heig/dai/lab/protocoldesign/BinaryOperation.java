/**
 * BinaryOperation represents mathematical operations supported by the server.
 * Each operation can be applied to two operands.
 *
 * @Authors Bouzourène Ryad & Christen Anthony
 * @Date    2024-11-08
 */

package ch.heig.dai.lab.protocoldesign;

public enum BinaryOperation {
    // ------------------------------------------------------------------------------
    // Values
    // ------------------------------------------------------------------------------
    ADD {
        @Override
        public double apply(double a, double b) {
            return a + b;
        }
    },
    MULTIPLY {
        @Override
        public double apply(double a, double b) {
            return a * b;
        }
    };

    // ------------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------------
    /**
     * Provides the formatted command syntax for the operation.
     * @return The command syntax format as a String.
     */
    public String getFormat() {
        return this + " <operand1> <operand2>";
    }

    /**
     * Parses a string to find the corresponding BinaryOperation.
     * @param name The string name of the operation.
     * @return The matching BinaryOperation.
     * @throws IllegalArgumentException If the operation name is unsupported.
     */
    public static BinaryOperation fromString(String name) {
        return BinaryOperation.valueOf(name.toUpperCase());
    }

    /**
     * Applies the operation to the two operands.
     * @param x The first operand.
     * @param y The second operand.
     * @return The result of the operation.
     */
    public abstract double apply(double x, double y);
}

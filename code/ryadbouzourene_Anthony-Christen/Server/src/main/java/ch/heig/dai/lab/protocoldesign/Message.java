/**
 * Message represents the types of messages that can be sent by the server.
 * Each message type supports formatting methods to structure the message
 * according to the protocol requirements.
 *
 * @Authors Bouzourène Ryad & Christen Anthony
 * @Date    2024-11-08
 */

package ch.heig.dai.lab.protocoldesign;

public enum Message {
    // ------------------------------------------------------------------------------
    // Values
    // ------------------------------------------------------------------------------
    GOODBYE,
    RESULT,
    ERROR;

    // ------------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------------
    /**
     * Formats the message as a simple string without additional content.
     * @return The formatted message as a String, ending with a newline.
     */
    public String formatMessage() {
        return this + "\n";
    }

    /**
     * Formats the message with additional content.
     * @param message The content to include in the message.
     * @return The formatted message as a String, ending with a newline.
     */
    public String formatMessage(String message) {
        return this + ": " + message + "\n";
    }
}

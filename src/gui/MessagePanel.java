package gui;
import javax.swing.*;
import java.awt.*;

/**
 * MessagePanel - Displays battle messages and game events
 * 
 * Shows the past two messages in a scrolling display area
 */
public class MessagePanel extends JPanel {
    
    private JLabel previousMessageLabel;
    private JLabel currentMessageLabel;
    
    // Visual constants
    private static final Color PANEL_BG = new Color(187, 124, 235);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color PREVIOUS_TEXT_COLOR = new Color(180, 180, 180);
    private static final Font MESSAGE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font PREVIOUS_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final int PANEL_HEIGHT = 80;
    
    /**
     * Constructor
     */
    public MessagePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(0, PANEL_HEIGHT));
        setBackground(PANEL_BG);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        // Previous message label (older, dimmed)
        previousMessageLabel = new JLabel("", SwingConstants.CENTER);
        previousMessageLabel.setFont(PREVIOUS_FONT);
        previousMessageLabel.setForeground(PREVIOUS_TEXT_COLOR);
        previousMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Current message label
        currentMessageLabel = new JLabel("Welcome to Monster Battle!", SwingConstants.CENTER);
        currentMessageLabel.setFont(MESSAGE_FONT);
        currentMessageLabel.setForeground(TEXT_COLOR);
        currentMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(previousMessageLabel);
        add(Box.createVerticalStrut(5));
        add(currentMessageLabel);
    }
    
    /**
     * Set the message to display (moves current to previous)
     * @param message The message text
     */
    public void setMessage(String message) {
        // Move current message to previous
        previousMessageLabel.setText(currentMessageLabel.getText());
        previousMessageLabel.setForeground(PREVIOUS_TEXT_COLOR);
        
        // Set new current message
        currentMessageLabel.setText(message);
        currentMessageLabel.setForeground(TEXT_COLOR);
    }
    
    /**
     * Set message with custom color
     * @param message The message text
     * @param color The text color
     */
    public void setMessage(String message, Color color) {
        // Move current message to previous
        previousMessageLabel.setText(currentMessageLabel.getText());
        previousMessageLabel.setForeground(PREVIOUS_TEXT_COLOR);
        
        // Set new current message with color
        currentMessageLabel.setText(message);
        currentMessageLabel.setForeground(color);
    }
    
    /**
     * Clear all messages
     */
    public void clearMessage() {
        previousMessageLabel.setText("");
        currentMessageLabel.setText("");
    }
}
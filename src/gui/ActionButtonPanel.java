package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ActionButtonPanel - Four customizable action buttons
 * 
 * Provides four large, attractive buttons that students can customize
 * for different game actions (Attack, Defend, Heal, etc.)
 */
public class ActionButtonPanel extends JPanel {
    
    private JButton[] buttons;
    private ActionListener actionListener;
    
    // Default button labels
    private static final String[] DEFAULT_LABELS = {
        "Action 1", "Action 2", "Action 3", "Action 4"
    };
    
    // Visual constants
    private static final Color BUTTON_BG = new Color(0, 0, 0);
    private static final Color BUTTON_HOVER = new Color(10, 10, 10);
    private static final Color BUTTON_PRESSED = new Color(50, 50, 50);
    private static final Color BUTTON_DISABLED = new Color(100, 100, 100);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    
    /**
     * Constructor
     * @param listener ActionListener to handle button clicks
     */
    public ActionButtonPanel(ActionListener listener) {
        this.actionListener = listener;
        this.buttons = new JButton[4];
        
        setLayout(new GridLayout(1, 4, 10, 0));
        setBackground(new Color(40, 40, 50));
        
        createButtons();
    }
    
    /**
     * Create the four action buttons
     */
    private void createButtons() {
        for (int i = 0; i < 4; i++) {
            buttons[i] = createStyledButton(DEFAULT_LABELS[i], i);
            add(buttons[i]);
        }
    }
    
    /**
     * Create a styled button
     * @param label Button label
     * @param index Button index (0-3)
     * @return The styled button
     */
    private JButton createStyledButton(String label, int index) {
        JButton button = new JButton(label);
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_BG);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        button.setActionCommand(String.valueOf(index));
        button.addActionListener(actionListener);
        
        // Add hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_HOVER);
                    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_BG);
                    button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_PRESSED);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(BUTTON_HOVER);
                }
            }
        });
        
        return button;
    }
    
    /**
     * Set custom labels for the buttons
     * @param labels Array of 4 labels
     */
    public void setButtonLabels(String[] labels) {
        if (labels.length != 4) {
            throw new IllegalArgumentException("Must provide exactly 4 labels");
        }
        
        for (int i = 0; i < 4; i++) {
            buttons[i].setText(labels[i]);
        }
    }
    
    /**
     * Enable or disable all buttons
     * @param enabled true to enable, false to disable
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (JButton button : buttons) {
            button.setEnabled(enabled);
            button.setBackground(enabled ? BUTTON_BG : BUTTON_DISABLED);
        }
    }
    
    /**
     * Enable or disable a specific button
     * @param index Button index (0-3)
     * @param enabled true to enable, false to disable
     */
    public void setButtonEnabled(int index, boolean enabled) {
        if (index >= 0 && index < 4) {
            buttons[index].setEnabled(enabled);
            buttons[index].setBackground(enabled ? BUTTON_BG : BUTTON_DISABLED);
        }
    }
}
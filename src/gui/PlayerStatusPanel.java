package gui;
import javax.swing.*;
import java.awt.*;

/**
 * PlayerStatusPanel - Displays the player's health and stats
 * 
 * Shows a large, prominent health bar for the player with visual indicators
 */
public class PlayerStatusPanel extends JPanel {
    
    private int currentHealth;
    private int maxHealth;
    private JLabel healthLabel;
    private HealthBar healthBar;
    
    // Visual constants
    private static final Color PANEL_BG = new Color(187, 124, 235);
    private static final Color HEALTH_BAR_BG = new Color(40, 40, 50);
    private static final Color HEALTH_BAR_FULL = new Color(50, 205, 50);
    private static final Color HEALTH_BAR_MED = new Color(255, 165, 0);
    private static final Color HEALTH_BAR_LOW = new Color(220, 20, 60);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font HEALTH_FONT = new Font("Arial", Font.BOLD, 20);
    private static final int BAR_HEIGHT = 30;
    
    /**
     * Constructor with default max health
     */
    public PlayerStatusPanel() {
        this(100);
    }
    
    /**
     * Constructor with custom max health
     * @param maxHealth The maximum health value
     */
    public PlayerStatusPanel(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(PANEL_BG);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        initializeComponents();
    }
    
    /**
     * Initialize panel components
     */
    private void initializeComponents() {
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("PLAYER", SwingConstants.LEFT);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.BLACK);
        
        healthLabel = new JLabel(currentHealth + " / " + maxHealth + " HP", SwingConstants.RIGHT);
        healthLabel.setFont(HEALTH_FONT);
        healthLabel.setForeground(Color.BLACK);
        
        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(healthLabel, BorderLayout.EAST);
        
        // Health bar
        healthBar = new HealthBar();
        
        // Add to panel
        add(titlePanel, BorderLayout.NORTH);
        add(healthBar, BorderLayout.CENTER);
    }
    
    /**
     * Update the player's health
     * @param currentHealth The current health value
     */
    public void setHealth(int currentHealth) {
        this.currentHealth = Math.max(0, Math.min(maxHealth, currentHealth));
        healthLabel.setText(this.currentHealth + " / " + maxHealth + " HP");
        
        // Update label color based on health
        double healthPercent = (double) this.currentHealth / maxHealth;
        if (healthPercent > 0.5) {
            healthLabel.setForeground(Color.WHITE);
        } else if (healthPercent > 0.25) {
            healthLabel.setForeground(new Color(255, 200, 100));
        } else {
            healthLabel.setForeground(new Color(255, 100, 100));
        }
        
        healthBar.repaint();
    }
    
    /**
     * Set the maximum health
     * @param maxHealth The new maximum health value
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        setHealth(currentHealth); // Update display
    }
    
    /**
     * Get current health
     * @return The current health value
     */
    public int getHealth() {
        return currentHealth;
    }
    
    /**
     * Get maximum health
     * @return The maximum health value
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * Visual health bar component
     */
    private class HealthBar extends JPanel {
        
        public HealthBar() {
            setPreferredSize(new Dimension(0, BAR_HEIGHT));
            setBackground(HEALTH_BAR_BG);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            int width = getWidth();
            int height = getHeight();
            
            // Calculate health percentage
            double healthPercent = (double) currentHealth / maxHealth;
            int barWidth = (int) (width * healthPercent);
            
            // Choose color based on health percentage
            Color barColor;
            if (healthPercent > 0.5) {
                barColor = HEALTH_BAR_FULL;
            } else if (healthPercent > 0.25) {
                barColor = HEALTH_BAR_MED;
            } else {
                barColor = HEALTH_BAR_LOW;
            }
            
            // Draw health bar with gradient
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Create gradient for depth effect
            GradientPaint gradient = new GradientPaint(
                0, 0, barColor.brighter(),
                0, height, barColor
            );
            g2d.setPaint(gradient);
            g2d.fillRect(2, 2, barWidth - 4, height - 4);
            
            // Draw percentage text
            String percentText = String.format("%.0f%%", healthPercent * 100);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(percentText);
            int textHeight = fm.getAscent();
            g2d.drawString(percentText, 
                (width - textWidth) / 2, 
                (height + textHeight) / 2 - 2);
        }
    }
}
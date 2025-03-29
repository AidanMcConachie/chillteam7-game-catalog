package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class modernButton extends JButton {
    // Color scheme that matches your UI
    private static final Color PRIMARY_COLOR = Color.DARK_GRAY; // Your main teal
    private static final Color HOVER_COLOR = Color.decode("#5a9ca1");  // Lighter teal
    private static final Color PRESSED_COLOR = Color.decode("#3a666a"); // Darker teal
    private static final Color TEXT_COLOR = Color.WHITE;

    public modernButton(String text) {
        super(text);
        applyModernStyle();
    }

    private void applyModernStyle() {
        // Basic styling
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);

        // Colors
        setBackground(PRIMARY_COLOR);
        setForeground(TEXT_COLOR);

        // Font
        setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Sizing
        setPreferredSize(new Dimension(120, 40));
        setMaximumSize(new Dimension(120, 40));

        // Add some padding
        setMargin(new Insets(5, 15, 5, 15));


        // Hover and press effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(PRIMARY_COLOR);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(PRESSED_COLOR);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(HOVER_COLOR);
            }
        });
    }
}
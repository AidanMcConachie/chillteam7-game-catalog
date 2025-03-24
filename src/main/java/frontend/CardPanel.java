package frontend;

import backend.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;

public class CardPanel extends JPanel {
    private Card card;
    private BufferedImage image;

    public CardPanel(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(150, 250)); // Set preferred size for the card
        setBackground(Color.darkGray);
        setLayout(new BorderLayout()); // Use BorderLayout for positioning

        // Load the image from the URL
        try {
            URL imageUrl = new URL(card.getImageUrl());
            image = ImageIO.read(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            // If the image fails to load, use a placeholder or leave it null
        }

        // Create a semi-transparent background panel for the title
        JPanel titleBackgroundPanel = new JPanel();
        titleBackgroundPanel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black (alpha = 150)
        titleBackgroundPanel.setLayout(new BorderLayout());

        // Create the title label
        JLabel titleLabel = new JLabel(card.getName());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE); // Set text color to white
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text

        // Add the label to the background panel
        titleBackgroundPanel.add(titleLabel, BorderLayout.CENTER);

        // Add the background panel to the bottom of the card
        add(titleBackgroundPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            // Calculate the scaled dimensions to fill the frame vertically
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            // Calculate scaling factor to fill the height
            double scale = (double) panelHeight / imageHeight;

            // Calculate scaled width and height
            int scaledWidth = (int) (imageWidth * scale);
            int scaledHeight = panelHeight; // Fill the height

            // Center the image horizontally
            int x = (panelWidth - scaledWidth) / 2;
            int y = 0; // Align to the top

            // Draw the scaled image
            g.drawImage(image, x, y, scaledWidth, scaledHeight, this);
        }
    }
}
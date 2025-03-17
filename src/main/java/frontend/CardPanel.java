package frontend;

import backend.Card;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CardPanel extends JPanel {
    private Card card;

    public CardPanel(Card card) {
        this.card = card;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(150, 250)); // Adjust as needed
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.setBackground(Color.DARK_GRAY);

        // Title label
        JLabel titleLabel = new JLabel(card.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        // Genre label
        String genreText = String.join(", ", card.getGenres()); // Convert array to a comma-separated string
        JLabel genreLabel = new JLabel("Genre: " + genreText, SwingConstants.CENTER);
        genreLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        genreLabel.setForeground(Color.WHITE);


        // Description area
        JTextArea descriptionArea = new JTextArea(card.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);
        descriptionArea.setBackground(getBackground());
        descriptionArea.setForeground(Color.white);


        // Image label
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setImage(imageLabel, card.getImageUrl());
        


        // Layout structure
        add(titleLabel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(genreLabel, BorderLayout.SOUTH);


        // Add description in a scroll pane (optional)
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(230, 80));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void setImage(JLabel imageLabel, String imageUrl) {
        try {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                ImageIcon icon = new ImageIcon(new URL(imageUrl));
                Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setText("No Image Available");
            }
        } catch (Exception e) {
            imageLabel.setText("Error Loading Image");
        }
    }
}
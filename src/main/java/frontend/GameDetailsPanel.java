package frontend;

import backend.Card;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;
import backend.Database;
import frontend.catalogUI;
import frontend.catalogUI;



public class GameDetailsPanel extends JPanel {
    private catalogUI parentUI;
    public GameDetailsPanel(Card card, List<Card> gameList, Database database, catalogUI parentUI) {
        this.parentUI = parentUI;
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        // Back button panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Game image
        try {
            URL imageUrl = new URL(card.getImageUrl());
            BufferedImage image = ImageIO.read(imageUrl);
            ImageIcon icon = new ImageIcon(image.getScaledInstance(300, -1, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(imageLabel);
            contentPanel.setBackground(Color.DARK_GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Game details
        JLabel nameLabel = new JLabel(card.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(nameLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel genreLabel = new JLabel("Genres: " + String.join(", ", card.getGenres()));
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genreLabel.setForeground(Color.WHITE);
        contentPanel.add(genreLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextArea descriptionArea = new JTextArea(card.getDescription());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.decode("#47797d"));
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(scrollPane);

        add(contentPanel, BorderLayout.CENTER);

        // Back button action
        backButton.addActionListener(e -> {parentUI.returnToMainScreen();
        });
    }
}
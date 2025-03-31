package frontend;

import backend.Card;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import backend.CatalogDatabase;
import backend.ReviewDatabase;
import frontend.catalogUI;
import frontend.catalogUI;



public class GameDetailsPanel extends JPanel {
    private catalogUI parentUI;
    public GameDetailsPanel(Card card, List<Card> gameList, CatalogDatabase database, ReviewDatabase reviews, catalogUI parentUI) {
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
        JLabel nameIDPriceLabel = new JLabel(card.getName() + " (" + card.getId() + "): " + card.getPrice());
        nameIDPriceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameIDPriceLabel.setForeground(Color.WHITE);
        nameIDPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(nameIDPriceLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel genreLabel = new JLabel("Genres: " + String.join(", ", card.getGenres()));
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genreLabel.setForeground(Color.WHITE);

        JLabel developerPublisherLabel = new JLabel("Developers: " + String.join(", ", card.getDevelopers()) + "\t, Publishers: " + String.join(", ", card.getPublishers()));
        developerPublisherLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        developerPublisherLabel.setForeground(Color.WHITE);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));


        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center'>" + card.getDescription() + "</div></html>");
        descriptionLabel.setBackground(Color.decode("#47797d"));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setOpaque(true); // Required to show background color

        contentPanel.add(descriptionLabel);
        descriptionLabel.setBackground(Color.decode("#47797d"));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // REVIEW STUFF
        JPanel reviewPanel = new JPanel();
        reviewPanel.setBackground(Color.GRAY);
        reviewPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        reviewPanel.setMaximumSize(new Dimension(600, 200));

        //
        JTextPane infoText = new JTextPane();
        infoText.setEditable(false);
        infoText.setText("Add a review: ");
        infoText.setBackground(Color.GRAY);
        JPanel reviewArea = new JPanel();
        reviewArea.setLayout(new BoxLayout(reviewArea, BoxLayout.Y_AXIS));
        JScrollPane reviewScrollPane = new JScrollPane(reviewArea);
        reviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        reviewScrollPane.setPreferredSize(new Dimension(300, 300));

        JTextField usernameField = new JTextField("Enter Username", 12);
        Integer[] ratings = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        JComboBox<Integer> rating = new JComboBox<>(ratings);
        rating.setSelectedIndex(4);

        JTextArea descriptionField = new JTextArea(3, 12);
        descriptionField.setText("Enter Additional Information...");
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        JButton addReviewButton = new JButton("Add Review");
        loadReviews(Integer.parseInt(card.getId()), reviewArea, reviews);


        addReviewButton.addActionListener(e -> {
            try {
                reviews.addReview(Integer.parseInt(card.getId()), usernameField.getText(), (int)rating.getSelectedItem(), descriptionField.getText());
                usernameField.setText("");
                rating.setSelectedIndex(4);
                descriptionField.setText("");
                loadReviews(Integer.parseInt(card.getId()), reviewArea, reviews);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        reviewPanel.add(infoText);
        reviewPanel.add(usernameField);
        reviewPanel.add(rating);
        reviewPanel.add(descriptionField);
        reviewPanel.add(addReviewButton);

        contentPanel.add(descriptionLabel);
        contentPanel.add(genreLabel);
        contentPanel.add(developerPublisherLabel);
        contentPanel.add(reviewPanel);
        contentPanel.add(reviewScrollPane);

        add(contentPanel, BorderLayout.CENTER);

        // Back button action
        backButton.addActionListener(e -> {parentUI.returnToMainScreen();
        });
    }

    private void loadReviews(int id, JPanel reviewArea, ReviewDatabase reviews) {
        try{
            reviewArea.removeAll();
            ArrayList<String[]> reviewsdb = reviews.getReviews(id);
            for(String[] review : reviewsdb){
                JPanel reviewCard = new JPanel();
                reviewCard.setLayout(new BorderLayout());
                reviewCard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                JLabel nameDateScorePanel = new JLabel(review[0] + " (" + review[3] + "): " + review[1] +"/10");
                JTextArea description = new JTextArea(3, 12);
                description.setText(review[2]);
                description.setLineWrap(true);
                description.setWrapStyleWord(true);
                description.setEditable(false);

                reviewCard.add(nameDateScorePanel, BorderLayout.NORTH);
                reviewCard.add(description, BorderLayout.CENTER);
                reviewArea.add(reviewCard);
            }
            reviewArea.revalidate();
            reviewArea.repaint();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
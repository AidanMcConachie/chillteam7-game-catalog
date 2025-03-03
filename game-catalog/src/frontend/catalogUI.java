package frontend;

import backend.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class catalogUI extends JFrame {
    private JPanel cardContainer;

    public catalogUI() {
        setTitle("Video Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
//        setBackground(Color.BLACK);

        cardContainer = new JPanel();
        cardContainer.setBackground(Color.decode("#47797d"));
        cardContainer.setPreferredSize(new Dimension(500, 400));


        cardContainer.setLayout(new FlowLayout()); // Adjust layout when needed

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.setPreferredSize(new Dimension(650, 450));
        scrollPane.setBackground(Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));

        add(scrollPane);

        setVisible(true);
    }

    // Add a list of cards to the ui
    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            cardContainer.add(new CardPanel(card));
        }
        cardContainer.revalidate(); // Refresh UI
    }
}
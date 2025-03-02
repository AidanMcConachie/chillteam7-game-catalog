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
        setLocationRelativeTo(null);

        cardContainer = new JPanel();
        cardContainer.setLayout(new FlowLayout()); // Adjust layout when needed

        JScrollPane scrollPane = new JScrollPane(cardContainer);
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
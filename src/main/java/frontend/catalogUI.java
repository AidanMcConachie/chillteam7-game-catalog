package frontend;

import backend.Card;
import backend.SortGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class catalogUI extends JFrame {
    private JPanel cardContainer;
    private JComboBox<String> sortDropdown;
    private JComboBox<String> orderDropdown;
    private JButton filterButton;
    private JComboBox<String> genreDropdown;
    private List<Card> gameList;
    private List<Card> displayedList;

    public catalogUI(List<Card> gameList) {
        this.gameList = gameList;
        this.displayedList = gameList; // ✅ Initially, display all games
        SortGame.setOriginalList(gameList); // ✅ Sets original list in SortGame

        setTitle("Video Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600)); // ✅ Set minimum window size
        setLocationRelativeTo(null);

        // ✅ Sorting UI
        JPanel sortingPanel = new JPanel();
        sortingPanel.add(new JLabel("Sort by:"));

        sortDropdown = new JComboBox<>(new String[]{"Name", "Genre", "ID"});
        sortingPanel.add(sortDropdown);

        sortingPanel.add(new JLabel("Order:"));
        orderDropdown = new JComboBox<>(new String[]{"Ascending", "Descending"});
        sortingPanel.add(orderDropdown);

        filterButton = new JButton("Apply Filter");
        sortingPanel.add(filterButton);

        genreDropdown = new JComboBox<>();
        genreDropdown.setVisible(false);
        sortingPanel.add(genreDropdown);

        add(sortingPanel, BorderLayout.NORTH);

        // ✅ Card Display Panel (With Updated Layout & Background Color)
        cardContainer = new JPanel();
        cardContainer.setBackground(Color.decode("#47797d")); // ✅ New background color
        cardContainer.setPreferredSize(new Dimension(500, 400)); // ✅ Adjusted panel size
        cardContainer.setLayout(new FlowLayout()); // ✅ Layout remains flexible

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.setPreferredSize(new Dimension(650, 450)); // ✅ Adjusted size
        scrollPane.setBackground(Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));

        add(scrollPane, BorderLayout.CENTER);

        displayGames();

        // ✅ Filter Button (Switch Between "Apply" and "Revert")
        filterButton.addActionListener(e -> {
            if (SortGame.isFilterActive()) {
                displayedList = SortGame.revertFilter();
                filterButton.setText("Apply Filter");
            } else {
                applySorting();
            }
            displayGames();
        });

        // ✅ Genre Selection (Filter Games by Genre)
        genreDropdown.addActionListener(e -> {
            String selectedGenre = (String) genreDropdown.getSelectedItem();
            displayedList = SortGame.filterByGenre(selectedGenre);
            filterButton.setText("Revert Filter");
            displayGames();
        });

        setVisible(true);
    }

    /**
     * ✅ Call `SortGame.sortGames()` with Sorting Order
     * - By default, everything is sorted in ascending order.
     * - Since genre is sorted differently than ID and Name, we turn off genre filters unless sorting by genre.
     */
    private void applySorting() {
        String criteria = (String) sortDropdown.getSelectedItem();
        boolean ascending = orderDropdown.getSelectedItem().equals("Ascending");

        // ✅ Genre dropdown is only visible when sorting by Genre
        if (criteria.equals("Genre")) {
            genreDropdown.setVisible(true);
            updateGenreDropdown();
        } else {
            genreDropdown.setVisible(false);
            displayedList = SortGame.sortGames(displayedList, criteria.toLowerCase(), ascending);
            displayGames();
        }
    }

    /**
     * ✅ Dynamically Adds Genre to `JComboBox`
     * - Ensures that duplicate genres are not added.
     */
    private void updateGenreDropdown() {
        genreDropdown.removeAllItems();
        genreDropdown.addItem("All Genres");

        for (Card card : gameList) {
            if (card.getGenre() != null && !genreDropdownContains(card.getGenre())) {
                genreDropdown.addItem(card.getGenre());
            }
        }

        if (genreDropdown.getItemCount() > 0) {
            genreDropdown.setSelectedIndex(0);
        }
    }

    /**
     * ✅ Checks if a game contains a genre within our `JComboBox`
     * - Helps `updateGenreDropdown()` avoid duplicates and only add new genres.
     */
    private boolean genreDropdownContains(String genre) {
        for (int i = 0; i < genreDropdown.getItemCount(); i++) {
            if (genreDropdown.getItemAt(i).equals(genre)) {
                return true; // ✅ Genre already exists
            }
        }
        return false; // ✅ Genre is new
    }

    /**
     * ✅ Adds a List of Cards to the UI After Creation
     */
    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            cardContainer.add(new CardPanel(card));
        }
        cardContainer.revalidate(); // ✅ Refresh UI
    }

    /**
     * ✅ Displays the Current List of Games
     */
    private void displayGames() {
        cardContainer.removeAll(); // ✅ Clear previous game cards
        for (Card card : displayedList) {
            cardContainer.add(new CardPanel(card)); // ✅ Add each card to UI
        }
        cardContainer.revalidate(); // ✅ Refresh UI
        cardContainer.repaint(); // ✅ Redraw components
    }
}

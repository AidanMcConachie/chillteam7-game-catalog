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
        this.displayedList = gameList; // Initially, display all games
        SortGame.setOriginalList(gameList); //Sets original list in sort game funcTIon

        setTitle("Video Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sorting UI
        JPanel sortingPanel = new JPanel();
        sortingPanel.add(new JLabel("Sort by:"));

        //maybe add different filters later on? ex price
        sortDropdown = new JComboBox<>(new String[]{"Name", "Genre", "ID"});
        sortingPanel.add(sortDropdown);

        sortingPanel.add(new JLabel("Order:"));
        orderDropdown = new JComboBox<>(new String[]{"Ascending", "Descending"});
        sortingPanel.add(orderDropdown);

        filterButton = new JButton("Apply Filter");
        sortingPanel.add(filterButton);

        //make a JComboBox for all the possible genres
        genreDropdown = new JComboBox<>();
        genreDropdown.setVisible(false);
        sortingPanel.add(genreDropdown);

        add(sortingPanel, BorderLayout.NORTH);

        // Card Display Panel
        cardContainer = new JPanel();
        cardContainer.setLayout(new FlowLayout()); // Adjust layout when needed

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        add(scrollPane, BorderLayout.CENTER);

        displayGames();


        //needs to change button behaviour because there would/can be different buttons for different filter status
        //ex: genre would have a revert button so that users can revert the current filter to see all the games again and/or use another filter

        filterButton.addActionListener(e -> {
            if (SortGame.isFilterActive()) {
                displayedList = SortGame.revertFilter();
                filterButton.setText("Apply Filter");
            } else {
                applySorting();
            }
            displayGames();
        });

        genreDropdown.addActionListener(e -> {
            String selectedGenre = (String) genreDropdown.getSelectedItem();
            displayedList = SortGame.filterByGenre(selectedGenre);
            filterButton.setText("Revert Filter");
            displayGames();
        });

        setVisible(true);
    }

    //Call `SortGame.sortGames()` with Sorting Order
    //by default sets everything to ascending order
    //Since genre is being sorted differntly than id and name, we would have to turn the special ...
    // ... filters made for genre off unless we are sorting by genre itself


    private void applySorting() {
        String criteria = (String) sortDropdown.getSelectedItem();
        boolean ascending = orderDropdown.getSelectedItem().equals("Ascending");

        //The differet genres are available if user chooses to sort by genre, othewise its not diplayed

        if (criteria.equals("Genre")) {
            genreDropdown.setVisible(true);
            updateGenreDropdown();
        } else {
            genreDropdown.setVisible(false);
            displayedList = SortGame.sortGames(displayedList, criteria.toLowerCase(), ascending);
            displayGames();
        }
    }


     //dynamically adds genre to our JComboBox
     // Ensures that duplicate genres are not added.
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

    //checks if a game contains a genre within our genre JComboBox list
    //this to help the updateGenreDropdown make sure we dont have duplicates and add genres that werent added
    private boolean genreDropdownContains(String genre) {
        for (int i = 0; i < genreDropdown.getItemCount(); i++) {
            if (genreDropdown.getItemAt(i).equals(genre)) {
                return true;
            }
        }
        return false;
    }

    // Add a list of cards to the ui
    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            cardContainer.add(new CardPanel(card));
        }
        cardContainer.revalidate(); // Refresh UI
    }

    private void displayGames() {
        cardContainer.removeAll();
        for (Card card : displayedList) {
            cardContainer.add(new CardPanel(card));
        }
        cardContainer.revalidate();
        cardContainer.repaint();
    }
}

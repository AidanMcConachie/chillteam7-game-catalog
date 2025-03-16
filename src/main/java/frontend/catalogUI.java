package frontend;

import backend.Card;
import backend.SortGame;
import backend.SearchGames;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class catalogUI extends JFrame {
    private JPanel cardContainer;
    private JComboBox<String> sortDropdown;
    private JComboBox<String> orderDropdown;
    private JButton filterButton;
    private JButton revertFilterButton;
    private JComboBox<String> genreDropdown;
    private JTextField searchField; //IMPLEMENTED: Search Bar
    private JButton searchButton;   //IMPLEMENTED: Search Button
    private List<Card> gameList;
    private List<Card> displayedList;

    public catalogUI(List<Card> gameList) {
        this.gameList = gameList;
        this.displayedList = gameList;
        SortGame.setOriginalList(gameList);

        setTitle("Video Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

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

        revertFilterButton = new JButton("Revert Filter");
        revertFilterButton.setVisible(false);
        sortingPanel.add(revertFilterButton);

        // Update: Search Bar
        searchField = new JTextField(15);
        sortingPanel.add(searchField);

        // Update: Search Button
        searchButton = new JButton("Search");
        sortingPanel.add(searchButton);

        add(sortingPanel, BorderLayout.NORTH);

        cardContainer = new JPanel();
        cardContainer.setBackground(Color.decode("#47797d"));
        cardContainer.setLayout(new FlowLayout());

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        add(scrollPane, BorderLayout.CENTER);

        displayGames();

        // Update: Calls Backend Instead of Handling Logic Here
        filterButton.addActionListener(e -> {
            String criteria = (String) sortDropdown.getSelectedItem();
            boolean ascending = orderDropdown.getSelectedItem().equals("Ascending");

            if (criteria.equals("Genre")) {
                genreDropdown.setVisible(true);
                updateGenreDropdown();
            } else {
                genreDropdown.setVisible(false);
                displayedList = SortGame.sortGames(displayedList, criteria.toLowerCase(), ascending);
                revertFilterButton.setVisible(false);
                displayGames();
            }
        });

        // Update: Calls Backend Instead of Handling Logic Here
        genreDropdown.addActionListener(e -> {
            String selectedGenre = (String) genreDropdown.getSelectedItem();
            displayedList = SortGame.filterByGenre(selectedGenre);
            revertFilterButton.setVisible(true);
            displayGames();
        });

        // Update: Calls Backend Instead of Handling Logic Here
        revertFilterButton.addActionListener(e -> {
            displayedList = SortGame.revertFilter();
            revertFilterButton.setVisible(false);
            genreDropdown.setVisible(false);
            displayGames();
        });

        //IMPLEMENTED: Search Button Functionality (Calls Backend)
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            displayedList = SearchGames.searchByName(searchQuery, gameList);
            displayGames();
        });

        setVisible(true);
    }

    //Update: Calls Backend to Get Unique Genres

    //For further implementation:
    //add back a genreDropdownContains() so it can help with adding games dynamically

    private void updateGenreDropdown() {
        Set<String> uniqueGenres = SortGame.getUniqueGenres();

        genreDropdown.removeAllItems();
        genreDropdown.addItem("All Genres");
        for (String genre : uniqueGenres) {
            genreDropdown.addItem(genre);
        }
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

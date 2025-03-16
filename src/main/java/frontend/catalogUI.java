package frontend;

import backend.Card;
import backend.SortGame;
import backend.SearchGames;

import backend.Database;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class catalogUI extends JFrame {
    private JPanel cardContainer;
    private JPanel sidePanel;
    private JPanel sortingPanel;
    private JComboBox<String> sortDropdown;
    private JComboBox<String> orderDropdown;
    private JButton filterButton;
    private JButton revertFilterButton;
    private JComboBox<String> genreDropdown;
    private JTextField searchField; //IMPLEMENTED: Search Bar
    private JButton searchButton;   //IMPLEMENTED: Search Button
    private List<Card> gameList;
    private List<Card> displayedList;
    private Database database;

    public catalogUI(List<Card> gameList, Database database) {
        this.gameList = gameList;
        this.displayedList = gameList; // Initially, display all games
        this.database = new Database();
        SortGame.setOriginalList(gameList); // Sets original list in SortGame
        this.displayedList = gameList;
        SortGame.setOriginalList(gameList);

        setTitle("Video Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600)); // Set minimum window size
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);
        this.setBackground(Color.DARK_GRAY);

        // Side Panel (Contains Add Game Button)
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(100, 600));
        sidePanel.setBackground(Color.DARK_GRAY);
        sidePanel.setLayout(new BorderLayout());

        JButton addGameButton = new JButton("Add Game");
        sidePanel.add(addGameButton, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST); // Add to the right

        addGameButton.addActionListener(e -> showAddGameScreen());

        // Sorting UI
        sortingPanel = new JPanel();
        sortingPanel.add(new JLabel("Sort by:"));
        sortingPanel.setBackground(Color.decode("#47797d"));

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

        // Card Display Panel (For Game Cards)
        cardContainer = new JPanel();
        cardContainer.setBackground(Color.decode("#47797d"));
        cardContainer.setLayout(new GridLayout(0, 3, 10, 10)); // 0 rows (dynamic), 3 columns, 10px spacing

        JScrollPane scrollPane = new JScrollPane(cardContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(650, 450));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);

        add(scrollPane, BorderLayout.CENTER);

        displayGames();

        // Filter Button Logic
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
    /**
     * Switches to the "Add Game" screen.
     */
    private void showAddGameScreen() {
        JPanel addGamePanel = new JPanel();
        addGamePanel.setBackground(Color.decode("#47797d"));
        addGamePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Title Label
        JLabel titleLabel = new JLabel("Add a New Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        addGamePanel.add(titleLabel, gbc);

        // Steam ID Label
        gbc.gridy++;
        JLabel steamIDLabel = new JLabel("Enter Steam ID:");
        addGamePanel.add(steamIDLabel, gbc);

        // Steam ID Input Field
        gbc.gridx = 1;
        JTextField steamIDField = new JTextField(15);
        addGamePanel.add(steamIDField, gbc);

        // Submit Button
        gbc.gridy++;
        gbc.gridx = 0;
        JButton submitButton = new JButton("Submit");
        addGamePanel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String steamIDText = steamIDField.getText().trim();

            if (!steamIDText.isEmpty()) {
                try {
                    int steamID = Integer.parseInt(steamIDText);
                    database.addGame(steamID);  // Use existing Database instance

                    String[] gameInfo = database.fetchAllGameInfo(steamID);
                    if (gameInfo != null && gameInfo.length >= 4) {
                        Card newCard = new Card(gameInfo[1], new String[]{gameInfo[5]}, gameInfo[0], gameInfo[2], gameInfo[3]);
                        gameList.add(newCard);
//                        displayedList.add(newCard);

                        displayGames(); // Refresh the UI

                        returnToMainScreen();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error fetching game info.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Steam ID! Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a Steam ID.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });


        // Back Button (Returns to Main Screen)
        gbc.gridx = 1;
        JButton backButton = new JButton("Back");
        addGamePanel.add(backButton, gbc);

        // Clear UI and Add New Panel
        getContentPane().removeAll();
        add(addGamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        // Back button functionality
        backButton.addActionListener(e -> returnToMainScreen());
    }

    /**
     * Restores the main catalog screen.
     */
    private void returnToMainScreen() {
        getContentPane().removeAll();

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.setPreferredSize(new Dimension(650, 450));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));
        scrollPane.getViewport().setBackground(Color.DARK_GRAY); // Ensure background consistency
        scrollPane.setBackground(Color.DARK_GRAY);

        // Restore the main components
        add(scrollPane, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);
        add(sortingPanel, BorderLayout.NORTH);

        revalidate();
        repaint();
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

    /**
     * Checks if a genre is already in the dropdown.
     */
    private boolean genreDropdownContains(String genre) {
        for (int i = 0; i < genreDropdown.getItemCount(); i++) {
            if (genreDropdown.getItemAt(i).equals(genre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds game cards to the UI.
     */
    public void addCards(List<Card> cards) {
        for (Card card : cards) {
            cardContainer.add(new CardPanel(card));
            cardContainer.setBackground(Color.darkGray);
        }
        cardContainer.revalidate();
    }

    /**
     * Displays the current list of games.
     */
    private void displayGames() {
        cardContainer.removeAll();
        for (Card card : displayedList) {
            cardContainer.add(new CardPanel(card));
        }
        cardContainer.revalidate();
        cardContainer.repaint();
    }
}
package frontend;

import backend.Card;
import backend.SortGame;
import backend.SearchGames;

import backend.Database;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class catalogUI extends JFrame {
    private JPanel cardContainer; // The panel of all the displayed games
    private JPanel sidePanel; // the right most panel containing functional buttons (add game, remove game, view wishlist)
    private JPanel sortingPanel; // The pannel containing all the filter functions
    private JPanel topPanel; // the top panel containing the application title and sort/search functions
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
    private String blue = "#47797d";

    public catalogUI(List<Card> gameList, Database database) {
        this.gameList = gameList;
        this.displayedList = gameList; // Initially, display all games
        this.database = new Database();
        SortGame.setOriginalList(gameList); // Sets original list in SortGame
        this.displayedList = gameList;
        SortGame.setOriginalList(gameList);

        setTitle("Video Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setMinimumSize(new Dimension(850, 600)); // Set minimum window size
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);
        this.setBackground(Color.DARK_GRAY);

        ArrayList<Integer> allGames = database.getAllGameIDs();


        for(int i = 0; i < allGames.size(); i++){
            String[] gameInfo = database.fetchAllGameInfo(allGames.get(i));
            Card newCard = new Card(gameInfo[1], new String[]{gameInfo[5]}, gameInfo[0], gameInfo[2], gameInfo[3]);
            gameList.add(newCard);
        }

        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(100, 600));
        sidePanel.setBackground(Color.decode(blue)); // Keeping as is

// Use BoxLayout for better height control
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton addGameButton = new JButton("Add Game");
        JButton removeGameButton = new JButton("Remove Game");

// Set fixed height while allowing width expansion
        addGameButton.setMaximumSize(new Dimension(100, 20));
        removeGameButton.setMaximumSize(new Dimension(100, 20));

// Center align buttons horizontally
        addGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

// Add buttons with spacing
        sidePanel.add(addGameButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 5))); // Small gap
        sidePanel.add(removeGameButton);



        add(sidePanel, BorderLayout.EAST); // Add to the right


        addGameButton.addActionListener(e -> showAddGameScreen());

        // Sorting UI
        sortingPanel = new JPanel();
        sortingPanel.add(new JLabel("Sort by:"));
        sortingPanel.setBackground(Color.decode(blue));

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


        //LIVE SEARCH: Updates as user types
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                liveSearch();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                liveSearch();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                liveSearch();
            }

            private void liveSearch() {
                String searchQuery = searchField.getText().trim();
                if (searchQuery.isEmpty()) {
                    displayedList = gameList;
                } else {
                    displayedList = SearchGames.searchByName(searchQuery, gameList);
                }
                displayGames();
            }
        });

        // Create a panel for the title and logo
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center elements
        titlePanel.setBackground(Color.decode(blue)); // Match theme

// Title Label
        JLabel titleLabel = new JLabel("Gavin's Games");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

// Logo Label (Placeholder Image)
        ImageIcon logoIcon = new ImageIcon("src/main/logo/logo1.png"); // Ensure this file exists in your project
        Image img = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setMaximumSize(new Dimension(50, 50));


// Add title panel between sortingPanel and game cards
        topPanel = new JPanel(new BorderLayout());
        topPanel.add(sortingPanel, BorderLayout.CENTER);
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(logoLabel, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);





        // Card Display Panel (For Game Cards)
        cardContainer = new JPanel();
        cardContainer.setBackground(Color.decode(blue));
        cardContainer.setLayout(new GridLayout(0, 4, 10, 10)); // 0 rows (dynamic), 3 columns, 10px spacing
        cardContainer.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JScrollPane scrollPane = new JScrollPane(cardContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(650, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));
        scrollPane.setBackground(Color.DARK_GRAY);

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(20); // Default is usually 1-5
        verticalBar.setBlockIncrement(100);

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
        addGamePanel.setBackground(Color.decode(blue));
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

        JScrollPane scrollPane = new JScrollPane(cardContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(650, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));
        scrollPane.setBackground(Color.DARK_GRAY);

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(20); // Default is usually 1-5
        verticalBar.setBlockIncrement(100);

        // Restore the main components
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

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
            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setMaximumSize(new Dimension(150, 250)); // Prevent stretching
            wrapper.add(new CardPanel(card));

            cardContainer.add(wrapper);
        }

        cardContainer.revalidate();
        cardContainer.repaint();
    }

}
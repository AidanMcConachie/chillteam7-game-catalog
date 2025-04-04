package frontend;

import backend.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.awt.event.*;

public class catalogUI extends JFrame {
    private JPanel cardContainer; // The panel of all the displayed games
    private JPanel sidePanel; // the right most panel containing functional buttons (add game, remove game, view wishlist)
    private JPanel sortingPanel; // The panel containing all the filter functions
    private JPanel topPanel; // the top panel containing the application title and sort/search functions
    private JComboBox<String> sortDropdown;
    private JComboBox<String> orderDropdown;
    private JButton filterButton;
    private JButton revertFilterButton;
    private JComboBox<String> genreDropdown;
    private JTextField searchField; // Search Bar
    private JButton searchButton;   // Search Button
    private List<Card> gameList;
    public List<Card> displayedList;
    private List<Card> preLoadList;
    private CatalogDatabase database;
    private ReviewDatabase reviews;
    private String blue = "#47797d";


    public catalogUI(List<Card> gameList, CatalogDatabase database, ReviewDatabase reviews) {
        this.gameList = gameList;
        this.displayedList = gameList; // Initially, display all games
        this.database = database;
        this.reviews = reviews;

//        for(Card card : gameList){
//            System.out.println(card.getName());
//        }

        ArrayList<Integer> allGames = database.getAllGameIDs();
        for(int i = 0; i < allGames.size(); i++){
            String[] gameInfo = database.fetchAllGameInfo(allGames.get(i));
            Card newCard = new Card(gameInfo[1], new String[]{gameInfo[5]}, gameInfo[0], gameInfo[2], gameInfo[3], gameInfo[6], gameInfo[7], gameInfo[4]);
            gameList.add(newCard);
        }


        SortGame.setOriginalList(gameList); // Sets original list in SortGame

        setTitle("Video Game Catalog");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1025, 600);
        setMinimumSize(new Dimension(1025, 600)); // Set minimum window size
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);
        this.setBackground(Color.DARK_GRAY);

        // Initialize UI components
        initializeUI();

        displayGames();
        setVisible(true);
    }

    private void initializeUI() {
        // Side Panel
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(100, 600));
        sidePanel.setBackground(Color.decode(blue));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton addGameButton = new modernButton("Add");
        JButton removeGameButton = new modernButton("Remove");

        addGameButton.setMaximumSize(new Dimension(100, 20));
        removeGameButton.setMaximumSize(new Dimension(100, 20));

        addGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidePanel.add(addGameButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        sidePanel.add(removeGameButton);

        add(sidePanel, BorderLayout.EAST);

        // Sorting Panel
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

        searchField = new JTextField(15);
        sortingPanel.add(searchField);

        searchButton = new JButton("Search");
        sortingPanel.add(searchButton);

        // Top Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.decode(blue));

        JLabel titleLabel = new JLabel("Gavin's Games");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // used as refresher when search filtering

                displayedList = gameList;
                returnToMainScreen();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                titleLabel.setForeground(Color.CYAN);  // change color when hovered
                titleLabel.setText("<html><u>Gavin's Games</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                titleLabel.setForeground(Color.WHITE); // reset color when mouse exits
                titleLabel.setText("Gavin's Games");
            }
        });

        titlePanel.add(titleLabel);

        topPanel = new JPanel(new BorderLayout());
        topPanel.add(sortingPanel, BorderLayout.CENTER);
        topPanel.add(titlePanel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);

        // Card Container
        cardContainer = new JPanel();
        cardContainer.setBackground(Color.decode(blue));
        cardContainer.setLayout(new GridBagLayout()); // Use GridBagLayout for flexible resizing
        cardContainer.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JScrollPane scrollPane = new JScrollPane(cardContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(650, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(40, 100, 20, 100));
        scrollPane.setBackground(Color.DARK_GRAY);

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(20); // Default is usually 1-5
        verticalBar.setBlockIncrement(100);

        add(scrollPane, BorderLayout.CENTER);

        // Add Game Button Action Listener
        addGameButton.addActionListener(e -> showAddGameScreen());
        removeGameButton.addActionListener(e -> showRemoveGameScreen());

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

        // Genre Dropdown Logic
        genreDropdown.addActionListener(e -> {
            String selectedGenre = (String) genreDropdown.getSelectedItem();
            if (selectedGenre != null && !selectedGenre.equals("All Genres")) {
                displayedList = SortGame.filterByGenre(selectedGenre);
            } else {
                displayedList = gameList;
            }
            revertFilterButton.setVisible(true);
            displayGames();
        });

        // Revert Filter Logic
        revertFilterButton.addActionListener(e -> {
            displayedList = gameList;
            revertFilterButton.setVisible(false);
            genreDropdown.setVisible(false);
            displayGames();
        });

        // Search Button Logic
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            displayedList = SearchGames.searchByName(searchQuery, gameList);
            displayGames();
        });
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
                    if(!database.isInDatabase(steamID)) {

                        database.addGame(steamID);  // Use existing CatalogDatabase instance
                        String[] gameInfo = database.fetchAllGameInfo(steamID);
                        if (gameInfo != null && gameInfo.length >= 4) {
                            Card newCard = new Card(gameInfo[1], new String[]{gameInfo[5]}, gameInfo[0], gameInfo[2], gameInfo[3], gameInfo[6], gameInfo[7], gameInfo[4]);
                            gameList.add(newCard);
                            displayedList = gameList; // Update displayedList to include the new game
                            displayGames(); // Refresh the UI

                            returnToMainScreen();
                            JOptionPane.showMessageDialog(this, "Game has been added succesfully", "Game Added", JOptionPane.INFORMATION_MESSAGE);

                        } else {
                            JOptionPane.showMessageDialog(this, "Error fetching game info.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Game already in Cataloge", "Warning", JOptionPane.WARNING_MESSAGE);
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

    private void showRemoveGameScreen(){
        JPanel removeGamePanel = new JPanel();
        removeGamePanel.setBackground(Color.decode(blue));
        removeGamePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Title Label
        JLabel titleLabel = new JLabel("Remove a Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        removeGamePanel.add(titleLabel, gbc);

        // Steam ID Label
        gbc.gridy++;
        JLabel steamIDLabel = new JLabel("Enter Steam ID:");
        removeGamePanel.add(steamIDLabel, gbc);

        // Steam ID Input Field
        gbc.gridx = 1;
        JTextField steamIDField = new JTextField(15);
        removeGamePanel.add(steamIDField, gbc);

        // Submit Button
        gbc.gridy++;
        gbc.gridx = 0;
        JButton submitButton = new JButton("Submit");
        removeGamePanel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String steamIDText = steamIDField.getText().trim();

            if (!steamIDText.isEmpty()) {
                try {
                    int steamID = Integer.parseInt(steamIDText);  // Parsing the Steam ID as integer

                    // Remove the game from the database
                    database.removeGame(steamID);

                    // Remove the game from the displayed list
                    for (Card card : displayedList) {
                        if (Objects.equals(card.getId(), steamIDText)) {
                            gameList.remove(card);
                            displayedList = gameList;  // Update displayed list
                            displayGames();  // Refresh the game display
                            returnToMainScreen();  // Navigate back to main screen
                            JOptionPane.showMessageDialog(this, "Game has been removed successfully", "Game Removed", JOptionPane.INFORMATION_MESSAGE);
                            return;  // Exit after removal
                        }
                    }

                    // If the game wasn't found in the displayed list
                    JOptionPane.showMessageDialog(this, "Game not found in the displayed list", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (NumberFormatException ex) {
                    // Handle invalid number format input
                    JOptionPane.showMessageDialog(this, "Invalid Steam ID. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // Catch any other unexpected errors
                    JOptionPane.showMessageDialog(this, "Error removing game, please try again", "Unexpected Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Handle empty Steam ID field
                JOptionPane.showMessageDialog(this, "Error fetching game info. Steam ID field is empty.", "Error: Empty Field", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back Button (Returns to Main Screen)
        gbc.gridx = 1;
        JButton backButton = new JButton("Back");
        removeGamePanel.add(backButton, gbc);

        // Clear UI and Add New Panel
        getContentPane().removeAll();
        add(removeGamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        // Back button functionality
        backButton.addActionListener(e -> returnToMainScreen());
    }



    public void showGameDetails(Card card) {
        getContentPane().removeAll();
        add(new GameDetailsPanel(card, gameList, database, reviews , this));
        revalidate();
        repaint();
    }

    /**
     * Restores the main catalog screen.
     */
    public void returnToMainScreen() {
        getContentPane().removeAll();

        // Recreate the cardContainer to ensure fresh state
        cardContainer = new JPanel();
        cardContainer.setBackground(Color.decode(blue));
        cardContainer.setLayout(new GridBagLayout());
        cardContainer.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // Repopulate the games
        displayGames();

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

    /**
     * Updates the genre dropdown with unique genres.
     */
    private void updateGenreDropdown() {
        Set<String> uniqueGenres = SortGame.getUniqueGenres();

        genreDropdown.removeAllItems();
        genreDropdown.addItem("All Genres");
        for (String genre : uniqueGenres) {
            genreDropdown.addItem(genre);
        }
    }

    /**
     * Displays the current list of games.
     */
    private void displayGames() {
//        System.out.println("Displaying " + displayedList.size() + " games");
//        for (Card card : displayedList) {
//            System.out.println(" - " + card.getName());
//        }
        cardContainer.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (Card card : displayedList) {
            CardPanel cardPanel = new CardPanel(card, this); // Make sure 'this' is passed here
            gbc.gridx = (cardContainer.getComponentCount() % 4);
            gbc.gridy = (cardContainer.getComponentCount() / 4);
            cardContainer.add(cardPanel, gbc);
        }

        cardContainer.revalidate();
        cardContainer.repaint();
    }




    public List<Card> getdatabaseGameList() {
        return gameList;
    }
}
package backend;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortGame {

    private static List<Card> originalList; // Store the original game list
    private static boolean isFiltered = false; // Track if filtering is active

    // Initialize with original game list (Call this in `Main.java`)
    public static void setOriginalList(List<Card> games) {
        originalList = games;
    }

    //Handles sorting for Name & ID (in Ascending/Descending order)
    // As well as Genre (specific genre)
    public static List<Card> sortGames(List<Card> games, String criteria, boolean ascending) {
        Comparator<Card> comparator;

        switch (criteria.toLowerCase()) {
            case "name":
                comparator = Comparator.comparing(Card::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "genre":
                comparator = Comparator.comparing(Card::getGenre, String.CASE_INSENSITIVE_ORDER);
                break;
            case "id":
                comparator = Comparator.comparingInt(game -> Integer.parseInt(game.getId()));
                break;
            default:
                System.out.println("Invalid sorting criteria. Sorting by name as default.");
                comparator = Comparator.comparing(Card::getName, String.CASE_INSENSITIVE_ORDER);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return games.stream().sorted(comparator).collect(Collectors.toList());
    }

    //Returns only games that match a specific genre chosen by the user
    public static List<Card> filterByGenre(String genre) {
        if (originalList == null) return null; // prevent null issues if the game list becomes null for some reason

        if (genre == null || genre.equalsIgnoreCase("All Genres")) {
            isFiltered = false;
            return originalList; // Show all games
        }

        isFiltered = true;
        return originalList.stream()
                .filter(game -> genre.equalsIgnoreCase(game.getGenre()))
                .collect(Collectors.toList());
    }

    //Resets the game list
    public static List<Card> revertFilter() {
        isFiltered = false;
        return originalList;
    }

    //Check if Filtering is Active
    public static boolean isFilterActive() {
        return isFiltered;
    }
}
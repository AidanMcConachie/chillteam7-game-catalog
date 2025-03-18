package backend;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SortGame {
    private static List<Card> originalList;
    private static boolean isFiltered = false;

    public static void setOriginalList(List<Card> games) {
        originalList = games;
    }

    // Handles Sorting (Name, ID, Genre)
    public static List<Card> sortGames(List<Card> games, String criteria, boolean ascending) {
        Comparator<Card> comparator;

        switch (criteria.toLowerCase()) {
            case "name":
                comparator = Comparator.comparing(Card::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "id":
                comparator = Comparator.comparingInt(game -> Integer.parseInt(game.getId()));
                break;
            case "genre":
                comparator = Comparator.comparing(game -> String.join(", ", game.getGenres()), String.CASE_INSENSITIVE_ORDER);
                break;
            default:
                comparator = Comparator.comparing(Card::getName, String.CASE_INSENSITIVE_ORDER);
        }

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return games.stream().sorted(comparator).collect(Collectors.toList());
    }

    //Handles Genre Filtering
    public static List<Card> filterByGenre(String genre) {
        if (originalList == null) return null;

        if (genre == null || genre.equalsIgnoreCase("All Genres")) {
            isFiltered = false;
            return originalList;
        }

        isFiltered = true;
        return originalList.stream()
                .filter(game -> {
                    for (String g : game.getGenres()) {
                        if (genre.equalsIgnoreCase(g)) return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    // Handles Reverting Genre Filter
    public static List<Card> revertFilter() {
        isFiltered = false;
        return originalList;
    }

    //Checks if Filtering is Active
//    public static boolean isFilterActive() {
//        return isFiltered;
//    }

    //Generates Unique Genres for Dropdown
    public static Set<String> getUniqueGenres() { //makes sure that the dropdown doesnt recieve gamelist duplicate genres
        Set<String> uniqueGenres = new HashSet<>(); //hashset to ensure uniqueness
        for (Card card : originalList) {
            for (String genre : card.getGenres()) {
                uniqueGenres.add(genre);
            }
        }
        return uniqueGenres;
    }
}
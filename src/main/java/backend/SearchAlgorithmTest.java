package backend;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchAlgorithmTest {
    private List<Card> testCards;

    @Test
    public void testAlgorithm() {
        String search = "F";
        testCards = new ArrayList<>();
        Card finalFantasyCard = new Card("Final Fantasy", new String[]{"RPG"}, "5", "Fantasy role-playing game", "ff.jpg", "Square Enix", "Square Enix", "$10");
        testCards.add(finalFantasyCard);
        List<Card> results = SearchGames.searchByName(search, testCards);

        // Initialize the original list in SortGame
        assertEquals(testCards, results);
    }

}

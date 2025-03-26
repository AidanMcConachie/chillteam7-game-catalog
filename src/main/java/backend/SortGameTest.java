package backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SortGameTest {

    private List<Card> testCards;

    @BeforeEach
    public void setUp() {
        // Create a test list of cards before each test
        testCards = new ArrayList<>();
        testCards.add(new Card("Zelda", new String[]{"Adventure"}, "2", "A legend of Zelda game", "zelda.jpg"));
        testCards.add(new Card("Mario", new String[]{"Platformer"}, "1", "Super Mario Bros game", "mario.jpg"));
        testCards.add(new Card("Tetris", new String[]{"Puzzle"}, "3", "Classic block puzzle game", "tetris.jpg"));
        testCards.add(new Card("Final Fantasy", new String[]{"RPG"}, "5", "Fantasy role-playing game", "ff.jpg"));
        testCards.add(new Card("Counter-Strike", new String[]{"FPS"}, "4", "First-person shooter game", "cs.jpg"));
        testCards.add(new Card("Age of Empires", new String[]{"Strategy"}, "6", "Historical real-time strategy game", "aoe.jpg"));
        testCards.add(new Card("Minecraft", new String[]{"Adventure"}, "7", "Block building sandbox game", "minecraft.jpg"));

        // Initialize the original list in SortGame
        SortGame.setOriginalList(new ArrayList<>(testCards));
    }

    @Test
    public void testSetOriginalList() {
        // Test that reverting filter returns the original list
        List<Card> result = SortGame.revertFilter();
        assertEquals(testCards.size(), result.size());
        for (int i = 0; i < testCards.size(); i++) {
            assertEquals(testCards.get(i).getId(), result.get(i).getId());
        }
    }

    @Test
    public void testSortGamesByNameAscending() {
        List<Card> sorted = SortGame.sortGames(testCards, "name", true);

        assertEquals("Age of Empires", sorted.get(0).getName());
        assertEquals("Counter-Strike", sorted.get(1).getName());
        assertEquals("Final Fantasy", sorted.get(2).getName());
        assertEquals("Zelda", sorted.get(sorted.size() - 1).getName());
    }

    @Test
    public void testSortGamesByNameDescending() {
        List<Card> sorted = SortGame.sortGames(testCards, "name", false);

        assertEquals("Zelda", sorted.get(0).getName());
        assertEquals("Tetris", sorted.get(1).getName());
        assertEquals("Age of Empires", sorted.get(sorted.size() - 1).getName());
    }


    @Test
    public void testSortGamesByIdAscending() {
        List<Card> sorted = SortGame.sortGames(testCards, "id", true);

        assertEquals("1", sorted.get(0).getId());
        assertEquals("2", sorted.get(1).getId());
        assertEquals("7", sorted.get(sorted.size() - 1).getId());
    }

    @Test
    public void testSortGamesByIdDescending() {
        List<Card> sorted = SortGame.sortGames(testCards, "id", false);

        assertEquals("7", sorted.get(0).getId());
        assertEquals("6", sorted.get(1).getId());
        assertEquals("1", sorted.get(sorted.size() - 1).getId());
    }

    @Test
    public void testSortGamesWithInvalidCriteria() {
        List<Card> sorted = SortGame.sortGames(testCards, "invalid_criteria", true);

        // Should default to sorting by name
        assertEquals("Age of Empires", sorted.get(0).getName());
        assertEquals("Counter-Strike", sorted.get(1).getName());
    }

    @Test
    public void testFilterByGenreAdventure() {
        List<Card> filtered = SortGame.filterByGenre("Adventure");

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().allMatch(card -> Arrays.equals(card.getGenres(), new String[]{"Adventure"})));
    }

    @Test
    public void testFilterByGenreFPS() {
        List<Card> filtered = SortGame.filterByGenre("FPS");

        assertEquals(1, filtered.size());
        assertEquals("Counter-Strike", filtered.get(0).getName());
    }

    @Test
    public void testFilterByNonExistentGenre() {
        List<Card> filtered = SortGame.filterByGenre("NonExistentGenre");

        assertEquals(0, filtered.size());
    }

    @Test
    public void testFilterByAllGenres() {
        List<Card> filtered = SortGame.filterByGenre("All Genres");

        assertEquals(testCards.size(), filtered.size());
    }

    @Test
    public void testFilterByNullGenre() {
        List<Card> filtered = SortGame.filterByGenre(null);

        assertEquals(testCards.size(), filtered.size());
    }

    @Test
    public void testRevertFilter() {
        // First apply a filter
        SortGame.filterByGenre("RPG");

        // Then revert it
        List<Card> result = SortGame.revertFilter();

        assertEquals(testCards.size(), result.size());
    }

    @Test
    public void testNullOriginalList() {
        // Create a new SortGame instance with null original list
        SortGame.setOriginalList(null);

        // Test filterByGenre with null original list
        List<Card> result = SortGame.filterByGenre("Adventure");
        assertNull(result);
    }
}

package backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardConstructorAndGetters() {
        // Arrange
        String name = "Zelda";
        String[] genre = {"Adventure"};
        String id = "1";
        String description = "A legend of Zelda game";
        String imageUrl = "zelda.jpg";

        // Act
        Card card = new Card(name, genre, id, description, imageUrl);

        // Assert
        assertEquals(name, card.getName(), "getName should return the correct name");
        assertEquals(genre, card.getGenres(), "getGenres should return the correct genre");
        assertEquals(id, card.getId(), "getId should return the correct id");
        assertEquals(description, card.getDescription(), "getDescription should return the correct description");
        assertEquals(imageUrl, card.getImageUrl(), "getImageUrl should return the correct imageUrl");
    }

    @Test
    public void testCardConstructorWithNullValues() {
        // Arrange & Act
        Card card = new Card(null, null, null, null, null);

        // Assert
        assertNull(card.getName(), "getName should return null");
        assertNull(card.getGenres(), "getGenres should return null");
        assertNull(card.getId(), "getId should return null");
        assertNull(card.getDescription(), "getDescription should return null");
        assertNull(card.getImageUrl(), "getImageUrl should return null");
    }

    @Test
    public void testCardConstructorWithEmptyStrings() {
        // Arrange & Act
        Card card = new Card("", new String[]{""}, "", "", "");

        // Assert
        assertEquals("", card.getName(), "getName should return empty string");
        assertArrayEquals(new String[]{""}, card.getGenres(), "getGenres should return an array with an empty string");
        assertEquals("", card.getId(), "getId should return empty string");
        assertEquals("", card.getDescription(), "getDescription should return empty string");
        assertEquals("", card.getImageUrl(), "getImageUrl should return empty string");
    }

    @Test
    public void testToString() {
        // Arrange
        Card card = new Card("Zelda", new String[]{"Adventure"}, "1", "A legend of Zelda game", "zelda.jpg");

        // Act
        String result = card.toString();

        // Assert
        assertTrue(result.contains("name='Zelda'"), "toString should contain the name");
        assertTrue(result.contains("Adventure"), "toString should contain the genre");
        assertTrue(result.contains("id='1'"), "toString should contain the id");
        assertTrue(result.contains("description='A legend of Zelda game'"), "toString should contain the description");
        // Note: imageUrl is commented out in the toString method, so we don't check for it
        assertFalse(result.contains("imageUrl='zelda.jpg'"), "toString should not contain the imageUrl as it's commented out");
    }

    @Test
    public void testToStringWithNullValues() {
        // Arrange
        Card card = new Card(null, null, null, null, null);

        // Act
        String result = card.toString();

        // Assert
        assertTrue(result.contains("name='null'"), "toString should handle null name");
        assertTrue(result.contains("null"), "toString should handle null genre");
        assertTrue(result.contains("id='null'"), "toString should handle null id");
        assertTrue(result.contains("description='null'"), "toString should handle null description");
    }

    @Test
    public void testToStringWithEmptyStrings() {
        // Arrange
        Card card = new Card("", new String[]{""}, "", "", "");

        // Act
        String result = card.toString();

        // Assert
        assertTrue(result.contains("name=''"), "toString should handle empty name");
        assertTrue(result.contains(""), "toString should handle empty genre");
        assertTrue(result.contains("id=''"), "toString should handle empty id");
        assertTrue(result.contains("description=''"), "toString should handle empty description");
    }

    @Test
    public void testToStringWithSpecialCharacters() {
        // Arrange
        Card card = new Card("Name with spaces", new String[]{"Action", "Adventure"}, "ID-123",
                "Description with special chars: !@#$%^&*()", "image/url.jpg");

        // Act
        String result = card.toString();

        // Assert
        assertTrue(result.contains("name='Name with spaces'"), "toString should handle spaces in name");
        assertTrue(result.contains("Action"), "toString should handle special chars in genre");
        assertTrue(result.contains("id='ID-123'"), "toString should handle hyphens in id");
        assertTrue(result.contains("description='Description with special chars: !@#$%^&*()'"),
                "toString should handle special characters in description");
    }
}

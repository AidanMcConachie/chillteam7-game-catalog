package backend;

import java.util.Arrays;

public class Card {
    private String name;
    private String[] genres;
    private String id;
    private String description;
    private String imageUrl;

    public Card(String name, String[] genres, String id, String description, String imageUrl) {
        this.name = name;
        this.genres = genres;
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String[] getGenres() { return genres; }
    public String getId() { return id; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", genres=" + Arrays.toString(genres) +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
package backend;

import java.util.Arrays;

public class Card {
    private String name;
    private String[] genres;
    private String id;
    private String description;
    private String imageUrl;
    private String developers;
    private String publishers;
    private String price;

    public Card(String name, String[] genres, String id, String description, String imageUrl, String developers, String publishers, String price) {
        this.name = name;
        this.genres = genres;
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.developers = developers;
        this.publishers = publishers;
        this.price = price;
    }

    public String getName() { return name; }
    public String[] getGenres() { return genres; }
    public String getId() { return id; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getDevelopers() { return developers; }
    public String getPublishers() { return publishers; }
    public String getPrice() { return price; }

    // TODO: Rewrite this for test cases
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

package backend;

public class Card {
    private String name;
    private String genre;
    private String id;
    private String description;
    private String imageUrl;  //maybe use when it works ?

    public Card(String name, String genre, String id, String description, String imageUrl) {
        this.name = name;
        this.genre = genre;
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    //get the card components
    public String getName() { return name; }
    public String getGenre() { return genre; }
    public String getId() { return id; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                //        ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
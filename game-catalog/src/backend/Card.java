package backend;

public class Card {
    public String name;
    public String genre;
    public String id;
    public String description;
    public String imageUrl; //maybe use when it works ?

    public Card(String name, String genre, String id, String description, String imageUrl) {
        this.name = name;
        this.genre = genre;
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", id='" + id + '\'' +
               ", description='" + description + '\'' +
//                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}

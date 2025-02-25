package backend;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class SteamAPIFetcher {
    public SteamAPIFetcher() {

    }
    String[] info = new String[7];

    public String[] fetchGameData(int steamID) throws Exception{
        URL url = new URL("https://store.steampowered.com/api/appdetails?appids=" + steamID);
        StringBuilder stringBuilder = new StringBuilder();
        try(InputStream input = url.openStream()) {
           InputStreamReader reader = new InputStreamReader(input);
           BufferedReader bufferedReader = new BufferedReader(reader);
           String line;
           while((line = bufferedReader.readLine()) != null ) {
               stringBuilder.append(line);
           }
       }
        JSONObject jsonData = new JSONObject(stringBuilder.toString());
        if(!jsonData.getJSONObject(String.valueOf(steamID)).getBoolean("success")){ // might be able to remove this
            return null;
        }
        JSONObject data = jsonData.getJSONObject(String.valueOf(steamID)).getJSONObject("data");
        String id = steamID + "";
        String name = data.getString("name");
        String description = data.getString("short_description");
        String headerImage = data.getString("header_image"); // more rectangular
        JSONArray genres = data.getJSONArray("genres");
        JSONArray developers = data.getJSONArray("developers");
        JSONArray publishers = data.getJSONArray("publishers");

        // Just to make the genres format better
        JSONArray genreArray = new JSONArray();
        for(int i = 0; i < genres.length(); i++){
            JSONObject genre = genres.getJSONObject(i);
            genreArray.put(genre.getString("description"));
        }
        // There might be a better way to implement this below to add to database
        info[0] = id;
        info[1] = name;
        info[2] = description;
        info[3] = headerImage;
        info[4] = genreArray.toString();
        info[5] = developers.toString();
        info[6] = publishers.toString();
        return info;
    }
    // Perhaps we have a toString method, but not ideal for database fetching
    @Override
    public String toString() {
        return Arrays.toString(info);
    }
}

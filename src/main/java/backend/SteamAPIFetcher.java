/*
YOU SHOULD NOT BE CALLING THESE METHODS! SEE DATABASE.JAVA
 */

package backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Arrays;

public class SteamAPIFetcher {
    public SteamAPIFetcher() {

    }
    String[] info = new String[8];

    public String[] fetchGameData(int steamID) throws Exception{
        JSONObject price; // for error handling later
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
        // Price error handling might need to be redone
        try {
            price = data.getJSONObject("price_overview");
        } catch (JSONException e) {
            price = null;
        }
        JSONArray genres = data.getJSONArray("genres");
        JSONArray developers = data.getJSONArray("developers");
        JSONArray publishers = data.getJSONArray("publishers");

        // Just to make the genres format better
        JSONArray genreArray = new JSONArray();
        for(int i = 0; i < genres.length(); i++){
            JSONObject genre = genres.getJSONObject(i);
            genreArray.put(genre.getString("description"));
        }
        // This approach is not ideal, but it works. Should optimize it for later
        float price_final = 0;
        if(price != null) {
            int price_cents = price.getInt("final");
            price_final = ((float) price_cents) / 100;
        }
        // There might be a better way to implement this below to add to database
        info[0] = id;
        info[1] = name;
        info[2] = description;
        info[3] = headerImage;
        info[4] = "$" + String.format("%.2f", price_final);
        info[5] = genreArray.toString();
        info[6] = developers.toString();
        info[7] = publishers.toString();
        return info;
    }
    // TODO: add a steam status function formally
}

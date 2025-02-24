package backend;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class SteamAPIFetcher {
    public SteamAPIFetcher() {

    }
    String[] info = new String[6];

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
        JSONObject data = jsonData.getJSONObject(String.valueOf(steamID)).getJSONObject("data");
        String id = steamID + "";
        String name = data.getString("name");
        String description = data.getString("short_description");
        String headerImage = data.getString("header_image"); // more rectangular
        JSONArray developers = data.getJSONArray("developers");
        JSONArray publishers = data.getJSONArray("publishers");
        // There might be a better way to implement this below to add to database
        info[0] = id;
        info[1] = name;
        info[2] = description;
        info[3] = headerImage;
        info[4] = developers.toString();
        info[5] = publishers.toString();
        return info;
    }
    // Perhaps we have a toString method, but not ideal for database fetching
}

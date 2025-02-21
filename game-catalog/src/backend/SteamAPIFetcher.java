package backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class SteamAPIFetcher {
    public SteamAPIFetcher() {

    }
    public String[] fetchGameData(int steamID) throws Exception{
       URL url = new URL("https://store.steampowered.com/api/appdetails?appids=" + steamID);
       try(InputStream input = url.openStream()) {
           InputStreamReader reader = new InputStreamReader(input);
           BufferedReader bufferedReader = new BufferedReader(reader);
           StringBuilder stringBuilder = new StringBuilder();
           String line;
           while((line = bufferedReader.readLine()) != null ) {
               stringBuilder.append(line);
           }
       }
        JSONObject data = new JSONObject(stringBuilder);
        String[] info = {};
        String id = steamID + "";
        String name = data.getString("name");
        String description = data.getString("short_description");
        String headerImage = data.getString("header_image"); // more rectangular
        String developers = data.getString("developers");
        String publishers = data.getString("publishers");
        info[0] = id;
        info[1] = name;
        info[2] = description;
        info[3] = headerImage;
        info[4] = developers;
        info[5] = publishers;
        return info;
    }
}

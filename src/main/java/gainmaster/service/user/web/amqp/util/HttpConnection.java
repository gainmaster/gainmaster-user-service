package gainmaster.service.user.web.amqp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by lorre on 4/13/15.
 */
public class HttpConnection {


    public static String getResponse(String path) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }

            reader.close();
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JsonNode valueNode = rootNode.path("value");
        return valueNode.get(0).toString();
    }
}

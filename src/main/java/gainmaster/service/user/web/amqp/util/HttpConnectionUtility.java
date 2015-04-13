package gainmaster.service.user.web.amqp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by lorre on 4/13/15.
 */

public class HttpConnectionUtility {

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
        System.out.println("HttpConnectionUtility->getResponse: " + response.toString());
        return response.toString();
    }

    public static String getJSONNode(String path, String node){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            String response = getResponse(path);
            if(response == null) return null;
            rootNode = objectMapper.readTree(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        JsonNode selectedNode = rootNode.path(node);
        System.out.println("HttpConnectionUtility->getJSONNode: " + selectedNode.get(0).toString());
        return selectedNode.get(0).toString();
    }
}

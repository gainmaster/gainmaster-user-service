package gainmaster.service.user.amqp.util;

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
        System.out.println("HttpConnectionUtility: " + response.toString());
        return response.toString();
    }

    public static String getRegisteredNodeAddress(String path){
        /* curl http://172.17.42.1:4001/v2/keys/registrator/rabbitmq/1
         * {"action":"get","node":{"key":"/registrator/rabbitmq/1","value":"172.17.0.2:15672",
         * "modifiedIndex":10577676,"createdIndex":10577676}}
         */

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

        return rootNode.path("node").path("value").toString();
    }
}

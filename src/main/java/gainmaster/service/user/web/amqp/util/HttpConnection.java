package gainmaster.service.user.web.amqp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lorre on 4/13/15.
 */
public class HttpConnection {

    public static String sendGet(String path){
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
        return response.toString();
    }
}

package geolocator;

import java.net.MalformedURLException;
import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLocator {

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Logger logger = LoggerFactory.getLogger(GeoLocator.class);
    private static Gson GSON = new Gson();

    public GeoLocator() {}

    public GeoLocation getGeoLocation() throws IOException {
        logger.info("RETURNING GEOLOCATION INFO");
        return getGeoLocation(null);
    }

    public GeoLocation getGeoLocation(String ipAddrOrHost)  {

        URL url = null;
        String s = new String();
        if (ipAddrOrHost != null) {
            logger.info("URL PARSING");
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            try {
                logger.debug("It can be debug message");
                url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
            } catch (MalformedURLException e) {
                logger.error("MalFormed URL", new RuntimeException(e.getMessage()));
            }
        } else {
            try {
                url = new URL(GEOLOCATOR_SERVICE_URI);
            } catch (MalformedURLException e) {
                logger.error("MalFormed URL", new RuntimeException(e.getMessage()));
            }
        }
        try {
            s = IOUtils.toString(url, "UTF-8");
        }
         catch (IOException e) {
             logger.error("IO Exception", new RuntimeException(e.getMessage()));
        }
        catch(NullPointerException e){
            logger.error("url is NULL", new RuntimeException(e.getMessage()));
        }
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {
        String arg = args.length > 0 ? args[0] : null;
        logger.info("Printing GeoLocation INFO");
        System.out.println(new GeoLocator().getGeoLocation(arg));
    }

}

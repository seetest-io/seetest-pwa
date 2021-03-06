package utils;

import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

public class SeeTestProperties {

    public static final String PROPERTIES_FILENAME = "seetest.properties";
    public static URL SEETEST_IO_APPIUM_URL;
    public static final String WEB_URL = "web.url" ;
    public static final String XPATH_QUERY = "xpath.query";
    public static final String SEETEST_REPORT_TYPE = "seetest.testtype";

    static {
        try {
            SEETEST_IO_APPIUM_URL = new URL("https://sales.experitest.com/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    static Logger LOGGER = new Log4jLoggerFactory().getLogger("SeeTestProperties");


    /**
     * Loads properties.
     */
    public static Properties getSeeTestProperties() {
        Properties properties = new Properties();
        LOGGER.info("Enter loadInitProperties() ...");

        String pathToProperties = Objects.requireNonNull(SeeTestProperties.class.getClassLoader().getResource(PROPERTIES_FILENAME)).getFile();

        try (FileReader fr = new FileReader(pathToProperties)) {
            properties.load(fr);
        } catch (IOException e) {
            LOGGER.warn("Could not load init properties", pathToProperties, e);
            throw new RuntimeException("Could not init properties from path!", e);
        }

        LOGGER.info("--------- List Of Properties ---------");
        properties.forEach((key, value) -> LOGGER.info("Key = " + key + " ; Value = " + value));
        LOGGER.info("--------- END ---------");

        LOGGER.info("Exit loadInitProperties() ...");
        return properties;
    }
}

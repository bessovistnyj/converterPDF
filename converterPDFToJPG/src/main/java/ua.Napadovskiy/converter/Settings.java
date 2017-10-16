package ua.Napadovskiy.converter;

import java.io.InputStream;
import java.util.Properties;

/**
 * Class for settings.
 *
 * @author Napadovskiy Bohdan
 * @version 1.0
 * @since 14.10.2017
 */
public class Settings {

    /**
     * Properties.
     */
    private final Properties properties = new Properties();

    /**
     * Method load setings.
     * @param is input stream.
     */
    public void load(InputStream is) {
        try {
            this.properties.load(is);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * Method return value for key.
     * @param key key.
     * @return value.
     */
    public String getValue(String key) {
        return this.properties.getProperty(key);

    }
}

package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
	
	// initializes a Properties object, which is a specialized Hashtable for storing key-value pairs of strings.
    private Properties prop;

    public ConfigLoader() { 
        prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config/config.properties");
            prop.load(fis); //reads the key-value pairs from the input stream and loads them into the prop object.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }
}

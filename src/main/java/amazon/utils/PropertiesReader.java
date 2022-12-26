package amazon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private java.util.Properties prop = new Properties();

    public PropertiesReader(){
        InputStream inputStream;
        String environment = System.getProperty("env");
        String propertiesFilePath = environment + ".properties";
        inputStream = getInputStream(propertiesFilePath);

        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private InputStream getInputStream(String propertiesFilePath) {
        return this.getClass().getClassLoader().getResourceAsStream(propertiesFilePath);
    }

    String getBaseUrl() {
        return prop.getProperty("baseUrl");
    }
}

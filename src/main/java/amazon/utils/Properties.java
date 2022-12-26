package amazon.utils;

public class Properties {
    private static final PropertiesReader propertiesReader = new PropertiesReader();
    public static final String baseUrl = propertiesReader.getBaseUrl();
}

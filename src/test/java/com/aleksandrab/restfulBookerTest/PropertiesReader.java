package com.aleksandrab.restfulBookerTest;

import java.io.InputStream;
import java.util.Properties;

/**
 * Class to read values from properties file
 */
public class PropertiesReader {

    public static void readDataFromProperties(Properties properties) {
        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream("env.properties")) {
            properties.load((inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PropsUtil {

    private final static Logger log = LogManager.getLogger();

    public static Properties props() {
        return props("application.properties");
    }
    public static Properties props(String filename) {
        Properties props = new Properties();
        try(InputStream input = new FileInputStream("src/main/resources/" + filename)) {
            props.load(input);
        } catch (IOException e) {
            log.error("Can't read settings file");
            // System.out.println("Can't read settings file");
        }
        return props;
    }

    public static int getPrecision(Properties props) {
        String propertyName = "precision";
        int defaultPrecision = 10;
        try {
            return Integer.parseInt(props.getProperty(propertyName));
        } catch (Exception e) {
            log.warn(String.format("Can't read %s value. Default value (%s) will be used.%n", propertyName, defaultPrecision), e);
            // System.out.printf("Can't read %s value. Default value (%s) will be used.%n", propertyName, defaultPrecision);
            return defaultPrecision;
        }
    }

    public static Set<String> getStopWords(Properties props) {
        String propertyName = "stop-words";
        try {
            return Arrays.stream(props.getProperty(propertyName, "exit").split(","))
                    .map(word -> word.trim().toLowerCase())
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.warn(String.format("Can't read %s.%n", propertyName), e);
            // System.out.printf("Can't read %s.%n", propertyName);
            return Collections.emptySet();
        }
    }
}

package twincat.utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import twincat.ads.AdsLogger;

public class Utilities {
    /*************************/
    /** public static final **/
    /*************************/

    public static final String readStringFromPath(String path) {
        try {
            URI uri = Utilities.class.getResource(path).toURI();
            byte[] encoded = Files.readAllBytes(Paths.get(uri));
            return new String(encoded, StandardCharsets.US_ASCII);
        } catch (URISyntaxException | IOException e) {
            Logger logger = AdsLogger.getLogger();
            logger.severe(Utilities.exceptionToString(e));
        }

        return new String();
    }

    public static final String exceptionToString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}

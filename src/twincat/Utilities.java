package twincat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

public class Utilities {
    /*************************/
    /** public static final **/
    /*************************/

    public static final String readStringFromFile(String path) {
        InputStream inputStream = Utilities.class.getResourceAsStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferReader = new BufferedReader(inputStreamReader);
        String lineSeperator = System.getProperty("line.separator");
        return bufferReader.lines().collect(Collectors.joining(lineSeperator));
    }

    public static final String exceptionToString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
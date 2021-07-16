package twincat;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

public class Utilities {
    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final Image getScaledIamgeFromFilePath(String path, double scale) {
        Image image = new ImageIcon(Utilities.class.getResource(path)).getImage();
  
        int sourceWidth = image.getWidth(null);
        int sourceHeight = image.getHeight(null);

        int targetWidth = (int) (sourceWidth * scale);
        int targetHeight = (int) (sourceHeight * scale);

        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();

        return bufferedImage;   
    }
    
    public static final Image getImageFromFilePath(String path) {
        return new ImageIcon(Utilities.class.getResource(path)).getImage();
    }
    
    public static final String getStringFromFilePath(String path) {
        InputStream inputStream = Utilities.class.getResourceAsStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferReader = new BufferedReader(inputStreamReader);
        String lineSeperator = System.getProperty("line.separator");
        return bufferReader.lines().collect(Collectors.joining(lineSeperator));
    }

    public static final String exceptionToString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
    
    public static final boolean isScheduleDone(ScheduledFuture<?> schedule) {
        if (schedule != null) {
            if (schedule.isDone()) {
                return true;
            } else {
                return false;
            }
        }
        
        return true;
    }
    
    public static final void stopSchedule(ScheduledFuture<?> schedule) {
        if (schedule != null) {
            if (!schedule.isCancelled()) {
                schedule.cancel(true);
            }
        }   
    }
 
    public static final long stringTimeToMilliseconds(String time)  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Date date = dateFormat.parse(time);
            return (int) date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}

package twincat.scope;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Scope {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final String TIME_FORMAT_TEMPLATE = "00:00:00.000";

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final String TIME_FORMAT_PATTERN = "hh:mm:ss.SSS";
    
    private static final int TIME_FORMAT_SIZE = 4;

    private static final int INDEX_MILLISECONDS = 0;

    private static final int INDEX_SECONDs = 1;

    private static final int INDEX_MINUTES = 2;

    private static final int INDEX_HOURES = 3;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private String scopeName = "Scope";

    private long recordTime = 0;

    /*********************************/
    /***** global final variable *****/
    /*********************************/

    private final CopyOnWriteArrayList<Chart> chartList = new CopyOnWriteArrayList<Chart>();

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public CopyOnWriteArrayList<Chart> getChartList() {
        return chartList;
    }

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public String toString() {
        return scopeName;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void setRecordTime(String recordTime) {
        this.recordTime = Scope.formatTimeStringToLong(recordTime);
    }

    public void addChart(Chart chart) {
        chartList.add(chart);
    }

    public void removeChart(Chart remove) {
        Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
            Chart chart = chartIterator.next();

            if (chart.equals(remove)) {
                chart.close();
                chartIterator.remove();
            }
        }
    }

    public void start() {
        Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
            Chart chart = chartIterator.next();
            chart.setRecordTime(recordTime);
            chart.start();
        }
    }

    public void stop() {
        Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
            Chart chart = chartIterator.next();
            chart.stop();
        }
    }

    public void close() {
        Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
            Chart chart = chartIterator.next();
            chart.close();
        }
    }

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final String formatTimeLongToString(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        return dateFormat.format(date);
    }
    
    public static final long formatTimeStringToLong(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT_PATTERN);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Date date = dateFormat.parse(time);
            return (int) date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static final String formatTime(String time) {
        List<String> timeList = new ArrayList<String>();

        // replace unnecessary data
        String value = time.replaceAll("[^0-9|^:|^.]", "");

        // get milliseconds
        int valueLength = value.length();
        int msIndex = value.lastIndexOf(".");
        if (msIndex != -1) {
            // milliseconds present
            int msLength = valueLength - msIndex < 4 ? valueLength : msIndex + 4;
            String msValue = value.substring(msIndex + 1, msLength);
            timeList.add(msValue.replaceAll("\\:", ""));
            value = value.substring(0, msIndex);
        } else {
            // no milliseconds present
            timeList.add("000");
        }

        // remove none relevant data
        List<String> purgeList = Arrays.asList(value.replaceAll("\\.", "").split("\\:"));
        purgeList = purgeList.subList(0, purgeList.size() > 3 ? 3 : purgeList.size());
        Collections.reverse(purgeList);
        timeList.addAll(purgeList);

        // convert to numbers
        List<Integer> numberList = new ArrayList<Integer>();
        for (int index = 0; index < TIME_FORMAT_SIZE; index++) {
            if (index < timeList.size()) {
                if (!timeList.get(index).isEmpty()) {
                    numberList.add(Integer.valueOf(timeList.get(index)));
                    continue;
                }
            }
            
            numberList.add(0);
        }

        // build and format numbers
        List<String> outputList = new ArrayList<String>();
        for (int index = 0; index < TIME_FORMAT_SIZE; index++) {
            switch (index) {
                case INDEX_MILLISECONDS:
                    int ms = numberList.get(0);
                    String msOut = Integer.toString(ms);
                    msOut = String.format("%1$3s", Integer.toString(ms)).replace(' ', '0');
                    outputList.add(msOut);
                    break;

                case INDEX_SECONDs:
                    int minMod = numberList.get(1) / 60;
                    if (minMod != 0) numberList.set(2, numberList.get(2) + minMod);
                    int sec = numberList.get(1) % 60;
                    String secOut = Integer.toString(sec);
                    secOut = String.format("%1$2s", secOut).replace(' ', '0');
                    outputList.add(secOut);
                    break;

                case INDEX_MINUTES:
                    int hrsMod = numberList.get(2) / 60;
                    if (hrsMod != 0) numberList.set(3, numberList.get(3) + hrsMod);
                    int min = numberList.get(2) % 60;
                    String minOut = Integer.toString(min);
                    minOut = String.format("%1$2s", minOut).replace(' ', '0');
                    outputList.add(minOut);
                    break;

                case INDEX_HOURES:
                    int hrs = numberList.get(3);
                    String hrsOut = Integer.toString(hrs);
                    hrsOut = String.format("%1$2s", hrsOut).replace(' ', '0');
                    outputList.add(hrsOut);
                    break;
            }
        }

        // convert numbers
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < TIME_FORMAT_SIZE; index++) {
            stringBuilder.insert(0, outputList.get(index));

            if (index == outputList.size() - 1) break;

            if (index != 0) {
                stringBuilder.insert(0, ":");
            } else {
                stringBuilder.insert(0, ".");
            }
        }

        return stringBuilder.toString();
    }
}

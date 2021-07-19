package twincat.scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Scope {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final String TIME_FORMAT_MAX_TIME = "99:59:59.999";
    
    public static final String TIME_FORMAT_MIN_TIME = "00:00:00.000";
    
    public static final String TIME_FORMAT_TEMPLATE = "hh:mm:ss.sss";

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

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
    
    private final CopyOnWriteArrayList<TriggerGroup> triggerGroupList = new CopyOnWriteArrayList<TriggerGroup>();
 
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
    
    public CopyOnWriteArrayList<TriggerGroup> getTriggerGroupList() {
        return triggerGroupList;
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

    public void setRecordTime(String recordTime) {
        setRecordTime(Scope.timeFormaterToLong(recordTime));
    }
    
    public void addChart(Chart chart) {
        chartList.add(chart);

        //chart.getTriggerGroupList().addAll(triggerGroupList);           
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

    public void addTriggerGroup(TriggerGroup triggerGroup) {
        triggerGroupList.add(triggerGroup);
        
        for (Chart chart : chartList) {
            chart.getTriggerGroupList().add(triggerGroup);
        }    
    }

    public void removeTrigger(TriggerGroup triggerGroupRemove) {
        Iterator<TriggerGroup> triggerGroupIterator = triggerGroupList.iterator();
        while (triggerGroupIterator.hasNext()) {
            TriggerGroup triggerGroup = triggerGroupIterator.next();

            if (triggerGroup.equals(triggerGroupRemove)) {
                triggerGroupIterator.remove();
            }  
        }
        
        Iterator<Chart> chartIterator = chartList.iterator();
        while (chartIterator.hasNext()) {
            Chart chart = chartIterator.next();
            
            Iterator<TriggerGroup> chartTriggerGroupIterator = chart.getTriggerGroupList().iterator();
            while (chartTriggerGroupIterator.hasNext()) {
                TriggerGroup chartTriggerGroup = chartTriggerGroupIterator.next();

                if (chartTriggerGroup.equals(triggerGroupRemove)) {
                    chartTriggerGroupIterator.remove();
                }  
            }
        }
    }

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final String timeFormaterToString(long time) {
        long ms = time % 1000;
        long sec = time / 1000 % 60;
        long min = time / (1000 * 60) % 60;
        long hrs = time / (1000 * 60 * 60);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%1$2s", hrs).replace(' ', '0'));
        stringBuilder.append(":");
        stringBuilder.append(String.format("%1$2s", min).replace(' ', '0'));
        stringBuilder.append(":");
        stringBuilder.append(String.format("%1$2s", sec).replace(' ', '0'));
        stringBuilder.append(".");
        stringBuilder.append(String.format("%1$3s", ms).replace(' ', '0'));

        return stringBuilder.toString();
    }

    public static final long timeFormaterToLong(String time) {
        time = Scope.timeFormaterParse(time);
        
        String[] timeArray = time.replaceAll("\\.", ":").split("\\:");

        long hrs = Long.valueOf(timeArray[0]) * 1000 * 60 * 60;
        long min = Long.valueOf(timeArray[1]) * 1000 * 60;
        long sec = Long.valueOf(timeArray[2]) * 1000;
        long ms = Long.valueOf(timeArray[3]);

        return hrs + min + sec + ms;
    }

    public static final String timeFormaterParse(String time) {
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
                    String msOut = String.format("%1$3s", Integer.toString(ms)).replace(' ', '0');
                    outputList.add(msOut);
                    break;

                case INDEX_SECONDs:
                    int minMod = numberList.get(1) / 60;
                    if (minMod != 0) numberList.set(2, numberList.get(2) + minMod);
                    int sec = numberList.get(1) % 60;
                    String secOut = String.format("%1$2s", Integer.toString(sec)).replace(' ', '0');
                    outputList.add(secOut);
                    break;

                case INDEX_MINUTES:
                    int hrsMod = numberList.get(2) / 60;
                    if (hrsMod != 0) numberList.set(3, numberList.get(3) + hrsMod);
                    int min = numberList.get(2) % 60;
                    String minOut = String.format("%1$2s", Integer.toString(min)).replace(' ', '0');
                    outputList.add(minOut);
                    break;

                case INDEX_HOURES:
                    int hrs = numberList.get(3);
                    String hrsOut = String.format("%1$2s", Integer.toString(hrs)).replace(' ', '0');
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

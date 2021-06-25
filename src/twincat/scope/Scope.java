package twincat.scope;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

public final class Scope {
	/*************************/
	/*** global attributes ***/
	/*************************/

	private long recordTime = 0;

	private final CopyOnWriteArrayList<Chart> chartList = new CopyOnWriteArrayList<Chart>();

	/*************************/
	/**** setter & getter ****/
	/*************************/
	
	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public CopyOnWriteArrayList<Chart> getChartList() {
		return chartList;
	}

	/*************************/
	/********* public ********/
	/*************************/

	public void setRecordTime(String recordTime) {
		this.recordTime = Scope.stringTimeToMilliseconds(recordTime);
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

	/*************************/
	/** public static final **/
	/*************************/
	
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

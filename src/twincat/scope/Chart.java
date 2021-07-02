package twincat.scope;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.VolatileImage;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.sun.management.OperatingSystemMXBean;

import twincat.TwincatLogger;
import twincat.Utilities;

public class Chart extends Observable {
	/*************************/
	/** constant attributes **/
	/*************************/

	private static final int CHART_PADDING = 5;

	private static final int CHART_MARGIN = 30;

	private static final int CHART_FONT_SIZE = 12;

	private static final int CHART_AXIS_WIDTH = 80;

	private static final int CHART_TIME_HEIGHT = 60;

	private static final int CHART_TICK_LENGTH = 10;

	/*************************/
	/*** global attributes ***/
	/*************************/

	private int width = 800;

	private int height = 600;

	private boolean debug = false;

	private String chartName = "Chart";

	private Color chartLineColor = new Color(41, 61, 74, 255);

	private Color timeLineColor = new Color(41, 61, 74, 255);

	private Color gridLineColor = new Color(41, 61, 74, 255);

	private Color borderColor = new Color(119, 136, 153, 255);

	private Color chartColor = new Color(192, 192, 192, 255);

	private int lineWidth = 1;

	private int timeTickCount = 11;

	private int axisTickCount = 11;
	
	private long recordTime = 0;

	private long displayTime = 1000;

	private VolatileImage image = Chart.createBitmaskVolatileImage(width, height);

	private final CopyOnWriteArrayList<Axis> axisList = new CopyOnWriteArrayList<Axis>();

	private final CopyOnWriteArrayList<TriggerGroup> triggerGroupList = new CopyOnWriteArrayList<TriggerGroup>();

	/*************************/
	/*** local attributes ***/
	/*************************/

	private boolean refresh = true;

	private long cycleTime = 0;

	private long drawTime = 0;

	private long deltaTime = 0;

	private long currentTime = 0;

	private long triggerTime = 0;

	private long pauseTime = 0;
	
	private long referenceTime = 0;		
	
	private long deltaTimeStamp = 0;

	private long currentTimeStamp = 0;

	private long triggerTimeStamp = 0;

	private long pauseTimeStamp = 0;

	private long startTimeStamp = 0;

	private long recordTimeStamp = 0;

	private long referenceTimeStamp = 0;

	private int visibleAxis = 0;

	private String chartError = new String();

	private VolatileImage staticImage = Chart.createBitmaskVolatileImage(width, height);
	
	private VolatileImage dynamicImage = Chart.createBitmaskVolatileImage(width, height);

	private ScheduledFuture<?> schedule = null;

	private final Runnable task = new Runnable() {
		public void run() {
			try {
				updateTiming();
				updateGraphic();
				updateObserver();
			} catch (Exception e) {
			    Logger logger = TwincatLogger.getLogger();
                logger.severe(Utilities.exceptionToString(e));
			}
		}
	};

	/*************************/
	/****** constructor ******/
	/*************************/

	public Chart(int framesPerSecond) {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		long refreshTime = 1000 / framesPerSecond;
		schedule = scheduler.scheduleAtFixedRate(task, 0, refreshTime, TimeUnit.MILLISECONDS);
		Chart.accelerateTranslucentVolatileImage();
	}

	/*************************/
	/**** setter & getter ****/
	/*************************/

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		this.refresh = true;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		this.refresh = true;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
		this.refresh = true;
	}

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
		this.refresh = true;
	}

	public Color getChartLineColor() {
		return chartLineColor;
	}

	public void setChartLineColor(Color chartLineColor) {
		this.chartLineColor = chartLineColor;
		this.refresh = true;
	}

	public Color getTimeLineColor() {
		return timeLineColor;
	}

	public void setTimeLineColor(Color timeLineColor) {
		this.timeLineColor = timeLineColor;
		this.refresh = true;
	}

	public Color getGridLineColor() {
		return gridLineColor;
	}

	public void setGridLineColor(Color gridLineColor) {
		this.gridLineColor = gridLineColor;
		this.refresh = true;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		this.refresh = true;
	}

	public Color getChartColor() {
		return chartColor;
	}

	public void setChartColor(Color chartColor) {
		this.chartColor = chartColor;
		this.refresh = true;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
		this.refresh = true;
	}

	public int getTimeTickCount() {
		return timeTickCount;
	}

	public void setTimeTickCount(int timeTickCount) {
		this.timeTickCount = timeTickCount;
		this.refresh = true;
	}

	public int getAxisTickCount() {
		return axisTickCount;
	}

	public void setAxisTickCount(int axisTickCount) {
		this.axisTickCount = axisTickCount;
		this.refresh = true;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
		this.refresh = true;
	}

	public long getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(long displayTime) {
		this.displayTime = displayTime;
		this.refresh = true;
	}

	public VolatileImage getImage() {
		return image;
	}

	public void setImage(VolatileImage image) {
		this.image = image;
		this.refresh = true;
	}

	public List<Axis> getAxisList() {
		return axisList;
	}

	public List<TriggerGroup> getTriggerGroupList() {
		return triggerGroupList;
	}

	/*************************/
	/********* public ********/
	/*************************/

	public void play() {
		pauseTimeStamp = 0;
	}

	public void pause() {
		pauseTimeStamp = currentTimeStamp;
	}

	public long getRunTime() {
		return currentTimeStamp - startTimeStamp;
	}

	public long getPauseTime() {
		return pauseTimeStamp - startTimeStamp;
	}

	public int getFramesPerSecond() {
		return (int) (deltaTime > 0 ? 1000 / deltaTime : 0);
	}

	public void start() {
		reset();
		Iterator<Axis> axisIterator = axisList.iterator();
		while (axisIterator.hasNext()) {
			Axis axis = axisIterator.next();
			axis.start();
		}
	}

	public void stop() {
		Iterator<Axis> axisIterator = axisList.iterator();
		while (axisIterator.hasNext()) {
			Axis axis = axisIterator.next();
			axis.stop();
		}
	}

	public void close() {
		Scope.stopSchedule(schedule);

		Iterator<Axis> axisIterator = axisList.iterator();
		while (axisIterator.hasNext()) {
			Axis axis = axisIterator.next();
			axis.close();
		}
	}

	public void addAxis(Axis axis) {
		this.refresh = true;
		axisList.add(axis);
	}

	public void removeAxis(Axis axisRemove) {
		this.refresh = true;
		for (Axis axis : axisList) {
			if (axis.equals(axisRemove)) {
				axis.close();
				axisList.remove(axis);
			}		
		}	
	}

	public void addTrigger(TriggerGroup trigger) {
		this.refresh = true;
		triggerGroupList.add(trigger);
	}

	public void removeTrigger(TriggerGroup triggerGroupRemove) {
		this.refresh = true;
		for (TriggerGroup triggerGroup : triggerGroupList) {
			if (triggerGroup.equals(triggerGroupRemove)) {
				triggerGroupList.remove(triggerGroup);
			}
		}
	}

	/*************************/
	/******** private ********/
	/*************************/

	private void updateObserver() {
		setChanged();
		notifyObservers();
	}

	private void updateTiming() {
		// update delta time
		deltaTime = System.currentTimeMillis() - cycleTime;

		// update cycle time
		cycleTime = System.currentTimeMillis();
		
		// update current time
		currentTime = currentTimeStamp - startTimeStamp;
		
		// update reference time
		referenceTime = referenceTimeStamp - startTimeStamp;
		
		// update trigger time
		triggerTime = triggerTimeStamp - startTimeStamp;
		
		// update pause time
		pauseTime = pauseTimeStamp - startTimeStamp;
			
		// get current time stamp
		long channelTimeStamp = 0;
		Iterator<Axis> axisIterator = axisList.iterator();
		while (axisIterator.hasNext()) {
			Axis axis = axisIterator.next();

			Iterator<Channel> channelIterator = axis.getChannelList().iterator();
			while (channelIterator.hasNext()) {
				Channel channel = channelIterator.next();
				channelTimeStamp = channel.getSamples().getCurrentTimeStamp();

				if (channelTimeStamp != 0) {
					currentTimeStamp = channelTimeStamp;

					if (channelTimeStamp < currentTimeStamp)
						currentTimeStamp = channelTimeStamp;
				} else {
					currentTimeStamp = 0;
					break;
				}
			}
		}

		// get trigger time stamp
		Iterator<TriggerGroup> triggerGroupIterator = triggerGroupList.iterator();
		while (triggerGroupIterator.hasNext()) {
			TriggerGroup triggerGroup = triggerGroupIterator.next();

			if (triggerGroup.isEnabled()) {
				long triggerTime = triggerGroup.getTriggerTimeStamp(displayTime);
				if (triggerTime != 0) {
					this.triggerTimeStamp = triggerTime;
				}
			}
		}

		// get start time stamp
		if (startTimeStamp == 0 && currentTimeStamp != 0) {
			startTimeStamp = currentTimeStamp;
		}

		// get delta time stamp
		deltaTimeStamp = referenceTimeStamp;

		// get record time stamp
		recordTimeStamp = startTimeStamp + recordTime;

		// get reference time stamp
		if (recordTimeStamp != startTimeStamp &&
			recordTimeStamp < currentTimeStamp) {
			// priority 1 : use record time stamp
			referenceTimeStamp = recordTimeStamp;
			stop();
		} else if (pauseTimeStamp != 0) {
			// priority 2 : use pause time stamp
			referenceTimeStamp = pauseTimeStamp;
		} else if (triggerTimeStamp != 0) {
			// priority 3 : use trigger time stamp
			referenceTimeStamp = triggerTimeStamp;	
		} else {
			// priority 4 : use current time stamp
			referenceTimeStamp = currentTimeStamp;
		}
	}

	private void updateGraphic() {
		long graphicTime = System.currentTimeMillis();

		Iterator<Axis> axisIterator = null;
		Graphics2D graphics = null;

		axisIterator = axisList.iterator();
		while (axisIterator.hasNext()) {
			Axis axis = axisIterator.next();
			
			Iterator<Channel> channelIterator = axis.getChannelList().iterator();
			while (channelIterator.hasNext()) {
				Channel channel = channelIterator.next();
				if (channel.isRefresh()) {
					channel.setRefresh(false);
					refresh = true;
				}
			}
		}

		boolean updateStatic = false;
		boolean updateDynamic = false;
		boolean updatePerformance = false;

		if (!Chart.isVolatileImageOk(image)) updateStatic = true;
		if (!Chart.isVolatileImageOk(staticImage)) updateStatic = true;
		if (!Chart.isVolatileImageOk(dynamicImage)) updateStatic = true;
		if (deltaTimeStamp > referenceTimeStamp) updateDynamic = true;
		if (deltaTimeStamp < referenceTimeStamp) updatePerformance = true;
		
		if (refresh) updateStatic = true;	
		refresh = false;

		if (updateStatic) {
			staticImage = Chart.createBitmaskVolatileImage(width, height);

			graphics = staticImage.createGraphics();
			graphics.setColor(borderColor);
			graphics.fillRect(0, 0, width, height);

			// draw axis
			visibleAxis = 0;
			axisIterator = axisList.iterator();
			while (axisIterator.hasNext()) {
				Axis axis = axisIterator.next();

				if (axis.isAxisVisible()) {
					visibleAxis++;

					int axisPositionX = CHART_AXIS_WIDTH * visibleAxis + CHART_MARGIN - CHART_PADDING;
					int axisPositionY = CHART_MARGIN;
					int axisLength = height - CHART_TIME_HEIGHT - CHART_MARGIN * 2;

					double axisTickOffset = (double) axisLength / (axisTickCount - 1);
					double axisValueRange = axis.getValueMax() - axis.getValueMin();

					graphics.setStroke(new BasicStroke(axis.getLineWidth()));
					graphics.setColor(axis.getAxisColor());
					graphics.drawLine(axisPositionX, axisPositionY, axisPositionX, axisPositionY + axisLength);

					for (int i = 0; i < timeTickCount; i++) {
						int axisIndicatorPositionX = axisPositionX - CHART_TICK_LENGTH;
						int axisIndicatorPositionY = axisPositionY + (int) (axisTickOffset * i);
						double axisValueRangeOffset = axisValueRange / (axisTickCount - 1) * i;

						DecimalFormat decimalFormat = new DecimalFormat("0.0");
						String yAxisText = decimalFormat.format(axis.getValueMax() - axisValueRangeOffset);

						Font font = new Font("Arial", Font.PLAIN, CHART_FONT_SIZE);
						FontMetrics fontMetrics = graphics.getFontMetrics(font);

						int axisFontPositionX = axisPositionX - fontMetrics.stringWidth(yAxisText) - CHART_TICK_LENGTH - 4;
						int axixFontPositionY = axisIndicatorPositionY + fontMetrics.getAscent() / 2 - 2;

						graphics.setColor(axis.getAxisColor());
						graphics.setStroke(new BasicStroke(axis.getLineWidth()));
						graphics.drawLine(axisPositionX, axisIndicatorPositionY, axisIndicatorPositionX, axisIndicatorPositionY);
						graphics.setFont(font);
						graphics.drawString(yAxisText, axisFontPositionX, axixFontPositionY);
					}
				}
			}

			// draw time
			int timePositionX = CHART_AXIS_WIDTH * visibleAxis + CHART_MARGIN;
			int timePositionY = height - CHART_TIME_HEIGHT - CHART_MARGIN + CHART_PADDING;
			int timeWidth = width - CHART_AXIS_WIDTH * visibleAxis - CHART_MARGIN * 2;

			double timeTickOffset = (double) timeWidth / (timeTickCount - 1);

			graphics.setColor(timeLineColor);
			graphics.setStroke(new BasicStroke(lineWidth));
			graphics.drawLine(timePositionX, timePositionY, timePositionX + timeWidth, timePositionY);

			for (int i = 0; i < timeTickCount; i++) {
				int timeTickPositionX = timePositionX + (int) (timeTickOffset * i);
				int timeTickPositionY = timePositionY + CHART_TICK_LENGTH;

				double timeDisplayOffset = displayTime / (timeTickCount - 1) * i;

				DecimalFormat decimalFormat = new DecimalFormat("0.0");
				String timeText = decimalFormat.format(timeDisplayOffset);
				Font timeFont = new Font("Arial", Font.PLAIN, CHART_FONT_SIZE);
				FontMetrics fontMetrics = graphics.getFontMetrics(timeFont);

				int timeFontPositionX = timeTickPositionX - fontMetrics.stringWidth(timeText) / 2;
				int timeFontPositionY = timePositionY + fontMetrics.getAscent() + CHART_TICK_LENGTH;

				graphics.setColor(timeLineColor);
				graphics.setStroke(new BasicStroke(lineWidth));
				graphics.drawLine(timeTickPositionX, timePositionY, timeTickPositionX, timeTickPositionY);

				graphics.setColor(timeLineColor);
				graphics.setFont(timeFont);
				graphics.drawString(timeText, timeFontPositionX, timeFontPositionY);
			}

			// draw chart
			int chartPositionX = CHART_AXIS_WIDTH * visibleAxis + CHART_MARGIN;
			int chartPositionY = CHART_MARGIN;
			int chartWidth = width - CHART_AXIS_WIDTH * visibleAxis - CHART_MARGIN * 2;
			int chartHeight = height - CHART_TIME_HEIGHT - CHART_MARGIN * 2;

			double chartTickOffsetTime = (double) chartWidth / (timeTickCount - 1);
			double chartTickOffsetAxis = (double) chartHeight / (axisTickCount - 1);

			graphics.setColor(chartColor);
			graphics.fillRect(chartPositionX, chartPositionY, chartWidth, chartHeight);
			graphics.setClip((int) chartPositionX, (int) chartPositionY, chartWidth, chartHeight);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for (int i = 0; i < timeTickCount; i++) {
				int chartTickPositionX = chartPositionX + (int) (chartTickOffsetTime * i);

				graphics.setColor(gridLineColor);
				graphics.setStroke(new BasicStroke(0.2f));
				graphics.drawLine(chartTickPositionX, chartPositionY, chartTickPositionX, chartPositionY + chartHeight);
			}

			for (int i = 0; i < axisTickCount; i++) {
				int chartTickPositionY = chartPositionY + (int) (chartTickOffsetAxis * i);

				graphics.setColor(gridLineColor);
				graphics.setStroke(new BasicStroke(0.2f));
				graphics.drawLine(chartPositionX, chartTickPositionY, chartPositionX + chartWidth, chartTickPositionY);
			}

			graphics.dispose();
		}

		if (updateStatic || updateDynamic || updatePerformance) {
			// draw channel
			int channelPositionX = CHART_AXIS_WIDTH * visibleAxis + CHART_MARGIN;
			int channelPositionY = CHART_MARGIN;
			int channelWidth = width - CHART_AXIS_WIDTH * visibleAxis - CHART_MARGIN * 2;
			int channelHeight = height - CHART_TIME_HEIGHT - CHART_MARGIN * 2;

			long stopTimeStamp, startTimeStamp;
			long deltaTime = referenceTimeStamp - deltaTimeStamp;
			double pixelToDisplayTime = (double) channelWidth / displayTime;
			double deltaTimeOffset = deltaTime * pixelToDisplayTime * -1;

			if (updateStatic || updateDynamic) {
				startTimeStamp = referenceTimeStamp - displayTime;
				stopTimeStamp = referenceTimeStamp;

				dynamicImage = Chart.createBitmaskVolatileImage(width, height);
			} else {
				startTimeStamp = referenceTimeStamp - deltaTime;
				stopTimeStamp = referenceTimeStamp;
				
				VolatileImage image = Chart.createBitmaskVolatileImage(width, height);
				graphics = image.createGraphics();
				graphics.setClip(channelPositionX, channelPositionY, channelWidth, channelHeight);
				graphics.translate(deltaTimeOffset, 0);
				graphics.drawImage(dynamicImage, 0, 0, null);
				graphics.dispose();
				
				dynamicImage = image;
			}

			graphics = dynamicImage.createGraphics();
			graphics.setClip(channelPositionX, channelPositionY, channelWidth, channelHeight);

			long referenceTime = stopTimeStamp - startTimeStamp;
			long referenceToDisplayTimeOffset = displayTime - referenceTime;

			axisIterator = axisList.iterator();
			while (axisIterator.hasNext()) {
				Axis axis = axisIterator.next();

				double valueRange = axis.getValueMax() - axis.getValueMin();
				double pixelToValueRange = channelHeight / valueRange;
				double pixelToValueRangeOffset = 0 - axis.getValueMin() * pixelToValueRange;

				Iterator<Channel> channelIterator = axis.getChannelList().iterator();
				while (channelIterator.hasNext()) {
					Channel channel = channelIterator.next();
					if (!channel.isChannelVisible()) continue;

					GeneralPath generalPath = new GeneralPath();
					List<Rectangle2D> rectangleList = new ArrayList<Rectangle2D>();

					Samples samples = channel.getSamples();
					int sampleCurrentIndex = samples.getCurrentIndex();
					if (sampleCurrentIndex == 0) continue;

					int stopIndex = 0;
					for (int i = 0; i <= sampleCurrentIndex; i++) {
						long sampleTime = samples.getTimeStamp(i);
						if (sampleTime >= stopTimeStamp) {
							stopIndex = i;
							break;
						}
					}

					int startIndex = 0;
					for (int i = stopIndex; i >= 0; i--) {
						long sampleTimeStamp = samples.getTimeStamp(i);
						if (sampleTimeStamp <= startTimeStamp) {
							startIndex = i;
							break;
						}
					}

					int sampleRange = stopIndex - startIndex;
					for (int i = 0; i <= sampleRange; i++) {
						int sampleIndex = startIndex + i;

						long sampleTime = samples.getTimeStamp(sampleIndex) - startTimeStamp;		// 1000 - 500
						long sampleTimeDisplayOffset = referenceToDisplayTimeOffset + sampleTime;
						double sampleTimePixelPosition = sampleTimeDisplayOffset * pixelToDisplayTime;
						double samplePositionX = channelPositionX + sampleTimePixelPosition;

						double sampleValue = samples.getValue(sampleIndex);
						double samplePixelPosition = sampleValue * pixelToValueRange;
						double samplePositionOffset = channelHeight - pixelToValueRangeOffset - samplePixelPosition;
						double samplePositionY = channelPositionY + samplePositionOffset;

						if (channel.isLineVisible()) {
							if (generalPath.getCurrentPoint() == null) {
								generalPath.moveTo(samplePositionX, samplePositionY);
							} else {
								generalPath.lineTo(samplePositionX, samplePositionY);
							}
						}

						if (channel.isPlotVisible()) {
							int size = channel.getPlotSize();
							double xRect = samplePositionX - (size / 2);
							double yRect = samplePositionY - (size / 2);
							Rectangle2D rectangle = new Rectangle2D.Double(xRect, yRect, size, size);
							rectangleList.add(rectangle);
						}
					}

					if (channel.isAntialias()) {
						graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					} else {
						graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
					}

					if (channel.isLineVisible()) {
						graphics.setColor(channel.getLineColor());
						graphics.setStroke(new BasicStroke(channel.getLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
						graphics.draw(generalPath);
					}

					if (!rectangleList.isEmpty()) {
						for (Rectangle2D rectangle : rectangleList) {
							graphics.setColor(channel.getPlotColor());
							graphics.fill(rectangle);
						}
					}
				}
			}

			graphics.dispose();
		}

		VolatileImage image = Chart.createBitmaskVolatileImage(width, height);
		graphics = image.createGraphics();
		graphics.drawImage(staticImage, 0, 0, null);
		graphics.drawImage(dynamicImage, 0, 0, null);

		if (debug) {
			int debugPositionX = CHART_AXIS_WIDTH * visibleAxis + CHART_MARGIN + 5;
			int debugPositionY = CHART_MARGIN;

			graphics = image.createGraphics();

			Font font = new Font("Arial", Font.PLAIN, CHART_FONT_SIZE);

			graphics.setColor(Color.BLACK);
			graphics.setFont(font);

			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

			graphics.drawString("General Data", debugPositionX, debugPositionY += 15);

			graphics.drawString("CPULoad:", debugPositionX, debugPositionY += 15);
			String cpuLoadText = Integer.toString((int) (osBean.getProcessCpuLoad() * 100));
			graphics.drawString(cpuLoadText, debugPositionX + 110, debugPositionY);

			graphics.drawString("FramesPerSecond:", debugPositionX, debugPositionY += 15);
			String deltaTimeText = getFramesPerSecond() > 0 ? Long.toString(getFramesPerSecond()) : "0";
			graphics.drawString(deltaTimeText, debugPositionX + 110, debugPositionY);

			graphics.drawString("DrawTime:", debugPositionX, debugPositionY += 15);
			String drawTimeText = drawTime > 0 ? Long.toString(drawTime) : "0";
			graphics.drawString(drawTimeText, debugPositionX + 110, debugPositionY);

			graphics.drawString("RecordTime:", debugPositionX, debugPositionY += 15);
			graphics.drawString(Long.toString(recordTime), debugPositionX + 110, debugPositionY);

			graphics.drawString("CurrentTime:", debugPositionX, debugPositionY += 15);
			String currentTimeText = currentTime > 0 ? Long.toString(currentTime) : "0";
			graphics.drawString(currentTimeText, debugPositionX + 110, debugPositionY);

			graphics.drawString("ReferenceTime:", debugPositionX, debugPositionY += 15);
			String referencetimeText = referenceTime > 0 ? Long.toString(referenceTime) : "0";
			graphics.drawString(referencetimeText, debugPositionX + 110, debugPositionY);

			graphics.drawString("TriggerTime:", debugPositionX, debugPositionY += 15);
			String triggerTimeText = triggerTime > 0 ? Long.toString(triggerTime) : "0";
			graphics.drawString(triggerTimeText, debugPositionX + 110, debugPositionY);

			graphics.drawString("PauseTime:", debugPositionX, debugPositionY += 15);
			String referenceTimeText = pauseTime > 0 ? Long.toString(pauseTime) : "0";
			graphics.drawString(referenceTimeText, debugPositionX + 110, debugPositionY);

			graphics.drawString("MemoryCapacity:", debugPositionX, debugPositionY += 15);
			String scopeSamplesMemory = Integer.toString(Samples.CAPACITY);
			graphics.drawString(scopeSamplesMemory, debugPositionX + 110, debugPositionY);

			graphics.drawString("Error:", debugPositionX, debugPositionY += 15);
			graphics.drawString(chartError, debugPositionX + 110, debugPositionY);

			graphics.drawString("Channel Data", debugPositionX, debugPositionY += 30);

			axisIterator = axisList.iterator();
			while (axisIterator.hasNext()) {
				Axis axis = axisIterator.next();

				Iterator<Channel> channelIterator = axis.getChannelList().iterator();
				while (channelIterator.hasNext()) {
					Channel channel = channelIterator.next();

					String channelName = channel.getChannelName().length() > 10 ?
							channel.getChannelName().substring(0, 10) : channel.getChannelName();
					graphics.drawString(channelName, debugPositionX, debugPositionY += 15);

					graphics.drawString("NotErrCnt:", debugPositionX + 80, debugPositionY);
					String notificationErrorCountText = Integer.toString(channel.getNotificationError());
					graphics.drawString(notificationErrorCountText, debugPositionX + 145, debugPositionY);

					graphics.drawString("Memory:", debugPositionX + 180, debugPositionY);
					String currentIndexText = Integer.toString(channel.getSamples().getCurrentIndex());
					graphics.drawString(currentIndexText, debugPositionX + 230, debugPositionY);

					graphics.drawString("Error:", debugPositionX + 300, debugPositionY);
					String errorText = channel.getChannelError();
					graphics.drawString(errorText, debugPositionX + 340, debugPositionY);
				}
			}
		}
		
        graphics.dispose();

		this.image = image;

		drawTime = System.currentTimeMillis() - graphicTime;
	}

	private void reset() {
		refresh = true;

		cycleTime     = 0;
		deltaTime     = 0;
		deltaTime     = 0;
		currentTime   = 0;
		triggerTime   = 0;
		pauseTime     = 0;
		referenceTime = 0;
		
		deltaTimeStamp     = 0;
		currentTimeStamp   = 0;
		triggerTimeStamp   = 0;
		pauseTimeStamp     = 0;
		startTimeStamp     = 0;
		recordTimeStamp    = 0;
		referenceTimeStamp = 0;
		
		chartError = "None";
	}

	
	
	private static final void accelerateTranslucentVolatileImage() {
		System.setProperty("sun.java2d.translaccel", "true");		
	}
	
	private static boolean isVolatileImageOk(VolatileImage image) {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
		
		int state = image.validate(graphicsConfiguration);
		
		switch (state) {
			case VolatileImage.IMAGE_OK:
				return true;
			case VolatileImage.IMAGE_RESTORED:
				return false;
			case VolatileImage.IMAGE_INCOMPATIBLE:
				return false;
			default:
				return true;
		}
	}

	private static final VolatileImage createBitmaskVolatileImage(int width, int height) {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
		GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();	
		
		VolatileImage image = graphicsConfiguration.createCompatibleVolatileImage(width, height, Transparency.TRANSLUCENT);

		if (image.validate(graphicsConfiguration) == VolatileImage.IMAGE_INCOMPATIBLE)
			return Chart.createBitmaskVolatileImage(width, height);
		
		Graphics2D graphics = image.createGraphics();
		graphics.setComposite(AlphaComposite.Clear);
		graphics.setColor(new Color(0, 0, 0, 0));
		graphics.fillRect(0, 0, width, height);
		graphics.dispose();

		return image;
	}
}

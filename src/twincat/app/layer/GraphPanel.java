package twincat.app.layer;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import twincat.scope.Chart;

public class GraphPanel extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** global variable ********/
    /*********************************/

    private Chart chart = new Chart();

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final ComponentAdapter componentListener = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent componentEvent) {
            Component component = componentEvent.getComponent();
            chart.setWidth(component.getWidth());
            chart.setHeight(component.getHeight());
            System.out.println("componentResized");
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public GraphPanel() {
        super();
        this.initialize();
    }

    public GraphPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        this.initialize();
    }

    public GraphPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        this.initialize();
    }

    public GraphPanel(LayoutManager layout) {
        super(layout);
        this.initialize();
    }

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public Chart getChart() {
        return chart;
    }

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(chart.getImage(), 0, 0, this);
    }

    @Override
    public void update(Observable observable, Object object) {
        this.repaint();
    }

    /*********************************/
    /********* public method *********/
    /*********************************/
    
    public void setChart(Chart chart) {
        this.chart = chart;
        this.chart.addObserver(this);
        this.chart.setWidth(this.getWidth());
        this.chart.setHeight(this.getHeight());
        this.chart.start();
    }
  
    /*********************************/
    /******** private method *********/
    /*********************************/

    private void initialize() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.addComponentListener(componentListener);
    }
}

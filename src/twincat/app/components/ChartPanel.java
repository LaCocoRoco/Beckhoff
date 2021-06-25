package twincat.app.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ChartPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    
    public ChartPanel() {
        JLabel scopeLabel = new JLabel("Chart Panel");
        
        JPanel containerPanel = new JPanel();
        containerPanel.setAlignmentX(TOP_ALIGNMENT);
        containerPanel.setAlignmentY(CENTER_ALIGNMENT);
        containerPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
        containerPanel.add(scopeLabel);

        this.setViewportView(containerPanel);
    }
    
}

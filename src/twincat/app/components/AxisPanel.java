package twincat.app.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class AxisPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;

	/*************************/
	/****** constructor ******/
	/*************************/
	
	public AxisPanel() {
		JLabel AxxaLabel = new JLabel("Axxa Panel");
		
		JPanel containerPanel = new JPanel();
		containerPanel.setAlignmentX(TOP_ALIGNMENT);
		containerPanel.setAlignmentY(CENTER_ALIGNMENT);
		containerPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		containerPanel.add(AxxaLabel);
		
		this.setViewportView(containerPanel);
	}
}

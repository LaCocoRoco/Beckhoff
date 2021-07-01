package twincat.app.components;

import javax.swing.JScrollPane;

public class AxisPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;

	/*************************/
	/****** constructor ******/
	/*************************/
	
	public AxisPanel() {
		this.setViewportView(new LoremIpsumText());
	}
}

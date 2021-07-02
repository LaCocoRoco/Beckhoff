package twincat.app.scope;

import javax.swing.JScrollPane;

import twincat.LoremIpsum;

public class AxisPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;

	/*************************/
	/****** constructor ******/
	/*************************/
	
	public AxisPanel() {
		this.setViewportView(new LoremIpsum());
	}
}

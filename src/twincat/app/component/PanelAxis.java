package twincat.app.component;

import javax.swing.JScrollPane;

import twincat.app.container.LoremIpsum;

public class PanelAxis extends JScrollPane {
	private static final long serialVersionUID = 1L;

	/*************************/
	/****** constructor ******/
	/*************************/

	public PanelAxis() {
		this.setViewportView(new LoremIpsum());
	}
}

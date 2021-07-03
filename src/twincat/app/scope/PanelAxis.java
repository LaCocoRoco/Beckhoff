package twincat.app.scope;

import javax.swing.JScrollPane;

public class PanelAxis extends JScrollPane {
	private static final long serialVersionUID = 1L;

	/*************************/
	/****** constructor ******/
	/*************************/

	public PanelAxis() {
		this.setViewportView(new LoremIpsum());
	}
}

package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import twincat.Resources;

public class ScopeProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopeProperties(XReference xref) {
        JScrollPane propertiesPanel = new JScrollPane();
        propertiesPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        propertiesPanel.setBorder(BorderFactory.createEmptyBorder());
        propertiesPanel.setViewportView(new JLabel("ScopeProperties"));

        this.setLayout(new BorderLayout());
        this.add(propertiesPanel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}

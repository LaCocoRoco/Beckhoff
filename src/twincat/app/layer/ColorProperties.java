package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import twincat.app.components.ScrollablePanel;
import twincat.app.components.WrapTopLayout;
import twincat.app.constant.Propertie;
import twincat.constant.ColorTable;

public class ColorProperties extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /******** local variable *********/
    /*********************************/

    private Component component = null;

    /*********************************/
    /******** setter & getter ********/
    /*********************************/

    public MouseAdapter getColorPropertieMouseAdapter() {
        return colorPropertieMouseAdapter;
    }

    /*********************************/
    /****** predefined variable ******/
    /*********************************/
    
    private final MouseAdapter colorPropertieMouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            component = mouseEvent.getComponent();  
            xref.propertiesPanel.setCard(Propertie.COLOR); 
        }
    };
    
    private final MouseAdapter colorPickerMouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            component.setBackground(mouseEvent.getComponent().getBackground());    
            xref.propertiesPanel.lastCard();
        }
    };

    private AbstractAction scrollPanelDisableKey = new AbstractAction() {
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            /* empty */ 
        }
    };
       
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ColorProperties(XReference xref) {
        this.xref = xref;
   
        ScrollablePanel contentPanel = new ScrollablePanel();
        contentPanel.setLayout(new WrapTopLayout(FlowLayout.LEADING));
        contentPanel.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);

        for (ColorTable tableCell : ColorTable.values()) {
            Border outerBorder = BorderFactory.createLineBorder(Color.BLACK);
            Border innerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
            CompoundBorder compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
            
            JPanel colorPicker = new JPanel();
            colorPicker.setBorder(compoundBorder);
            colorPicker.setBackground(tableCell.color);
            colorPicker.setPreferredSize(new Dimension(60, 60));
            colorPicker.addMouseListener(colorPickerMouseAdapter); 
            
            contentPanel.add(colorPicker);
        }

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(contentPanel);
        scrollPanel.getActionMap().put("unitScrollUp", scrollPanelDisableKey);
        scrollPanel.getActionMap().put("unitScrollDown", scrollPanelDisableKey);
        
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setLayout(new BorderLayout());
        this.add(scrollPanel, BorderLayout.CENTER);
    }
}

package twincat.app.components;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class ComboBox extends JComboBox<String> {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public void setUI(ComboBoxUI comboBoxUI) {   
        super.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
    }

    @Override
    public void setBorder(Border border) {
        Border insideBorder = BorderFactory.createLoweredBevelBorder();
        Border outsideBorder = BorderFactory.createEmptyBorder(0, 0, 0, 1);
        CompoundBorder compoundBorder = new CompoundBorder(outsideBorder, insideBorder);
        super.setBorder(compoundBorder);
    }

    @Override
    public void setRenderer(ListCellRenderer<? super String> renderer) {
        super.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }
        });
    } 
}

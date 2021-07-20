package twincat.app.components;

import java.awt.Component;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class ComboBox extends JComboBox<String> {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 1);
    
    private final Border innerBorder = BorderFactory.createLoweredBevelBorder();
        
    private final CompoundBorder border = new CompoundBorder(outerBorder, innerBorder); 
   
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
            JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
            return lbl;
        }
    };
    
    private final BasicComboBoxUI comboBoxUI = new BasicComboBoxUI() {
        protected JButton createArrowButton() {
            return new JButton() {
                private static final long serialVersionUID = 1L;

                public int getWidth() {
                    return 0;
                }
            };
        }
    };
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ComboBox() {
        super();
        this.initialize();
    }

    public ComboBox(ComboBoxModel<String> aModel) {
        super(aModel);
        this.initialize();
    }

    public ComboBox(String[] items) {
        super(items);
        this.initialize();
    }

    public ComboBox(Vector<String> items) {
        super(items);
        this.initialize();
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void initialize() {
        this.setRenderer(renderer);
        this.setBorder(border);
        this.setUI(comboBoxUI);
    }
}

package twincat.app.components;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import twincat.Resources;
import twincat.Utilities;

public class CheckBox extends JCheckBox {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/
    
    public CheckBox() {
        super();
        this.initialize();
    }

    public CheckBox(Action action) {
        super(action);
        this.initialize();
    }

    public CheckBox(Icon icon, boolean selected) {
        super(icon, selected);
        this.initialize();
    }

    public CheckBox(Icon icon) {
        super(icon);
        this.initialize();
    }

    public CheckBox(String text, boolean selected) {
        super(text, selected);
        this.initialize();
    }

    public CheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
        this.initialize();
    }

    public CheckBox(String text, Icon icon) {
        super(text, icon);
        this.initialize();
    }

    public CheckBox(String text) {
        super(text);
        this.initialize();
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void initialize() {
        this.setFocusPainted(false);
        this.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_CHECKBOX)));
        this.setSelectedIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_CHECKBOX_SELECTED)));
    }    
}

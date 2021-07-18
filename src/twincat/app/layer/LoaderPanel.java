package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import twincat.Resources;
import twincat.Utilities;
import twincat.app.constant.Navigation;
import twincat.scope.Scope;

public class LoaderPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/ 

    private static final ImageIcon ICON_CHANNEL = new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_CHANNEL));

    /*********************************/
    /******** local variable *********/
    /*********************************/

    private final JFileChooser fileChooser = new JFileChooser();

    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    private final FileNameExtensionFilter filter = new FileNameExtensionFilter("Scope", "sv2");
    
    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final FileView fileView = new FileView() {
        @Override
        public Icon getIcon(File file) {
            if (!file.isDirectory() && filter.accept(file)) {
                return ICON_CHANNEL;
            } else {
                return super.getIcon(file);
            }
        }
    };

    private final ActionListener fileOpenListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                loadScopeFile(fileChooser.getSelectedFile().getPath());
            }

            if (actionEvent.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                xref.navigationPanel.setCard(Navigation.CHART);
            }
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/
  
    public LoaderPanel(XReference xref) {
        this.xref = xref;

        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileView(fileView);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.addActionListener(fileOpenListener);

        this.setLayout(new BorderLayout());
        this.add(fileChooser, BorderLayout.CENTER); 
        this.setBorder(BorderFactory.createEmptyBorder()); 
    }
    
    /*********************************/
    /******** private method *********/
    /*********************************/    

    private void loadScopeFile(String path) {
        List<Scope> scopeList = new ArrayList<Scope>();
        Scope scope = new Scope();

        try {
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(path));
            document.getDocumentElement().normalize();

            // scope node list
            NodeList scopeNodeList = document.getElementsByTagName("ScopeViewSerializable");

            for (int i = 0; i < scopeNodeList.getLength(); i++) {
                
                // scope node
                Node scopeNode = scopeNodeList.item(i);

                if (scopeNode.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = scopeNode.getAttributes();
                    for (int x = 0; x < nodeMap.getLength(); x++) {
                        Node node = nodeMap.item(x);
                        System.out.println("attr name : " + node.getNodeName());
                        System.out.println("attr value : " + node.getNodeValue());
                    }

                }
  
                /*
                if (scopeNode.getNodeType() == Node.ELEMENT_NODE) {
                    // scope element
                    Element scopeElement = (Element) scopeNode;

                    // scope name
                    String scopeName = scopeElement.getElementsByTagName("Title").item(0).getTextContent();
  
                    scopeNode.getChildNodes()
                }
                */
            }
 
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

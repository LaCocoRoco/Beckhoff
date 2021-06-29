package twincat.test;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

<<<<<<< HEAD
import twincat.ads.AdsDataTypeInfo;
import twincat.ads.constants.AdsDataType;

=======
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
public class Backup {

    public String getTreePathSymbolName(TreePath treePath) {
        if (treePath != null) {
            TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();

            if (treeNode.isLeaf()) {
                Object[] resourceList = treePath.getPath();

                String symbolName = new String();
                for (int i = 1; i < resourceList.length; i++) {
                    String resource = resourceList[i].toString();
                    if (!resource.isEmpty()) symbolName += "." + resource;
                }

                return symbolName;
            }
        }

        return null;
    }

    public DefaultMutableTreeNode copyTreeNode(DefaultMutableTreeNode node) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(node.getUserObject());

        for (int iChilddCount = node.getChildCount(), i = 0; i < iChilddCount; i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            newNode.add((DefaultMutableTreeNode) copyTreeNode(childNode));
        }

        return newNode;
    }
    
    public DefaultListModel<String> stringListToListModel(List<String> stringList) {
        DefaultListModel<String> defaultListModel = new DefaultListModel<String>();
        Iterator<String> iterator = stringList.iterator();
        
        while(iterator.hasNext()) {
            defaultListModel.addElement(iterator.next());
        }
        
        return defaultListModel;
    }
    
    // java text field
    JTextField jTextField = new JTextField();
    jTextField.setFont(new Font("SansSerif", Font.BOLD, 15));
    jTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
    jTextField.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            String input = jTextField.getText();
            Pattern pattern = Pattern.compile(input);
            
            // java tree test
            DefaultTreeModel treeModel = (DefaultTreeModel) jTree.getModel();
            DefaultMutableTreeNode treeNode = symbolTableTreeNode;

            if (!input.isEmpty()) {
                Stream<String> filteredStream = symbolTable.stream().filter(pattern.asPredicate());
                List<String> filteredList = filteredStream.collect(Collectors.toList());
                treeNode = createTreeNodeFromSymbolTable(amsPort, filteredList);
            }

            treeModel.setRoot(treeNode);
            treeModel.reload();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            String input = jTextField.getText();    
            Pattern pattern = Pattern.compile(input);
            
            // java tree test
            DefaultTreeModel treeModel = (DefaultTreeModel) jTree.getModel();
            DefaultMutableTreeNode treeNode = symbolTableTreeNode;

            if (!input.isEmpty()) {
                Stream<String> filteredStream = symbolTable.stream().filter(pattern.asPredicate());
                List<String> filteredList = filteredStream.collect(Collectors.toList());
                treeNode = createTreeNodeFromSymbolTable(amsPort, filteredList);
            }

            treeModel.setRoot(treeNode);
            treeModel.reload();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // do nothing
        }    
    });
    
    // read tree
    String symbolName = getTreePathSymbolName(treePath);
    if (symbolName != null) {
        System.out.println(symbolName);
    }
    
    /********************************/ 
    /********************************/
    /********************************/
    
    public List<String> readSymbolNameTable() throws AdsException {
        List<AdsSymbolInfo> symbolInfoList = readSymbolInfoTable();
        List<String> symbolNameList = new ArrayList<String>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            symbolNameList.add(symbolInfo.getName());       
        }
        
        return symbolNameList;
    }

    public List<String> readSymbolVariableTable() throws AdsException {
        List<AdsSymbolInfo> symbolInfoList = readSymbolInfoTable();
        List<String> symbolNameList = new ArrayList<String>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            
            if (symbolInfo.getType().contains(AdsIndexGroup.SYM_INFO_TYP_ARRAY)) {
                List<String> symbolNameArrayList = getSymbolNameOfArrayTypeBySymbolInfo(symbolInfo);
                symbolNameList.addAll(symbolNameArrayList); 
            }

            symbolNameList.add(symbolInfo.getName());       
        }
        
        return symbolNameList;
    }
    
    /********************************/ 
    /********************************/
    /********************************/
    
    if (!dataTypeInfo.getType().isEmpty()) {
        // is array
    } else  {
        symbolTable.add(symbolInfo); 
    }
<<<<<<< HEAD
    
    /********************************/ 
    /********************************/
    /********************************/
    
    public List<String> getDataTypeNameList(List<AdsDataTypeInfo> dataTypeInfoList) {
        AdsTypeInfo typeInfo = new AdsTypeInfo(type); // include into class ?
        List<String> dataTypeNameList = new ArrayList<String>();

        // get array name list
        List<String> typeArrayNameList = new ArrayList<String>();
        if (!typeInfo.getTypeArray().isEmpty()) {
            typeArrayNameList.addAll(typeInfo.getNamedTypeArray());

        }

        // get sub name list
        List<String> subDataTypeNameList = new ArrayList<String>();
        for (AdsDataTypeInfo subDataTypeInfo : subDataTypeInfoList) {
            subDataTypeNameList.addAll(subDataTypeInfo.getDataTypeNameList(dataTypeInfoList));
        }

        // get type name list
        List<String> typeNameList = new ArrayList<String>();
        if (dataType.equals(AdsDataType.BIGTYPE)) {
            for (AdsDataTypeInfo dataTypeInfo : dataTypeInfoList) {
                if (typeInfo.getType().equals(dataTypeInfo.getDataTypeName())) {
                    typeNameList = dataTypeInfo.getDataTypeNameList(dataTypeInfoList);
                    break;
                }
            }
        }

        // if (!typeArrayNameList.isEmpty()) {
        // has array but no sub data

        for (String typeArrayName : typeArrayNameList) {

            //if (!typeNameList.isEmpty()) {
                // is big type

                for (String typeName : typeNameList) {

                    // TODO : problem //System.out.println(typeName);

                    dataTypeNameList.add(dataTypeName + typeArrayName + "." + typeName);

                }
            //} else {
                // no big type

                //dataTypeNameList.add(dataTypeName + typeArrayName);

            //}
        }
        // } else {
        // has sub data but no array

        // if (!subDataTypeNameList.isEmpty()) {

        for (String subDataTypeName : subDataTypeNameList) {

            //if (!typeNameList.isEmpty()) {
                // is big type

                for (String typeName : typeNameList) {

                    dataTypeNameList.add(dataTypeName + "." + subDataTypeName + typeName);

                }
            //} else {
                // no big type

                dataTypeNameList.add(dataTypeName + "." + subDataTypeName);

            //}

        }
        // } else {

        dataTypeNameList.add(dataTypeName);

        // }

        // }

        return dataTypeNameList;
    }
=======
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
}

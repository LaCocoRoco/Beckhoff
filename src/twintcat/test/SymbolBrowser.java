package twintcat.test;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.enums.AmsPort;

// TODO : split by array

public class SymbolBrowser extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws AdsException {
		new SymbolBrowser();
	}

	public DefaultMutableTreeNode addResourceToSymbolTree(DefaultMutableTreeNode rootNode, String value) {
		for (int i = 0; i < rootNode.getChildCount(); i++) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);

			if (childNode.toString().equals(value)) {
				if (!childNode.isLeaf()) {
					return childNode;
				}
			}
		}

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(value);
		rootNode.add(node);
		return node;
	}

	public DefaultMutableTreeNode createTreeNodeFromSymbolTable(String name, List<String> symbolList) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(name);

		for (String symbol : symbolList) {
			String[] resourceList = symbol.split("\\.");

			DefaultMutableTreeNode node = root;
			for (String resource : resourceList) {
				node = addResourceToSymbolTree(node, resource);
			}
		}

		return root;
	}

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
	
	public SymbolBrowser() throws AdsException {
		// create ads
		Ads ads = new Ads();
		ads.open();
		ads.setAmsPort(AmsPort.PLC_RT1);
		
		// get symbol table
		String amsPort = AmsPort.PLC_RT1.toString();
		List<String> symbolTable = ads.readVariableSymbolNameList();

		// symbol table to tree
		DefaultMutableTreeNode symbolTableTreeNode = createTreeNodeFromSymbolTable(amsPort, symbolTable);

		// java tree
		JTree jTree = new JTree(symbolTableTreeNode);
		jTree.setBorder(new EmptyBorder(10, 10, 10, 10));
		jTree.setScrollsOnExpand(false);
		jTree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					int x = mouseEvent.getX();
					int y = mouseEvent.getY();

					TreePath treePath = jTree.getPathForLocation(x, y);
					String symbolName = getTreePathSymbolName(treePath);
					
					if (symbolName != null) {
						System.out.println(symbolName);
					}
				}
			}
		});
		JScrollPane jScrollPaneTree = new JScrollPane(jTree);
		jScrollPaneTree.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		// java list
		DefaultListModel<String> listModel = stringListToListModel(symbolTable);
		JList<String> jList = new JList<String>(listModel);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList.setLayoutOrientation(JList.VERTICAL);
		jList.setBorder(new EmptyBorder(10, 10, 10, 10));
		jList.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					System.out.println(jList.getSelectedValue());
				}
			}
		});
		//JScrollPane jScrollPaneList = new JScrollPane(jList);
		
		/* java text field */
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
				
				// java list test
				DefaultListModel<String> listModel = stringListToListModel(symbolTable);
				
				if (!input.isEmpty()) {
					Stream<String> filteredStream = symbolTable.stream().filter(pattern.asPredicate());
					List<String> filteredList = filteredStream.collect(Collectors.toList());
					listModel = stringListToListModel(filteredList);
				}
				
				jList.setModel(listModel);
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
				
				// java list test
				DefaultListModel<String> listModel = stringListToListModel(symbolTable);
				
				if (!input.isEmpty()) {
					Stream<String> filteredStream = symbolTable.stream().filter(pattern.asPredicate());
					List<String> filteredList = filteredStream.collect(Collectors.toList());
					listModel = stringListToListModel(filteredList);
				}
				
				jList.setModel(listModel);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// do nothing
			}    
		});
		
		// java panel
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		jPanel.add(Box.createHorizontalGlue());
		jPanel.setAlignmentX(LEFT_ALIGNMENT);
		jPanel.setAlignmentY(TOP_ALIGNMENT);
		jPanel.add(jTextField);
		//jPanel.add(jScrollPaneList);	
		jPanel.add(jScrollPaneTree);
		
		// frame test
		this.add(jPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(500, 500));
		this.pack();
		this.setVisible(true);
	}
}

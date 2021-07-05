package twincat.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;

import twincat.ads.AdsException;
import twincat.ads.AdsSymbol;
import twincat.ads.AdsSymbolInfo;

public class SymbolBrowser extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws AdsException {
		new SymbolBrowser();
	}

	public DefaultMutableTreeNode addResourceToSymbolTree(DefaultMutableTreeNode rootNode, String value, AdsSymbolInfo symbol) {
		for (int i = 0; i < rootNode.getChildCount(); i++) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);

			if (childNode.toString().equals(value)) {
				if (!childNode.isLeaf()) {
					return childNode;
				}
			}
		}

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(value);
		node.setUserObject(symbol);
		rootNode.add(node);
		return node;
	}
 
	public DefaultMutableTreeNode createTreeNodeFromSymbolTable(String name, List<AdsSymbolInfo> symbolInfoList) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(name);

		for (AdsSymbolInfo symbol : symbolInfoList) {
			String[] resourceList = symbol.getSymbolName().split("\\.");

			DefaultMutableTreeNode node = root;
			for (String resource : resourceList) {
				node = addResourceToSymbolTree(node, resource, symbol);
			}
		}

		return root;
	}

	/**************************/
	/**************************/
	
	/*
	 * 1  (BIGT) MAIN.L_SIZE_ENTRY.ARRDATA
	 * -> is leaf and big, add
	 * 2. (PRIM) MAIN.L_SIZE_ENTRY.ARRDATA.ARRDATA[1768]
	 * 3. (BIGT) .MIB_IF_TYPE_TOKENRING
	 * -> is leaf and big, add
	 * 4. (PRIM) .MIB_IF_TYPE_TOKENRING.E_MIB_IF_TYPE
	 *
	 * 1. add whole list
	 * 2. if part exist it will be added
	 * 3. only add if is leaf and big
	 * 
	 * 1. MAIN.L_FB_UNIT
	 * 2. MAIN.L_FB_UNIT.INTERNAL_STRUCTURE_ARRAY[1].ST_VALUE.M
	 * 
	 * 
	 */

	
    private DefaultMutableTreeNode symbolToNodeList(List<AdsSymbol> symbolList) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
    
        for (AdsSymbol symbol : symbolList) {
            
            List<String> symbolNameList = Arrays.asList(symbol.getName().split("\\."));
            Iterator<String> symbolNameIterator = symbolNameList.iterator();
            
            for (int i = 0; i < rootNode.getChildCount(); i++) {
    
            }

            
            DefaultMutableTreeNode symbolRootNode = null;
    
            // TODO
            DefaultMutableTreeNode node = null;
     
         }

         return rootNode;
     }
	
	

	public SymbolBrowser() throws AdsException {
	    /*
	    
		// create ads
		AdsClient ads = new AdsClient();
		ads.setAmsPort(AmsPort.TC2PLC1);
		ads.setAmsNetId(AmsNetId.LOCAL);

		// get symbol list
		AdsSymbolLoader symbolLoader = new AdsSymbolLoader(ads);
		List<AdsSymbol> symbolList = symbolLoader.getSymbolTable();

		// get symbol only from name
		String symbolName = ".junit_array_small"; // MAIN.L_FB_UNIT
		List<AdsSymbol> symbolNameList = symbolLoader.getSymbolBySymbolName(symbolName);
		
		// get node list
		DefaultMutableTreeNode rootNode = symbolToNodeList(symbolNameList);

		
		
        // get root
	    ads.open();
	    String rootName = "LOCAL: " + ads.readAmsNetId();
	    ads.close();


		// read tree element
		JTree jTree = new JTree(rootNode);
		jTree.setBorder(new EmptyBorder(10, 10, 10, 10));
		jTree.setScrollsOnExpand(false);
		jTree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					int x = mouseEvent.getX();
					int y = mouseEvent.getY();
					
					TreePath treePath = jTree.getPathForLocation(x, y);
					TreeNode treeNode = (TreeNode) treePath.getLastPathComponent();
					Object[] resourceList = treePath.getPath();
					Object test = resourceList[0];
					
					System.out.println(resourceList[0]);
				}
			}
		});
		
		JScrollPane jScrollPaneTree = new JScrollPane(jTree);
		jScrollPaneTree.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		jPanel.add(Box.createHorizontalGlue());
		jPanel.setAlignmentX(LEFT_ALIGNMENT);
		jPanel.setAlignmentY(TOP_ALIGNMENT);
		jPanel.add(jScrollPaneTree);
		
		this.add(jPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(500, 500));
		this.pack();
		this.setVisible(true);
		*/
	}
}

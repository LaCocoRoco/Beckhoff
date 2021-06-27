package twincat.test;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import twincat.ads.Ads;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbolInfo;

/* TODO : rework with ads symbol info
 *        add data to tree
 *        on click load info array if necessary
 */

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
 
	public DefaultMutableTreeNode createTreeNodeFromSymbolTable(String name, List<AdsSymbolInfo> symbolInfoList) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(name);

		for (AdsSymbolInfo symbol : symbolInfoList) {
			String[] resourceList = symbol.getSymbolName().split("\\.");

			DefaultMutableTreeNode node = root;
			for (String resource : resourceList) {
				node = addResourceToSymbolTree(node, resource);
			}
		}

		return root;
	}

	
	
	
	
	
    public DefaultMutableTreeNode getSymbolInfoTreeTable(List<AdsSymbolInfo> symbolInfoList) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();

        for (AdsSymbolInfo symbol : symbolInfoList) {
            String[] resourceList = symbol.getSymbolName().split("\\.");

            DefaultMutableTreeNode node = root;
            for (String resource : resourceList) {
                node = addResourceToSymbolTree(node, resource);
            }
        }

        return root;
    }
	
	public SymbolBrowser() throws AdsException {
		// create ads
		Ads ads = new Ads();
		ads.open();
		String amsNetId = ads.readAmsNetId();
		ads.setAmsNetId(amsNetId);
		
		// get symbol table
		List<AdsSymbolInfo> symbolInfoTable = ads.readSymbolInfoList();

		// symbol table to tree
		DefaultMutableTreeNode symbolTableTreeNode = createTreeNodeFromSymbolTable(amsNetId, symbolInfoTable);

		// read tree element
		JTree jTree = new JTree(symbolTableTreeNode);
		jTree.setBorder(new EmptyBorder(10, 10, 10, 10));
		jTree.setScrollsOnExpand(false);
		jTree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					int x = mouseEvent.getX();
					int y = mouseEvent.getY();
					TreePath treePath = jTree.getPathForLocation(x, y);
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
	}
}

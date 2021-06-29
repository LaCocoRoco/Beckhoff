package twincat.app;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import twincat.ads.AdsLogger;
import twincat.app.components.AdsInfoPanel;
import twincat.app.components.AxisPanel;
import twincat.app.components.ConsolePanel;
import twincat.app.components.ScopePanel;

public class Application extends JFrame {

	private static final long serialVersionUID = 1L;

	/*************************/
	/** constant attributes **/
	/*************************/

	private static final int WIDTH_FRAME = 800;

	private static final int HEIGHT_FRAME = 600;

	private static final int HEIGHT_CONSOLE = 250;

	private static final String WINDOW_SCOPE = "Scope";

	private static final String WINDOW_ADS_INFO = "Ads Info";
	
	private static final String WINDOW_AXIS = "Achsen";

	private static final Level LOG_LEVEL = Level.INFO;
	
	/*************************/
	/*** local attributes ****/
	/*************************/

	private final JSplitPane mainPanel = new JSplitPane();

	private final JPanel windowPanel = new JPanel();

	private final ConsolePanel consolePanel = new ConsolePanel();

	private final AdsInfoPanel adsInfoPanel = new AdsInfoPanel();

	private final ScopePanel scopePanel = new ScopePanel();
	
	private final AxisPanel axisPanel = new AxisPanel();

	/*************************/
	/****** constructor ******/
	/*************************/
	
	public Application() {
		// global configuration
	    AdsLogger.getLogger().setLevel(LOG_LEVEL);
		
		// window panel
		windowPanel.setLayout(new CardLayout());
		windowPanel.add(scopePanel, WINDOW_SCOPE);
		windowPanel.add(adsInfoPanel, WINDOW_ADS_INFO);
		windowPanel.add(axisPanel, WINDOW_AXIS);

		// main panel
		mainPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainPanel.setContinuousLayout(true);
		mainPanel.setLeftComponent(windowPanel);
		mainPanel.setRightComponent(consolePanel);
		mainPanel.setOneTouchExpandable(false);
		mainPanel.setBorder(new EmptyBorder(3, 3, 3, 3));

		// menu extras
		JMenuItem menuConsoleOn = new JMenuItem("On");
		menuConsoleOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				showConsole();
			}
		});

		JMenuItem menuConsoleOff = new JMenuItem("Off");
		menuConsoleOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				hideConsole();
			}
		});

		JMenu menuConsole = new JMenu("Console");
		menuConsole.add(menuConsoleOn);
		menuConsole.add(menuConsoleOff);

		// menu window
		JMenuItem menuScope = new JMenuItem(WINDOW_SCOPE);
		menuScope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				displayWindow(WINDOW_SCOPE);
			}
		});

		JMenuItem menuAds = new JMenuItem(WINDOW_ADS_INFO);
		menuAds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				displayWindow(WINDOW_ADS_INFO);
			}
		});

		JMenuItem menuAxxa = new JMenuItem(WINDOW_AXIS);
		menuAxxa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				displayWindow(WINDOW_AXIS);
			}
		});
		
		JMenu menuWindow = new JMenu("Window");
		menuWindow.add(menuScope);
		menuWindow.add(menuAxxa);
		menuWindow.add(menuAds);

		// main menu
		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(menuWindow);
		mainMenu.add(menuConsole);

		// load default
		showConsole();
		displayWindow(WINDOW_ADS_INFO);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int framePositionX = screenSize.width / 2 - WIDTH_FRAME / 2;
		int framePositionY = screenSize.height / 2 - HEIGHT_FRAME / 2;
		this.getParent();
		this.setTitle("Blum Scope");
		this.setType(Type.NORMAL);
		this.setIconImage(new ImageIcon(getClass().getResource("/resource/app.png")).getImage());
		this.setJMenuBar(mainMenu);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(WIDTH_FRAME, HEIGHT_FRAME));
		this.setBounds(framePositionX, framePositionY, WIDTH_FRAME, HEIGHT_FRAME);
		this.pack();
		this.setVisible(true);
	}

	/*************************/
	/******** private ********/
	/*************************/

	private void showConsole() {
		mainPanel.setDividerLocation(HEIGHT_FRAME - HEIGHT_CONSOLE);  // TODO : resize
		mainPanel.getRightComponent().setVisible(true);
		mainPanel.getLeftComponent().setVisible(true);
		mainPanel.setDividerSize(5);	
	}
	
	private void hideConsole() {
		mainPanel.getRightComponent().setVisible(false);
		mainPanel.getLeftComponent().setVisible(true);
		mainPanel.setDividerSize(0);
	}

	private void displayWindow(String name) {
		CardLayout windowLayout = (CardLayout) (windowPanel.getLayout());
		windowLayout.show(windowPanel, name);
	}

	/*************************/
	/** public static final **/
	/*************************/

	public static final void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Application();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

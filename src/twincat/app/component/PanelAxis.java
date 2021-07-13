package twincat.app.component;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import twincat.ads.constant.AmsPort;
import twincat.ads.container.RouteSymbolData;
import twincat.ads.container.Symbol;
import twincat.ads.worker.RouteSymbolLoader;
import twincat.ads.worker.SymbolLoader;

// TODO : scroll able interface

public class PanelAxis extends JScrollPane {
    /***********************************/
    /***** local constant variable *****/
    /***********************************/

	private static final long serialVersionUID = 1L;

    /***********************************/
    /******* local final variable ******/
    /***********************************/

    private final RouteSymbolLoader routeSymbolLoader = new RouteSymbolLoader();

    /***********************************/
    /*********** constructor ***********/
    /***********************************/

	public PanelAxis() {
        JPanel axisPanel = new JPanel();
        axisPanel.setBorder(BorderFactory.createEmptyBorder());
        axisPanel.setLayout(new FlowLayout());
	    
        routeSymbolLoader.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object object) {
                // loadingData.setText(routeSymbolLoader.getLoadingState());
            } 
        });

        routeSymbolLoader.loadRouteSymbolDataList(AmsPort.NC);
        routeSymbolLoader.loadRouteSymbolDataList(AmsPort.NCSAF);
        routeSymbolLoader.loadRouteSymbolDataList(AmsPort.NCSVB);
        
        for (RouteSymbolData routeSymbolData : routeSymbolLoader.getRouteSymbolDataList()) {
            String route = routeSymbolData.getRoute().getHostName();
            String port = routeSymbolData.getSymbolLoader().getAmsPort().toString();

            // loadingData.setText(route + " | " + port);

            SymbolLoader symbolLoader = routeSymbolData.getSymbolLoader();

            List<Symbol> routeSymbolList = symbolLoader.getSymbolList();
            for (Symbol symbol : routeSymbolList) {
                
                JButton dummyButton = new JButton();
                dummyButton.setPreferredSize(new Dimension(150, 50));
                
                axisPanel.add(dummyButton);
                
                System.out.println(symbol.getSymbolName());
            }
        }

		this.setViewportView(axisPanel);
	}
}

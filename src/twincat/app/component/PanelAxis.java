package twincat.app.component;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import twincat.ads.constant.AmsPort;
import twincat.ads.container.RouteSymbolData;
import twincat.ads.container.Symbol;
import twincat.ads.worker.RouteSymbolLoader;
import twincat.ads.worker.SymbolLoader;
import twincat.app.container.ScrollablePanel;
import twincat.app.container.WrapLayout;

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
	    ScrollablePanel scrollablePanel = new ScrollablePanel(new WrapLayout());
	    scrollablePanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);

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
            // needed to build scope
            String amsNetId = routeSymbolData.getRoute().getAmsNetId();
            AmsPort amsPort = routeSymbolData.getSymbolLoader().getAmsPort();

            // loadingData.setText(route + " | " + port);

            SymbolLoader symbolLoader = routeSymbolData.getSymbolLoader();

            List<String> axisNameList = new ArrayList<String>();
            
            List<Symbol> routeSymbolList = symbolLoader.getSymbolList();
            for (Symbol symbol : routeSymbolList) {
                String symbolName = symbol.getSymbolName();

                int rangeBeg = symbolName.lastIndexOf(".");
                int rangeMid = symbolName.indexOf(".");

                String axisName = symbolName.substring(rangeMid + 1, rangeBeg);
                
                if (!axisNameList.contains(axisName)) {
                    axisNameList.add(axisName);
                }
            }
            
            for (String axisName : axisNameList) {
                JButton dummyButton = new JButton();
                dummyButton.setText(axisName);
                dummyButton.setPreferredSize(new Dimension(150, 50));
                scrollablePanel.add(dummyButton);
            }
        }

        this.setViewportView(scrollablePanel);
	}
}

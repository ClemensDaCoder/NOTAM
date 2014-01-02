package at.jku.dke.wsdl.notamforwaypoint;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;


/**
 * @author Michael Weichselbaumer
 * @author Manuel Hochreiter
 *
 */
@WebServiceClient(name = "Notam_For_Waypoint", 
                  wsdlLocation = "file:WebContent/wsdl/NotamForWaypoint.wsdl",
                  targetNamespace = "http://dke.jku.at/wsdl/NotamForWaypoint") 
public class NotamForWaypoint extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://dke.jku.at/wsdl/NotamForWaypoint", "Notam_For_Waypoint");
    public final static QName NotamForWaypointPort = new QName("http://dke.jku.at/wsdl/NotamForWaypoint", "NotamForWaypoint_Port");
    static {
        URL url = null;
        try {
            url = new URL("file:WebContent/wsdl/NotamForWaypoint.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(NotamForWaypoint.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:WebContent/wsdl/NotamForWaypoint.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public NotamForWaypoint(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public NotamForWaypoint(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NotamForWaypoint() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns NotamForWaypointPortType
     */
    @WebEndpoint(name = "NotamForWaypoint_Port")
    public NotamForWaypointPortType getNotamForWaypointPort() {
        return super.getPort(NotamForWaypointPort, NotamForWaypointPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NotamForWaypointPortType
     */
    @WebEndpoint(name = "NotamForWaypoint_Port")
    public NotamForWaypointPortType getNotamForWaypointPort(WebServiceFeature... features) {
        return super.getPort(NotamForWaypointPort, NotamForWaypointPortType.class, features);
    }

}

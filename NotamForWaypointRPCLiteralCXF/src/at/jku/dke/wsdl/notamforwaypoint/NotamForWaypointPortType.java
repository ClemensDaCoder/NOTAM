package at.jku.dke.wsdl.notamforwaypoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


/**
 * @author Michael Weichselbaumer
 * @author Manuel Hochreiter
 *
 */
@WebService(targetNamespace = "http://dke.jku.at/wsdl/NotamForWaypoint", name = "NotamForWaypoint_PortType")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface NotamForWaypointPortType {

    @WebResult(name = "notamValid", targetNamespace = "http://dke.jku.at/wsdl/NotamForWaypoint", partName = "notamValid")
    @WebMethod(action = "notamValidForWaypoint")
    public boolean notamValidForWaypoint(
        @WebParam(partName = "flightDepartureTime", name = "flightDepartureTime")
        javax.xml.datatype.XMLGregorianCalendar flightDepartureTime,
        @WebParam(partName = "waypointRelativeDuration", name = "waypointRelativeDuration")
        javax.xml.datatype.Duration waypointRelativeDuration,
        @WebParam(partName = "notamValidFrom", name = "notamValidFrom")
        javax.xml.datatype.XMLGregorianCalendar notamValidFrom,
        @WebParam(partName = "notamValidTo", name = "notamValidTo")
        javax.xml.datatype.XMLGregorianCalendar notamValidTo,
        @WebParam(partName = "notamApplyFrom", name = "notamApplyFrom")
        javax.xml.datatype.XMLGregorianCalendar notamApplyFrom,
        @WebParam(partName = "notamApplyTo", name = "notamApplyTo")
        javax.xml.datatype.XMLGregorianCalendar notamApplyTo
    );
}

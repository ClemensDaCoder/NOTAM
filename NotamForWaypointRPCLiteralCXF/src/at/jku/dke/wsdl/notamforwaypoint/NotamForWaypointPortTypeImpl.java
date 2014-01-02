
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package at.jku.dke.wsdl.notamforwaypoint;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.xml.datatype.XMLGregorianCalendar;



/**
 * @author Michael Weicshelbaumer
 * @author Manuel Hochreiter
 *
 */
@javax.jws.WebService(
                      serviceName = "Notam_For_Waypoint",
                      portName = "NotamForWaypoint_Port",
                      targetNamespace = "http://dke.jku.at/wsdl/NotamForWaypoint",
                      wsdlLocation = "file:WebContent/wsdl/NotamForWaypoint.wsdl",
                      endpointInterface = "at.jku.dke.wsdl.notamforwaypoint.NotamForWaypointPortType")
                      
public class NotamForWaypointPortTypeImpl implements NotamForWaypointPortType {

    private static final Logger LOG = Logger.getLogger(NotamForWaypointPortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see at.jku.dke.wsdl.notamforwaypoint.NotamForWaypointPortType#notamValidForWaypoint(javax.xml.datatype.XMLGregorianCalendar  flightDepartureTime ,)javax.xml.datatype.Duration  waypointRelativeDuration ,)javax.xml.datatype.XMLGregorianCalendar  notamValidFrom ,)javax.xml.datatype.XMLGregorianCalendar  notamValidTo ,)javax.xml.datatype.XMLGregorianCalendar  notamApplyFrom ,)javax.xml.datatype.XMLGregorianCalendar  notamApplyTo )*
     */
    public boolean notamValidForWaypoint(javax.xml.datatype.XMLGregorianCalendar flightDepartureTime,javax.xml.datatype.Duration waypointRelativeDuration,javax.xml.datatype.XMLGregorianCalendar notamValidFrom,javax.xml.datatype.XMLGregorianCalendar notamValidTo,javax.xml.datatype.XMLGregorianCalendar notamApplyFrom,javax.xml.datatype.XMLGregorianCalendar notamApplyTo) { 
        LOG.info(Messages.getString("NotamForWaypointPortTypeImpl.flightDeparture") + flightDepartureTime.toString()); //$NON-NLS-1$
        try {
        	flightDepartureTime.add(waypointRelativeDuration);
        	LOG.info(Messages.getString("NotamForWaypointPortTypeImpl.analyze") + flightDepartureTime.toString()); //$NON-NLS-1$
        	
        	Calendar wayPointCalendar = convertToJavaCalendar(flightDepartureTime);
        	Calendar validFromCalendar = convertToJavaCalendar(notamValidFrom);
        	Calendar validToCalendar = convertToJavaCalendar(notamValidTo);
        	
        	if (wayPointCalendar.after(validFromCalendar) && wayPointCalendar.before(validToCalendar)) {
        		Calendar applyFromCalendar = convertApplyTimeToJavaCalendar(notamApplyFrom, wayPointCalendar);
        		Calendar applyToCalendar = convertApplyTimeToJavaCalendar(notamApplyTo, wayPointCalendar);
        		
        		if (applyToCalendar.before(applyFromCalendar)) {
        			applyFromCalendar.add(Calendar.DAY_OF_MONTH, -1);
        			
        			//if modified applyFrom is earlier than validFrom of Notam, applyFrom is invalid, notam is not relevant for flight
        			if (applyFromCalendar.before(validFromCalendar)) {
        				LOG.info(Messages.getString("NotamForWaypointPortTypeImpl.invalid")); //$NON-NLS-1$
        				return false;
        			}
        		}
        		
        		if (wayPointCalendar.after(applyFromCalendar) && wayPointCalendar.before(applyToCalendar)) {
        			LOG.info(Messages.getString("NotamForWaypointPortTypeImpl.valid")); //$NON-NLS-1$
        			return true;
        		} else {
        			LOG.info(Messages.getString("NotamForWaypointPortTypeImpl.wrongApplyTime")); //$NON-NLS-1$
        			return false;
        		}
        	} else {
        		LOG.info(Messages.getString("NotamForWaypointPortTypeImpl.invalid")); //$NON-NLS-1$
        		return false;
        	}
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    private Calendar convertToJavaCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(), xmlGregorianCalendar.getDay(), xmlGregorianCalendar.getHour(), xmlGregorianCalendar.getMinute(), xmlGregorianCalendar.getSecond());
    	return cal;
    }
    
    private Calendar convertApplyTimeToJavaCalendar(XMLGregorianCalendar xmlGregorianCalendar, Calendar wayPointCalendar) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(wayPointCalendar.get(Calendar.YEAR), wayPointCalendar.get(Calendar.MONTH), wayPointCalendar.get(Calendar.DATE));
    	cal.set(Calendar.HOUR_OF_DAY, xmlGregorianCalendar.getHour());
    	cal.set(Calendar.MINUTE, xmlGregorianCalendar.getMinute());
    	cal.set(Calendar.SECOND, xmlGregorianCalendar.getSecond());
    	return cal;
    }

}

package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.jku.dke.wsdl.notamforwaypoint.NotamForWaypointPortTypeImpl;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;


@RunWith(JUnit4.class)
public class NotamForWaypointTest {
	
	private NotamForWaypointPortTypeImpl object;
	private XMLGregorianCalendar flightDeparture;
	private Duration duration;
	private XMLGregorianCalendar validFrom;
	private XMLGregorianCalendar validTo;
	
	private XMLGregorianCalendar applyFrom;
	private XMLGregorianCalendar applyTo;
	
	
	
	@Before
	public void setup() throws DatatypeConfigurationException {

		object = new NotamForWaypointPortTypeImpl();
		
		flightDeparture = XMLGregorianCalendarImpl.createDateTime(2013, 11, 5, 10, 00, 00); //2013-11-05 10:00
		duration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 0, 10, 0); //10 minutes
	
		validFrom = XMLGregorianCalendarImpl.createDateTime(2013, 11, 4, 8, 0, 0); //2013-11-04 08:00
		validTo = XMLGregorianCalendarImpl.createDateTime(2013, 11, 9, 8, 0, 0); //2013-11-09 08:00
		
		applyFrom = XMLGregorianCalendarImpl.createTime(9, 0, 0, 0); //09:00 Z
		applyTo = XMLGregorianCalendarImpl.createTime(15, 0, 0, 0); //15:00 Z
		
	
		
	}
	
	@Test
    public void thisAlwaysPasses() throws RemoteException {
		assertTrue(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
    }
	
	@Test
	public void thisAlwaysFails() {
		applyFrom = XMLGregorianCalendarImpl.createTime(11, 0, 0, 0);
		assertFalse(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
	}

}

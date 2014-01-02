package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import at.jku.dke.wsdl.notamforwaypoint.NotamForWaypointPortTypeImpl;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;


@RunWith(JUnit4.class)
public class NotamForWaypointTest {
	
	private static DatatypeFactory dtFactory;
	
	
	private NotamForWaypointPortTypeImpl object;
	private XMLGregorianCalendar flightDeparture;
	private Duration duration;
	private XMLGregorianCalendar validFrom;
	private XMLGregorianCalendar validTo;
	
	private XMLGregorianCalendar applyFrom;
	private XMLGregorianCalendar applyTo;
	
	@BeforeClass
	public static void generalSetup() throws DatatypeConfigurationException {
		dtFactory = DatatypeFactory.newInstance();
	}
	
	@Before
	public void setup() throws DatatypeConfigurationException {
		object = new NotamForWaypointPortTypeImpl();
		flightDeparture = XMLGregorianCalendarImpl.createDateTime(2013, 11, 5, 10, 00, 00); //2013-11-05 10:00
		duration = dtFactory.newDuration(true, 0, 0, 0, 0, 10, 0); //10 minutes
		validFrom = XMLGregorianCalendarImpl.createDateTime(2013, 11, 4, 8, 0, 0); //2013-11-04 08:00
		validTo = XMLGregorianCalendarImpl.createDateTime(2013, 11, 9, 8, 0, 0); //2013-11-09 08:00
		applyFrom = XMLGregorianCalendarImpl.createTime(9, 0, 0, 0); //09:00 Z
		applyTo = XMLGregorianCalendarImpl.createTime(15, 0, 0, 0); //15:00 Z
	}
	
	@Test
    public void notamValidForFlight() throws RemoteException {
		assertTrue(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
    }
	
	@Test
	public void applyTimeTooLate() {
		applyFrom = XMLGregorianCalendarImpl.createTime(11, 0, 0, 0); //11:00
		assertFalse(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
	}
	
	@Test
	public void applyToTooEarly() {
		applyTo = XMLGregorianCalendarImpl.createTime(9, 50, 0, 0); //09:50
		assertFalse(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
	}
	
	@Test
	public void wayPointLaterThanValidTo() {
		duration = dtFactory.newDuration(true, 0, 0, 6, 0, 10, 0); //takes 6 days to reach waypoint
		assertFalse(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
 	}
	
	@Test 
	public void flightAndWaypointEarlierThanValidFrom() {
		flightDeparture = XMLGregorianCalendarImpl.createDateTime(2013, 11, 5, 7, 00, 00); //2013-11-05 7:00
		assertFalse(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
	}
	
	@Test 
	public void flightDepartureEarlierThanValidFromWaypointLateEnoughForValidFrom() {
		flightDeparture = XMLGregorianCalendarImpl.createDateTime(2013, 11, 5, 7, 00, 00); //2013-11-05 7:00
		duration = dtFactory.newDuration(true, 0, 0, 0, 3, 10, 0); //takes 3 hours and 10 minutes to reach waypoint
		assertTrue(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
		
	}
	
	@Test
	public void applyFromLaterThanApplyTo() {
		applyFrom = XMLGregorianCalendarImpl.createTime(22, 0, 0, 0); //22:00 Z
		assertTrue(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
	}
	
	@Test
	public void applyFromLaterThanApplyToAndFlightOnValidFromDate() {
		//applyFrom is later than applyTo
		applyFrom = XMLGregorianCalendarImpl.createTime(22, 0, 0, 0); //16:00 Z
		//flightDeparture on same date as validFrom date
		flightDeparture = XMLGregorianCalendarImpl.createDateTime(2013, 11, 4, 7, 00, 00); //2013-11-05 7:00
		assertFalse(object.notamValidForWaypoint(flightDeparture, duration, validFrom, validTo, applyFrom, applyTo));
	}

}

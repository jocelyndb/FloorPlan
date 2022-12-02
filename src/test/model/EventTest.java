package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e1;
    private Event e2;
    private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e1 = new Event("Sensor open at door");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Sensor open at door", e1.getDescription());
		assertEquals(d, e1.getDate());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Sensor open at door", e1.toString());
	}

    @Test
    public void testEqualsNull() {
        assertFalse(e1.equals(e2));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(e1.equals("Test"));
    }

    @Test
    public void testHashCode() {
        assertTrue(e1.hashCode() == 13 * e1.getDate().hashCode() + e1.getDescription().hashCode());
    }
}

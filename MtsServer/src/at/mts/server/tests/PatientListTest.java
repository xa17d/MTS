package at.mts.server.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.mts.entity.PatientList;
import at.mts.entity.PatientListItem;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

public class PatientListTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEmpty() {
		PatientList l = new PatientList();
		
		assertTrue(l.isEmpty());
		
		String xml = l.asXml();
		PatientList m = new PatientList(xml);
		
		assertTrue(m.isEmpty());
	}
	
	@Test
	public void testContent() {
		PatientList l = new PatientList();
		
		PatientListItem item;
		
		item = new PatientListItem();
		item.setNameFamily("Family1");
		item.setNameGiven("Given1");
		item.setCategory(TriageCategory.minor);
		item.setTreatment(Treatment.sighted);
		item.setUrl("http://example.com/1234");
		l.add(item);
		
		item = new PatientListItem();
		l.add(item);
		
		String xml = l.asXml();
		PatientList m = new PatientList(xml);
		
		assertTrue(m.get(0).getNameFamily().equals("Family1"));
		assertTrue(m.get(0).getNameGiven().equals("Given1"));
		assertTrue(m.get(0).getCategory().equals(TriageCategory.minor));
		assertTrue(m.get(0).getTreatment().equals(Treatment.sighted));
		assertTrue(m.get(0).getUrl().equals("http://example.com/1234"));
		
		assertTrue(m.get(1).getNameFamily().equals(""));
		assertTrue(m.get(1).getNameGiven().equals(""));
		assertTrue(m.get(1).getCategory().equals(TriageCategory.notSpecified));
		assertTrue(m.get(1).getTreatment().equals(Treatment.notSpecified));
		assertTrue(m.get(1).getUrl().equals(""));
	}

}

import java.util.UUID;

import at.mts.entity.Gender;
import at.mts.entity.Patient;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.TriageCategory;
import at.mts.entity.cda.CdaDocument;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Patient p = new Patient();
		p.setId(UUID.randomUUID());
		p.setNameFamily("Dobler");
		p.setNameGiven("Lucas");
		p.setCategory(TriageCategory.immediate);
		p.setGender(Gender.male);
		p.setPhaseOfLife(PhaseOfLife.child);
		
		System.out.print(new CdaDocument(p).asXml());
	}

}

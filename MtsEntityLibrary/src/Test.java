import at.mts.entity.Patient;
import at.mts.entity.cda.CdaDocument;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Patient p = new Patient();
		
		CdaDocument doc = new CdaDocument(p);
		String xml = doc.asXml();
		
		System.out.print(xml);
	}

}

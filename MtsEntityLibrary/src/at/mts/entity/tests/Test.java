package at.mts.entity.tests;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import at.mts.entity.cda.*;

import static org.junit.Assert.*;

public class Test {

	@org.junit.Test
	public void test() throws IOException {
		
		String xml1 = readFile("CDA1.xml");
		CdaDocument cda1= new CdaDocument(xml1);
		CdaDocument cda1c = new CdaDocument(cda1.asXml());
		assertEquals("Erstelltes CDA-File ist nicht identisch mit der Quelldatei(CDA1.xml)",0,cda1.asXml().compareTo(cda1c.asXml()));

		String xml2 = readFile("CDA2.xml");
		CdaDocument cda2= new CdaDocument(xml2);
		CdaDocument cda2c = new CdaDocument(cda2.asXml());
		assertEquals("Erstelltes CDA-File ist nicht identisch mit der Quelldatei(CDA2.xml)",0,cda2.asXml().compareTo(cda2c.asXml()));
		
		String xml3 = readFile("CDA3.xml");
		CdaDocument cda3= new CdaDocument(xml3);
		CdaDocument cda3c = new CdaDocument(cda3.asXml());
		assertEquals("Erstelltes CDA-File ist nicht identisch mit der Quelldatei(CDA3.xml)",0,cda3.asXml().compareTo(cda3c.asXml()));
		
		String xml4 = readFile("CDA4.xml");
		CdaDocument cda4= new CdaDocument(xml4);
		CdaDocument cda4c = new CdaDocument(cda4.asXml());
		assertEquals("Erstelltes CDA-File ist nicht identisch mit der Quelldatei(CDA4.xml)",0,cda4.asXml().compareTo(cda4c.asXml()));
		
		}

	private static String readFile(String path) throws IOException {
		Charset encoding = Charset.defaultCharset();
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	
	
}
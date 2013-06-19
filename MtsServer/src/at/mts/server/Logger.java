package at.mts.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Logger {

	private Logger(Class<?> clazz) { this.clazz = clazz; }
	private Class<?> clazz;
	
	public static Logger forClass(Class<?> clazz) {
		return new Logger(clazz);
	}

	public void info(String msg) {
		append(clazz, "[INFO] " + msg, null);
	}

	public void error(String msg, Throwable e) {
		append(clazz, "[ERROR] " + msg, e);
	}

	public void warn(String msg) {
		append(clazz, "[WARN] " + msg, null);
	}
	
	private static synchronized void append(Class<?> clazz, String msg, Throwable e) {
		
		System.out.println(msg);
		
		PrintWriter w = getWriter();
		if (w!=null) {
			w.println("* "+new Date().toString()+"\t"+msg);
			while (e != null) {
				w.println("Cause: "+e.getMessage());
				for (StackTraceElement ste : e.getStackTrace()) {
					w.println("  "+ste.toString());
				}
				e = e.getCause();
			}
			w.flush();
		}
	}
	
	private static String path;
	private static PrintWriter writer;
	private static PrintWriter getWriter() {
		if (writer == null) {
			try {
				path = "/usr/local/Tomcat7/logs/MtsServer.log";
			    writer = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
			} catch (IOException e) {
				try {
					path = "C:\\temp\\MtsServer.log";
				    writer = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
				} catch (IOException f) {
				}
			}
		}
		
		return writer;
	}

	private static String readFile(String path) throws IOException
	{
		Charset encoding = Charset.defaultCharset();
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	public String readAll() {
		try {
			return readFile(path);
		} catch (IOException e) {
			return "Error loading Log: "+e.getMessage();
		}
	}
}

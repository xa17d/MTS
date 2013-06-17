package at.mts.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
	
	private static PrintWriter writer;
	private static PrintWriter getWriter() {
		if (writer == null) {
			try {
			    writer = new PrintWriter(new BufferedWriter(new FileWriter("/usr/local/Tomcat7/logs/MtsServer.log", true)));
			} catch (IOException e) {
				try {
				    writer = new PrintWriter(new BufferedWriter(new FileWriter("C:\\temp\\MtsServer.log", true)));
				} catch (IOException f) {
				}
			}
		}
		
		return writer;
	}
}

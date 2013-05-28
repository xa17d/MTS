package at.mts.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class Hello {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello MTS Server" + "</title>"
		+ "<body><h1>Hello MTS Server</h1><p>MTS Server is now running on your System.</p></body>" + "</html> ";
	}
}

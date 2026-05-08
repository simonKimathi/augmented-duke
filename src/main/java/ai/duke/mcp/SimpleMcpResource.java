package ai.duke.mcp;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("mcp")
public class SimpleMcpResource {

    @Inject
    private SimpleMcpAiService simpleMcpAiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String callAI() {

        return simpleMcpAiService.chat("My name is Donald Duck, please list my bookings");
    }
}
package ai.duke.rag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("rag")
public class SimpleRagResource {

    @Inject
    private SimpleRagAiService simpleRagAiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String callAI() {

//        return simpleRagAiService.askAboutDocuments("What cars Quacks for Ducks Car Rental have?");
        return simpleRagAiService.askAboutDocuments("What cars do ducks rent?");
    }
}
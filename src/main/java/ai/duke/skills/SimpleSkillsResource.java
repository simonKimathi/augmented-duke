package ai.duke.skills;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("skills")
public class SimpleSkillsResource {

    @Inject
    private SimpleSkillsAiService skilledAiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String callAI() {

       return new StringBuilder()
               .append("Famous Duck: ")
               .append(skilledAiService.chat("Who is a famous White Pekin duck??"))
               .append("\n\n")
               .append(skilledAiService.chat("What does a White Pekin duck say?"))
               .append("\n\n")
               .append(skilledAiService.chat("Where does White Pekin ducks live?"))
               .toString();
    }
}
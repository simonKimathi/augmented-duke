package ai.duke.mcp;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.SystemMessage;

@RegisterAIService(toolProviderName = "mcp")
public interface SimpleMcpAiService {

    @SystemMessage("You are a helpful assistant.")
    String chat(String message);
}

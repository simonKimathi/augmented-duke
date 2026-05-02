package ai.duke.skills;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class SkillsAiServiceProducer {

    @Produces
    public SimpleSkillsAiService createSkillsAiService() {
        return AiServices.builder(SimpleSkillsAiService.class)
                .chatModel(OpenAiChatModel.builder()
                        .apiKey(System.getenv("OPENAI_API_KEY"))
                        .modelName("gpt-5")
                        .build())
                .systemMessage("""
                                                You are a helpful assistant.
                        Follow these skill instructions:
                        
                        %s
                        
                        """.formatted(loadResourceAsString("myskills/ducks/SKILL.md")))
                .build();
    }

    private String loadResourceAsString(String resourcePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Resource not found: " + resourcePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load resource: " + resourcePath, e);
        }
    }
}

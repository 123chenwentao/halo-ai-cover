package run.halo.starter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import run.halo.starter.config.AiCoverProperties;
import run.halo.starter.service.AiCoverService;

import java.util.HashMap;
import java.util.Map;

/**
 * AI 封面生成服务实现
 */
@Service
public class AiCoverServiceImpl implements AiCoverService {

    private final AiCoverProperties properties;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AiCoverServiceImpl(AiCoverProperties properties) {
        this.properties = properties;
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String generateCover(String content) {
        try {
            // 1. 分析文章内容，生成提示词
            String prompt = generatePrompt(content);
            
            // 2. 调用 AI API 生成图片
            String imageUrl = callAiApi(prompt);
            
            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            // 返回占位图
            return "https://via.placeholder.com/1792x1024?text=AI+Generated+Cover";
        }
    }

    /**
     * 根据文章内容生成提示词
     */
    private String generatePrompt(String content) {
        // 简单提取前200个字符作为摘要
        String摘要 = content.length() > 200 ? content.substring(0, 200) : content;
        
        // 生成提示词
        return String.format(
            "Generate a professional blog cover image for an article about: %s. " +
            "The image should be modern, clean, and visually appealing. " +
            "Use a color scheme that conveys professionalism and creativity.",
            摘要
        );
    }

    /**
     * 调用 AI API 生成图片
     */
    private String callAiApi(String prompt) throws Exception {
        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", properties.getModel());
        requestBody.put("prompt", prompt);
        requestBody.put("n", 1);
        requestBody.put("size", properties.getSize());

        // 发送请求
        String response = webClient.post()
            .uri(properties.getApiUrl())
            .header("Authorization", "Bearer " + properties.getApiKey())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        // 解析响应
        JsonNode root = objectMapper.readTree(response);
        JsonNode data = root.get("data");
        if (data != null && data.isArray() && data.size() > 0) {
            JsonNode firstImage = data.get(0);
            return firstImage.get("url").asText();
        }

        throw new RuntimeException("Failed to generate image from AI API");
    }
}

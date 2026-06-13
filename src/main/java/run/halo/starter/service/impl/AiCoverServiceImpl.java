package run.halo.starter.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import run.halo.starter.service.AiCoverService;
import run.halo.starter.service.PluginSettingService;

import java.util.HashMap;
import java.util.Map;

/**
 * AI 封面生成服务实现
 */
@Service
public class AiCoverServiceImpl implements AiCoverService {

    private final PluginSettingService settingService;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AiCoverServiceImpl(PluginSettingService settingService) {
        this.settingService = settingService;
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String generateCover(String content) {
        try {
            // 1. 获取配置
            String apiUrl = settingService.getApiUrl().block();
            String apiKey = settingService.getApiKey().block();
            String model = settingService.getModel().block();
            String size = settingService.getSize().block();

            // 2. 检查配置
            if (apiKey == null || apiKey.isEmpty()) {
                throw new RuntimeException("API Key 未配置");
            }

            // 3. 生成提示词
            String prompt = generatePrompt(content);

            // 4. 调用 AI API
            String imageUrl = callAiApi(apiUrl, apiKey, model, size, prompt);

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
        String summary = content.length() > 200 ? content.substring(0, 200) : content;
        
        // 生成提示词
        return String.format(
            "Generate a professional blog cover image for an article about: %s. " +
            "The image should be modern, clean, and visually appealing. " +
            "Use a color scheme that conveys professionalism and creativity.",
            summary
        );
    }

    /**
     * 调用 AI API 生成图片
     */
    private String callAiApi(String apiUrl, String apiKey, String model, String size, String prompt) throws Exception {
        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("n", 1);
        requestBody.put("size", size);

        // 发送请求
        String response = webClient.post()
            .uri(apiUrl)
            .header("Authorization", "Bearer " + apiKey)
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

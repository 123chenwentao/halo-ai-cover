package run.halo.starter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AI API 配置
 */
@Component
@ConfigurationProperties(prefix = "halo.ai.cover")
public class AiCoverProperties {

    /**
     * AI API 地址
     */
    private String apiUrl = "https://api.openai.com/v1/images/generations";

    /**
     * API Key
     */
    private String apiKey;

    /**
     * 图片模型
     */
    private String model = "dall-e-3";

    /**
     * 图片尺寸
     */
    private String size = "1792x1024";

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}

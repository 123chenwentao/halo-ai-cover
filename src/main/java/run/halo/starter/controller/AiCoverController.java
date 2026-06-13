package run.halo.starter.controller;

import org.springframework.web.bind.annotation.*;
import run.halo.starter.service.AiCoverService;
import run.halo.starter.service.PluginSettingService;
import reactor.core.publisher.Mono;

/**
 * AI 封面生成控制器
 */
@RestController
@RequestMapping("/apis/ai-cover")
public class AiCoverController {

    private final AiCoverService aiCoverService;
    private final PluginSettingService settingService;

    public AiCoverController(AiCoverService aiCoverService, PluginSettingService settingService) {
        this.aiCoverService = aiCoverService;
        this.settingService = settingService;
    }

    /**
     * 生成封面
     */
    @PostMapping("/generate")
    public Mono<GenerateResponse> generate(@RequestBody GenerateRequest request) {
        return Mono.fromCallable(() -> {
            String coverUrl = aiCoverService.generateCover(request.getContent());
            return new GenerateResponse(coverUrl);
        });
    }

    /**
     * 获取配置
     */
    @GetMapping("/settings")
    public Mono<SettingsResponse> getSettings() {
        return Mono.zip(
            settingService.getApiUrl(),
            settingService.getApiKey(),
            settingService.getModel(),
            settingService.getSize()
        ).map(tuple -> new SettingsResponse(
            tuple.getT1(),
            tuple.getT2(),
            tuple.getT3(),
            tuple.getT4()
        ));
    }

    /**
     * 保存配置
     */
    @PostMapping("/settings")
    public Mono<SettingsResponse> saveSettings(@RequestBody SettingsRequest request) {
        // TODO: 保存配置到 PluginSetting
        // 需要使用 PluginSettingManager 来保存
        return Mono.just(new SettingsResponse(
            request.getApiUrl(),
            request.getApiKey(),
            request.getModel(),
            request.getSize()
        ));
    }

    public static class GenerateRequest {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class GenerateResponse {
        private String coverUrl;

        public GenerateResponse(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }
    }

    public static class SettingsRequest {
        private String apiUrl;
        private String apiKey;
        private String model;
        private String size;

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

    public static class SettingsResponse {
        private String apiUrl;
        private String apiKey;
        private String model;
        private String size;

        public SettingsResponse(String apiUrl, String apiKey, String model, String size) {
            this.apiUrl = apiUrl;
            this.apiKey = apiKey;
            this.model = model;
            this.size = size;
        }

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
}

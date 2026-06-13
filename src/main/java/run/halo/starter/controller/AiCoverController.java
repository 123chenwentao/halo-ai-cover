package run.halo.starter.controller;

import org.springframework.web.bind.annotation.*;
import run.halo.starter.service.AiCoverService;

/**
 * AI 封面生成控制器
 */
@RestController
@RequestMapping("/apis/ai-cover")
public class AiCoverController {

    private final AiCoverService aiCoverService;

    public AiCoverController(AiCoverService aiCoverService) {
        this.aiCoverService = aiCoverService;
    }

    /**
     * 生成封面
     */
    @PostMapping("/generate")
    public GenerateResponse generate(@RequestBody GenerateRequest request) {
        String coverUrl = aiCoverService.generateCover(request.getContent());
        return new GenerateResponse(coverUrl);
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
}

package run.halo.starter.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import run.halo.app.content.PostContentService;
import run.halo.app.event.post.PostPublishedEvent;
import run.halo.app.event.post.PostUpdatedEvent;
import run.halo.starter.service.AiCoverService;

/**
 * 文章事件监听器
 * 当文章发布或更新时自动生成 AI 封面
 */
@Component
public class PostEventListener {

    private final AiCoverService aiCoverService;
    private final PostContentService postContentService;

    public PostEventListener(AiCoverService aiCoverService, PostContentService postContentService) {
        this.aiCoverService = aiCoverService;
        this.postContentService = postContentService;
    }

    /**
     * 监听文章发布事件
     */
    @EventListener
    public void onPostPublished(PostPublishedEvent event) {
        System.out.println("文章发布事件: " + event.getName());
        generateCoverForPost(event.getName());
    }

    /**
     * 监听文章更新事件
     */
    @EventListener
    public void onPostUpdated(PostUpdatedEvent event) {
        System.out.println("文章更新事件: " + event.getName());
        generateCoverForPost(event.getName());
    }

    /**
     * 为文章生成封面
     */
    private void generateCoverForPost(String postName) {
        try {
            // 获取文章内容
            postContentService.getHeadContent(postName)
                .subscribe(contentWrapper -> {
                    // 提取纯文本内容（去掉HTML标签）
                    String rawContent = contentWrapper.getRaw();
                    if (rawContent == null || rawContent.isEmpty()) {
                        rawContent = contentWrapper.getContent();
                    }
                    
                    // 生成封面
                    String coverUrl = aiCoverService.generateCover(rawContent);
                    System.out.println("为文章 " + postName + " 生成封面: " + coverUrl);
                    
                    // TODO: 更新文章封面
                    // 需要注入 PostService 来更新文章的 cover 字段
                });
        } catch (Exception e) {
            System.err.println("为文章生成封面失败: " + postName);
            e.printStackTrace();
        }
    }
}

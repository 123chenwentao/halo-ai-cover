package run.halo.starter.service;

/**
 * AI 封面生成服务接口
 */
public interface AiCoverService {
    /**
     * 根据文章内容生成封面图片 URL
     *
     * @param content 文章内容
     * @return 生成的封面图片 URL
     */
    String generateCover(String content);
}

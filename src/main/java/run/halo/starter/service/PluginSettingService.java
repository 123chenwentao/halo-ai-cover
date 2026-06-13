package run.halo.starter.service;

import run.halo.app.plugin.setting.PluginSetting;
import run.halo.app.plugin.setting.PluginSettingFetcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 插件设置服务
 */
@Service
public class PluginSettingService {

    private static final String SETTING_KEY_API_URL = "apiUrl";
    private static final String SETTING_KEY_API_KEY = "apiKey";
    private static final String SETTING_KEY_MODEL = "model";
    private static final String SETTING_KEY_SIZE = "size";

    private final PluginSettingFetcher settingFetcher;

    public PluginSettingService(PluginSettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    /**
     * 获取 API URL
     */
    public Mono<String> getApiUrl() {
        return settingFetcher.get(SETTING_KEY_API_URL, String.class)
            .map(PluginSetting.Value::getData)
            .defaultIfEmpty("https://api.openai.com/v1/images/generations");
    }

    /**
     * 获取 API Key
     */
    public Mono<String> getApiKey() {
        return settingFetcher.get(SETTING_KEY_API_KEY, String.class)
            .map(PluginSetting.Value::getData)
            .defaultIfEmpty("");
    }

    /**
     * 获取模型
     */
    public Mono<String> getModel() {
        return settingFetcher.get(SETTING_KEY_MODEL, String.class)
            .map(PluginSetting.Value::getData)
            .defaultIfEmpty("dall-e-3");
    }

    /**
     * 获取图片尺寸
     */
    public Mono<String> getSize() {
        return settingFetcher.get(SETTING_KEY_SIZE, String.class)
            .map(PluginSetting.Value::getData)
            .defaultIfEmpty("1792x1024");
    }
}

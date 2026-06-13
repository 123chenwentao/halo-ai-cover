package run.halo.starter;

import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

/**
 * Halo AI Cover Plugin
 * 根据文章内容自动生成 AI 封面
 */
@Component
public class StarterPlugin extends BasePlugin {

    public StarterPlugin(PluginContext pluginContext) {
        super(pluginContext);
    }

    @Override
    public void start() {
        System.out.println("Halo AI Cover 插件启动成功！");
    }

    @Override
    public void stop() {
        System.out.println("Halo AI Cover 插件停止！");
    }
}

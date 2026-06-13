# halo-ai-cover

Halo 2.x AI 封面生成器插件 - 根据文章内容自动生成 AI 封面图片。

## 功能特性

- 根据文章内容自动生成封面图片
- 支持多种 AI 绘图模型（DALL-E 3、DALL-E 2）
- 可配置 API 地址和密钥
- 支持多种图片尺寸

## 开发环境

插件开发的详细文档请查阅：<https://docs.halo.run/developer-guide/plugin/introduction>

所需环境：

1. JDK 21
2. Node 20
3. pnpm 9
4. Docker (可选)

克隆项目：

```bash
git clone https://github.com/123chenwentao/halo-ai-cover.git
```

```bash
cd path/to/halo-ai-cover
```

### 运行方式 1（推荐）

> 此方式需要本地安装 Docker

```bash
# macOS / Linux
./gradlew pnpmInstall

# Windows
./gradlew.bat pnpmInstall
```

```bash
# macOS / Linux
./gradlew haloServer

# Windows
./gradlew.bat haloServer
```

执行此命令后，会自动创建一个 Halo 的 Docker 容器并加载当前的插件，更多文档可查阅：<https://docs.halo.run/developer-guide/plugin/basics/devtools>

### 运行方式 2

> 此方式需要使用源码运行 Halo

编译插件：

```bash
# macOS / Linux
./gradlew build

# Windows
./gradlew.bat build
```

修改 Halo 配置文件：

```yaml
halo:
  plugin:
    runtime-mode: development
    fixedPluginPath:
      - "/path/to/halo-ai-cover"
```

最后重启 Halo 项目即可。

## 配置说明

在插件配置页面可以设置：

- **API 地址**: AI 绘图 API 的地址
- **API Key**: API 密钥
- **模型**: 选择 AI 绘图模型
- **图片尺寸**: 生成图片的尺寸

## 使用方法

1. 安装并启用插件
2. 在插件配置页面设置 API 信息
3. 编辑文章时，点击"生成 AI 封面"按钮
4. 插件会根据文章内容自动生成封面图片

## 许可证

[GPL-3.0](LICENSE)

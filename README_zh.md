# Carpet-SRU-Addition（地毯模组-SRU 附加包）

[![English](https://img.shields.io/badge/Language-English-blue)](README.md) [![中文](https://img.shields.io/badge/Language-中文-red)](README_zh.md)

一个地毯模组（Carpet Mod）的扩展包，为 Minecraft 1.21.1 添加各种实用功能，包括悦灵增强、可再生龙息和TNT动量控制等。

## 功能概览

本模组扩展提供以下地毯规则：

- **悦灵增强**：微调悦灵的行为，包括静音共振、投掷冷却和听觉距离
- **龙息可再生**：通过交互使龙息可以再生
- **TNT动量控制**：限制随机动量方向以实现精确放置
- **配方开关**：启用/禁用特定SRU配方
- **MCDR集成**：为MCDR命令启用制表符自动补全

## 地毯规则表

### 悦灵规则

| 规则名称 | 命令 | 类型 | 说明 | 默认值 |
|---------|------|------|------|--------|
| `allaySilentResonanceEnabled` | `/carpet allaySilentResonanceEnabled` | boolean | 悦灵即使在被方块遮挡的音符盒上方也会接收游戏事件 | `false` |
| `allayThrowCooldownTicks` | `/carpet allayThrowCooldownTicks <ticks>` | int | 悦灵投掷物品后的自定义拾取冷却时间。设为 -1 使用原版逻辑（60 ticks） | `-1` |
| `allayMaxHearingDistance` | `/carpet allayMaxHearingDistance <distance>` | int | 悦灵能听到音符盒的最大距离。设为 -1 使用原版逻辑（16 格） | `-1` |

### 龙息规则

| 规则名称 | 命令 | 类型 | 说明 | 默认值 |
|---------|------|------|------|--------|
| `renewableDragonBreath` | `/carpet renewableDragonBreath` | boolean | 通过龙蛋与玻璃瓶交互使龙息云可再生 | `false` |

### TNT规则

| 规则名称 | 命令 | 类型 | 说明 | 默认值 |
|---------|------|------|------|--------|
| `limitTntRandomMomentum` | `/carpet limitTntRandomMomentum [add\|clear\|query]` | string | 限制TNT在指定偏航角范围内的水平动量。存储基础角度（0-90°）的逗号分隔列表 | `""` |

**TNT命令详细说明：**
- `query` / 无参数：显示所有受限角度及其有效范围
- `add <0-90>`：添加基础角度限制
- `clear <0-90>`：移除基础角度限制
- `clear all`：清除所有限制

例子：
```
/carpet limitTntRandomMomentum add 0      # 限制主方向（北南东西）
/carpet limitTntRandomMomentum add 45     # 限制对角线方向
/carpet limitTntRandomMomentum            # 查询当前限制
/carpet limitTntRandomMomentum clear all  # 清除所有限制
```

### 其他规则

| 规则名称 | 命令 | 类型 | 说明 | 默认值 |
|---------|------|------|------|--------|
| `mcdrCommandAutoCompletion` | `/carpet mcdrCommandAutoCompletion` | boolean | 为聊天框中 !! 开头的 MCDR/Prime Backup 命令启用制表符自动补全 | `false` |

## 规则分类

规则按以下分类组织，便于在地毯设置菜单中筛选：
- **allay**：悦灵相关功能
- **sru**：SRU 附加包通用功能
- **TNT**：TNT相关功能

## 安装方式

1. 安装 [Fabric Loader](https://fabricmc.net/use)（Minecraft 1.21.1版本）
2. 安装 [Carpet Mod](https://www.curseforge.com/minecraft/mods/carpet)（兼容1.21.1的版本）
3. 从 [Releases](../../releases) 下载最新的 `carpet-sru-addition-*.jar`
4. 将jar文件放入 `mods` 文件夹
5. 启动服务器/游戏

## 开发 & 编译

开发环境设置请参考 [Fabric 文档](https://docs.fabricmc.net/develop/getting-started/creating-a-project#setting-up)。

### 从源代码构建

```bash
git clone <repository-url>
cd Carpet-SRU-Addition
./gradlew build
```

编译后的jar文件将位于 `build/libs/` 中

## 文档资源

- [TNT动量限制完整指南](LIMIT_TNT_RANDOM_MOMENTUM_GUIDE.md) - TNT动量限制的详细文档
- [示例和故障排除](EXAMPLES_AND_TROUBLESHOOTING.md) - 实用示例和解决方案
- [快速参考](QUICK_REFERENCE.md) - 快速命令参考
- [实现细节](IMPLEMENTATION_SUMMARY.md) - 技术实现信息

## 语言支持

本模组提供以下语言翻译：
- 🇬🇧 English（英文）
- 🇨🇳 简体中文

切换语言：[English](README.md)

## 许可证

本项目采用 CC0 许可证。欢迎学习和在自己的项目中使用。

## 鸣谢

- 基于 [Carpet Mod](https://github.com/gnembon/fabric-carpet)（由gnembon开发）
- 使用 [Fabric API](https://github.com/FabricMC/fabric-api)
- 兼容 Minecraft 1.21.1


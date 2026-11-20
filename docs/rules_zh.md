# Carpet 规则

[English](rules.md)

## allaySilentResonanceEnabled

允许悦灵在音符盒上方被方块遮挡时仍然接收该音符盒发出的游戏事件。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`Allay`、`sru`

## allayAiFreezeEnabled

允许空闲悦灵在附近没有可见玩家且没有绑定音符盒时进入睡眠状态，减少 AI 活动，直到需要再次响应。

- 类型：`boolean`
- 默认值：`false`
- 分类：`optimization`、`Allay`、`sru`

## allayThrowCooldownTicks

覆盖悦灵投掷物品后的拾取冷却时间。

设置为 `-1` 时使用原版逻辑，即 60 刻。

- 类型：`int`
- 默认值：`-1`
- 参考选项：`-1`、`20`、`60`、`120`
- 分类：`feature`、`Allay`、`sru`

## allayThrowCooldownFix

修复悦灵投掷冷却过短时，刚扔出的物品瞬间又被自己捡起的问题，使悦灵投掷出的物品在 3 秒（60 ticks）内对所有悦灵不可拾取。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`Allay`、`sru`

## allayMaxHearingDistance

覆盖悦灵能听到音符盒的最大距离。

设置为 `-1` 时使用原版逻辑，即 16 格。

- 类型：`int`
- 默认值：`-1`
- 参考选项：`-1`、`0`、`4`、`16`、`32`
- 分类：`feature`、`Allay`、`sru`

## renewableDragonBreath

使龙息可再生。

开启后，手持龙息与龙蛋交互会生成可收集的龙息云。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`sru`

## freezingTickNoStarve

当使用 `/tick freeze` 冻结游戏刻时，阻止 Carpet 假人受到饥饿伤害。

该规则只会在冻结游戏刻时取消 Carpet 假人的饥饿伤害；真人玩家和其它伤害类型不受影响。

- 类型：`boolean`
- 默认值：`false`
- 分类：`bugfix`、`sru`

## commandHat

启用 `/hat` 命令。移植自 Vulpeus Carpet，原功能来自 essential addons。

- 类型：`String`
- 默认值：`ops`
- 可用选项：`true`、`false`、`ops`、`0`、`1`、`2`、`3`、`4`
- 分类：`survival`、`command`、`sru`、`porting`

## commandSit

启用 `/sit` 命令。移植自 Vulpeus Carpet，原功能来自 PCA。

- 类型：`String`
- 默认值：`ops`
- 可用选项：`true`、`false`、`ops`、`0`、`1`、`2`、`3`、`4`
- 分类：`survival`、`command`、`sru`、`porting`

## commandSet

启用 `/set <scale>` 命令，使用原版 `minecraft:scale` 属性修改执行命令玩家自身的大小。

大小范围为 `0.0625` 到 `1.5`。

- 类型：`String`
- 默认值：`ops`
- 可用选项：`true`、`false`、`ops`、`0`、`1`、`2`、`3`、`4`
- 分类：`survival`、`command`、`sru`

## limitTntRandomMomentum

限制 TNT 被点燃时生成的随机水平动量方向。

使用 `/limitTntRandomMomentum add <angle>` 添加受限基础角度，`<angle>` 取值范围为 `0` 到 `90`。使用 `/limitTntRandomMomentum clear` 清空所有受限角度。直接运行 `/limitTntRandomMomentum` 可查看当前角度列表。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`sru`、`TNT`

## visibleSpectators

允许生存、创造和冒险模式的玩家看到旁观者模式下的玩家。。

该规则移植自 Vulpeus Carpet 的 `visibleSpectators` 规则，原规则来自 totos carpet tweaks。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`porting`

## superBoneMeal

允许骨粉让额外的可生长方块推进一个生长阶段。

`plants` 选项会影响原版骨粉通常无法催熟的植物类可生长方块，例如下界疣、甘蔗、仙人掌和紫颂花。`all` 选项还会影响紫水晶母岩、滴水石等非植物可生长方块。动物和其它实体不受影响。

- 类型：`String`
- 默认值：`false`
- 可用选项：`false`、`plants`、`all`
- 分类：`feature`、`sru`

## crashReportCarpetRules

将已修改的 Carpet 规则加入 Minecraft 崩溃报告，包括从配置文件加载的规则和只在内存中临时修改的规则。

每条规则都会标注来源模组，来源会根据 Carpet settings manager 或规则声明类推断。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`sru`

## 额外命令

### `/srurecipe enable`

启用本模组提供的~~not vanilla~~的配方。

### `/hat`

将主手中的 1 个物品装备到头部槽位。不允许装备不死图腾、非空潜影盒，也不能替换带有绑定诅咒的头部装备。

### `/sit`

让执行命令的玩家在当前位置坐下。

### `/set <scale>`

设置执行命令玩家自身的大小。允许范围为 `0.0625` 到 `1.5`。

别名：`/small` = `/set 0.5`，`/smallpro` = `/set 0.0625`，`/big` = `/set 1`，`/bigpro` = `/set 1.5`。

### `/log rule <规则名>`

为执行命令的玩家订阅或取消订阅某个 Carpet 规则的状态。玩家可以同时订阅多个规则。已订阅的规则会显示在该玩家 Tab 列表的页脚中，格式为`规则名（中文译名）：当前值`，规则值变化后会自动更新。

Tab 页脚中，颜色表示当前值是否为规则的默认值：绿色表示默认值，黄色表示非默认值。字体样式表示是否已永久保存：普通字体表示当前值已写入配置文件；斜体加下划线表示当前值只存在于内存中，重启后不会保留。默认值同样会根据是否写入配置文件显示对应的字体样式。

规则列表在 Carpet IGNY Addition 存在时会优先通过反射读取其可选的 `MOD_RULE_TREE` 字段；没有 IGNY 时则回退到 Carpet 已注册的 settings manager。IGNY 不会成为本模组的前置依赖。

# Carpet 规则

[English](rules.md)

## allaySilentResonanceEnabled

允许悦灵在音符盒上方被方块遮挡时仍然接收该音符盒发出的游戏事件。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`allay`、`sru`

## allayAiFreezeEnabled

允许空闲悦灵在附近没有可见玩家且没有绑定音符盒时进入深度睡眠状态，减少 AI 活动，直到需要再次响应。

- 类型：`boolean`
- 默认值：`false`
- 分类：`optimization`、`allay`、`sru`

## allayThrowCooldownTicks

覆盖悦灵投掷物品后的拾取冷却时间。

设置为 `-1` 时使用原版逻辑，即 60 刻。

- 类型：`int`
- 默认值：`-1`
- 参考选项：`-1`、`16`、`60`、`120`
- 分类：`feature`、`allay`、`sru`

## allayMaxHearingDistance

覆盖悦灵能听到音符盒的最大距离。

设置为 `-1` 时使用原版逻辑，即 16 格。

- 类型：`int`
- 默认值：`-1`
- 参考选项：`-1`、`0`、`4`、`16`、`32`
- 分类：`feature`、`allay`、`sru`

## renewableDragonBreath

使龙息可再生。

开启后，手持龙息与龙蛋交互会生成可收集的龙息云。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`sru`

## mcdrCommandAutoCompletion

为聊天框中以 `!!` 开头的常见 MCDR 和 Prime Backup 命令启用 Tab 补全。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`sru`

## limitTntRandomMomentum

限制 TNT 被点燃时生成的随机水平动量方向。

使用 `/limitTntRandomMomentum add <angle>` 添加受限基础角度，`<angle>` 取值范围为 `0` 到 `90`。使用 `/limitTntRandomMomentum clear` 清空所有受限角度。直接运行 `/limitTntRandomMomentum` 可查看当前角度列表。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`sru`、`TNT`

## visibleSpectators

允许生存、创造和冒险模式的玩家看到旁观者模式下的玩家，且旁观者显示为完全不透明的玩家头颅，而不是半透明浮空头颅。

- 类型：`boolean`
- 默认值：`false`
- 分类：`feature`、`porting`

## 额外命令

### `/srurecipe enable`

启用本模组提供的~~not vanilla~~的配方。

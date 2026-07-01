package carpetsruaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetsruaddition.command.HatCommand;
import carpetsruaddition.command.SRURecipeCommand;
import carpetsruaddition.command.LimitTntRandomMomentumCommand;
import carpetsruaddition.command.SitCommand;
import carpetsruaddition.command.SetCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;

import java.util.HashMap;
import java.util.Map;

public class CarpetSRUAdditionExtension implements CarpetExtension {
    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetSettings.class);
    }

    @Override
    public String version() {
        return CarpetSRUAddition.MOD_VERSION;
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandBuildContext) {
        SRURecipeCommand.register(dispatcher);
        LimitTntRandomMomentumCommand.register(dispatcher);
        HatCommand.register(dispatcher);
        SitCommand.register(dispatcher);
        SetCommand.register(dispatcher);
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        Map<String, String> translations = new HashMap<>();
        boolean zh = lang != null && lang.toLowerCase().startsWith("zh");

        translations.put("carpet.category.Allay", zh ? "悦灵" : "Allay");
        translations.put("carpet.category.SRU", "SRU");
        translations.put("carpet.category.TNT", "TNT");
        translations.put("carpet.category.porting", zh ? "移植" : "Porting");

        translations.put("carpet.rule.allaySilentResonanceEnabled.name", zh ? "悦灵静音共振" : "Allay Silent Resonance");
        translations.put("carpet.rule.allaySilentResonanceEnabled.desc", zh
            ? "开启后，即使音符盒上方被方块遮挡，悦灵仍会接收到该音符盒的游戏事件并响应。"
            : "If enabled, note blocks blocked by a block above will still emit the note block game event for Allays.");

        translations.put("carpet.rule.allayAiFreezeEnabled.name", zh ? "悦灵 AI 冻结" : "Allay AI Freeze");
        translations.put("carpet.rule.allayAiFreezeEnabled.desc", zh
            ? "当附近没有可见玩家或绑定音符盒时，冻结空闲悦灵的 AI。"
            : "Freezes idle Allay AI when no visible player or liked note block is nearby.");

        translations.put("carpet.rule.allayThrowCooldownTicks.name", zh ? "悦灵投掷冷却时间" : "Allay Throw Cooldown Ticks");
        translations.put("carpet.rule.allayThrowCooldownTicks.desc", zh
            ? "覆盖悦灵投掷物品后的拾取冷却时间。设为 -1 使用原版逻辑（60 ticks）。"
            : "Overrides the allay item pickup cooldown after throwing. Use -1 for vanilla (60 ticks).");

        translations.put("carpet.rule.allayThrowCooldownFix.name", zh ? "修改悦灵投掷冷却修复" : "Allay Throw Cooldown Fix");
        translations.put("carpet.rule.allayThrowCooldownFix.desc", zh
            ? "修复悦灵投掷冷却过短时，刚扔出的物品瞬间又被自己捡起的问题（使投掷出的物品在 3 秒/60 ticks 内对所有悦灵不可拾取）。"
            : "Prevents items thrown by Allays from being picked up by any Allay for 3 seconds (60 ticks), avoiding pickup loops when throw cooldown is short.");

        translations.put("carpet.rule.allayMaxHearingDistance.name", zh ? "悦灵最大听觉距离" : "Allay Max Hearing Distance");
        translations.put("carpet.rule.allayMaxHearingDistance.desc", zh
            ? "覆盖悦灵能听到音符盒的最大距离。设为 -1 使用原版逻辑（16 格）。"
            : "Overrides the maximum distance at which Allays can hear note blocks. Use -1 for vanilla (16 blocks).");

        translations.put("carpet.rule.renewableDragonBreath.name", zh ? "龙息可再生" : "Renewable Dragon Breath");
        translations.put("carpet.rule.renewableDragonBreath.desc", zh
            ? "开启后，可通过龙蛋与龙息瓶交互重新生成可收集的龙息云。"
            : "If enabled, dragon breath clouds become renewable via dragon egg interaction.");


        translations.put("carpet.rule.freezingTickNoStarve.name", zh ? "冻结刻假人免饥饿伤害" : "Freezing Tick No Starve");
        translations.put("carpet.rule.freezingTickNoStarve.desc", zh
            ? "开启后，/tick freeze 冻结游戏刻时 Carpet 假人不会受到饥饿伤害。"
            : "Prevents Carpet fake players from taking starvation damage while game ticks are frozen.");

        translations.put("carpet.rule.commandHat.name", zh ? "帽子命令" : "Hat Command");
        translations.put("carpet.rule.commandHat.desc", zh
            ? "启用 /hat 命令。移植自 Vulpeus Carpet，原功能来自 essential addons。"
            : "Enables the /hat command. Ported from Vulpeus Carpet, originally from essential addons.");

        translations.put("carpet.rule.commandSit.name", zh ? "坐下命令" : "Sit Command");
        translations.put("carpet.rule.commandSit.desc", zh
            ? "启用 /sit 命令。移植自 Vulpeus Carpet，原功能来自 PCA。"
            : "Enables the /sit command. Ported from Vulpeus Carpet, originally from PCA.");


        translations.put("carpet.rule.commandSet.name", zh ? "缩放命令" : "Set Scale Command");
        translations.put("carpet.rule.commandSet.desc", zh
            ? "启用 /set <scale> 命令，允许玩家修改自身大小。范围为原版最小 0.0625 到 1.5。"
            : "Enables /set <scale>, allowing players to change their own size. Range: vanilla minimum 0.0625 to 1.5.");

        translations.put("carpet.rule.limitTntRandomMomentum.name", zh ? "限制TNT随机动量" : "Limit TNT Random Momentum");
        translations.put("carpet.rule.limitTntRandomMomentum.desc", zh
            ? "启用后，限制TNT点燃时的随机动量。使用 /limitTntRandomMomentum add <0-90> 和 clear 子命令来管理受限角度。"
            : "When enabled, restrict random momentum of TNT when lit. Use /limitTntRandomMomentum add and clear subcommands to manage restricted angles.");

        translations.put("carpet.rule.visibleSpectators.name", zh ? "可见旁观者" : "Visible Spectators");
        translations.put("carpet.rule.visibleSpectators.desc", zh
            ? "允许生存、创造和冒险模式的玩家看到旁观者模式下的玩家，且旁观者显示为完全不透明的玩家头颅。"
            : "Allows survival, creative, and adventure players to see spectator players, rendered as fully opaque player heads.");

        translations.put("carpet.rule.superBoneMeal.name", zh ? "超级骨粉" : "Super Bone Meal");
        translations.put("carpet.rule.superBoneMeal.desc", zh
            ? "允许骨粉让额外的可生长方块推进一个生长阶段。plants 仅影响植物类方块；all 还会影响紫水晶母岩、滴水石等非植物可生长方块。"
            : "Allows bone meal to advance extra growable blocks by one growth step. plants affects plant-like blocks only; all also affects non-plant growable blocks such as budding amethyst and pointed dripstone.");

        translations.put("carpet.rule.crashReportCarpetRules.name", zh ? "崩溃报告记录 Carpet 规则" : "Crash Report Carpet Rules");
        translations.put("carpet.rule.crashReportCarpetRules.desc", zh
            ? "在 Minecraft 崩溃报告中加入已修改的 Carpet 规则及其来源模组。"
            : "Adds modified Carpet rules and their source mods to Minecraft crash reports.");

        return translations;
    }
}

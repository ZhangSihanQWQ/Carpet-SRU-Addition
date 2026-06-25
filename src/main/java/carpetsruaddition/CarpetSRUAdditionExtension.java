package carpetsruaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetsruaddition.command.SRURecipeCommand;
import carpetsruaddition.command.LimitTntRandomMomentumCommand;
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
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        Map<String, String> translations = new HashMap<>();
        boolean zh = lang != null && lang.toLowerCase().startsWith("zh");

        translations.put("carpet.category.allay", zh ? "悦灵" : "Allay");
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

        translations.put("carpet.rule.allayMaxHearingDistance.name", zh ? "悦灵最大听觉距离" : "Allay Max Hearing Distance");
        translations.put("carpet.rule.allayMaxHearingDistance.desc", zh
            ? "覆盖悦灵能听到音符盒的最大距离。设为 -1 使用原版逻辑（16 格）。"
            : "Overrides the maximum distance at which Allays can hear note blocks. Use -1 for vanilla (16 blocks).");

        translations.put("carpet.rule.renewableDragonBreath.name", zh ? "龙息可再生" : "Renewable Dragon Breath");
        translations.put("carpet.rule.renewableDragonBreath.desc", zh
            ? "开启后，可通过龙蛋与龙息瓶交互重新生成可收集的龙息云。"
            : "If enabled, dragon breath clouds become renewable via dragon egg interaction.");

        translations.put("carpet.rule.mcdrCommandAutoCompletion.name", zh ? "MCDR 命令自动补全" : "MCDR Command Auto Completion");
        translations.put("carpet.rule.mcdrCommandAutoCompletion.desc", zh
            ? "开启后，聊天框中以 !! 开头的 MCDR / Prime Backup 指令也能使用 Tab 自动补全。"
            : "If enabled, !!-prefixed MCDR / Prime Backup commands can use Tab completion in the chat box.");

        translations.put("carpet.rule.limitTntRandomMomentum.name", zh ? "限制TNT随机动量" : "Limit TNT Random Momentum");
        translations.put("carpet.rule.limitTntRandomMomentum.desc", zh
            ? "启用后，限制TNT点燃时的随机动量。使用 /limitTntRandomMomentum add <0-90> 和 clear 子命令来管理受限角度。"
            : "When enabled, restrict random momentum of TNT when lit. Use /limitTntRandomMomentum add and clear subcommands to manage restricted angles.");

        translations.put("carpet.rule.visibleSpectators.name", zh ? "可见旁观者" : "Visible Spectators");
        translations.put("carpet.rule.visibleSpectators.desc", zh
            ? "允许生存、创造和冒险模式的玩家看到旁观者模式下的玩家，且旁观者显示为完全不透明的玩家头颅。"
            : "Allows survival, creative, and adventure players to see spectator players, rendered as fully opaque player heads.");

        return translations;
    }
}

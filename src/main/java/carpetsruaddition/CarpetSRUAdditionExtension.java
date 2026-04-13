package carpetsruaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;

public class CarpetSRUAdditionExtension implements CarpetExtension {
    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetSettings.class);
    }

    @Override
    public String version() {
        return CarpetSRUAddition.MOD_VERSION;
    }
}


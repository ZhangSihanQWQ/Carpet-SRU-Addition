package carpetsruaddition;

import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarpetSRUAddition implements ModInitializer {
	public static final String MOD_ID = "carpet-sru-addition";
	public static final String MOD_NAME = "Carpet-SRU-Addition";
	public static final String MOD_VERSION = FabricLoader.getInstance()
		.getModContainer(MOD_ID)
		.map(container -> container.getMetadata().getVersion().getFriendlyString())
		.orElse("unknown");
	public static final CarpetSRUAdditionExtension EXTENSION = new CarpetSRUAdditionExtension();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CarpetServer.manageExtension(EXTENSION);
		LOGGER.info("{} v{} registered as a Carpet extension", MOD_NAME, MOD_VERSION);
	}
}
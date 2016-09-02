package me.hpt.EquiChests;

/**
 * Simple logger class
 */
public final class Logger {
	private static java.util.logging.Logger logger = null;

	private Logger(EquiChests plugin) {
		logger = plugin.getLogger();
	}

	public static void setLogger(java.util.logging.Logger logger) {
		Logger.logger = logger;
	}

	public static void debug(String msg, Object... args) {
		logger.info(String.format(msg, args));
	}

	public static void info(String msg, Object... args) {
		logger.info(String.format(msg, args));
	}

	public static void warn(String msg, Object... args) {
		logger.warning(String.format(msg, args));
	}

	public static void error(String msg, Object... args) {
		logger.severe(String.format(msg, args));
	}

	public static void fatal(String msg, Object... args) {
		logger.severe("============ FATAL ERROR. CANNOT RECOVER ============");
		logger.severe(String.format(msg, args));
		logger.severe("=====================================================");
		EquiChests.get().getServer().shutdown();
	}
}

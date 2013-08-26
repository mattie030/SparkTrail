package com.github.dsh105.sparktrail.config.options;

import com.github.dsh105.sparktrail.config.YAMLConfig;
import com.github.dsh105.sparktrail.particle.DisplayType;
import com.github.dsh105.sparktrail.particle.ParticleType;
import com.github.dsh105.sparktrail.util.EnumUtil;
import com.github.dsh105.sparktrail.util.StringUtil;

/**
 * Project by DSH105
 */

public class ConfigOptions extends Options{

	public static ConfigOptions instance;

	public ConfigOptions(YAMLConfig config) {
		super(config);
		instance = this;
		this.setMaxTick();
	}

	public int maxTick = 0;

	public void setMaxTick() {
		for (String key : config.getConfigurationSection("effects").getKeys(false)) {
			int i = config.getInt("effects." + key + ".frequency", 20);
			if (this.maxTick < i) {
				this.maxTick = i;
			}
		}

		for (int i = maxTick; i >= maxTick; i++) {
			if (i % 20 == 0) {
				maxTick = i;
				break;
			}
		}
	}

	public int getEffectFrequency(ParticleType particleType) {
		return config.getInt("effects." + StringUtil.capitalise(particleType.toString()) + ".frequency", 20);
	}

	public DisplayType getEffectDisplay(ParticleType particleType) {
		String s = config.getString("effects." + StringUtil.capitalise(particleType.toString()) + ".playType", "normal");
		if (EnumUtil.isEnumType(DisplayType.class, s.toUpperCase())) {
			DisplayType dt = DisplayType.valueOf(s.toUpperCase());
			if (dt != null) {
				return dt;
			}
		}
		return null;
	}

	@Override
	public void setDefaults() {
		set("command", "trail");

		set("autoUpdate", false);
		set("checkForUpdates", true);

		set("sql.overrideFile", true, "If true, data saved to the MySQL Database will override data saved to a file");
		set("sql.timeout", 30);
		set("sql.use", false);
		set("sql.host", "localhost");
		set("sql.port", 3306);
		set("sql.database", "SparkTrail");
		set("sql.username", "none");
		set("sql.password", "none");

		set("autosave", true, "If true, SparkTrail will autosave all pet data to prevent data", "loss in the event of a server crash.");
		set("autosaveTimer", 180, "AutoSave interval (in seconds)");

		for (ParticleType pt : ParticleType.values()) {
			String name = StringUtil.capitalise(pt.toString());
			set("effects." + name + ".enable", true);
			set("effects." + name + ".frequency", 20);
			set("effects." + name + ".playType", "normal");
		}
	}
}
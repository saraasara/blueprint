package com.teamabnormals.blueprint.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Class for storing all the information about the Blueprint config.
 *
 * @author SmellyModder(Luke Tonon)
 */
public final class BlueprintConfig {

	/**
	 * Class that stores all the information about the Blueprint common config.
	 */
	public static class Common {
		@ConfigKey("quark_poison_potato_compat_enabled")
		public final ConfigValue<Boolean> poisonPotatoCompatEnabledValue;
		public boolean poisonPotatoCompatEnabled;
		@ConfigKey("potato_poison_effect")
		public final ConfigValue<Boolean> poisonEffectValue;
		public boolean poisonEffect;
		@ConfigKey("potato_poison_chance")
		public final ConfigValue<Double> poisonChanceValue;
		public double poisonChance;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("Compatibility with Quark's poisonous potatoes feature")
			.push("poisonousPotatoCompat");
			this.poisonPotatoCompatEnabledValue = builder
					.comment("If baby mobs can be fed a poisonous potato to stunt their growth when Quark is installed")
					.translation(makeTranslation("poison_potato_compat_enabled"))
					.define("poisonPotatoCompatEnabled", true);

			this.poisonEffectValue = builder
					.comment("If growth stunting should give baby mobs poison")
					.translation(makeTranslation("poison_effect"))
					.define("poisonEffect", true);

			this.poisonChanceValue = builder
					.comment("The chance to stunt baby mob growth when feeding a poisonous potato")
					.translation(makeTranslation("poison_chance"))
					.defineInRange("poisonChance", 0.1, 0, 1);

			builder.pop();
		}

		/**
		 * Caches the common config values.
		 */
		public void load() {
			this.poisonPotatoCompatEnabled = this.poisonPotatoCompatEnabledValue.get();
			this.poisonEffect = this.poisonEffectValue.get();
			this.poisonChance = this.poisonChanceValue.get();
		}
	}

	/**
	 * Class that stores all the information about the Blueprint client config.
	 */
	public static final class Client {
		@ConfigKey("screen_shake_scale")
		public final ConfigValue<Double> screenShakeScaleValue;
		public double screenShakeScale;
		@ConfigKey("max_screen_shakers")
		public final ConfigValue<Integer> maxScreenShakersValue;
		public int maxScreenShakers;

		public final SlabfishSettings slabfishSettings;

		Client(ForgeConfigSpec.Builder builder) {
			this.screenShakeScaleValue = builder
					.comment("Scale for screen shake effects")
					.translation(makeTranslation("screen_shake_scale"))
					.defineInRange("screenShakeScale", 1.0D, 0.0D, 1.0D);

			this.maxScreenShakersValue = builder
					.comment("Max amount of sources that can contribute to screen shaking, adjustable for performance and preference reasons. 0 will disable the addition of shake sources")
					.translation(makeTranslation("max_screen_shakers"))
					.defineInRange("maxScreenShakers", 256, 0, Integer.MAX_VALUE);

			this.slabfishSettings = new SlabfishSettings(builder);
		}

		/**
		 * Caches some client config values.
		 */
		public void load() {
			this.screenShakeScale = this.screenShakeScaleValue.get();
			this.maxScreenShakers = this.maxScreenShakersValue.get();
		}
	}

	/**
	 * Class that stores all the information about the Slabfish Patreon Hat settings.
	 */
	public static final class SlabfishSettings {
		@ConfigKey("slabfish_hat_enabled")
		public final ConfigValue<Boolean> enabled;
		@ConfigKey("slabfish_hat_backpack_enabled")
		public final ConfigValue<Boolean> backpackEnabled;
		@ConfigKey("slabfish_hat_sweater_enabled")
		public final ConfigValue<Boolean> sweaterEnabled;
		@ConfigKey("slabfish_hat_custom_type_enabled")
		public final ConfigValue<Boolean> typeEnabled;

		SlabfishSettings(ForgeConfigSpec.Builder builder) {
			builder.comment("Slabfish patron hat settings.")
			.push("slabfishSettings");

			this.enabled = builder
					.comment("If the slabfish hat should be enabled")
					.translation(makeTranslation("slabfishHat"))
					.define("enabled", true);
			this.backpackEnabled = builder
					.comment("If the slabfish hat's backpack should be enabled")
					.translation(makeTranslation("slabfishHat.backpack"))
					.define("backpackEnabled", true);
			this.sweaterEnabled = builder
					.comment("If the slabfish hat's sweater should be enabled")
					.translation(makeTranslation("slabfishHat.sweater"))
					.define("sweaterEnabled", true);
			this.typeEnabled = builder
					.comment("If the slabfish hat's custom type should be enabled. If false, the default swamp slabfish appears")
					.translation(makeTranslation("slabfishHat.type"))
					.define("typeEnabled", true);

			builder.pop();
		}
	}
	
	private static String makeTranslation(String name) {
		return "blueprint.config." + name;
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();

		final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
	}

}
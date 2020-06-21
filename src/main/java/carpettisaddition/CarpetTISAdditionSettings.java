package carpettisaddition;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import java.util.regex.Pattern;

import static carpet.settings.RuleCategory.*;

/**
 * Here is your example Settings class you can plug to use carpetmod /carpet settings command
 */
public class CarpetTISAdditionSettings
{
    public static final String TIS = "TIS";

    @Rule(
            desc = "Set the range where player will receive a block event packet after a block event fires successfully",
            extra = "For piston the packet is used to render the piston movement animation. Decrease it to reduce client's lag",
            validate = ValidatorBlockEventPacketRange.class,
            options = {"0", "16", "64", "128"},
            strict = false,
            category = {TIS, OPTIMIZATION}
    )
    public static double blockEventPacketRange = 64;
	public static class ValidatorBlockEventPacketRange extends Validator<Double>
	{
		@Override
		public Double validate(ServerCommandSource source, ParsedRule<Double> currentRule, Double newValue, String string)
		{
			return 0 <= newValue && newValue <= 1024 ? newValue : null;
		}

		@Override
		public String description() { return "You must choose a value from 0 to 1024";}
	}


	@Rule(
			desc = "Overwrite the size limit of structure block",
			extra = "Relative position might display wrongly on client side if it's larger than 32",
			validate = ValidateStructureBlockLimit.class,
			options = {"32", "64", "96", "127"},
			strict = false,
			category = {TIS, CREATIVE}
	)
	public static int structureBlockLimit = 32;
	private static class ValidateStructureBlockLimit extends Validator<Integer>
	{
		@Override
		public Integer validate(ServerCommandSource source, ParsedRule<Integer> currentRule, Integer newValue, String string)
		{
			return (newValue > 0 && newValue <= 127) ? newValue : null;
		}
		public String description()
		{
			return "You must choose a value from 1 to 127";
		}
	}


	@Rule(
			desc = "Overwrite the tracking distance of xp orb",
			extra = "Change it to 0 to disable tracking",
			options = {"0", "1", "8", "32"},
			validate = ValidateXPTrackingDistance.class,
			strict = false,
			category = {TIS, CREATIVE}
	)
	public static double xpTrackingDistance = 8;
	private static class ValidateXPTrackingDistance extends Validator<Double>
	{
		@Override
		public Double validate(ServerCommandSource source, ParsedRule<Double> currentRule, Double newValue, String string)
		{
			return (newValue >= 0 && newValue <= 128) ? newValue : null;
		}
		public String description()
		{
			return "You must choose a value from 0 to 128";
		}
	}

	@Rule(
			desc = "Disable TNT, carpet and rail duping",
			extra = {
					"Attachment block update based dupers will do nothing and redstone component update based dupers can no longer keep their duped block",
					"Dupe bad dig good"
			},
			category = {TIS, BUGFIX, EXPERIMENTAL}
	)
	public static boolean tntDupingFix = false;

	public static final String fakePlayerNameNone = "#none";
	@Rule(
			desc = "Add a name prefix for fake players spawned with /player command",
			extra = {
					"Set it to " + fakePlayerNameNone + " to stop adding a prefix",
					"Which can prevent summoning fake player with illegal names and make player list look nicer"
			},
			options = {fakePlayerNameNone, "bot_"},
			validate = ValidateFakePlayerNamePrefix.class,
			strict = false,
			category = {TIS}
	)
	public static String fakePlayerNamePrefix = fakePlayerNameNone;
	private static class ValidateFakePlayerNamePrefix extends Validator<String>
	{
		@Override
		public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string)
		{
			return (newValue.equals(fakePlayerNameNone) || Pattern.matches("[a-zA-Z_0-9]{1,15}", newValue)) ? newValue : null;
		}
		public String description()
		{
			return "You must give a string without special characters and with a length from 1 to 16";
		}
	}

	@Rule(
			desc = "Make dragon egg renewable",
			extra = {
					"When a dragon egg is in dragon breath effect cloud it has a possibility to absorb the effect cloud and \"summon\" a new dragon egg",
					"Use with rule \"dispensersFireDragonBreath\" for more ease"
			},
			category = {TIS, FEATURE}
	)
	public static boolean renewableDragonEgg = false;

	@Rule(
			desc = "Dispenser can fire dragon breath bottle to create a dragon breath effect cloud",
			category = {TIS, FEATURE, DISPENSER}
	)
	public static boolean dispensersFireDragonBreath = false;
}
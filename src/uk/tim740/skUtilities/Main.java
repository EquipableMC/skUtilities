package uk.tim740.skUtilities;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import uk.tim740.skUtilities.convert.*;
import uk.tim740.skUtilities.util.*;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
        Skript.registerAddon(this);
        Skript.registerExpression(ExprBinDeConvert.class,String.class,ExpressionType.PROPERTY,"convert bin[ary] %string% to (0¦(text|string)|1¦decimal|2¦hexa[decimal]|3¦octal)");
        Skript.registerExpression(ExprBinConvert.class,String.class,ExpressionType.PROPERTY,"convert (0¦(text|string)|1¦decimal|2¦hexa[decimal]|3¦octal) %string% to bin[ary]");

        Skript.registerExpression(ExprBase64Encode.class,String.class,ExpressionType.PROPERTY,"encode %string% to base[ ]64 using (0¦utf-8|1¦ascii|2¦ISO-8859-1)");
        Skript.registerExpression(ExprBase64Decode.class,String.class,ExpressionType.PROPERTY,"decode %string% from base[ ]64 using (0¦utf-8|1¦ascii|2¦ISO-8859-1)");

        Skript.registerExpression(ExprMorseEncode.class,String.class,ExpressionType.PROPERTY,"encode %string% to morse[ code]");
        Skript.registerExpression(ExprMorseDecode.class,String.class,ExpressionType.PROPERTY,"decode %string% from morse[ code]");

        Skript.registerExpression(ExprAsciiToTxt.class,String.class,ExpressionType.PROPERTY,"convert ascii %number% to (text|string)");
        Skript.registerExpression(ExprTxtToAscii.class,String.class,ExpressionType.PROPERTY,"convert (text|string) %string% to ascii");
        Skript.registerExpression(ExprHexaToNum.class,String.class,ExpressionType.PROPERTY,"convert hexa[decimal] %string% to num[ber]");
        Skript.registerExpression(ExprNumToHexa.class,String.class,ExpressionType.PROPERTY,"convert num[ber] %string% to hexa[decimal]");
        Skript.registerExpression(ExprHexToRgb.class,String.class,ExpressionType.PROPERTY,"convert hex %string% to rgb");
        Skript.registerExpression(ExprRgbToHex.class,String.class,ExpressionType.PROPERTY,"convert rgb %number%, %number%, %number% to hex");
        Skript.registerExpression(ExprUnixToDate.class,String.class,ExpressionType.PROPERTY,"convert unix[ date] %string% to date[ formatted as %-string%]");
        Skript.registerExpression(ExprMirrorTxt.class,String.class,ExpressionType.PROPERTY,"mirror[ed] %string%");
        Bukkit.getServer().getLogger().info("[skUtilities] v" + Bukkit.getServer().getPluginManager().getPlugin("skUtilities").getDescription().getVersion() + " loaded conversions (50% loaded)!");

        Skript.registerExpression(ExprLoaded.class,Number.class,ExpressionType.PROPERTY,"number of[ loaded] (0¦(commands|cmds)|1¦functions|2¦s(c|k)ripts|3¦triggers|4¦statements|5¦variables|6¦aliases|7¦plugins|8¦addons|9¦events|10¦effects|11¦expressions|12¦conditions)");
        Skript.registerExpression(ExprGenerateTxt.class,String.class,ExpressionType.PROPERTY,"generate[ random] string with length %integer%");
        Skript.registerEffect(EffPacketTrial.class, "send[ fake] trial packet to %player%");
        Skript.registerEffect(EffRunScript.class, "run script at %string%");
        Skript.registerEffect(EffVillagerProfession.class, "spawn a %entity% with profession (0¦farmer|1¦librarian|2¦priest|3¦blacksmith|4¦butcher) at %location%");
        //Skript.registerEvent("CauldronLevelChange", SimpleEvent.class, CauldronLevelChangeEvent.class,"on cauldron[ water] level change");
        Bukkit.getServer().getLogger().info("[skUtilities] v" + Bukkit.getServer().getPluginManager().getPlugin("skUtilities").getDescription().getVersion() + " loaded util (100% loaded)!");
    }
    public static void prErr(String s) {
        Bukkit.getServer().getLogger().severe("[skUtilities] " + s);
    }
}

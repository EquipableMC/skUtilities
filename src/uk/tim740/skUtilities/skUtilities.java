package uk.tim740.skUtilities;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import uk.tim740.skUtilities.skript.effects.EffReloadConfig;

import java.io.File;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.Duration;

public class skUtilities extends JavaPlugin {
  
  private static skUtilities instance;
  private static SkriptAddon addonInstance;
  
  public skUtilities() {
    if (instance == null) {
      instance = this;
    } else {
      throw new IllegalStateException();
    }
  }
  
  public static skUtilities getInstance() {
    if (instance == null) {
      throw new IllegalStateException();
    }
    return instance;
  }
  
  public static SkriptAddon getAddonInstance() {
    if (addonInstance == null) {
      addonInstance = Skript.registerAddon(getInstance());
    }
    return addonInstance;
  }

  @Override
  public void onEnable() {

    int playerAmount = Bukkit.getOnlinePlayers().size();
    int onlineMode = Bukkit.getOnlineMode() ? 1 : 0;
    String bukkitVersion = Bukkit.getVersion();
    bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);

    String javaVersion = System.getProperty("java.version");
    String osName = System.getProperty("os.name");
    String osArch = System.getProperty("os.arch");
    String osVersion = System.getProperty("os.version");
    int coreCount = Runtime.getRuntime().availableProcessors();

    int pluginId = 22727;
    Metrics metrics = new Metrics(this, pluginId);


    long s = System.currentTimeMillis();
    if (!Skript.isAcceptRegistrations()) {
      getServer().getPluginManager().disablePlugin(this);
      getLogger().severe("skUtilities cannot be loaded when the server is already loaded! Plugin is disabled.");
      return;
    }
    
    saveDefaultConfig();
    if (!(getConfig().getInt("configVersion") == 9) || !(getConfig().isSet("configVersion"))) {
      try {
        Path pth = Paths.get(getDataFolder().getAbsolutePath() + File.separator + "config.yml");
        Path ptho = Paths.get(getDataFolder().getAbsolutePath() + File.separator + "config.old");
        if (Files.exists(ptho)) {
          Files.delete(ptho);
        }
        pth.toFile().renameTo(ptho.toFile());
        saveDefaultConfig();
        prSysI("");
        prSysI("You where using an old version of the config!");
        prSysI("It was copied and renamed to 'config.old'");
        prSysI("A new config has been generated!");
        prSysI("New config has reset to default options!");
        prSysI("");
      } catch (Exception x) {
        skUtilities.prSysE(x.getMessage(), getClass().getSimpleName(), x);
      }
    }
    String ls = "";
    try {
      if (getConfig().getBoolean("loadConversions", true)) {
        ls += "Conversions,";
        Reg.convert();
      }
      if (getConfig().getBoolean("loadFiles", true)) {
        ls += " Files,";
        Reg.files();
      }
      if (getConfig().getBoolean("loadYaml", true)) {
        ls += " Yaml,";
        Reg.yaml();
      }
      if (getConfig().getBoolean("loadUrls", true)) {
        ls += " Urls,";
        Reg.url();
      }
      if (getConfig().getBoolean("loadUtilities", true)) {
        ls += " Utilities";
        Reg.utils();
      }
  } catch (Exception e) {
      e.printStackTrace();
    }
    Skript.registerEffect(EffReloadConfig.class, "reload %string%'s config", "reload config of %string%");

    if (getConfig().getBoolean("checkForUpdates", true)) {
      Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
        boolean downloadUpdates = getConfig().getBoolean("downloadUpdates", false);
        boolean downloadChangelog = getConfig().getBoolean("downloadChangelog", false);
        boolean broadcastUpdates = getConfig().getBoolean("broadcastUpdates", true);
        UpdateChecker task = new UpdateChecker(this, broadcastUpdates, downloadUpdates, downloadChangelog);
        Bukkit.getScheduler().runTaskAsynchronously(this, task);
      }, 1L, Duration.ofHours(12).getSeconds());
    } else {
      prSysI("Updater is disabled, you should consider enabling it again!");
    }
    prSysI("loaded modules (" + ls + ") in " + (System.currentTimeMillis() - s) + "ms");
  }

  public static void prSysE(String s, String c) {
    Bukkit.getServer().getLogger().severe("[skUtilities] v" + getVer() + ": " + s + " (" + c + ".class)");
    if (Bukkit.getPluginManager().getPlugin("skUtilities").getConfig().getBoolean("broadcastErrors", true)) {
      Bukkit.broadcast(ChatColor.RED + "[skUtilities: WARN]" + ChatColor.GRAY + " v" + getVer() + ": " + s + " (" + c + ".class)", "skUtilities.error");
    }
  }

  public static void prSysE(String s, String c, Exception e) {
    if (Bukkit.getPluginManager().getPlugin("skUtilities").getConfig().getBoolean("debug", true)) {
      e.printStackTrace();
    } else {
      prSysE(s, c);
    }
  }

  public static void prSysI(String s) {
    Bukkit.getServer().getLogger().info("[skUtilities] v" + getVer() + ": " + s);
  }

  private static String getVer() {
    return Bukkit.getPluginManager().getPlugin("skUtilities").getDescription().getVersion();
  }

  public static String getFileSize(double i) {
    DecimalFormat df = new DecimalFormat("#.##");
    if (i < 1024) {
      return (i + " B").replaceFirst(".0", "");
    } else if (i < 1048576) {
      return df.format(i / 1024) + " KB";
    } else if (i < 1073741824) {
      return df.format(i / 1048576) + " MB";
    } else if (i < 1099511627776L) {
      return df.format(i / 1073741824) + " GB";
    } else {
      return df.format(i / 1099511627776L) + " TB";
    }
  }

  public static void downloadFile(Path pth, String url) {
    try {
      Files.copy(new URL(url).openStream(), pth);
    } catch (FileAlreadyExistsException x) {
      skUtilities.prSysE("File Already Exists: '" + pth + "' cannot download into a file that already exists!", "Utils", x);
    } catch (Exception x) {
      skUtilities.prSysE("Error downloading from: '" + url + "' Is the site down?", "Utils", x);
    }
  }

  public static String getDefaultPath(String pth) {
    if (!Bukkit.getPluginManager().getPlugin("skUtilities").getConfig().getBoolean("useRootAsDefaultPath", false)) {
      String dp = Paths.get("").normalize().toAbsolutePath().toString();
      if (pth.contains(dp)) {
        return (pth + File.separator);
      } else {
        return (dp + File.separator + pth);
      }
    } else {
      return pth;
    }
  }
}

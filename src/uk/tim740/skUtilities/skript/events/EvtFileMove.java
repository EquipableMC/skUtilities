package uk.tim740.skUtilities.skript.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.nio.file.Path;

/**
 * Created by tim740 on 22/03/2016
 */
public class EvtFileMove extends Event implements Cancellable {
  private static final HandlerList hls = new HandlerList();
  private boolean cancel = false;
  private Path file;
  private String mfile;

  public EvtFileMove(Path sfile, String smfile) {
    cancel = false;
    file = sfile;
    mfile = smfile;
  }

  public Path getEvtFile() {
    return file;
  }

  public String getEvtMFile() {
    return mfile;
  }

  public boolean isCancelled() {
    return cancel;
  }

  public void setCancelled(boolean c) {
    cancel = c;
  }

  @Override
  public HandlerList getHandlers() {
    return hls;
  }

  public static HandlerList getHandlerList() {
    return hls;
  }
}

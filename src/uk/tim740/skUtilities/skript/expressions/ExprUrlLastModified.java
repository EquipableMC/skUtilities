package uk.tim740.skUtilities.skript.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import uk.tim740.skUtilities.skUtilities;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tim740 on 15/10/2016
 */
public class ExprUrlLastModified extends SimpleExpression<Number> {
  private Expression<String> url;

  @Override
  @Nullable
  protected Number[] get(Event e) {
    try {
      HttpURLConnection.setFollowRedirects(false);
      HttpURLConnection c = (HttpURLConnection) new URL(url.getSingle(e)).openConnection();
      c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
      String n = String.valueOf(c.getLastModified());
      c.disconnect();
      return new Number[]{Long.valueOf(n.substring(0, 10))};
    } catch (Exception x) {
      skUtilities.prSysE("Error Reading from: '" + url.getSingle(e) + "' Is the site down?", getClass().getSimpleName(), x);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean init(Expression<?>[] e, int i, Kleenean k, ParseResult p) {
    url = (Expression<String>) e[0];
    return true;
  }

  @Override
  public Class<? extends Number> getReturnType() {
    return Number.class;
  }

  @Override
  public boolean isSingle() {
    return true;
  }

  @Override
  public String toString(@Nullable Event e, boolean b) {
    return getClass().getName();
  }
}

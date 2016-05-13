package uk.tim740.skUtilities.util;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

/**
 * Created by tim740 on 05/04/2016
 */
public class CondServerWhitelist extends Condition {
    private int ty;

    @Override
    public boolean check(Event arg0) {
        if (ty == 0){
            return Bukkit.hasWhitelist();
        }else{
            return !Bukkit.hasWhitelist();
        }
    }

    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
        ty = arg3.mark;
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return getClass().getName();
    }


}

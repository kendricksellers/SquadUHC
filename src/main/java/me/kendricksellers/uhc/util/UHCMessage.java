package me.kendricksellers.uhc.util;

import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/*
*
* Enum names must match key name in properties file
*
* */
public enum UHCMessage {
    TEST_MESSAGE,
    ANOTHER_ONE;

    
    Map<String, ResourceBundle> lang = new HashMap<>();
    public String get(Object... args) {
        return getLocale("en_US", args);
    }

    public String get(Player player,Object... args) {
        return getLocale(player.spigot().getLocale(), args);
    }

    public String getLocale(String locale, Object... args) {
        String base = "";
        if(lang.containsKey(locale)) {
            base = lang.get(locale).getString(this.name());
        } else {

        }
        ResourceBundle bundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag(locale));
        base = bundle.getString(this.name());

        base = base.replace('`', '\u00A7');
        return MessageFormat.format(base, args);
    }
}

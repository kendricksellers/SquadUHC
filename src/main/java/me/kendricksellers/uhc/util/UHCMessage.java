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
    ANVIL_USE_DENY,

    COMMAND_CHAT_ALIVE_ERR,
    COMMAND_CHAT_EMPTY,
    COMMAND_CHAT_OBS_ERR,
    COMMAND_CREATE_ON_TEAM,
    COMMAND_GENERATE_GENERATED,
    COMMAND_INVITE_INVITED,
    COMMAND_INVITE_NO_INVITE,
    COMMAND_INVITE_NO_TEAM,
    COMMAND_INVITE_PENDING,
    COMMAND_INVITE_PLAYER_OTHER_TEAM,
    COMMAND_INVITE_PLAYER_YOUR_TEAM,
    COMMAND_JOIN_JOINED,
    COMMAND_LEAVE_DISBAND,
    COMMAND_LEAVE_FAIL,
    COMMAND_LEAVE_SUCCESS,
    COMMAND_MISC_MATCH_STARTED,
    COMMAND_MISC_NOT_LEADER,
    COMMAND_MISC_PLAYER_NOT_FOUND,
    COMMAND_MISC_TEAM_LIMIT,
    COMMAND_MISC_TEAM_NOT_FOUND,
    COMMAND_NICK_NEW_NAME,
    COMMAND_TEAM_CREATE,
    COMMAND_TEAM_INVITED,
    COMMAND_TEAM_JOINED,
    COMMAND_TEAM_LEFT,
    COMMAND_TEAM_REVOKE,
    COMMAND_TEAM_REVOKE_NONE,

    WIN_MESSAGE;

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

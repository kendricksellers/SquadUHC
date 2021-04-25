package me.kendricksellers.uhc.module.modules.core;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import me.kendricksellers.uhc.module.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.state.PlayerState;
import me.kendricksellers.uhc.util.ChatChannel;
import me.kendricksellers.uhc.util.ChatUtils;
import me.kendricksellers.uhc.util.ItemUtils;
import me.kendricksellers.uhc.util.UHCPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatModule extends Module {

    public ChatModule() {
        setName("Chat");
        setType(ModuleType.CORE);
        setEmblem(ItemUtils.createItemStack(Material.SIGN, 1, getName()));
        setGUI(new ToggleModuleGUI(this));

        setEnabled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (isEnabled()) {
            try {
                PlayerModule playerModule = (PlayerModule) Match.getInstance().getModules().getModule("Player");
                UHCPlayer player = playerModule.getPlayer(event.getPlayer().getUniqueId());
                String message = event.getMessage();
                if (event.getPlayer().hasPermission("squaduhc.server.host")) {
                    ChatUtils.sendMessage(player, message, ChatChannel.GLOBAL);
                    event.setCancelled(true);
                    return;
                }
                PlayerState state = player.getState();
                if (Match.getInstance().isRunning()) {
                    if (state == PlayerState.DECEASED || state == PlayerState.OBSERVER) {
                        ChatUtils.sendMessage(player, message, ChatChannel.OBSERVER);
                        event.setCancelled(true);
                        return;
                    }
                }
                ChatUtils.sendMessage(player, message, ChatChannel.GLOBAL);
            } catch (ModuleNotFoundException e) {
                e.printStackTrace();
            }
            event.setCancelled(true);
        }
    }

}

package me.kendricksellers.uhc.module.modules.option;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.module.Module;
import me.kendricksellers.uhc.module.ModuleType;
import me.kendricksellers.uhc.module.gui.ToggleModuleGUI;
import me.kendricksellers.uhc.util.ItemUtils;
import org.bukkit.Material;

public class PermadayModule extends Module {

    public PermadayModule() {
        setName("Permaday");
        setType(ModuleType.OPTION);
        setEmblem(ItemUtils.createItemStack(Material.WATCH, 1, getName()));
        setGUI(new ToggleModuleGUI(this));

        setEnabled(false);
    }

    @Override
    public void enable() {
        Match.getInstance().getWorld().setTime(6000);
        Match.getInstance().getWorld().setGameRuleValue("doDaylightCycle", "false");
        super.enable();
    }

    @Override
    public void disable() {
        Match.getInstance().getWorld().setGameRuleValue("doDaylightCycle", "true");
        super.disable();
    }
}

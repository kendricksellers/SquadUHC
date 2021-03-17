package me.kendricksellers.uhc.module;

import me.kendricksellers.uhc.module.exception.ModuleNotFoundException;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class ModuleList<T extends Module> extends ArrayList<T> {

    // change to register and deregister events on enable and disable??

    @Override
    public boolean add(T t) {
        boolean res = super.add(t);
        Bukkit.getLogger().info(t.getName() + " loaded successfully");
        return res;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        boolean res = super.remove(o);
        T module = (T) o;
        Bukkit.getLogger().info(module.getName() + " unloaded");
        return res;
    }

    public Module getModule(String name) throws ModuleNotFoundException {
        if (this.contains(name)) {
            for (T t : this) {
                if (t.getName().equals(name)) {
                    return t;
                }
            }
        }
        throw new ModuleNotFoundException("Module - " + name + " - not found in ModuleList");
    }

    public boolean contains(String name) {
        for (T t : this) {
            if (t.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

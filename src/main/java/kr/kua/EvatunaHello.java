package kr.kua;

import kr.kua.listener.EventListenerBase;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public final class EvatunaHello extends Plugin {

    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new EventListenerBase(this));

        getLogger().info("EvaTunaHello Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("EvaTunaHello Disabled.");
    }
}
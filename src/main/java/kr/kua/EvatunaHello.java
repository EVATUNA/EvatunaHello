package kr.kua;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.kua.command.SearchCommand;
import kr.kua.listener.EventListenerBase;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class EvatunaHello extends Plugin {
    private Config config = null;

    @Override
    public void onEnable() {
        PluginManager pm = getProxy().getPluginManager();
        pm.registerListener(this, new EventListenerBase(this));

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SearchCommand());

        getLogger().info("EvaTunaHello Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("EvaTunaHello Disabled.");
    }
}

package kr.kua.listener;

import kr.kua.EvatunaHello;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class EventListenerBase implements Listener {

    private final EvatunaHello plugin;

    public EventListenerBase(EvatunaHello plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void ServerConnectEvent(final ServerConnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        if (event.getTarget().getName().equals("guest")) return;
        if (event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY) return;

        sendJoinMessage(ProxyServer.getInstance().getPlayers(), player.getName());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void PlayerDisconnectEvent(final PlayerDisconnectEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        if (event.getPlayer().getServer().getInfo().getName().equals("guest")) return;

        sendLeaveMessage(ProxyServer.getInstance().getPlayers(), player.getName());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void ServerSwitchEvent(final ServerSwitchEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        if (event.getFrom() == null) return;
        if (!event.getFrom().getName().equals("guest")) return;

        broadcastMessage(ProxyServer.getInstance().getPlayers(),
                "\ue4db " + ChatColor.of("#FF55FF") + player.getName() + "'s first time accessing the server! :)"
        );
    }

    private void broadcastMessage(final Iterable<ProxiedPlayer> players, final String content) {
        final BaseComponent[] message = TextComponent.fromLegacyText(content);
        for (final ProxiedPlayer player : players) {
            player.sendMessage(message);
        }
    }

    private void sendJoinMessage(final Iterable<ProxiedPlayer> players, final String name) {
        final BaseComponent[] message = TextComponent.fromLegacyText(
                        ChatColor.of("#AAAAAA") + "[" +
                        ChatColor.of("#55FF55") + "+" +
                        ChatColor.of("#AAAAAA") + "] " + name
        );

        for (final ProxiedPlayer player : players) {
            player.sendMessage(message);
        }
    }
    private void sendLeaveMessage(final Iterable<ProxiedPlayer> players, final String name) {
        final BaseComponent[] message = TextComponent.fromLegacyText(
                ChatColor.of("#AAAAAA") + "[" +
                        ChatColor.of("#FF5555") + "-" +
                        ChatColor.of("#AAAAAA") + "] " + name
        );

        for (final ProxiedPlayer player : players) {
            player.sendMessage(message);
        }
    }
}
